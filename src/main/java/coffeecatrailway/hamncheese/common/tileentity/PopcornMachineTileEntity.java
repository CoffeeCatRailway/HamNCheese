package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.PopcornMachineContainer;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 3/08/2021
 */
public class PopcornMachineTileEntity extends TickableLockableTileEntity
{
    private static final int[] SLOTS_KERNELS = new int[]{0};
    private static final int[] SLOTS_FLAVOUR = new int[]{1};
    private static final int[] SLOTS_SEASONING = new int[]{2};
    private static final int[] SLOTS_BAG = new int[]{3};
    private static final int[] SLOTS_DOWN = new int[]{4};

    private int flavourAmount;
    private int popcornAmount;
    public final IIntArray data = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return PopcornMachineTileEntity.this.flavourAmount;
                case 1:
                    return PopcornMachineTileEntity.this.popcornAmount;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value)
        {
            switch (index)
            {
                case 0:
                    PopcornMachineTileEntity.this.flavourAmount = value;
                    break;
                case 1:
                    PopcornMachineTileEntity.this.popcornAmount = value;
                    break;
            }
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    };

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.WEST, Direction.EAST);

    public PopcornMachineTileEntity()
    {
        super(HNCTileEntities.POPCORN_MACHINE.get(), 5, HNCRecipes.GRILL_TYPE); // TODO: CHANGE
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container." + HNCMod.MOD_ID + ".popcorn_machine");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory)
    {
        return new PopcornMachineContainer(id, playerInventory, this, this.data);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) // TODO: Change to relative facing
    {
        switch (direction)
        {
            case DOWN:
                return SLOTS_DOWN;
            default:
                return SLOTS_SEASONING;
        }
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.flavourAmount = compound.getInt("Flavour");
        this.popcornAmount = compound.getInt("Popcorn");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.putInt("Flavour", this.flavourAmount);
        compound.putInt("Popcorn", this.popcornAmount);
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (!this.isRemoved() && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            switch (side)
            {
                case DOWN:
                    return this.handlers[0].cast();
                case UP:
                    return this.handlers[1].cast();
                case WEST:
                    return this.handlers[3].cast();
                case EAST:
                    return this.handlers[4].cast();
                default:
                    return this.handlers[2].cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers) handler.invalidate();
    }
}
