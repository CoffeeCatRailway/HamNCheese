package io.github.coffeecatrailway.hamncheese.registry.forge;

import io.github.coffeecatrailway.hamncheese.common.item.crafting.forge.ForgeChoppingBoardRecipeSerializer;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.forge.ForgePopcornRecipeSerializer;
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
        return ForgePopcornRecipeSerializer::new;
    }

    public static Supplier<RecipeSerializer<?>> getChoppingBoardSerializer()
    {
        return ForgeChoppingBoardRecipeSerializer::new;
    }
}
