package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.item.crafting.*;
import com.sun.org.apache.regexp.internal.RE;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
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

    public static final RegistryObject<PizzaOvenRecipeSerializer> PIZZA_OVEN_SERIALIZER = RECIPE_SERIALIZERS.register("pizza_oven", PizzaOvenRecipeSerializer::new);

    public static final RegistryObject<SpecialRecipeSerializer<SandwichRecipe>> SANDWICH_SERIALIZER = RECIPE_SERIALIZERS.register("sandwich", () -> new SpecialRecipeSerializer<>(SandwichRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<CrackerRecipe>> CRACKER_SERIALIZER = RECIPE_SERIALIZERS.register("cracker", () -> new SpecialRecipeSerializer<>(CrackerRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<PizzaRecipe>> PIZZA_SERIALIZER = RECIPE_SERIALIZERS.register("pizza", () -> new SpecialRecipeSerializer<>(PizzaRecipe::new));

    private static <R extends IRecipe<?>> IRecipeType<R> registerType(String id)
    {
        return Registry.register(Registry.RECIPE_TYPE, HNCMod.getLocation(id), new IRecipeType<R>()
        {
            @Override
            public String toString()
            {
                return id;
            }
        });
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        PIZZA_OVEN_TYPE = registerType("pizza_oven");
        RECIPE_SERIALIZERS.register(bus);
    }
}
