package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * @author CoffeeCatRailway
 * Created: 10/06/2021
 */
public class GrillRecipe implements Recipe<Container>
{
    private final ResourceLocation id;

    public GrillRecipe(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public boolean matches(Container inventory, Level level)
    {
        for (int i = 0; i < 4; i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (SandwichItem.isUntoastedSandwich(stack))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container inventory)
    {
        for (int i = 0; i < 4; i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (SandwichItem.isUntoastedSandwich(stack))
            {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                copy.getOrCreateTag().putBoolean(SandwichItem.TAG_TOASTED, true);
                return copy;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(HNCBlocks.GRILL.get());
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.GRILL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return HNCRecipes.GRILL_TYPE.get();
    }
}
