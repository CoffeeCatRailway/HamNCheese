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
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCItemTags extends PollinatedItemTagsProvider
{
    public static final Tag.Named<Item> GEARS = TagRegistry.bindItem(new ResourceLocation("forge", "gears"));
    private static final Tag.Named<Item> GEARS_WOODEN = TagRegistry.bindItem(new ResourceLocation("forge", "gears/wooden"));
    public static final Tag.Named<Item> GEARS_WOODEN_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "wood_gears"));

    private static final Tag.Named<Item> WHEAT = TagRegistry.bindItem(new ResourceLocation("forge", "wheat"));
    public static final Tag.Named<Item> GRAIN_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "grain"));
    private static final Tag.Named<Item> SUGAR = TagRegistry.bindItem(new ResourceLocation("forge", "sugar"));
    public static final Tag.Named<Item> SUGAR_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "sugar"));

    private static final Tag.Named<Item> CHEESE_SLICE = TagRegistry.bindItem(new ResourceLocation("forge", "cheese_slice"));
    public static final Tag.Named<Item> CHEESE_SLICE_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "cheese_slice"));

    private static final Tag.Named<Item> CHEESE = TagRegistry.bindItem(new ResourceLocation("forge", "cheese"));
    public static final Tag.Named<Item> CHEESE_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "cheese"));

    private static final Tag.Named<Item> SALT = TagRegistry.bindItem(new ResourceLocation("forge", "salt"));
    public static final Tag.Named<Item> SALT_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "salt"));
    private static final Tag.Named<Item> FLOUR = TagRegistry.bindItem(new ResourceLocation("forge", "flour"));
    public static final Tag.Named<Item> FLOUR_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "flour"));

    private static final Tag.Named<Item> DOUGH = TagRegistry.bindItem(new ResourceLocation("forge", "dough"));
    public static final Tag.Named<Item> DOUGH_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "dough"));

    private static final Tag.Named<Item> PIZZA = TagRegistry.bindItem(new ResourceLocation("forge", "pizza"));
    public static final Tag.Named<Item> PIZZAS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "pizzas"));

    public static final Tag.Named<Item> BREAD_SLICE = TagRegistry.bindItem(new ResourceLocation("forge", "bread_slice"));
    private static final Tag.Named<Item> BREAD = TagRegistry.bindItem(new ResourceLocation("forge", "bread"));
    public static final Tag.Named<Item> BREAD_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "sandwich/bread"));

    private static final Tag.Named<Item> CRACKER = TagRegistry.bindItem(new ResourceLocation("forge", "cracker"));
    public static final Tag.Named<Item> CRACKER_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "cracker"));

    private static final Tag.Named<Item> EGGS_FORGE = TagRegistry.bindItem(new ResourceLocation("forge", "eggs"));
    public static final Tag.Named<Item> EGGS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "eggs"));
    public static final Tag.Named<Item> CRACKED_EGG = TagRegistry.bindItem(new ResourceLocation("forge", "cracked_egg"));
    public static final Tag.Named<Item> COOKED_EGG_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "cooked_eggs"));

    private static final Tag.Named<Item> HAM = TagRegistry.bindItem(new ResourceLocation("forge", "ham"));
    public static final Tag.Named<Item> HAM_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "ham"));
    private static final Tag.Named<Item> BACON = TagRegistry.bindItem(new ResourceLocation("forge", "bacon"));
    public static final Tag.Named<Item> BACON_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "bacon"));

    private static final Tag.Named<Item> PINEAPPLE = TagRegistry.bindItem(new ResourceLocation("forge", "pineapple"));
    public static final Tag.Named<Item> PINEAPPLE_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "fruits/pineapples"));

    private static final Tag.Named<Item> TOMATO = TagRegistry.bindItem(new ResourceLocation("forge", "tomato"));
    public static final Tag.Named<Item> TOMATO_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "vegetables/tomato"));
    public static final Tag.Named<Item> TOMATO_SAUCE = TagRegistry.bindItem(new ResourceLocation("forge", "tomato_sauce"));

    private static final Tag.Named<Item> CORN_COB = TagRegistry.bindItem(new ResourceLocation("forge", "corn_cob"));
    public static final Tag.Named<Item> CORN_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "corn"));

    public static final Tag.Named<Item> MOUSE_BLACKLIST = TagRegistry.bindItem(HamNCheese.getLocation("mouse_blacklist"));

    private static final Tag.Named<Item> FRUITS = TagRegistry.bindItem(new ResourceLocation("forge", "fruits"));
    public static final Tag.Named<Item> FRUITS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "fruits"));
    private static final Tag.Named<Item> CROPS = TagRegistry.bindItem(new ResourceLocation("forge", "crops"));
    public static final Tag.Named<Item> CROPS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "crops"));
    private static final Tag.Named<Item> CARBS = TagRegistry.bindItem(new ResourceLocation("forge", "carbs"));
    public static final Tag.Named<Item> CARBS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "carbs"));
    private static final Tag.Named<Item> SEEDS = TagRegistry.bindItem(new ResourceLocation("forge", "seeds"));
    public static final Tag.Named<Item> SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds"));

    public static final Tag.Named<Item> PINEAPPLE_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/pineapple"));
    public static final Tag.Named<Item> TOMATO_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/tomato"));
    public static final Tag.Named<Item> CORN_SEEDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "seeds/corn"));

    public static final Tag.Named<Item> CAMPFIRES = TagRegistry.bindItem(new ResourceLocation("forge", "campfires"));

    public static final Tag.Named<Item> JEI_FOOD_BLACKLIST = TagRegistry.bindItem(HamNCheese.getLocation("jei_food_blacklist"));

    public static final Tag.Named<Item> MAPLE_LOGS = TagRegistry.bindItem(HamNCheese.getLocation("maple_logs"));

    private static final Tag.Named<Item> RODS_WOODEN = TagRegistry.bindItem(new ResourceLocation("forge", "rods/wooden"));
    private static final Tag.Named<Item> RODS_WOODEN_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "wooden_rods"));
    public static final Tag.Named<Item> WOOD_STICKS = TagRegistry.bindItem(new ResourceLocation("c", "wood_sticks"));

    private static final Tag.Named<Item> COBBLESTONE = TagRegistry.bindItem(new ResourceLocation("forge", "cobblestone"));
    private static final Tag.Named<Item> STONE = TagRegistry.bindItem(new ResourceLocation("forge", "stone"));
    private static final Tag.Named<Item> COBBLESTONES = TagRegistry.bindItem(new ResourceLocation("c", "cobblestones"));
    private static final Tag.Named<Item> COBBLESTONE_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "cobblestone"));
    public static final Tag.Named<Item> STONE_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "stones"));

    private static final Tag.Named<Item> IRON_INGOTS = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/iron"));
    public static final Tag.Named<Item> IRON_INGOTS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "iron_ingots"));

    private static final Tag.Named<Item> IRON_NUGGETS = TagRegistry.bindItem(new ResourceLocation("forge", "nuggets/iron"));
    public static final Tag.Named<Item> IRON_NUGGETS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "iron_nuggets"));

    private static final Tag.Named<Item> COPPER_INGOTS = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/copper"));
    private static final Tag.Named<Item> COPPER_INGOT = TagRegistry.bindItem(new ResourceLocation("c", "copper_ingot"));
    public static final Tag.Named<Item> COPPER_INGOTS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "copper_ingots"));

    private static final Tag.Named<Item> GOLD_INGOTS = TagRegistry.bindItem(new ResourceLocation("forge", "ingots/gold"));
    public static final Tag.Named<Item> GOLD_INGOTS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "gold_ingots"));

    private static final Tag.Named<Item> DIAMOND_GEMS = TagRegistry.bindItem(new ResourceLocation("forge", "gems/diamond"));
    public static final Tag.Named<Item> DIAMONDS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "diamonds"));

    private static final Tag.Named<Item> GREEN_DYE = TagRegistry.bindItem(new ResourceLocation("forge", "dyes/green"));
    private static final Tag.Named<Item> GREEN_DYE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "green_dye"));
    public static final Tag.Named<Item> GREEN_DYES_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "green_dyes"));

    private static final Tag.Named<Item> RED_DYE = TagRegistry.bindItem(new ResourceLocation("forge", "dyes/red"));
    private static final Tag.Named<Item> RED_DYE_FABRIC = TagRegistry.bindItem(new ResourceLocation("c", "red_dye"));
    public static final Tag.Named<Item> RED_DYES_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "red_dyes"));

    private static final Tag.Named<Item> REDSTONE_DUSTS = TagRegistry.bindItem(new ResourceLocation("forge", "dusts/redstone"));
    public static final Tag.Named<Item> REDSTONE_DUSTS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "redstone_dusts"));

    private static final Tag.Named<Item> KNIVES_WOOD = TagRegistry.bindItem(new ResourceLocation("forge", "knives/wooden"));
    private static final Tag.Named<Item> KNIVES_STONE = TagRegistry.bindItem(new ResourceLocation("forge", "knives/stone"));
    private static final Tag.Named<Item> KNIVES_COPPER = TagRegistry.bindItem(new ResourceLocation("forge", "knives/copper"));
    private static final Tag.Named<Item> KNIVES_GOLD = TagRegistry.bindItem(new ResourceLocation("forge", "knives/golden"));
    private static final Tag.Named<Item> KNIVES_IRON = TagRegistry.bindItem(new ResourceLocation("forge", "knives/iron"));
    private static final Tag.Named<Item> KNIVES_DIAMOND = TagRegistry.bindItem(new ResourceLocation("forge", "knives/diamond"));
    private static final Tag.Named<Item> KNIVES_NETHERITE = TagRegistry.bindItem(new ResourceLocation("forge", "knives/metherite"));
    private static final Tag.Named<Item> KNIVES = TagRegistry.bindItem(new ResourceLocation("forge", "knives"));
    public static final Tag.Named<Item> KNIVES_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "knives"));

    private static final Tag.Named<Item> SHEARS = TagRegistry.bindItem(new ResourceLocation("forge", "shears"));
    public static final Tag.Named<Item> SHEARS_COMMON = TagRegistry.bindItem(new ResourceLocation("c", "shears"));

    public static final Tag.Named<Item> CHOPPING_BOARDS = TagRegistry.bindItem(HamNCheese.getLocation("chopping_boards"));

    public HNCItemTags(DataGenerator dataGenerator, PollinatedModContainer container, BlockTagsProvider blockTags)
    {
        super(dataGenerator, container, blockTags);
    }

    @Override
    protected void addTags()
    {
        this.tag(GEARS_WOODEN).add(HNCItems.WOODEN_GEAR.get());
        this.tag(GEARS_WOODEN_COMMON).addTag(GEARS_WOODEN);
        this.tag(GEARS).addTag(GEARS_WOODEN_COMMON);

        this.tag(WHEAT).add(Items.WHEAT);
        this.tag(GRAIN_COMMON).addTag(WHEAT);
        this.tag(SUGAR).add(Items.SUGAR);
        this.tag(SUGAR_COMMON).addTag(SUGAR);

        this.tag(CHEESE_SLICE).add(HNCItems.CHEESE_SLICE.get(), HNCItems.BLUE_CHEESE_SLICE.get(), HNCItems.GOUDA_CHEESE_SLICE.get(), HNCItems.SWISS_CHEESE_SLICE.get(), HNCItems.GOAT_CHEESE_SLICE.get());
        this.tag(CHEESE_SLICE_COMMON).addTag(CHEESE_SLICE);

        this.tag(CHEESE).addTag(CHEESE_SLICE_COMMON).add(HNCItems.SWISS_CHEESE_BITS.get(), HNCBlocks.BLOCK_OF_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_BLUE_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_SWISS_CHEESE.get().asItem(), HNCBlocks.BLOCK_OF_GOAT_CHEESE.get().asItem());
        this.tag(CHEESE_COMMON).addTag(CHEESE);

        this.tag(SALT).add(HNCItems.ROCK_SALT.get());
        this.tag(SALT_COMMON).addTag(SALT);
        this.tag(FLOUR).add(HNCItems.FLOUR.get());
        this.tag(FLOUR_COMMON).addTag(FLOUR);

        this.tag(DOUGH).add(HNCItems.DOUGH.get(), HNCItems.UNBAKED_PIZZA_BASE.get(), HNCItems.UNBAKED_CROISSANT.get());
        this.tag(DOUGH_COMMON).addTag(DOUGH);

        this.tag(PIZZA).add(HNCItems.UNBAKED_PIZZA_BASE.get());
        this.tag(PIZZAS_COMMON).addTag(PIZZA);

        this.tag(BREAD_SLICE).add(HNCItems.BREAD_SLICE.get());
        this.tag(BREAD).add(HNCItems.BREAD_SLICE.get());
        this.tag(BREAD_COMMON).addTag(BREAD);

        this.tag(CRACKER).add(HNCItems.CRACKER.get());
        this.tag(CRACKER_COMMON).addTag(CRACKER);

        this.tag(EGGS_FORGE).add(HNCItems.CRACKED_EGG.get(), HNCItems.COOKED_EGG.get(), HNCItems.GREEN_EGG.get());
        this.tag(EGGS_COMMON).addTag(EGGS_FORGE);
        this.tag(CRACKED_EGG).addTag(EGGS_FORGE);
        this.tag(COOKED_EGG_COMMON);

        this.tag(HAM).add(HNCItems.HAM_SLICE.get(), HNCItems.COOKED_HAM_SLICE.get(), HNCItems.GREEN_HAM_SLICE.get());
        this.tag(HAM_COMMON).addTag(HAM);
        this.tag(BACON).add(HNCItems.BACON.get(), HNCItems.COOKED_BACON.get());
        this.tag(BACON_COMMON).addTag(BACON);

        this.tag(PINEAPPLE).add(HNCItems.PINEAPPLE.get());
        this.tag(PINEAPPLE_COMMON).addTag(PINEAPPLE);

        this.tag(TOMATO).add(HNCItems.TOMATO.get());
        this.tag(TOMATO_COMMON).addTag(TOMATO);
        this.tag(TOMATO_SAUCE).add(HNCItems.TOMATO_SAUCE.get());

        this.tag(CORN_COB).add(HNCItems.CORN_COB.get());
        this.tag(CORN_COMMON).addTag(CORN_COB);

        this.tag(FRUITS).addTag(PINEAPPLE, TOMATO, CORN_COB);
        this.tag(FRUITS_COMMON).addTag(FRUITS);
        this.tag(CROPS).addTag(PINEAPPLE, TOMATO, CORN_COB);
        this.tag(CROPS_COMMON).addTag(CROPS);
        this.tag(CARBS).add(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get());
        this.tag(CARBS_COMMON).addTag(CARBS);
        this.tag(SEEDS).add(HNCItems.PINEAPPLE_PLANT.get(), HNCItems.TOMATO_SEEDS.get());
        this.tag(SEEDS_COMMON).addTag(SEEDS);

        this.tag(PINEAPPLE_SEEDS_COMMON).add(HNCItems.PINEAPPLE_PLANT.get());
        this.tag(TOMATO_SEEDS_COMMON).add(HNCItems.TOMATO_SEEDS.get());
        this.tag(CORN_SEEDS_COMMON).addTag(CORN_COB);

        this.tag(MOUSE_BLACKLIST).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, HNCItems.MOUSE.get(), HNCItems.COOKED_MOUSE.get(), HNCItems.FOOD_SCRAPS.get());

        this.tag(CAMPFIRES).add(Blocks.CAMPFIRE.asItem(), Blocks.SOUL_CAMPFIRE.asItem());

        this.tag(JEI_FOOD_BLACKLIST).addTag(BREAD_SLICE, CRACKER, PIZZA, TOMATO_SAUCE).add(HNCItems.CRACKER_DUMMY.get(), HNCItems.BAKED_PIZZA_DUMMY.get());

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

        this.tag(RODS_WOODEN).add(Items.STICK);
        this.tag(RODS_WOODEN_FABRIC).addTag(RODS_WOODEN);
        this.tag(WOOD_STICKS).addTag(RODS_WOODEN_FABRIC);

        this.tag(COBBLESTONE).add(Blocks.COBBLESTONE.asItem(), Blocks.INFESTED_COBBLESTONE.asItem());
        this.tag(COBBLESTONES).addTag(COBBLESTONE);
        this.tag(COBBLESTONE_COMMON).addTag(COBBLESTONES);
        this.tag(STONE).add(Blocks.STONE.asItem(), Blocks.DIORITE.asItem(), Blocks.GRANITE.asItem(), Blocks.ANDESITE.asItem(), Blocks.INFESTED_STONE.asItem());
        this.tag(STONE_COMMON).addTag(STONE, COBBLESTONE_COMMON);

        this.tag(IRON_INGOTS).add(Items.IRON_INGOT);
        this.tag(IRON_INGOTS_COMMON).addTag(IRON_INGOTS);

        this.tag(IRON_NUGGETS).add(Items.IRON_NUGGET);
        this.tag(IRON_NUGGETS_COMMON).addTag(IRON_NUGGETS);

        this.tag(COPPER_INGOTS).add(Items.COPPER_INGOT);
        this.tag(COPPER_INGOT).addTag(COPPER_INGOTS);
        this.tag(COPPER_INGOTS_COMMON).addTag(COPPER_INGOT);

        this.tag(GOLD_INGOTS).add(Items.GOLD_INGOT);
        this.tag(GOLD_INGOTS_COMMON).addTag(GOLD_INGOTS);

        this.tag(DIAMOND_GEMS).add(Items.DIAMOND);
        this.tag(DIAMONDS_COMMON).addTag(DIAMOND_GEMS);

        this.tag(GREEN_DYE).add(Items.GREEN_DYE);
        this.tag(GREEN_DYE_FABRIC).addTag(GREEN_DYE);
        this.tag(GREEN_DYES_COMMON).addTag(GREEN_DYE_FABRIC);

        this.tag(RED_DYE).add(Items.RED_DYE);
        this.tag(RED_DYE_FABRIC).addTag(RED_DYE);
        this.tag(RED_DYES_COMMON).addTag(RED_DYE_FABRIC);

        this.tag(REDSTONE_DUSTS).add(Items.REDSTONE);
        this.tag(REDSTONE_DUSTS_COMMON).addTag(REDSTONE_DUSTS);

        this.tag(KNIVES_WOOD).add(HNCItems.WOODEN_KNIFE.get());
        this.tag(KNIVES_STONE).add(HNCItems.STONE_KNIFE.get());
        this.tag(KNIVES_COPPER).add(HNCItems.COPPER_KNIFE.get());
        this.tag(KNIVES_GOLD).add(HNCItems.GOLDEN_KNIFE.get());
        this.tag(KNIVES_IRON).add(HNCItems.IRON_KNIFE.get());
        this.tag(KNIVES_DIAMOND).add(HNCItems.DIAMOND_KNIFE.get());
        this.tag(KNIVES_NETHERITE).add(HNCItems.NETHERITE_KNIFE.get());
        this.tag(KNIVES).addTag(KNIVES_WOOD, KNIVES_STONE, KNIVES_COPPER, KNIVES_GOLD, KNIVES_IRON, KNIVES_DIAMOND, KNIVES_NETHERITE);
        this.tag(KNIVES_COMMON).addTag(KNIVES);

        this.tag(SHEARS).add(Items.SHEARS);
        this.tag(SHEARS_COMMON).addTag(SHEARS);

        this.copy(HNCBlockTags.CHOPPING_BOARDS, CHOPPING_BOARDS);
    }
}
