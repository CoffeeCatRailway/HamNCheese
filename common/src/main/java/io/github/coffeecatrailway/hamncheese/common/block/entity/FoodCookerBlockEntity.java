package io.github.coffeecatrailway.hamncheese.common.block.entity;

import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import io.github.coffeecatrailway.hamncheese.common.block.GrillBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public abstract class FoodCookerBlockEntity extends BaseFoodMakerBlockEntity
{
    protected int burnTime;
    protected int burnTimeTotal;
    protected int cookTime;
    protected int cookTimeTotal;
    public final ContainerData data = new ContainerData()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return FoodCookerBlockEntity.this.burnTime;
                case 1:
                    return FoodCookerBlockEntity.this.burnTimeTotal;
                case 2:
                    return FoodCookerBlockEntity.this.cookTime;
                case 3:
                    return FoodCookerBlockEntity.this.cookTimeTotal;
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
                    FoodCookerBlockEntity.this.burnTime = value;
                    break;
                case 1:
                    FoodCookerBlockEntity.this.burnTimeTotal = value;
                    break;
                case 2:
                    FoodCookerBlockEntity.this.cookTime = value;
                    break;
                case 3:
                    FoodCookerBlockEntity.this.cookTimeTotal = value;
                    break;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    };

//    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public FoodCookerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, RecipeType<? extends Recipe<?>> recipeType)
    {
        super(type, pos, state, inventorySize, recipeType);
    }

    protected abstract int[] getTableSlots();

    protected abstract int[] getOutputSlots();

    protected abstract int[] getFuelSlots();

    protected abstract int getCookTimeTotal();

    protected abstract boolean canSmelt(@Nullable Recipe<Container> recipe);

    protected abstract void smeltRecipe(@Nullable Recipe<Container> recipe);

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
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());

        if (index == 0 && !flag)
        {
            this.cookTimeTotal = this.getCookTimeTotal();
            this.cookTime = 0;
            this.markUpdated();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FoodCookerBlockEntity blockEntity)
    {
        boolean isBurningFlag = blockEntity.isBurning();
        boolean flag = false;
        if (blockEntity.isBurning())
        {
            blockEntity.burnTime--;
            blockEntity.markUpdated();
        }

        if (level != null && !level.isClientSide())
        {
            ItemStack fuelStack = blockEntity.getNextFuelStack().getFirst();
            int fuelSlot = blockEntity.getNextFuelStack().getSecond();
            if (blockEntity.isBurning() || !fuelStack.isEmpty() && blockEntity.hasItems(0, blockEntity.getTableSlots().length))
            {
                Recipe<Container> recipe = level.getRecipeManager().getRecipeFor(blockEntity.recipeType, blockEntity, level).orElse(null);
                if (!blockEntity.isBurning() && blockEntity.canSmelt(recipe))
                {
                    blockEntity.burnTime = getBurnTime(fuelStack, blockEntity.recipeType);
                    blockEntity.burnTimeTotal = blockEntity.burnTime;
                    if (blockEntity.isBurning())
                    {
                        flag = true;
                        if (fuelStack.getItem().hasCraftingRemainingItem())
                        {
                            blockEntity.setItem(fuelSlot, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                            blockEntity.markUpdated();
                        } else
                        {
                            if (!fuelStack.isEmpty())
                            {
                                fuelStack.shrink(1);
                                if (fuelStack.isEmpty())
                                    blockEntity.setItem(fuelSlot, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                                blockEntity.markUpdated();
                            }
                        }
                    }
                }

                if (blockEntity.isBurning() && blockEntity.canSmelt(recipe))
                {
                    blockEntity.cookTime++;
                    if (blockEntity.cookTime >= blockEntity.cookTimeTotal)
                    {
                        blockEntity.smeltRecipe(recipe);
                        blockEntity.cookTime = 0;
                        blockEntity.cookTimeTotal = blockEntity.getCookTimeTotal();
                        flag = true;
                        blockEntity.markUpdated();
                    }
                } else
                {
                    blockEntity.cookTime = 0;
                    blockEntity.markUpdated();
                }
            } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0)
            {
                blockEntity.cookTime -= Mth.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
                blockEntity.markUpdated();
            }

            if (isBurningFlag != blockEntity.isBurning())
            {
                flag = true;
                level.setBlock(pos, level.getBlockState(pos).setValue(GrillBlock.LIT, blockEntity.isBurning()), 3);
            }
        }

        if (flag)
            blockEntity.markUpdated();
    }

    private boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.burnTime = compound.getInt("BurnTime");
        this.burnTimeTotal = getBurnTime(this.getNextFuelStack().getFirst(), this.recipeType);
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    private Pair<ItemStack, Integer> getNextFuelStack()
    {
        for (int i = this.getTableSlots().length; i < this.getTableSlots().length + this.getFuelSlots().length; i++)
            if (!this.getItem(i).isEmpty())
                return Pair.of(this.getItem(i), i);
        return Pair.of(ItemStack.EMPTY, this.getTableSlots().length);
    }

    @ExpectPlatform
    public static int getBurnTime(ItemStack fuelStack, @Nullable RecipeType<?> recipeType)
    {
        return Platform.error();
    }

//    @Override
//    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
//    {
//        if (!this.isRemoved() && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//        {
//            switch (side)
//            {
//                case UP:
//                    return this.handlers[0].cast();
//                case DOWN:
//                    return this.handlers[1].cast();
//                default:
//                    return this.handlers[2].cast();
//            }
//        }
//        return super.getCapability(cap, side);
//    }
}
