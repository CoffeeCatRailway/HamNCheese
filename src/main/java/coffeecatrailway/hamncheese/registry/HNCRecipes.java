package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.item.crafting.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public class HNCRecipes
{
    private static final Logger LOGGER = HNCMod.getLogger("Recipes");
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, HNCMod.MOD_ID);

    public static IRecipeType<PizzaOvenRecipe> PIZZA_OVEN_TYPE;
    public static IRecipeType<GrillRecipe> GRILL_TYPE;
    public static IRecipeType<PopcornRecipe> POPCORN_TYPE;

    // Blocks
    public static final RegistryObject<SpecialRecipeSerializer<PizzaOvenRecipe>> PIZZA_OVEN_SERIALIZER = RECIPE_SERIALIZERS.register("pizza_oven", () -> new SpecialRecipeSerializer<>(PizzaOvenRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<GrillRecipe>> GRILL_SERIALIZER = RECIPE_SERIALIZERS.register("grill", () -> new SpecialRecipeSerializer<>(GrillRecipe::new));
    public static final RegistryObject<PopcornRecipe.Serializer> POPCORN_SERIALIZER = RECIPE_SERIALIZERS.register("popcorn", PopcornRecipe.Serializer::new);

    // Crafting grid
    public static final RegistryObject<SpecialRecipeSerializer<SandwichRecipe>> SANDWICH_SERIALIZER = RECIPE_SERIALIZERS.register("sandwich", () -> new SpecialRecipeSerializer<>(SandwichRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<CrackerRecipe>> CRACKER_SERIALIZER = RECIPE_SERIALIZERS.register("cracker", () -> new SpecialRecipeSerializer<>(CrackerRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<PizzaRecipe>> PIZZA_SERIALIZER = RECIPE_SERIALIZERS.register("pizza", () -> new SpecialRecipeSerializer<>(PizzaRecipe::new));

    public static final RegistryObject<SpecialRecipeSerializer<MapleSyrupRecipe>> MAPLE_SYRUP_SERIALIZER = RECIPE_SERIALIZERS.register("maple_syrup", () -> new SpecialRecipeSerializer<>(MapleSyrupRecipe::new));

    private static <R extends IRecipe<?>> IRecipeType<R> registerType(String id)
    {
        return Registry.register(Registry.RECIPE_TYPE, HNCMod.getLocation(id), new IRecipeType<R>()
        {
            @Override
            public String toString()
            {
                return Registry.RECIPE_TYPE.getKey(this).toString();
            }
        });
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        PIZZA_OVEN_TYPE = registerType("pizza_oven");
        GRILL_TYPE = registerType("grill");
        POPCORN_TYPE = registerType("popcorn");
        RECIPE_SERIALIZERS.register(bus);
    }
}
