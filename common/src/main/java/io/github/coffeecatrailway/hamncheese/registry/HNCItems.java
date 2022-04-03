package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.*;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCBlockTags;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 1/02/2021
 */
public class HNCItems
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, HamNCheese.MOD_ID);

    // Crafting Ingredients
    public static final Supplier<Item> WOODEN_GEAR = registerIdAsName("wooden_gear", Item::new);

    // Tools
    public static final Supplier<CraftingToolItem> CURDLER = registerIdAsName("curdler", getCraftingToolItem(2f, 2.5d, Tiers.WOOD, null));
    public static final Supplier<CraftingToolItem> ROLLING_PIN = registerIdAsName("rolling_pin", getCraftingToolItem(1f, 2.5d, Tiers.WOOD, null));
    public static final Supplier<CraftingToolItem> GRIND_STONES = registerIdAsName("grind_stones", getCraftingToolItem(2f, 2.7d, Tiers.STONE, null));
    public static final Supplier<CraftingToolItem> KNIFE = registerIdAsName("knife", getCraftingToolItem(1f, 2.5d, Tiers.IRON, HNCBlockTags.MINEABLE_WITH_KNIFE));

    // Foods
    public static final Supplier<Item> CHEESE_SLICE = registerIdAsName("cheese_slice", prop -> new Item(prop.food(HNCFoods.CHEESE_SLICE).stacksTo(32)));

    public static final Supplier<Item> ROCK_SALT = registerIdAsName("rock_salt", prop -> new Item(prop.food(HNCFoods.INGREDIENT)));
    public static final Supplier<Item> FLOUR = registerIdAsName("flour", prop -> new Item(prop.food(HNCFoods.INGREDIENT)));

    public static final Supplier<Item> DOUGH = registerIdAsName("dough", prop -> new Item(prop.food(HNCFoods.DOUGH)));

    public static final Supplier<Item> UNBAKED_PIZZA_BASE = registerIdAsName("unbaked_pizza_base", prop -> new Item(prop.food(HNCFoods.DOUGH).stacksTo(32)));
    public static final Supplier<Item> BAKED_PIZZA_DUMMY = registerWithName("baked_pizza_dummy", "Baked Pizza Base", prop -> new Item(prop.food(HNCFoods.BREAD_SLICE)));
    public static final Supplier<PizzaItem> PIZZA = registerIdAsName("pizza", getPizzaItem());

    public static final Supplier<Item> UNBAKED_BREAD = registerIdAsName("unbaked_bread", prop -> new Item(prop.food(HNCFoods.DOUGH)));
    public static final Supplier<Item> BREAD_SLICE = registerIdAsName("bread_slice", prop -> new Item(prop.food(HNCFoods.BREAD_SLICE)));
    public static final Supplier<Item> TOAST = registerIdAsName("toast", prop -> new Item(prop.food(HNCFoods.TOAST)));

    public static final Supplier<Item> UNBAKED_CRACKER = registerIdAsName("unbaked_cracker", prop -> new Item(prop.food(HNCFoods.DOUGH).stacksTo(16)));
    public static final Supplier<Item> CRACKER_DUMMY = registerIdAsName("cracker_dummy", prop -> new Item(prop.food(HNCFoods.CRACKER).tab(null)));
    public static final Supplier<CrackerItem> CRACKER = registerIdAsName("cracker", getCrackerItem());

    public static final Supplier<Item> CRACKED_EGG = registerIdAsName("cracked_egg", prop -> new Item(prop.food(HNCFoods.CRACKED_EGG).stacksTo(32)));
    public static final Supplier<Item> COOKED_EGG = registerIdAsName("cooked_egg", prop -> new Item(prop.food(HNCFoods.COOKED_EGG).stacksTo(32)));
    public static final Supplier<GreenFoodItem> GREEN_EGG = registerIdAsName("green_cracked_egg", prop -> new GreenFoodItem(prop, HNCFoods.GREEN_EGG, 32));

    public static final Supplier<Item> HAM_SLICE = registerWithName("ham_slice", "Ham Slice", prop -> new Item(prop.food(HNCFoods.HAM_SLICE).stacksTo(32)));
    public static final Supplier<Item> COOKED_HAM_SLICE = registerIdAsName("cooked_ham_slice", prop -> new Item(prop.food(HNCFoods.COOKED_HAM_SLICE).stacksTo(32)));
    public static final Supplier<GreenFoodItem> GREEN_HAM_SLICE = registerIdAsName("green_ham_slice", prop -> new GreenFoodItem(prop, HNCFoods.GREEN_HAM_SLICE, 32));

    public static final Supplier<Item> BACON = registerWithName("bacon", "Raw Bacon", prop -> new Item(prop.food(HNCFoods.BACON).stacksTo(32)));
    public static final Supplier<Item> COOKED_BACON = registerWithName("cooked_bacon", "Bacon", prop -> new Item(prop.food(HNCFoods.COOKED_BACON).stacksTo(32)));

    public static final Supplier<SandwichItem> SANDWICH = registerIdAsName("sandwich", getSandwichItem());

    //    public static final Supplier<BlockNamedItem> PINEAPPLE_PLANT = registerIdAsName("pineapple_plant", prop -> new BlockNamedItem(HNCBlocks.PINEAPPLE_PLANT.get(), prop));
    public static final Supplier<Item> PINEAPPLE = registerIdAsName("pineapple", prop -> new Item(prop.food(HNCFoods.PINEAPPLE).stacksTo(16)));
    public static final Supplier<Item> PINEAPPLE_RING = registerIdAsName("pineapple_ring", prop -> new Item(prop.food(HNCFoods.PINEAPPLE_RING).stacksTo(32)));
    public static final Supplier<Item> PINEAPPLE_BIT = registerIdAsName("pineapple_bit", prop -> new Item(prop.food(HNCFoods.PINEAPPLE_BIT).stacksTo(32)));

    //    public static final Supplier<BlockNamedItem> TOMATO_SEEDS = registerIdAsName("tomato_seeds", prop -> new BlockNamedItem(HNCBlocks.TOMATO_PLANT.get(), prop));
    public static final Supplier<Item> TOMATO = registerIdAsName("tomato", prop -> new Item(prop.food(HNCFoods.TOMATO).stacksTo(32)));
    public static final Supplier<Item> TOMATO_SAUCE = registerIdAsName("tomato_sauce", prop -> new Item(prop.food(HNCFoods.TOMATO_SAUCE).stacksTo(1)));
    public static final Supplier<Item> TOMATO_SLICE = registerIdAsName("tomato_slice", prop -> new Item(prop.food(HNCFoods.TOMATO_SLICE)));

    //    public static final Supplier<BlockNamedItem> CORN_COB = registerIdAsName("corn_cob", prop -> new BlockNamedItem(HNCBlocks.CORN_PLANT.get(), prop.food(HNCFoods.CORN_COB).stacksTo(32)));
    public static final Supplier<Item> CORN_KERNELS = registerIdAsName("corn_kernels", prop -> new Item(prop.food(HNCFoods.CORN_KERNELS)));
    public static final Supplier<Item> DRIED_CORN_KERNELS = registerIdAsName("dried_corn_kernels", prop -> new Item(prop.food(HNCFoods.CORN_KERNELS)));

    public static final Supplier<Item> POPCORN_BAG = registerIdAsName("popcorn_bag", Item::new);
    public static final Supplier<PopcornItem> POPCORN = registerIdAsName("popcorn", prop -> new PopcornItem(prop.food(HNCFoods.POPCORN)));
    public static final Supplier<PopcornItem> CHEESY_POPCORN = registerIdAsName("cheesy_popcorn", prop -> new PopcornItem(prop.food(HNCFoods.CHEESY_POPCORN)));
    public static final Supplier<PopcornItem> CARAMEL_POPCORN = registerIdAsName("caramel_popcorn", prop -> new PopcornItem(prop.food(HNCFoods.CARAMEL_POPCORN)));
    public static final Supplier<PopcornItem> MAPLE_POPCORN = registerIdAsName("maple_popcorn", prop -> new PopcornItem(prop.food(HNCFoods.MAPLE_POPCORN)));

    public static final Supplier<Item> MOUSE = registerIdAsName("mouse", prop -> new Item(prop.food(HNCFoods.MOUSE)));
    public static final Supplier<Item> COOKED_MOUSE = registerIdAsName("cooked_mouse", prop -> new Item(prop.food(HNCFoods.COOKED_MOUSE)));

    public static final Supplier<Item> FOOD_SCRAPS = registerIdAsName("food_scraps", prop -> new Item(prop.food(HNCFoods.FOOD_SCRAPS)));

    // Misc
