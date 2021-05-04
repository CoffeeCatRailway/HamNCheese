package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public class CrackerRecipe extends AbstractSandwichRecipe
{
    public CrackerRecipe(ResourceLocation id)
    {
        super(id, HNCItemTags.CRACKER, HNCItems.CRACKER);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.CRACKER_SERIALIZER.get();
    }
}
