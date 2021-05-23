package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public class PizzaRecipe extends AbstractSandwichRecipe
{
    public PizzaRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.PIZZA, HNCItems.PIZZA);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
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
