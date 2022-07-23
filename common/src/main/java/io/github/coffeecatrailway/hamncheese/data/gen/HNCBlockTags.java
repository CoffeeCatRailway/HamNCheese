package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.tags.PollinatedBlockTagsProvider;
import gg.moonflower.pollen.api.registry.resource.TagRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCBlockTags extends PollinatedBlockTagsProvider
{
    private static final TagKey<Block> BARRELS_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "barrels"));
    private static final TagKey<Block> BARRELS_FABRIC = TagRegistry.bindBlock(new ResourceLocation("c", "barrel"));
    private static final TagKey<Block> CHESTS_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "chests"));
    private static final TagKey<Block> CHESTS_FABRIC = TagRegistry.bindBlock(new ResourceLocation("c", "chest"));
    private static final TagKey<Block> SHULKERS_BOXES_VANILLA = TagRegistry.bindBlock(new ResourceLocation("shulker_boxs"));
    private static final TagKey<Block> SHULKERS_BOXES_FABRIC = TagRegistry.bindBlock(new ResourceLocation("c", "shulker_box"));
    public static final TagKey<Block> MOUSE_SEARCHABLE = TagRegistry.bindBlock(HamNCheese.getLocation("mouse_searchable"));

    public static final TagKey<Block> MINEABLE_WITH_KNIFE = TagRegistry.bindBlock(HamNCheese.getLocation("mineable/knife"));

    private static final TagKey<Block> ENDER_STORAGE_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "chests/ender"));
    public static final TagKey<Block> ENDER_STORAGE = TagRegistry.bindBlock(new ResourceLocation("c", "ender_storage"));

    public static final TagKey<Block> CHOPPING_BOARDS = TagRegistry.bindBlock(HamNCheese.getLocation("chopping_boards"));

    // Vanilla
