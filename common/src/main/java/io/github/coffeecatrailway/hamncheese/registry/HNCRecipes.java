package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
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
    private static final PollinatedRegistry<RecipeType<?>> TYPES = PollinatedRegistry.create(Registry.RECIPE_TYPE, HamNCheese.MOD_ID);
    private static final PollinatedRegistry<RecipeSerializer<?>> SERIALIZERS = PollinatedRegistry.create(Registry.RECIPE_SERIALIZER, HamNCheese.MOD_ID);

    public static final Supplier<RecipeType<PizzaOvenRecipe>> PIZZA_OVEN_TYPE = register("pizza_oven");
    public static final Supplier<RecipeType<GrillRecipe>> GRILL_TYPE = register("grill");
    public static final Supplier<RecipeType<PopcornRecipe>> POPCORN_TYPE = register("popcorn");

    public static final Supplier<RecipeType<ChoppingBoardRecipe>> CHOPPING_BOARD_TYPE = register("chopping_board");

    // Blocks
    public static final Supplier<SimpleRecipeSerializer<PizzaOvenRecipe>> PIZZA_OVEN_SERIALIZER = SERIALIZERS.register("pizza_oven", () -> new SimpleRecipeSerializer<>(PizzaOvenRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<GrillRecipe>> GRILL_SERIALIZER = SERIALIZERS.register("grill", () -> new SimpleRecipeSerializer<>(GrillRecipe::new));
    public static final Supplier<RecipeSerializer<?>> POPCORN_SERIALIZER = SERIALIZERS.register("popcorn", getPopcornSerializer());

    public static final Supplier<RecipeSerializer<?>> CHOPPING_BOARD_SERIALIZER = SERIALIZERS.register("chopping_board", getChoppingBoardSerializer());

    // Crafting grid
    public static final Supplier<SimpleRecipeSerializer<SandwichRecipe>> SANDWICH_SERIALIZER = SERIALIZERS.register("sandwich", () -> new SimpleRecipeSerializer<>(SandwichRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<CrackerRecipe>> CRACKER_SERIALIZER = SERIALIZERS.register("cracker", () -> new SimpleRecipeSerializer<>(CrackerRecipe::new));
    public static final Supplier<SimpleRecipeSerializer<PizzaRecipe>> PIZZA_SERIALIZER = SERIALIZERS.register("pizza", () -> new SimpleRecipeSerializer<>(PizzaRecipe::new));

    public static final Supplier<SimpleRecipeSerializer<MapleSyrupRecipe>> MAPLE_SYRUP_SERIALIZER = SERIALIZERS.register("maple_syrup", () -> new SimpleRecipeSerializer<>(MapleSyrupRecipe::new));

    private static <R extends Recipe<?>> Supplier<RecipeType<R>> register(String id)
    {
        return TYPES.register(id, () -> new RecipeType<>()
        {
            @Override
            public String toString()
            {
                return Registry.RECIPE_TYPE.getKey(this).toString();
            }
        });
    }

    // Mod loader sided serializers
    @ExpectPlatform
    private static Supplier<RecipeSerializer<?>> getPopcornSerializer()
    {
        return Platform.error();
    }

    @ExpectPlatform
    private static Supplier<RecipeSerializer<?>> getChoppingBoardSerializer()
    {
        return Platform.error();
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        TYPES.register(platform);
        SERIALIZERS.register(platform);
    }
}
