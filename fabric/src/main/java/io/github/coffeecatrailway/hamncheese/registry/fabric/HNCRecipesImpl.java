package io.github.coffeecatrailway.hamncheese.registry.fabric;

import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 19/05/2022
 */
public class HNCRecipesImpl
{
    public static Supplier<RecipeSerializer<?>> getPopcornSerializer()
    {
        return PopcornRecipe.Serializer::new;
    }

    public static Supplier<RecipeSerializer<?>> getChoppingBoardSerializer()
    {
        return ChoppingBoardRecipe.Serializer::new;
    }
}
