package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public class PizzaRecipe extends AbstractSandwichRecipe
{
    public PizzaRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.PIZZAS_COMMON, HNCItems.PIZZA);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.PIZZA_SERIALIZER.get();
    }

    @Nullable
    @Override
    public Item getNeededItem()
    {
        return HNCItems.TOMATO_SAUCE.get();
    }
}
