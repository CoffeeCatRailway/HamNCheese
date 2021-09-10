package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PopcornMachineBlock;
import coffeecatrailway.hamncheese.common.inventory.PopcornMachineContainer;
import coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
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
    public static final int MAX_POPCORN = 500;
    public static final int MAX_FLAVOUR_TIME = 200;

    public static final int SLOT_KERNELS = 0;
    public static final int SLOT_FLAVOURING = 1;
    public static final int SLOT_SEASONING = 2;
    public static final int SLOT_BAG = 3;
    public static final int SLOT_DOWN = 4;

    private int flavourTime;
    private int popcornAmount;
    public final IIntArray data = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return PopcornMachineTileEntity.this.flavourTime;
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
                    PopcornMachineTileEntity.this.flavourTime = value;
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

    private PopcornRecipe recipe = null;

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.WEST, Direction.EAST);

    public PopcornMachineTileEntity()
    {
        super(HNCTileEntities.POPCORN_MACHINE.get(), 5, HNCRecipes.POPCORN_TYPE);
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed()
    {
        return this.recipe;
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
        int slot;
        switch (direction)
        {
            case DOWN:
                slot = SLOT_DOWN;
                break;
            case UP:
                slot = SLOT_KERNELS;
                break;
            case WEST:
                slot = SLOT_BAG;
                break;
            case EAST:
                slot = SLOT_FLAVOURING;
                break;
            default:
                slot = SLOT_SEASONING;
                break;
        }
        return new int[]{slot};
    }

    @Override
    public void tick()
    {
        boolean updateFlag = false;
        boolean popcornFlag = this.popcornAmount > 0;

        if (this.hasLevel() && !this.level.isClientSide())
        {
            ItemStack kernelsStack = this.getItem(SLOT_KERNELS);
            if (!kernelsStack.isEmpty() && this.level.getGameTime() % 5L == 0L && this.popcornAmount + 50 <= MAX_POPCORN)
            {
                kernelsStack.shrink(1);
                this.popcornAmount += 50;
            }

            if (!this.getItem(SLOT_BAG).isEmpty() && !this.getItem(SLOT_SEASONING).isEmpty())
            {
                this.recipe = this.level.getRecipeManager().getRecipeFor(HNCRecipes.POPCORN_TYPE, this, this.level).orElse(null);
                if (this.canWork())
                {
                    this.flavourTime++;
                    if (this.flavourTime >= MAX_FLAVOUR_TIME)
                    {
                        ItemStack result = this.recipe.assemble(this);
                        ItemStack output = this.getItem(SLOT_DOWN);

                        if (output.isEmpty())
                            this.setItem(SLOT_DOWN, result.copy());
                        else if (output.getItem() == result.getItem())
                            output.grow(result.getCount());

                        this.setRecipeUsed(this.recipe);

                        this.popcornAmount -= this.recipe.getPopcorn();
                        ItemStack flavouring = this.getItem(SLOT_FLAVOURING);
                        if (flavouring.hasContainerItem())
                            this.setItem(SLOT_FLAVOURING, flavouring.getContainerItem().copy());
                        else
                            flavouring.shrink(1);
                        this.getItem(SLOT_SEASONING).shrink(2);
                        this.getItem(SLOT_BAG).shrink(result.getCount());

                        this.flavourTime = 0;
                        updateFlag = true;
                        this.sendUpdates(this);
                    }
                } else
                {
                    this.flavourTime = 0;
                    this.sendUpdates(this);
                }
            } else if (this.flavourTime > 0)
            {
                this.flavourTime -= MathHelper.clamp(this.flavourTime - 2, 0, MAX_FLAVOUR_TIME);
                this.sendUpdates(this);
            }

            if (popcornFlag != this.popcornAmount > 0)
            {
                updateFlag = true;
                this.level.setBlock(this.getBlockPos(), this.level.getBlockState(this.getBlockPos()).setValue(PopcornMachineBlock.LIT, this.popcornAmount > 0), Constants.BlockFlags.DEFAULT);
            }
        }

        if (updateFlag)
            this.sendUpdates(this);
    }

    private boolean canWork()
    {
        if (this.recipe != null)
        {
            if (this.popcornAmount < this.recipe.getPopcorn())
                return false;
            ItemStack flavouring = this.getItem(SLOT_FLAVOURING);
            if ((!flavouring.isEmpty() && this.recipe.getFlavouring().isEmpty()) || !this.recipe.getFlavouring().test(flavouring) || (flavouring.hasContainerItem() && flavouring.getCount() > 1))
                return false;
            ItemStack seasoning = this.getItem(SLOT_SEASONING);
            if (seasoning.getCount() < 2 || !this.recipe.getSeasoning().test(seasoning))
                return false;
            if (this.getItem(SLOT_BAG).getCount() < this.recipe.getResultItem().getCount())
                return false;

            ItemStack result = this.recipe.assemble(this);
            if (result.isEmpty())
                return false;
            else
            {
                ItemStack output = this.getItem(SLOT_DOWN);
                int combinedCount = output.getCount() + result.getCount();
                if (!output.isEmpty() && output.getItem() != result.getItem())
                    return false;
                else if (output.isEmpty() || (combinedCount <= this.getMaxStackSize() && combinedCount <= output.getMaxStackSize()))
                    return true;
                else if (!output.areShareTagsEqual(result))
                    return false;
                else
                    return combinedCount <= output.getMaxStackSize();
            }
        } else
            return false;
    }

    public int getPopcorn()
    {
        return this.popcornAmount;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.flavourTime = compound.getInt("FlavourTime");
        this.popcornAmount = compound.getInt("Popcorn");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.putInt("FlavourTime", this.flavourTime);
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
