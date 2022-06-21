package io.github.coffeecatrailway.hamncheese.common.block.entity;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PizzaOvenContainer;
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
 * Created: 11/05/2021
 */
public class PizzaOvenBlockEntity extends FoodCookerBlockEntity<PizzaOvenBlockEntity>
{
    private static final int[] SLOTS_UP = new int[]{
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };
    private static final int[] SLOTS_DOWN = new int[]{12};
    private static final int[] SLOTS_HORIZONTAL = new int[]{9, 10, 11};

    public PizzaOvenBlockEntity(BlockPos pos, BlockState state)
    {
        super(HNCBlockEntities.PIZZA_OVEN.get(), pos, state, 13, HNCRecipes.PIZZA_OVEN_TYPE.get());
    }

    @Override
    public PizzaOvenBlockEntity getThis()
    {
        return this;
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container." + HamNCheese.MOD_ID + ".pizza_oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new PizzaOvenContainer(id, inventory, this, this.data);
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
        return HamNCheese.CONFIG_SERVER.pizzaOvenCookTimeTotal.get();
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
                ItemStack output = this.getItem(12);
                int combinedCount = output.getCount() + result.getCount();
                if (output.isEmpty() || (combinedCount <= this.getMaxStackSize() && combinedCount <= output.getMaxStackSize()))
                    return true;
                else if (!ItemStack.tagMatches(output, result))
                    return false;
                else
                    return combinedCount <= output.getMaxStackSize();
            }
        } else
            return false;
    }

    @Override
    protected void smeltRecipe(@Nullable Recipe<Container> recipe)
    {
        if (this.canSmelt(recipe))
        {
            ItemStack[] ingredients = new ItemStack[9];
            for (int i = 0; i < 9; i++)
                ingredients[i] = this.getItem(i);

            ItemStack result = recipe.assemble(this);
            ItemStack output = this.getItem(12);
            if (output.isEmpty())
                this.setItem(12, result.copy());
            else if (ItemStack.tagMatches(output, result))
                output.grow(result.getCount());

            if (this.hasLevel() && !this.level.isClientSide())
                this.setRecipeUsed(recipe);

            for (ItemStack ingredient : ingredients)
                if (!ingredient.isEmpty())
                    ingredient.shrink(1);
        }
    }
}
