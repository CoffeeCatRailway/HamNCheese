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
public class SandwichRecipe extends AbstractSandwichRecipe
{
    public SandwichRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.BREAD_SLICE, HNCItems.SANDWICH);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
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
