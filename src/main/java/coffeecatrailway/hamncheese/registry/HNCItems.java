package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import coffeecatrailway.hamncheese.common.item.GreenFoodItem;
import coffeecatrailway.hamncheese.datagen.HNCLanguage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 1/02/2021
 */
public class HNCItems
{
    private static final Logger LOGGER = HNCMod.getLogger("Items");
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HNCMod.MOD_ID);

    /*
     * Crafting Ingredients
     */
    public static final RegistryObject<Item> WOODEN_GEAR = register("wooden_gear", Item::new);

    /*
     * Tools
     */
    public static final RegistryObject<CraftingToolItem> CURDLER = register("curdler", prop -> new CraftingToolItem(ItemTier.WOOD, 2, 2.5f, prop));
    public static final RegistryObject<CraftingToolItem> ROLLING_PIN = register("rolling_pin", prop -> new CraftingToolItem(ItemTier.WOOD, 1, 2.5f, prop));
    public static final RegistryObject<CraftingToolItem> GRIND_STONES = register("grind_stones", prop -> new CraftingToolItem(ItemTier.STONE, 2, 2.7f, prop));
    public static final RegistryObject<CraftingToolItem> KNIFE = register("knife", prop -> new CraftingToolItem(ItemTier.IRON, 1, 2.5f, prop));

    /*
     * Foods
     */
    public static final RegistryObject<Item> BLOCK_OF_CHEESE = register("block_of_cheese", prop -> new Item(prop.food(HNCFoods.BLOCK_OF_CHEESE).stacksTo(16)));
    public static final RegistryObject<Item> CHEESE_SLICE = register("cheese_slice", prop -> new Item(prop.food(HNCFoods.CHEESE_SLICE).stacksTo(32)));

    public static final RegistryObject<Item> ROCK_SALT = register("rock_salt", prop -> new Item(prop.food(HNCFoods.INGREDIENT)));
    public static final RegistryObject<Item> FLOUR = register("flour", prop -> new Item(prop.food(HNCFoods.INGREDIENT)));

    public static final RegistryObject<Item> DOUGH = register("dough", prop -> new Item(prop.food(HNCFoods.DOUGH)));
    public static final RegistryObject<Item> PIZZA_BASE = register("pizza_base", prop -> new Item(prop.food(HNCFoods.DOUGH).stacksTo(32)));

    public static final RegistryObject<Item> UNBAKED_BREAD = register("unbaked_bread", prop -> new Item(prop.food(HNCFoods.DOUGH)));
    public static final RegistryObject<Item> BREAD_SLICE = register("bread_slice", prop -> new Item(prop.food(HNCFoods.BREAD_SLICE)));
    public static final RegistryObject<Item> TOAST = register("toast", prop -> new Item(prop.food(HNCFoods.TOAST)));

    public static final RegistryObject<Item> UNBAKED_CRACKER = register("unbaked_cracker", prop -> new Item(prop.food(HNCFoods.DOUGH).stacksTo(16)));
    public static final RegistryObject<Item> CRACKER = register("cracker", prop -> new Item(prop.food(HNCFoods.CRACKER).stacksTo(16)));

    public static final RegistryObject<Item> CRACKED_EGG = register("cracked_egg", prop -> new Item(prop.food(HNCFoods.CRACKED_EGG).stacksTo(32)));
    public static final RegistryObject<Item> COOKED_EGG = register("cooked_egg", prop -> new Item(prop.food(HNCFoods.COOKED_EGG).stacksTo(32)));
    public static final RegistryObject<GreenFoodItem> GREEN_EGG = register("green_cracked_egg", prop -> new GreenFoodItem(prop, HNCFoods.GREEN_EGG, 32));

    public static final RegistryObject<Item> HAM_SLICE = register("ham_slice", "Ham Slice", prop -> new Item(prop.food(HNCFoods.HAM_SLICE).stacksTo(32)));
    public static final RegistryObject<Item> COOKED_HAM_SLICE = register("cooked_ham_slice", prop -> new Item(prop.food(HNCFoods.COOKED_HAM_SLICE).stacksTo(32)));
    public static final RegistryObject<GreenFoodItem> GREEN_HAM_SLICE = register("green_ham_slice", prop -> new GreenFoodItem(prop, HNCFoods.GREEN_HAM_SLICE, 32));

    public static final RegistryObject<Item> BACON = register("bacon", "Raw Bacon", prop -> new Item(prop.food(HNCFoods.BACON).stacksTo(32)));
    public static final RegistryObject<Item> COOKED_BACON = register("cooked_bacon", "Bacon", prop -> new Item(prop.food(HNCFoods.COOKED_BACON).stacksTo(32)));

    public static final RegistryObject<Item> PINEAPPLE = register("pineapple", prop -> new Item(prop.food(HNCFoods.PINEAPPLE).stacksTo(16))); // TODO: Add plant
    public static final RegistryObject<Item> PINEAPPLE_RING = register("pineapple_ring", prop -> new Item(prop.food(HNCFoods.PINEAPPLE_RING).stacksTo(32)));
    public static final RegistryObject<Item> PINEAPPLE_BIT = register("pineapple_bit", prop -> new Item(prop.food(HNCFoods.PINEAPPLE_BIT).stacksTo(32)));

    private static <T extends Item> RegistryObject<T> register(String id, Function<Item.Properties, T> factory)
    {
        return register(id, null, factory);
    }

    private static <T extends Item> RegistryObject<T> register(String id, @Nullable String name, Function<Item.Properties, T> factory)
    {
        Supplier<T> item = () -> factory.apply(new Item.Properties().tab(HNCMod.GROUP_ALL));
        RegistryObject<T> object = ITEMS.register(id, item);
        if (name == null)
            HNCLanguage.ITEMS.put(object, HNCLanguage.capitalize(id));
        else
            HNCLanguage.ITEMS.put(object, name);
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        ITEMS.register(bus);
    }
}
