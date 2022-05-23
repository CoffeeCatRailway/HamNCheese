package io.github.coffeecatrailway.hamncheese.common.block.entity;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.PopcornMachineBlock;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PopcornMachineContainer;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 3/08/2021
 */
public class PopcornMachineBlockEntity extends BaseFoodMakerBlockEntity
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
    public final ContainerData data = new ContainerData()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return PopcornMachineBlockEntity.this.flavourTime;
                case 1:
                    return PopcornMachineBlockEntity.this.popcornAmount;
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
                    PopcornMachineBlockEntity.this.flavourTime = value;
                    break;
                case 1:
                    PopcornMachineBlockEntity.this.popcornAmount = value;
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

    public PopcornMachineBlockEntity(BlockPos pos, BlockState state)
    {
        super(HNCBlockEntities.POPCORN_MACHINE.get(), pos, state, 5, HNCRecipes.POPCORN_TYPE);
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed()
    {
        return this.recipe;
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container." + HamNCheese.MOD_ID + ".popcorn_machine");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory)
    {
        return new PopcornMachineContainer(id, playerInventory, this, this.data);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) // TODO: Change to relative facing
    {
        int slot = switch (direction)
                {
                    case DOWN -> SLOT_DOWN;
                    case UP -> SLOT_KERNELS;
                    case WEST -> SLOT_BAG;
                    case EAST -> SLOT_FLAVOURING;
                    default -> SLOT_SEASONING;
                };
        return new int[]{slot};
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PopcornMachineBlockEntity blockEntity)
    {
        boolean updateFlag = false;
        boolean popcornFlag = blockEntity.popcornAmount > 0;

        if (level != null && !level.isClientSide())
        {
            ItemStack kernelsStack = blockEntity.getItem(SLOT_KERNELS);
            if (kernelsStack.getItem().equals(HNCItems.DRIED_CORN_KERNELS.get()) && kernelsStack.getCount() > 0 && level.getGameTime() % 5L == 0L && blockEntity.popcornAmount + 50 <= MAX_POPCORN)
            {
                kernelsStack.shrink(1);
                blockEntity.popcornAmount += 50;
                blockEntity.markUpdated();
            }

            if (!blockEntity.getItem(SLOT_BAG).isEmpty() && !blockEntity.getItem(SLOT_SEASONING).isEmpty())
            {
                blockEntity.recipe = level.getRecipeManager().getRecipeFor(HNCRecipes.POPCORN_TYPE, blockEntity, level).orElse(null);
                if (blockEntity.canWork())
                {
                    blockEntity.flavourTime++;
                    if (blockEntity.flavourTime >= MAX_FLAVOUR_TIME)
                    {
                        ItemStack result = blockEntity.recipe.assemble(blockEntity);
                        ItemStack output = blockEntity.getItem(SLOT_DOWN);

                        if (output.isEmpty())
                            blockEntity.setItem(SLOT_DOWN, result.copy());
                        else if (output.getItem() == result.getItem())
                            output.grow(result.getCount());

                        blockEntity.setRecipeUsed(blockEntity.recipe);

                        blockEntity.popcornAmount -= blockEntity.recipe.getPopcorn();
                        ItemStack flavouring = blockEntity.getItem(SLOT_FLAVOURING);
                        if (flavouring.getItem().hasCraftingRemainingItem())
                            blockEntity.setItem(SLOT_FLAVOURING, new ItemStack(flavouring.getItem().getCraftingRemainingItem()));
                        else
                            flavouring.shrink(1);
                        blockEntity.getItem(SLOT_SEASONING).shrink(2);
                        blockEntity.getItem(SLOT_BAG).shrink(result.getCount());

                        blockEntity.flavourTime = 0;
                        updateFlag = true;
                    }
                    blockEntity.markUpdated();
                } else
                {
                    blockEntity.flavourTime = 0;
                    blockEntity.markUpdated();
                }
            } else if (blockEntity.flavourTime > 0)
            {
                blockEntity.flavourTime -= Mth.clamp(blockEntity.flavourTime - 2, 0, MAX_FLAVOUR_TIME);
                blockEntity.markUpdated();
            }

            if (popcornFlag != blockEntity.popcornAmount > 0)
            {
                updateFlag = true;
                level.setBlock(pos, state.setValue(PopcornMachineBlock.LIT, blockEntity.popcornAmount > 0), 3);
            }
        }

        if (updateFlag)
            blockEntity.markUpdated();
    }

    private boolean canWork()
    {
        if (this.recipe != null)
        {
            if (this.popcornAmount < this.recipe.getPopcorn())
                return false;
            ItemStack flavouring = this.getItem(SLOT_FLAVOURING);
            if ((!flavouring.isEmpty() && this.recipe.getFlavouring().isEmpty()) || !this.recipe.getFlavouring().test(flavouring) || (flavouring.getItem().hasCraftingRemainingItem() && flavouring.getCount() > 1))
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
                else if (!ItemStack.tagMatches(output, result))
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
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.flavourTime = compound.getInt("FlavourTime");
        this.popcornAmount = compound.getInt("Popcorn");
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putInt("FlavourTime", this.flavourTime);
        compound.putInt("Popcorn", this.popcornAmount);
    }
}
