package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.tags.PollinatedItemTagsProvider;
import gg.moonflower.pollen.api.registry.resource.TagRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCItemTags extends PollinatedItemTagsProvider
{
    private static final TagKey<Item> GEARS_WOODEN_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "gears/wooden"));
    private static final TagKey<Item> GEARS_STONE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "gears/stone"));
    private static final TagKey<Item> GEARS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "gears"));
    private static final TagKey<Item> GEARS_WOODEN_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "wood_gears"));
    private static final TagKey<Item> GEARS_STONE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "stone_gears"));
    public static final TagKey<Item> GEARS_WOODEN_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("wood_gears"));
    public static final TagKey<Item> GEARS_STONE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("stone_gears"));

    public static final TagKey<Item> CURDLERS = TagRegistry.bindItem(HamNCheese.getLocation("curdlers"));
    public static final TagKey<Item> ROLLING_PINS = TagRegistry.bindItem(HamNCheese.getLocation("rolling_pins"));

    private static final TagKey<Item> WHEAT_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "crops/wheat"));
    private static final TagKey<Item> GRAIN_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "grain/wheat"));
    private static final TagKey<Item> GRAIN_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "grain"));
    public static final TagKey<Item> GRAIN_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("grain"));

    private static final TagKey<Item> SUGAR_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "sugar"));
    private static final TagKey<Item> SUGAR_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "sugar"));
    public static final TagKey<Item> SUGAR_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("sugar"));

    private static final TagKey<Item> CHEESE_SLICE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "cheese_slice"));
    private static final TagKey<Item> CHEESE_SLICE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cheese_slice"));
    public static final TagKey<Item> CHEESE_SLICE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("cheese_slice"));

    private static final TagKey<Item> CHEESE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "cheese"));
    private static final TagKey<Item> CHEESE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cheese"));
    public static final TagKey<Item> CHEESE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("cheese"));

    private static final TagKey<Item> SALT_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "salt"));
    private static final TagKey<Item> SALT_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "salt"));
    public static final TagKey<Item> SALT_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("salt"));

    private static final TagKey<Item> FLOUR_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "flour"));
    private static final TagKey<Item> FLOUR_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "flour"));
    public static final TagKey<Item> FLOUR_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("flour"));

    private static final TagKey<Item> DOUGH_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "dough"));
    private static final TagKey<Item> DOUGH_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "dough"));
    public static final TagKey<Item> DOUGH_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("dough"));

    private static final TagKey<Item> PIZZA_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "pizza"));
    private static final TagKey<Item> PIZZAS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "pizzas"));
    public static final TagKey<Item> PIZZA_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("pizza"));

    private static final TagKey<Item> BREAD_SLICE_FABRIC = TagRegistry.bindItem(new ResourceLocation("forge", "bread_slice"));
    private static final TagKey<Item> BREAD_SLICE_FORGE = TagRegistry.bindItem(new ResourceLocation("c", "bread_slice"));
    public static final TagKey<Item> BREAD_SLICE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("bread_slice"));

    private static final TagKey<Item> BREAD_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "bread"));
    private static final TagKey<Item> BREAD_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "sandwich/bread"));
    public static final TagKey<Item> BREAD_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("sandwich/bread"));

    private static final TagKey<Item> CRACKER_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "crackers"));
    private static final TagKey<Item> CRACKER_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cracker"));
    public static final TagKey<Item> CRACKER_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("cracker"));

    private static final TagKey<Item> EGGS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "eggs"));
    private static final TagKey<Item> EGGS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "eggs"));
    public static final TagKey<Item> EGGS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("eggs"));

    private static final TagKey<Item> CRACKED_EGG_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "cracked_eggs"));
    private static final TagKey<Item> COOKED_EGG_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cooked_eggs"));

    private static final TagKey<Item> HAM_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "ham"));
    private static final TagKey<Item> HAM_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "ham"));
    public static final TagKey<Item> HAM_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("ham"));

    private static final TagKey<Item> BACON_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "bacon"));
    private static final TagKey<Item> BACON_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "bacon"));
    public static final TagKey<Item> BACON_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("bacon"));

    private static final TagKey<Item> PINEAPPLE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "fruits/pineapple"));
    private static final TagKey<Item> PINEAPPLE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "fruits/pineapples"));
    public static final TagKey<Item> PINEAPPLE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("fruits/pineapples"));

    private static final TagKey<Item> TOMATO_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "fruits/tomato"));
    private static final TagKey<Item> TOMATO_FRUIT_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "fruits/tomato"));
    private static final TagKey<Item> TOMATO_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "vegetables/tomato"));
    public static final TagKey<Item> TOMATO_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("vegetables/tomato"));

    public static final TagKey<Item> TOMATO_SAUCE = TagRegistry.bindItem(new ResourceLocation("forge", "tomato_sauce"));

    private static final TagKey<Item> CORN_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "corn_cob"));
    private static final TagKey<Item> CORN_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "corn"));
    public static final TagKey<Item> CORN_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("corn"));

    public static final TagKey<Item> MOUSE_BLACKLIST = TagRegistry.bindItem(HamNCheese.getLocation("mouse_blacklist"));

    private static final TagKey<Item> FRUITS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "fruits"));
    private static final TagKey<Item> FRUITS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "fruits"));
    public static final TagKey<Item> FRUITS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("fruits"));

    private static final TagKey<Item> CROPS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "crops"));
    private static final TagKey<Item> CROPS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "crops"));
    public static final TagKey<Item> CROPS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("crops"));

    private static final TagKey<Item> CARBS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "carbs"));
    private static final TagKey<Item> CARBS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "carbs"));
    public static final TagKey<Item> CARBS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("carbs"));

    private static final TagKey<Item> SEEDS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "seeds"));
    private static final TagKey<Item> SEEDS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "seeds"));
    public static final TagKey<Item> SEEDS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("seeds"));

    public static final TagKey<Item> PINEAPPLE_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/pineapple"));
    public static final TagKey<Item> TOMATO_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/tomato"));
    public static final TagKey<Item> CORN_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/corn"));

    public static final TagKey<Item> CAMPFIRES = TagRegistry.bindItem(new ResourceLocation("forge", "campfires"));

    public static final TagKey<Item> JEI_FOOD_BLACKLIST = TagRegistry.bindItem(HamNCheese.getLocation("jei_food_blacklist"));

    public static final TagKey<Item> MAPLE_LOGS = TagRegistry.bindItem(HamNCheese.getLocation("maple_logs"));

    private static final TagKey<Item> RODS_WOODEN_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "rods/wooden"));
    private static final TagKey<Item> RODS_WOODEN_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "wooden_rods"));
    private static final TagKey<Item> WOODEN_STICKS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "wood_sticks"));
    public static final TagKey<Item> WOODEN_STICKS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("wooden_sticks"));

    private static final TagKey<Item> COBBLESTONE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "cobblestone"));
    private static final TagKey<Item> STONE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "stone"));
    private static final TagKey<Item> COBBLESTONES_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cobblestones"));
    private static final TagKey<Item> COBBLESTONE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "cobblestone"));
    private static final TagKey<Item> STONE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "stones"));
    public static final TagKey<Item> STONE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("stones"));

    private static final TagKey<Item> IRON_INGOTS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/iron"));
    private static final TagKey<Item> IRON_INGOTS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "iron_ingots"));
    public static final TagKey<Item> IRON_INGOTS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("iron_ingots"));

    private static final TagKey<Item> IRON_NUGGETS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "nuggets/iron"));
    private static final TagKey<Item> IRON_NUGGETS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "iron_nuggets"));
    public static final TagKey<Item> IRON_NUGGETS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("iron_nuggets"));

    private static final TagKey<Item> COPPER_INGOTS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/copper"));
    private static final TagKey<Item> COPPER_INGOTS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "copper_ingots"));
    public static final TagKey<Item> COPPER_INGOTS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("copper_ingots"));

    private static final TagKey<Item> GOLD_INGOTS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/gold"));
    private static final TagKey<Item> GOLD_INGOTS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "gold_ingots"));
    public static final TagKey<Item> GOLD_INGOTS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("gold_ingots"));

    private static final TagKey<Item> DIAMONDS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "gems/diamond"));
    private static final TagKey<Item> DIAMONDS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "diamonds"));
    public static final TagKey<Item> DIAMONDS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("diamonds"));

    private static final TagKey<Item> GREEN_DYE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "dyes/green"));
    private static final TagKey<Item> GREEN_DYE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "green_dye"));
    private static final TagKey<Item> GREEN_DYES_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "green_dyes"));
    public static final TagKey<Item> GREEN_DYES_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("green_dyes"));

    private static final TagKey<Item> RED_DYE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "dyes/red"));
    private static final TagKey<Item> RED_DYE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "red_dye"));
    private static final TagKey<Item> RED_DYES_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "red_dyes"));
    public static final TagKey<Item> RED_DYES_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("red_dyes"));

    private static final TagKey<Item> REDSTONE_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "dusts/redstone"));
    private static final TagKey<Item> REDSTONE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "redstone_dusts"));
    public static final TagKey<Item> REDSTONE_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("redstone_dusts"));

    private static final TagKey<Item> KNIVES_WOOD = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/wooden"));
    private static final TagKey<Item> KNIVES_STONE = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/stone"));
    private static final TagKey<Item> KNIVES_COPPER = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/copper"));
    private static final TagKey<Item> KNIVES_GOLD = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/golden"));
    private static final TagKey<Item> KNIVES_IRON = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/iron"));
    private static final TagKey<Item> KNIVES_DIAMOND = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/diamond"));
    private static final TagKey<Item> KNIVES_NETHERITE = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives/netherite"));
    private static final TagKey<Item> KNIVES_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "tools/knives"));
    private static final TagKey<Item> KNIVES_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "knives"));
    public static final TagKey<Item> KNIVES_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("knives"));

    private static final TagKey<Item> SHEARS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "shears"));
    private static final TagKey<Item> SHEARS_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "shears"));
    public static final TagKey<Item> SHEARS_COMMON = TagRegistry.bindItem(HamNCheese.getLocation("shears"));

    public static final TagKey<Item> CHOPPING_BOARDS = TagRegistry.bindItem(HamNCheese.getLocation("chopping_boards"));

    public HNCItemTags(DataGenerator dataGenerator, PollinatedModContainer container, BlockTagsProvider blockTags)
    {
        super(dataGenerator, container, blockTags);
    }

    @Override
    protected void addTags()
    {
        this.tag(GEARS_WOODEN_FORGE).add(HNCItems.WOODEN_GEAR.get());
        this.tag(GEARS_STONE_FORGE).add(HNCItems.STONE_GEAR.get());
        this.tag(GEARS_WOODEN_FABRIC).add(HNCItems.WOODEN_GEAR.get());
        this.tag(GEARS_STONE_FABRIC).add(HNCItems.STONE_GEAR.get());
        this.tag(GEARS_FORGE).addTag(GEARS_WOODEN_FORGE, GEARS_STONE_FORGE, GEARS_WOODEN_FABRIC, GEARS_STONE_FABRIC);
        this.tag(GEARS_WOODEN_COMMON).addTag(GEARS_WOODEN_FORGE, GEARS_WOODEN_FABRIC);
        this.tag(GEARS_STONE_COMMON).addTag(GEARS_STONE_FORGE, GEARS_STONE_FABRIC);

        this.tag(CURDLERS).add(HNCItems.WOODEN_CURDLER.get(), HNCItems.STONE_CURDLER.get());
        this.tag(ROLLING_PINS).add(HNCItems.WOODEN_ROLLING_PIN.get(), HNCItems.STONE_ROLLING_PIN.get());

        this.tag(WHEAT_FORGE).add(Items.WHEAT);
        this.tag(GRAIN_FORGE).add(Items.WHEAT);
        this.tag(GRAIN_FABRIC).add(Items.WHEAT);
        this.tag(GRAIN_COMMON).addTag(WHEAT_FORGE, GRAIN_FORGE, GRAIN_FABRIC);

        this.tag(SUGAR_FORGE).add(Items.SUGAR);
        this.tag(SUGAR_FABRIC).add(Items.SUGAR);
        this.tag(SUGAR_COMMON).addTag(SUGAR_FORGE, SUGAR_FABRIC);

        this.tag(CHEESE_SLICE_FORGE).add(HNCItems.CHEESE_SLICE.get(), HNCItems.BLUE_CHEESE_SLICE.get(), HNCItems.GOUDA_CHEESE_SLICE.get(), HNCItems.SWISS_CHEESE_SLICE.get(), HNCItems.GOAT_CHEESE_SLICE.get());
        this.tag(CHEESE_SLICE_FABRIC).add(HNCItems.CHEESE_SLICE.get(), HNCItems.BLUE_CHEESE_SLICE.get(), HNCItems.GOUDA_CHEESE_SLICE.get(), HNCItems.SWISS_CHEESE_SLICE.get(), HNCItems.GOAT_CHEESE_SLICE.get());
        this.tag(CHEESE_SLICE_COMMON).addTag(CHEESE_SLICE_FORGE, CHEESE_SLICE_FABRIC);

        this.tag(CHEESE_FORGE).add(HNCItems.SWISS_CHEESE_BITS.get(), HNCBlocks.BLOCK_OF_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_BLUE_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_SWISS_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOAT_CHEESE.get().asItem());
        this.tag(CHEESE_FABRIC).add(HNCItems.SWISS_CHEESE_BITS.get(), HNCBlocks.BLOCK_OF_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_BLUE_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_SWISS_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOAT_CHEESE.get().asItem());
        this.tag(CHEESE_COMMON).addTag(CHEESE_FORGE, CHEESE_FABRIC, CHEESE_SLICE_COMMON);

        this.tag(SALT_FORGE).add(HNCItems.ROCK_SALT.get());
        this.tag(SALT_FABRIC).add(HNCItems.ROCK_SALT.get());
        this.tag(SALT_COMMON).addTag(SALT_FORGE, SALT_FABRIC);

        this.tag(FLOUR_FORGE).add(HNCItems.FLOUR.get());
        this.tag(FLOUR_FABRIC).add(HNCItems.FLOUR.get());
        this.tag(FLOUR_COMMON).addTag(FLOUR_FORGE, FLOUR_FABRIC);

        this.tag(DOUGH_FORGE).add(HNCItems.DOUGH.get(), HNCItems.UNBAKED_PIZZA_BASE.get(), HNCItems.UNBAKED_CROISSANT.get());
        this.tag(DOUGH_FABRIC).add(HNCItems.DOUGH.get(), HNCItems.UNBAKED_PIZZA_BASE.get(), HNCItems.UNBAKED_CROISSANT.get());
        this.tag(DOUGH_COMMON).addTag(DOUGH_FORGE, DOUGH_FABRIC);

        this.tag(PIZZA_FORGE).add(HNCItems.UNBAKED_PIZZA_BASE.get());
        this.tag(PIZZAS_FABRIC).add(HNCItems.UNBAKED_PIZZA_BASE.get());
        this.tag(PIZZA_COMMON).addTag(PIZZA_FORGE, PIZZAS_FABRIC);

        this.tag(BREAD_SLICE_FORGE).add(HNCItems.BREAD_SLICE.get());
        this.tag(BREAD_SLICE_FABRIC).add(HNCItems.BREAD_SLICE.get());
        this.tag(BREAD_SLICE_COMMON).addTag(BREAD_SLICE_FORGE, BREAD_SLICE_FABRIC);

        this.tag(BREAD_FORGE).add(Items.BREAD);
        this.tag(BREAD_FABRIC).add(Items.BREAD);
        this.tag(BREAD_COMMON).addTag(BREAD_FORGE, BREAD_FABRIC);

        this.tag(CRACKER_FORGE).add(HNCItems.CRACKER.get());
        this.tag(CRACKER_FABRIC).add(HNCItems.CRACKER.get());
        this.tag(CRACKER_COMMON).addTag(CRACKER_FORGE, CRACKER_FABRIC);

        this.tag(EGGS_FORGE).add(HNCItems.CRACKED_EGG.get(), HNCItems.COOKED_EGG.get(), HNCItems.GREEN_EGG.get());
        this.tag(EGGS_FABRIC).add(HNCItems.CRACKED_EGG.get(), HNCItems.COOKED_EGG.get(), HNCItems.GREEN_EGG.get());
        this.tag(EGGS_COMMON).addTag(EGGS_FORGE, EGGS_FABRIC);

        this.tag(CRACKED_EGG_FORGE).addTag(EGGS_FORGE);
        this.tag(COOKED_EGG_FABRIC);

        this.tag(HAM_FORGE).add(HNCItems.HAM_SLICE.get(), HNCItems.COOKED_HAM_SLICE.get(), HNCItems.GREEN_HAM_SLICE.get());
        this.tag(HAM_FABRIC).add(HNCItems.HAM_SLICE.get(), HNCItems.COOKED_HAM_SLICE.get(), HNCItems.GREEN_HAM_SLICE.get());
        this.tag(HAM_COMMON).addTag(HAM_FORGE, HAM_FABRIC);

        this.tag(BACON_FORGE).add(HNCItems.BACON.get(), HNCItems.COOKED_BACON.get());
        this.tag(BACON_FABRIC).add(HNCItems.BACON.get(), HNCItems.COOKED_BACON.get());
        this.tag(BACON_COMMON).addTag(BACON_FORGE, BACON_FABRIC);

        this.tag(PINEAPPLE_FORGE).add(HNCItems.PINEAPPLE.get(), HNCItems.PINEAPPLE_RING.get(), HNCItems.PINEAPPLE_BIT.get());
        this.tag(PINEAPPLE_FABRIC).add(HNCItems.PINEAPPLE.get(), HNCItems.PINEAPPLE_RING.get(), HNCItems.PINEAPPLE_BIT.get());
        this.tag(PINEAPPLE_COMMON).addTag(PINEAPPLE_FORGE, PINEAPPLE_FABRIC);

        this.tag(TOMATO_FORGE).add(HNCItems.TOMATO.get(), HNCItems.TOMATO_SLICE.get());
        this.tag(TOMATO_FRUIT_FABRIC).add(HNCItems.TOMATO.get(), HNCItems.TOMATO_SLICE.get());
        this.tag(TOMATO_FABRIC).addTag(TOMATO_FRUIT_FABRIC);
        this.tag(TOMATO_COMMON).addTag(TOMATO_FORGE, TOMATO_FABRIC);

        this.tag(TOMATO_SAUCE).add(HNCItems.TOMATO_SAUCE.get());

        this.tag(CORN_FORGE).add(HNCItems.CORN_COB.get());
        this.tag(CORN_FABRIC).add(HNCItems.CORN_COB.get());
        this.tag(CORN_COMMON).addTag(CORN_FORGE, CORN_FABRIC);

        this.tag(MOUSE_BLACKLIST).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, HNCItems.MOUSE.get(), HNCItems.COOKED_MOUSE.get(), HNCItems.FOOD_SCRAPS.get());

        this.tag(FRUITS_FORGE).addTag(PINEAPPLE_COMMON, TOMATO_COMMON);
        this.tag(FRUITS_FABRIC).addTag(PINEAPPLE_COMMON, TOMATO_COMMON);
        this.tag(FRUITS_COMMON).addTag(FRUITS_FORGE, FRUITS_FABRIC);

        this.tag(CROPS_FORGE).addTag(PINEAPPLE_COMMON, TOMATO_COMMON, CORN_FABRIC);
        this.tag(CROPS_FABRIC).addTag(PINEAPPLE_COMMON, TOMATO_COMMON, CORN_FABRIC);
        this.tag(CROPS_COMMON).addTag(CROPS_FORGE, CROPS_FABRIC);

        this.tag(CARBS_FORGE).add(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get());
        this.tag(CARBS_FABRIC).add(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get());
        this.tag(CARBS_COMMON).addTag(CARBS_FORGE, CARBS_FABRIC);

        this.tag(SEEDS_FORGE).add(HNCItems.PINEAPPLE_PLANT.get(), HNCItems.TOMATO_SEEDS.get());
        this.tag(SEEDS_FABRIC).add(HNCItems.PINEAPPLE_PLANT.get(), HNCItems.TOMATO_SEEDS.get());
        this.tag(SEEDS_COMMON).addTag(SEEDS_FORGE, SEEDS_FABRIC);

        this.tag(PINEAPPLE_SEEDS_COMMON).add(HNCItems.PINEAPPLE_PLANT.get());
        this.tag(TOMATO_SEEDS_COMMON).add(HNCItems.TOMATO_SEEDS.get());
        this.tag(CORN_SEEDS_COMMON).addTag(CORN_FABRIC);

        this.tag(CAMPFIRES).add(Blocks.CAMPFIRE.asItem(), Blocks.SOUL_CAMPFIRE.asItem());

        this.tag(JEI_FOOD_BLACKLIST).addTag(BREAD_SLICE_COMMON, CRACKER_FORGE, PIZZA_FORGE, TOMATO_SAUCE).add(HNCItems.CRACKER_DUMMY.get(), HNCItems.BAKED_PIZZA_DUMMY.get());

        this.tag(ItemTags.PLANKS).add(HNCBlocks.MAPLE_PLANKS.get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(HNCBlocks.MAPLE_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(HNCBlocks.MAPLE_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(HNCBlocks.MAPLE_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(HNCBlocks.MAPLE_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(HNCBlocks.MAPLE_FENCE.get().asItem());
        this.tag(ItemTags.SAPLINGS).add(HNCBlocks.MAPLE_SAPLING.get().asItem());
        this.tag(MAPLE_LOGS).add(HNCBlocks.MAPLE_LOG.get().asItem(), HNCBlocks.MAPLE_WOOD.get().asItem(), HNCBlocks.STRIPPED_MAPLE_LOG.get().asItem(), HNCBlocks.STRIPPED_MAPLE_WOOD.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN).addTag(MAPLE_LOGS);
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(HNCBlocks.MAPLE_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.LEAVES).add(HNCBlocks.MAPLE_LEAVES.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(HNCBlocks.MAPLE_TRAPDOOR.get().asItem());
        this.tag(ItemTags.SIGNS).add(HNCBlocks.MAPLE_SIGN.getFirst().get().asItem());
        this.tag(ItemTags.BOATS).add(HNCItems.MAPLE_BOAT.get());

        this.tag(RODS_WOODEN_FORGE).add(Items.STICK);
        this.tag(RODS_WOODEN_FABRIC).add(Items.STICK);
        this.tag(WOODEN_STICKS_FABRIC).addTag(RODS_WOODEN_FABRIC);
        this.tag(WOODEN_STICKS_COMMON).addTag(RODS_WOODEN_FORGE, WOODEN_STICKS_FABRIC);

        this.tag(COBBLESTONE_FORGE).add(Blocks.COBBLESTONE.asItem(), Blocks.INFESTED_COBBLESTONE.asItem(), Blocks.MOSSY_COBBLESTONE.asItem(), Blocks.COBBLED_DEEPSLATE.asItem());
        this.tag(STONE_FORGE).add(Blocks.STONE.asItem(), Blocks.DIORITE.asItem(), Blocks.GRANITE.asItem(), Blocks.ANDESITE.asItem(), Blocks.INFESTED_STONE.asItem(), Blocks.DEEPSLATE.asItem(), Blocks.BLACKSTONE.asItem());
        this.tag(COBBLESTONES_FABRIC).add(Blocks.COBBLESTONE.asItem(), Blocks.INFESTED_COBBLESTONE.asItem(), Blocks.MOSSY_COBBLESTONE.asItem(), Blocks.COBBLED_DEEPSLATE.asItem());
        this.tag(COBBLESTONE_FABRIC).addTag(COBBLESTONES_FABRIC);
        this.tag(STONE_FABRIC).add(Blocks.STONE.asItem(), Blocks.DIORITE.asItem(), Blocks.GRANITE.asItem(), Blocks.ANDESITE.asItem(), Blocks.INFESTED_STONE.asItem(), Blocks.DEEPSLATE.asItem(), Blocks.BLACKSTONE.asItem());
        this.tag(STONE_COMMON).addTag(COBBLESTONE_FORGE, STONE_FORGE, COBBLESTONE_FABRIC, STONE_FABRIC);

        this.tag(IRON_INGOTS_FORGE).add(Items.IRON_INGOT);
        this.tag(IRON_INGOTS_FABRIC).add(Items.IRON_INGOT);
        this.tag(IRON_INGOTS_COMMON).addTag(IRON_INGOTS_FORGE, IRON_INGOTS_FABRIC);

        this.tag(IRON_NUGGETS_FORGE).add(Items.IRON_NUGGET);
        this.tag(IRON_NUGGETS_FABRIC).add(Items.IRON_NUGGET);
        this.tag(IRON_NUGGETS_COMMON).addTag(IRON_NUGGETS_FORGE, IRON_NUGGETS_FABRIC);

        this.tag(COPPER_INGOTS_FORGE).add(Items.COPPER_INGOT);
        this.tag(COPPER_INGOTS_FABRIC).add(Items.COPPER_INGOT);
        this.tag(COPPER_INGOTS_COMMON).addTag(COPPER_INGOTS_FORGE, COPPER_INGOTS_FABRIC);

        this.tag(GOLD_INGOTS_FORGE).add(Items.GOLD_INGOT);
        this.tag(GOLD_INGOTS_FABRIC).add(Items.GOLD_INGOT);
        this.tag(GOLD_INGOTS_COMMON).addTag(GOLD_INGOTS_FORGE, GOLD_INGOTS_FABRIC);

        this.tag(DIAMONDS_FORGE).add(Items.DIAMOND);
        this.tag(DIAMONDS_FABRIC).add(Items.DIAMOND);
        this.tag(DIAMONDS_COMMON).addTag(DIAMONDS_FORGE, DIAMONDS_FABRIC);

        this.tag(GREEN_DYE_FORGE).add(Items.GREEN_DYE);
        this.tag(GREEN_DYE_FABRIC).add(Items.GREEN_DYE);
        this.tag(GREEN_DYES_FABRIC).addTag(GREEN_DYE_FABRIC);
        this.tag(GREEN_DYES_COMMON).addTag(GREEN_DYE_FORGE, GREEN_DYES_FABRIC);

        this.tag(RED_DYE_FORGE).add(Items.RED_DYE);
        this.tag(RED_DYE_FABRIC).add(Items.RED_DYE);
        this.tag(RED_DYES_FABRIC).addTag(RED_DYE_FABRIC);
        this.tag(RED_DYES_COMMON).addTag(RED_DYE_FORGE, RED_DYES_FABRIC);

        this.tag(REDSTONE_FORGE).add(Items.REDSTONE);
        this.tag(REDSTONE_FABRIC).add(Items.REDSTONE);
        this.tag(REDSTONE_COMMON).addTag(REDSTONE_FORGE, REDSTONE_FABRIC);

        this.tag(KNIVES_WOOD).add(HNCItems.WOODEN_KNIFE.get());
        this.tag(KNIVES_STONE).add(HNCItems.STONE_KNIFE.get());
        this.tag(KNIVES_COPPER).add(HNCItems.COPPER_KNIFE.get());
        this.tag(KNIVES_GOLD).add(HNCItems.GOLDEN_KNIFE.get());
        this.tag(KNIVES_IRON).add(HNCItems.IRON_KNIFE.get());
        this.tag(KNIVES_DIAMOND).add(HNCItems.DIAMOND_KNIFE.get());
        this.tag(KNIVES_NETHERITE).add(HNCItems.NETHERITE_KNIFE.get());
        this.tag(KNIVES_FORGE).addTag(KNIVES_WOOD, KNIVES_STONE, KNIVES_COPPER, KNIVES_GOLD, KNIVES_IRON, KNIVES_DIAMOND, KNIVES_NETHERITE);
        this.tag(KNIVES_FABRIC).add(HNCItems.WOODEN_KNIFE.get(), HNCItems.STONE_KNIFE.get(), HNCItems.COPPER_KNIFE.get(), HNCItems.GOLDEN_KNIFE.get(), HNCItems.IRON_KNIFE.get(), HNCItems.DIAMOND_KNIFE.get(), HNCItems.NETHERITE_KNIFE.get());
        this.tag(KNIVES_COMMON).addTag(KNIVES_FORGE, KNIVES_FABRIC);

        this.tag(SHEARS_FORGE).add(Items.SHEARS);
        this.tag(SHEARS_FABRIC).add(Items.SHEARS);
        this.tag(SHEARS_COMMON).addTag(SHEARS_FORGE, SHEARS_FABRIC);

        this.copy(HNCBlockTags.CHOPPING_BOARDS, CHOPPING_BOARDS);
    }
}
