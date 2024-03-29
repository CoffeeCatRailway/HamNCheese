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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public abstract class FoodCookerBlockEntity<T extends FoodCookerBlockEntity<?>> extends BaseFoodMakerBlockEntity<T>
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
            return switch (index)
                    {
                        case 0 -> FoodCookerBlockEntity.this.burnTime;
                        case 1 -> FoodCookerBlockEntity.this.burnTimeTotal;
                        case 2 -> FoodCookerBlockEntity.this.cookTime;
                        case 3 -> FoodCookerBlockEntity.this.cookTimeTotal;
                        default -> 0;
                    };
        }

        @Override
        public void set(int index, int value)
        {
            switch (index)
            {
                case 0 -> FoodCookerBlockEntity.this.burnTime = value;
                case 1 -> FoodCookerBlockEntity.this.burnTimeTotal = value;
                case 2 -> FoodCookerBlockEntity.this.cookTime = value;
                case 3 -> FoodCookerBlockEntity.this.cookTimeTotal = value;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    };
    protected final RecipeType<Recipe<Container>> recipeType;

    public FoodCookerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, RecipeType<? extends Recipe<?>> recipeType)
    {
        super(type, pos, state, inventorySize);
        this.recipeType = (RecipeType<Recipe<Container>>) recipeType;
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
        return switch (direction)
                {
                    case DOWN -> this.getOutputSlots();
                    case UP -> this.getTableSlots();
                    default -> this.getFuelSlots();
                };
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

    public static <T extends FoodCookerBlockEntity<?>> void tick(Level level, BlockPos pos, BlockState state, FoodCookerBlockEntity<T> blockEntity)
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
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        this.burnTime = compoundTag.getInt("BurnTime");
        this.burnTimeTotal = getBurnTime(this.getNextFuelStack().getFirst(), this.recipeType);
        this.cookTime = compoundTag.getInt("CookTime");
        this.cookTimeTotal = compoundTag.getInt("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("BurnTime", this.burnTime);
        compoundTag.putInt("CookTime", this.cookTime);
        compoundTag.putInt("CookTimeTotal", this.cookTimeTotal);
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
}
