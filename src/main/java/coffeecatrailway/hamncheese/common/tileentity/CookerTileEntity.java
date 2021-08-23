package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
@SuppressWarnings("unchecked")
public abstract class CookerTileEntity extends TickableLockableTileEntity
{
    protected int burnTime;
    protected int burnTimeTotal;
    protected int cookTime;
    protected int cookTimeTotal;
    public final IIntArray data = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return CookerTileEntity.this.burnTime;
                case 1:
                    return CookerTileEntity.this.burnTimeTotal;
                case 2:
                    return CookerTileEntity.this.cookTime;
                case 3:
                    return CookerTileEntity.this.cookTimeTotal;
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
                    CookerTileEntity.this.burnTime = value;
                    break;
                case 1:
                    CookerTileEntity.this.burnTimeTotal = value;
                    break;
                case 2:
                    CookerTileEntity.this.cookTime = value;
                    break;
                case 3:
                    CookerTileEntity.this.cookTimeTotal = value;
                    break;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    };

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public CookerTileEntity(TileEntityType<?> type, int inventorySize, IRecipeType<? extends IRecipe<?>> recipeType)
    {
        super(type, inventorySize, recipeType);
    }

    protected abstract int[] getTableSlots();

    protected abstract int[] getOutputSlots();

    protected abstract int[] getFuelSlots();

    protected abstract int getCookTimeTotal();

    protected abstract boolean canSmelt(@Nullable IRecipe<IInventory> iRecipe);

    protected abstract void smeltRecipe(@Nullable IRecipe<IInventory> iRecipe);

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        switch (direction)
        {
            case DOWN:
                return this.getOutputSlots();
            case UP:
                return this.getTableSlots();
            default:
                return this.getFuelSlots();
        }
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        ItemStack itemStack = this.getItem(index);
        boolean flag = !stack.isEmpty() && stack.sameItemStackIgnoreDurability(itemStack) && ItemStack.tagMatches(stack, itemStack);
        this.inventory.setStackInSlot(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());

        if (index == 0 && !flag)
        {
            this.cookTimeTotal = this.getCookTimeTotal();
            this.cookTime = 0;
            this.sendUpdates(this);
        }
    }

    @Override
    public void tick()
    {
        boolean isBurningFlag = this.isBurning();
        boolean flag = false;
        if (this.isBurning())
        {
            this.burnTime--;
            this.sendUpdates(this);
        }

        if (this.hasLevel() && !this.level.isClientSide())
        {
            ItemStack fuelStack = this.getNextFuelStack().getFirst();
            int fuelSlot = this.getNextFuelStack().getSecond();
            if (this.isBurning() || !fuelStack.isEmpty() && this.hasItems(0, this.getTableSlots().length))
            {
                IRecipe<IInventory> iRecipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
                if (!this.isBurning() && this.canSmelt(iRecipe))
                {
                    this.burnTime = getBurnTime(fuelStack);
                    this.burnTimeTotal = this.burnTime;
                    if (this.isBurning())
                    {
                        flag = true;
                        if (fuelStack.hasContainerItem())
                        {
                            this.setItem(fuelSlot, fuelStack.getContainerItem());
                            this.sendUpdates(this);
                        } else
                        {
                            if (!fuelStack.isEmpty())
                            {
                                fuelStack.shrink(1);
                                if (fuelStack.isEmpty())
                                    this.setItem(fuelSlot, fuelStack.getContainerItem());
                                this.sendUpdates(this);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(iRecipe))
                {
                    this.cookTime++;
                    if (this.cookTime >= this.cookTimeTotal)
                    {
                        this.smeltRecipe(iRecipe);
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTimeTotal();
                        flag = true;
                        this.sendUpdates(this);
                    }
                } else
                {
                    this.cookTime = 0;
                    this.sendUpdates(this);
                }
            } else if (!this.isBurning() && this.cookTime > 0)
            {
                this.cookTime -= MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                this.sendUpdates(this);
            }

            if (isBurningFlag != this.isBurning())
            {
                flag = true;
                this.level.setBlock(this.getBlockPos(), this.level.getBlockState(this.getBlockPos()).setValue(PizzaOvenBlock.LIT, this.isBurning()), Constants.BlockFlags.DEFAULT);
            }
        }

        if (flag)
            this.sendUpdates(this);
    }

    private boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.burnTime = compound.getInt("BurnTime");
        this.burnTimeTotal = getBurnTime(this.getNextFuelStack().getFirst());
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        return compound;
    }

    private Pair<ItemStack, Integer> getNextFuelStack()
    {
        for (int i = this.getTableSlots().length; i < this.getTableSlots().length + this.getFuelSlots().length; i++)
            if (!this.getItem(i).isEmpty())
                return Pair.of(this.getItem(i), i);
        return Pair.of(ItemStack.EMPTY, this.getTableSlots().length);
    }

    public static int getBurnTime(ItemStack fuelStack)
    {
        if (fuelStack.isEmpty())
            return 0;
        else
            return ForgeEventFactory.getItemBurnTime(fuelStack, fuelStack.getBurnTime() == -1 ? ForgeHooks.getBurnTime(fuelStack) : fuelStack.getBurnTime());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (!this.isRemoved() && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            switch (side)
            {
                case UP:
                    return this.handlers[0].cast();
                case DOWN:
                    return this.handlers[1].cast();
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
