package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCBlockTags extends BlockTagsProvider
{
    public HNCBlockTags(DataGenerator generator, @Nullable ExistingFileHelper fileHelper)
    {
        super(generator, HNCMod.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(BlockTags.CROPS).add(HNCBlocks.PINEAPPLE_PLANT.get());
    }
}
