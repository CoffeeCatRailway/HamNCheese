package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCBlockTags extends BlockTagsProvider
{
    public static final ITag.INamedTag<Block> MOUSE_SEARCH = BlockTags.createOptional(HNCMod.getLocation("mouse_search"));

    public static final ITag.INamedTag<Block> PLANKS = BlockTags.createOptional(new ResourceLocation("planks"));
    public static final ITag.INamedTag<Block> WOODEN_BUTTONS = BlockTags.createOptional(new ResourceLocation("wooden_buttons"));
    public static final ITag.INamedTag<Block> WOODEN_DOORS = BlockTags.createOptional(new ResourceLocation("wooden_doors"));
    public static final ITag.INamedTag<Block> WOODEN_STAIRS = BlockTags.createOptional(new ResourceLocation("wooden_stairs"));
    public static final ITag.INamedTag<Block> WOODEN_SLABS = BlockTags.createOptional(new ResourceLocation("wooden_slabs"));
    public static final ITag.INamedTag<Block> WOODEN_FENCES = BlockTags.createOptional(new ResourceLocation("wooden_fences"));
    public static final ITag.INamedTag<Block> SAPLINGS = BlockTags.createOptional(new ResourceLocation("saplings"));
    public static final ITag.INamedTag<Block> MAPLE_LOGS = BlockTags.createOptional(HNCMod.getLocation("maple_logs"));
    public static final ITag.INamedTag<Block> LOGS_THAT_BURN = BlockTags.createOptional(new ResourceLocation("logs_that_burn"));
    public static final ITag.INamedTag<Block> FLOWER_POTS = BlockTags.createOptional(new ResourceLocation("flower_pots"));
    public static final ITag.INamedTag<Block> WOODEN_PRESSURE_PLATES = BlockTags.createOptional(new ResourceLocation("wooden_pressure_plate"));
    public static final ITag.INamedTag<Block> LEAVES = BlockTags.createOptional(new ResourceLocation("leaves"));
    public static final ITag.INamedTag<Block> WOODEN_TRAPDOORS = BlockTags.createOptional(new ResourceLocation("wooden_trapdoors"));
    public static final ITag.INamedTag<Block> STANDING_SIGNS = BlockTags.createOptional(new ResourceLocation("standing_signs"));
    public static final ITag.INamedTag<Block> WALL_SIGNS = BlockTags.createOptional(new ResourceLocation("wall_signs"));
    public static final ITag.INamedTag<Block> FENCE_GATES = BlockTags.createOptional(new ResourceLocation("fence_gates"));

    public HNCBlockTags(DataGenerator generator, @Nullable ExistingFileHelper fileHelper)
    {
        super(generator, HNCMod.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(BlockTags.CROPS).add(HNCBlocks.PINEAPPLE_PLANT.get(), HNCBlocks.TOMATO_PLANT.get(), HNCBlocks.CORN_PLANT.get());

        this.tag(MOUSE_SEARCH).add(Blocks.BARREL).addTags(Tags.Blocks.CHESTS, BlockTags.SHULKER_BOXES);

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