//    public static final TagKey<Block> MINEABLE_WITH_PICKAXE = TagRegistry.bindBlock(HamNCheese.getLocation("mineable/pickaxe"));

    public static final TagKey<Block> PLANKS = TagRegistry.bindBlock(new ResourceLocation("planks"));
    public static final TagKey<Block> WOODEN_BUTTONS = TagRegistry.bindBlock(new ResourceLocation("wooden_buttons"));
    public static final TagKey<Block> WOODEN_DOORS = TagRegistry.bindBlock(new ResourceLocation("wooden_doors"));
    public static final TagKey<Block> WOODEN_STAIRS = TagRegistry.bindBlock(new ResourceLocation("wooden_stairs"));
    public static final TagKey<Block> WOODEN_SLABS = TagRegistry.bindBlock(new ResourceLocation("wooden_slabs"));
    public static final TagKey<Block> WOODEN_FENCES = TagRegistry.bindBlock(new ResourceLocation("wooden_fences"));
    public static final TagKey<Block> SAPLINGS = TagRegistry.bindBlock(new ResourceLocation("saplings"));
    public static final TagKey<Block> MAPLE_LOGS = TagRegistry.bindBlock(HamNCheese.getLocation("maple_logs"));
    public static final TagKey<Block> LOGS_THAT_BURN = TagRegistry.bindBlock(new ResourceLocation("logs_that_burn"));
    public static final TagKey<Block> FLOWER_POTS = TagRegistry.bindBlock(new ResourceLocation("flower_pots"));
    public static final TagKey<Block> WOODEN_PRESSURE_PLATES = TagRegistry.bindBlock(new ResourceLocation("wooden_pressure_plate"));
    public static final TagKey<Block> LEAVES = TagRegistry.bindBlock(new ResourceLocation("leaves"));
    public static final TagKey<Block> WOODEN_TRAPDOORS = TagRegistry.bindBlock(new ResourceLocation("wooden_trapdoors"));
    public static final TagKey<Block> STANDING_SIGNS = TagRegistry.bindBlock(new ResourceLocation("standing_signs"));
    public static final TagKey<Block> WALL_SIGNS = TagRegistry.bindBlock(new ResourceLocation("wall_signs"));
    public static final TagKey<Block> FENCE_GATES = TagRegistry.bindBlock(new ResourceLocation("fence_gates"));
    
    public HNCBlockTags(DataGenerator dataGenerator, PollinatedModContainer container)
    {
        super(dataGenerator, container);
    }

    @Override
    protected void addTags()
    {
        this.tag(BARRELS_FORGE).add(Blocks.BARREL);
        this.tag(BARRELS_FABRIC).add(Blocks.BARREL);
        this.tag(CHESTS_FORGE).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
        this.tag(CHESTS_FABRIC).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
        this.tag(SHULKERS_BOXES_VANILLA).add(Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
        this.tag(SHULKERS_BOXES_FABRIC).addTag(SHULKERS_BOXES_VANILLA);
        this.tag(MOUSE_SEARCHABLE).addTag(BARRELS_FORGE, BARRELS_FABRIC, CHESTS_FORGE, CHESTS_FABRIC, SHULKERS_BOXES_FABRIC).add(Blocks.DISPENSER, Blocks.DROPPER, Blocks.HOPPER);

        this.tag(MINEABLE_WITH_KNIFE).add(HNCBlocks.BLOCK_OF_CHEESE.get(), HNCBlocks.BLOCK_OF_BLUE_CHEESE.get(), HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get(), HNCBlocks.BLOCK_OF_SWISS_CHEESE.get(), HNCBlocks.BLOCK_OF_GOAT_CHEESE.get());

        this.tag(ENDER_STORAGE_FORGE).add(Blocks.ENDER_CHEST);
        this.tag(ENDER_STORAGE).addTag(ENDER_STORAGE_FORGE);

        this.tag(CHOPPING_BOARDS).add(HNCBlocks.OAK_CHOPPING_BOARD.get(), HNCBlocks.BIRCH_CHOPPING_BOARD.get(), HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), HNCBlocks.ACACIA_CHOPPING_BOARD.get(), HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), HNCBlocks.WARPED_CHOPPING_BOARD.get(), HNCBlocks.STONE_CHOPPING_BOARD.get(), HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), HNCBlocks.GOLD_CHOPPING_BOARD.get(), HNCBlocks.IRON_CHOPPING_BOARD.get(), HNCBlocks.MAPLE_CHOPPING_BOARD.get());

        // Vanilla
        this.tag(BlockTags.CROPS).add(HNCBlocks.PINEAPPLE_PLANT.get(), HNCBlocks.TOMATO_PLANT.get(), HNCBlocks.CORN_PLANT.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(CHOPPING_BOARDS);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(HNCBlocks.GRILL.get(), HNCBlocks.PIZZA_OVEN.get(), HNCBlocks.POPCORN_MACHINE.get(), HNCBlocks.TREE_TAP.get());

        this.tag(PLANKS).add(HNCBlocks.MAPLE_PLANKS.get());
        this.tag(WOODEN_BUTTONS).add(HNCBlocks.MAPLE_BUTTON.get());
        this.tag(WOODEN_DOORS).add(HNCBlocks.MAPLE_DOOR.get());
        this.tag(WOODEN_STAIRS).add(HNCBlocks.MAPLE_STAIRS.get());
        this.tag(WOODEN_SLABS).add(HNCBlocks.MAPLE_SLAB.get());
        this.tag(WOODEN_FENCES).add(HNCBlocks.MAPLE_FENCE.get());
        this.tag(SAPLINGS).add(HNCBlocks.MAPLE_SAPLING.get());
        this.tag(MAPLE_LOGS).add(HNCBlocks.MAPLE_LOG.get(), HNCBlocks.MAPLE_WOOD.get(), HNCBlocks.STRIPPED_MAPLE_LOG.get(), HNCBlocks.STRIPPED_MAPLE_WOOD.get());
        this.tag(LOGS_THAT_BURN).addTag(MAPLE_LOGS);
        this.tag(FLOWER_POTS).add(HNCBlocks.POTTED_MAPLE_SAPLING.get());
        this.tag(WOODEN_PRESSURE_PLATES).add(HNCBlocks.MAPLE_PRESSURE_PLATE.get());
        this.tag(LEAVES).add(HNCBlocks.MAPLE_LEAVES.get());
        this.tag(WOODEN_TRAPDOORS).add(HNCBlocks.MAPLE_TRAPDOOR.get());
        this.tag(STANDING_SIGNS).add(HNCBlocks.MAPLE_SIGN.getFirst().get());
        this.tag(WALL_SIGNS).add(HNCBlocks.MAPLE_SIGN.getSecond().get());
        this.tag(FENCE_GATES).add(HNCBlocks.MAPLE_FENCE_GATE.get());
    }
}
