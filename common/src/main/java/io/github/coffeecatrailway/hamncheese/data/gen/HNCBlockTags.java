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
    public static final Tag.Named<Block> MOUSE_SEARCH = TagRegistry.bindBlock(HamNCheese.getLocation("mouse_search"));
    public static final Tag.Named<Block> MINEABLE_WITH_KNIFE = TagRegistry.bindBlock(HamNCheese.getLocation("mineable_with_knife"));

    // Minecraft
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

    // Common
    public static final Tag.Named<Block> CHESTS = TagRegistry.bindBlock(new ResourceLocation("c", "chests"));
    
    public HNCBlockTags(DataGenerator dataGenerator, PollinatedModContainer container)
    {
        super(dataGenerator, container);
    }

    @Override
    protected void addTags()
    {
        this.tag(BlockTags.CROPS);//.add(HNCBlocks.PINEAPPLE_PLANT.get(), HNCBlocks.TOMATO_PLANT.get(), HNCBlocks.CORN_PLANT.get());

        this.tag(CHESTS).addOptionalTag(new ResourceLocation("forge", "chests"));
        this.tag(MOUSE_SEARCH).add(Blocks.BARREL).addTag(CHESTS, BlockTags.SHULKER_BOXES);
        this.tag(MINEABLE_WITH_KNIFE);//.add(HNCBlock.BLOCK_OF_CHEESE.get());

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
        this.tag(STANDING_SIGNS).add(HNCBlocks.MAPLE_SIGN.get());
        this.tag(WALL_SIGNS).add(HNCBlocks.MAPLE_WALL_SIGN.get());
        this.tag(FENCE_GATES).add(HNCBlocks.MAPLE_FENCE_GATE.get());
    }
}
