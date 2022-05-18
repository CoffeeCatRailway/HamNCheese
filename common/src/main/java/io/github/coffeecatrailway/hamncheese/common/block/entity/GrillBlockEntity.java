package io.github.coffeecatrailway.hamncheese.common.block.entity;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.GrillContainer;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2021
 */
public class GrillBlockEntity extends FoodCookerBlockEntity
{
    private static final int[] SLOTS_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_DOWN = new int[]{6, 7, 8, 9};
    private static final int[] SLOTS_HORIZONTAL = new int[]{4, 5};

    public GrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(HNCBlockEntities.GRILL.get(), pos, state, 10, HNCRecipes.GRILL_TYPE);
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container." + HamNCheese.MOD_ID + ".grill");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new GrillContainer(id, inventory, this, this.data);
    }

    @Override
    protected int[] getTableSlots()
    {
        return SLOTS_UP;
    }

    @Override
    protected int[] getOutputSlots()
    {
        return SLOTS_DOWN;
    }

    @Override
    protected int[] getFuelSlots()
    {
        return SLOTS_HORIZONTAL;
    }

    @Override
    protected int getCookTimeTotal()
    {
        return HamNCheese.CONFIG_SERVER.grillCookTimeTotal.get();
    }

    @Override
    protected boolean canSmelt(@Nullable Recipe<Container> recipe)
    {
        if (recipe != null && this.hasItems(0, this.getTableSlots().length))
        {
            ItemStack result = recipe.assemble(this);
            if (result.isEmpty())
                return false;
            else
            {
                boolean ret = false;
                for (int i = 0; i < 4; i++)
                {
                    ItemStack outStack = this.getItem(i + 6);
                    if (outStack.isEmpty() || (outStack.getCount() + result.getCount() <= this.getMaxStackSize() && outStack.getCount() + result.getCount() <= outStack.getMaxStackSize()))
                        ret = true;
                    else if (!ItemStack.tagMatches(outStack, result))
                        ret = false;
                    else
                        ret = outStack.getCount() + result.getCount() <= outStack.getMaxStackSize();
                }
                return ret;
            }
        }
        return false;
    }

    @Override
    protected void smeltRecipe(@Nullable Recipe<Container> recipe)
    {
        for (int i = 0; i < 4; i++)
        {
            if (this.canSmelt(recipe))
            {
                ItemStack stack = this.getItem(i);
                if (SandwichItem.isUntoastedSandwich(stack))
                {
                    ItemStack result = recipe.assemble(this);
                    for (int j = 6; j < 10; j++)
                    {
                        ItemStack outStack = this.getItem(j);
                        if (outStack.isEmpty())
                        {
                            this.setItem(j, result.copy());
                            break;
                        } else if (ItemStack.tagMatches(outStack, result) && outStack.getCount() + result.getCount() <= this.getMaxStackSize() && outStack.getCount() + result.getCount() <= outStack.getMaxStackSize())
                        {
                            outStack.grow(1);
                            break;
                        }
                    }

                    if (this.hasLevel() && !this.level.isClientSide())
                        this.setRecipeUsed(recipe);

                    if (!stack.isEmpty())
                        stack.shrink(1);
                }
            }
        }
    }
}
