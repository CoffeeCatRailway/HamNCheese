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
public class SandwichRecipe extends AbstractSandwichRecipe
{
    public SandwichRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.BREAD_SLICE_COMMON, HNCItems.SANDWICH);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.SANDWICH_SERIALIZER.get();
    }

    @Nullable
    @Override
    public Item getNeededItem()
    {
        return null;
    }
}
