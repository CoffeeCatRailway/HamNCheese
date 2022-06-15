package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.tags.PollinatedBlockTagsProvider;
import gg.moonflower.pollen.api.registry.resource.TagRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCBlockTags extends PollinatedBlockTagsProvider
{
    private static final Tag.Named<Block> BARRELS_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "barrels"));
    private static final Tag.Named<Block> BARRELS_COMMON = TagRegistry.bindBlock(new ResourceLocation("c", "barrel"));
    private static final Tag.Named<Block> CHESTS_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "chests"));
    private static final Tag.Named<Block> CHESTS_COMMON = TagRegistry.bindBlock(new ResourceLocation("c", "chest"));
    private static final Tag.Named<Block> SHULKERS_COMMON = TagRegistry.bindBlock(new ResourceLocation("c", "shulker_box"));
    public static final Tag.Named<Block> MOUSE_SEARCHABLE = TagRegistry.bindBlock(HamNCheese.getLocation("mouse_searchable"));

    public static final Tag.Named<Block> MINEABLE_WITH_KNIFE = TagRegistry.bindBlock(HamNCheese.getLocation("mineable_with_knife"));

    private static final Tag.Named<Block> ENDER_STORAGE_FORGE = TagRegistry.bindBlock(new ResourceLocation("forge", "chests/ender"));
    public static final Tag.Named<Block> ENDER_STORAGE = TagRegistry.bindBlock(new ResourceLocation("c", "ender_storage"));

    public static final Tag.Named<Block> CHOPPING_BOARDS = TagRegistry.bindBlock(HamNCheese.getLocation("chopping_boards"));

    // Vanilla
    public static final Tag.Named<Block> PLANKS = TagRegistry.bindBlock(new ResourceLocation("planks"));
    public static final Tag.Named<Block> WOODEN_BUTTONS = TagRegistry.bindBlock(new ResourceLocation("wooden_buttons"));
    public static final Tag.Named<Block> WOODEN_DOORS = TagRegistry.bindBlock(new ResourceLocation("wooden_doors"));
    public static final Tag.Named<Block> WOODEN_STAIRS = TagRegistry.bindBlock(new ResourceLocation("wooden_stairs"));
    public static final Tag.Named<Block> WOODEN_SLABS = TagRegistry.bindBlock(new ResourceLocation("wooden_slabs"));
    public static final Tag.Named<Block> WOODEN_FENCES = TagRegistry.bindBlock(new ResourceLocation("wooden_fences"));
    public static final Tag.Named<Block> SAPLINGS = TagRegistry.bindBlock(new ResourceLocation("saplings"));
    public static final Tag.Named<Block> MAPLE_LOGS = TagRegistry.bindBlock(HamNCheese.getLocation("maple_logs"));
    public static final Tag.Named<Block> LOGS_THAT_BURN = TagRegistry.bindBlock(new ResourceLocation("logs_that_burn"));
    public static final Tag.Named<Block> FLOWER_POTS = TagRegistry.bindBlock(new ResourceLocation("flower_pots"));
    public static final Tag.Named<Block> WOODEN_PRESSURE_PLATES = TagRegistry.bindBlock(new ResourceLocation("wooden_pressure_plate"));
    public static final Tag.Named<Block> LEAVES = TagRegistry.bindBlock(new ResourceLocation("leaves"));
    public static final Tag.Named<Block> WOODEN_TRAPDOORS = TagRegistry.bindBlock(new ResourceLocation("wooden_trapdoors"));
    public static final Tag.Named<Block> STANDING_SIGNS = TagRegistry.bindBlock(new ResourceLocation("standing_signs"));
    public static final Tag.Named<Block> WALL_SIGNS = TagRegistry.bindBlock(new ResourceLocation("wall_signs"));
    public static final Tag.Named<Block> FENCE_GATES = TagRegistry.bindBlock(new ResourceLocation("fence_gates"));
    
    public HNCBlockTags(DataGenerator dataGenerator, PollinatedModContainer container)
    {
        super(dataGenerator, container);
    }

    @Override
    protected void addTags()
    {
        this.tag(BARRELS_FORGE).add(Blocks.BARREL);
        this.tag(BARRELS_COMMON).addTag(BARRELS_FORGE);
        this.tag(CHESTS_FORGE).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
        this.tag(CHESTS_COMMON).addTag(CHESTS_FORGE);
        this.tag(SHULKERS_COMMON).addTag(BlockTags.SHULKER_BOXES);
        this.tag(MOUSE_SEARCHABLE).addTag(BARRELS_COMMON, CHESTS_COMMON, SHULKERS_COMMON).add(Blocks.DISPENSER, Blocks.DROPPER, Blocks.HOPPER);

        this.tag(MINEABLE_WITH_KNIFE).add(HNCBlocks.BLOCK_OF_CHEESE.get(), HNCBlocks.BLOCK_OF_BLUE_CHEESE.get(), HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get(), HNCBlocks.BLOCK_OF_SWISS_CHEESE.get(), HNCBlocks.BLOCK_OF_GOAT_CHEESE.get());

        this.tag(ENDER_STORAGE_FORGE).add(Blocks.ENDER_CHEST);
        this.tag(ENDER_STORAGE).addTag(ENDER_STORAGE_FORGE);

        this.tag(CHOPPING_BOARDS).add(HNCBlocks.OAK_CHOPPING_BOARD.get(), HNCBlocks.BIRCH_CHOPPING_BOARD.get(), HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), HNCBlocks.ACACIA_CHOPPING_BOARD.get(), HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), HNCBlocks.WARPED_CHOPPING_BOARD.get(), HNCBlocks.STONE_CHOPPING_BOARD.get(), HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), HNCBlocks.GOLD_CHOPPING_BOARD.get(), HNCBlocks.IRON_CHOPPING_BOARD.get(), HNCBlocks.MAPLE_CHOPPING_BOARD.get());

        // Vanilla
        this.tag(BlockTags.CROPS).add(HNCBlocks.PINEAPPLE_PLANT.get(), HNCBlocks.TOMATO_PLANT.get(), HNCBlocks.CORN_PLANT.get());

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
