package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
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

    public HNCBlockTags(DataGenerator generator, @Nullable ExistingFileHelper fileHelper)
    {
        super(generator, HNCMod.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(BlockTags.CROPS).add(HNCBlocks.PINEAPPLE_PLANT.get(), HNCBlocks.TOMATO_PLANT.get(), HNCBlocks.CORN_PLANT.get());

        this.tag(MOUSE_SEARCH).add(Blocks.BARREL).addTags(Tags.Blocks.CHESTS, BlockTags.SHULKER_BOXES);
    }
}