//    public static final Supplier<HNCBoatItem> MAPLE_BOAT = registerIdAsName("maple_boat", prop -> new HNCBoatItem(HNCBoatEntity.ModType.MAPLE, prop));

    //    public static final Supplier<BucketItem> MAPLE_SAP_BUCKET = registerIdAsName("maple_sap_bucket", prop -> new BucketItem(HNCFluids.MAPLE_SAP, prop.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<FoodBottleItem> MAPLE_SAP_BOTTLE = registerIdAsName("maple_sap_bottle", prop -> new FoodBottleItem(prop.food(HNCFoods.MAPLE_SAP_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(1)));
    public static final Supplier<FoodBottleItem> MAPLE_SYRUP = registerIdAsName("maple_syrup", prop -> new FoodBottleItem(prop.food(HNCFoods.MAPLE_SYRUP).craftRemainder(Items.GLASS_BOTTLE).stacksTo(1)));

    protected static <T extends Item> Supplier<T> registerIdAsName(String id, Function<Item.Properties, T> factory)
    {
        return registerWithName(id, null, factory);
    }

    private static <T extends Item> Supplier<T> registerWithName(String id, @Nullable String name, Function<Item.Properties, T> factory)
    {
        return register(id, name, true, factory);
    }

    private static <T extends Item> Supplier<T> register(String id, @Nullable String name, boolean addLang, Function<Item.Properties, T> factory)
    {
        Supplier<T> object = ITEMS.register(id, () -> factory.apply(new Item.Properties().tab(HamNCheese.TAB)));
        if (addLang)
            HNCLanguage.ITEMS.put(object, name == null ? HNCLanguage.capitalize(id) : name);
        return object;
    }

    // Mod loader sided items
    @ExpectPlatform
    private static Function<Item.Properties, CraftingToolItem> getCraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable Tag<Block> mineableBlocks)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    private static Function<Item.Properties, PizzaItem> getPizzaItem()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    private static Function<Item.Properties, CrackerItem> getCrackerItem()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    private static Function<Item.Properties, SandwichItem> getSandwichItem()
    {
        throw new AssertionError();
    }

    // Load items
    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        ITEMS.register(platform);
    }
}
