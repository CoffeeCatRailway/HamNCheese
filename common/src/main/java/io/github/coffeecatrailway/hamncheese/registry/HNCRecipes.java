package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public class HNCRecipes
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PollinatedRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = PollinatedRegistry.create(Registry.RECIPE_SERIALIZER, HamNCheese.MOD_ID);

    public static RecipeType<PizzaOvenRecipe> PIZZA_OVEN_TYPE;
    public static RecipeType<GrillRecipe> GRILL_TYPE;
//    public static RecipeType<PopcornRecipe> POPCORN_TYPE;

    // Blocks
    public static final Supplier<SimpleRecipeSerializer<PizzaOvenRecipe>> PIZZA_OVEN_SERIALIZER = RECIPE_SERIALIZERS.register("pizza_oven", () -> new SimpleRecipeSerializer<>(PizzaOvenRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<GrillRecipe>> GRILL_SERIALIZER = RECIPE_SERIALIZERS.register("grill", () -> new SimpleRecipeSerializer<>(GrillRecipe::new));
//    public static final Supplier<PopcornRecipe.Serializer> POPCORN_SERIALIZER = RECIPE_SERIALIZERS.register("popcorn", PopcornRecipe.Serializer::new);

    // Crafting grid
    public static final Supplier<SimpleRecipeSerializer<SandwichRecipe>> SANDWICH_SERIALIZER = RECIPE_SERIALIZERS.register("sandwich", () -> new SimpleRecipeSerializer<>(SandwichRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<CrackerRecipe>> CRACKER_SERIALIZER = RECIPE_SERIALIZERS.register("cracker", () -> new SimpleRecipeSerializer<>(CrackerRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<PizzaRecipe>> PIZZA_SERIALIZER = RECIPE_SERIALIZERS.register("pizza", () -> new SimpleRecipeSerializer<>(PizzaRecipe::new));

    public static final Supplier<SimpleRecipeSerializer<MapleSyrupRecipe>> MAPLE_SYRUP_SERIALIZER = RECIPE_SERIALIZERS.register("maple_syrup", () -> new SimpleRecipeSerializer<>(MapleSyrupRecipe::new));

    private static <R extends Recipe<?>> RecipeType<R> registerType(String id)
    {
        return Registry.register(Registry.RECIPE_TYPE, HamNCheese.getLocation(id), new RecipeType<R>()
        {
            @Override
            public String toString()
            {
                return Registry.RECIPE_TYPE.getKey(this).toString();
            }
        });
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        PIZZA_OVEN_TYPE = registerType("pizza_oven");
        GRILL_TYPE = registerType("grill");
//        POPCORN_TYPE = registerType("popcorn");
        RECIPE_SERIALIZERS.register(platform);
    }
}
