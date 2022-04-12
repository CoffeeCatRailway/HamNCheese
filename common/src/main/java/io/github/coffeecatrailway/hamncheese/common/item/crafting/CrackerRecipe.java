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
public class CrackerRecipe extends AbstractSandwichRecipe
{
    public CrackerRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.CRACKER_COMMON, HNCItems.CRACKER);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.CRACKER_SERIALIZER.get();
    }

    @Nullable
    @Override
    public Item getNeededItem()
    {
        return null;
    }
}
