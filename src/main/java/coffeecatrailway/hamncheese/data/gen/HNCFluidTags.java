package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 12/09/2021
 */
public class HNCFluidTags extends FluidTagsProvider
{
    public static final ITag.INamedTag<Fluid> MAPLE_SAP = FluidTags.createOptional(HNCMod.getLocation("maple_sap"));

    public HNCFluidTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(generator, HNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(MAPLE_SAP).add(HNCFluids.MAPLE_SAP.get(), HNCFluids.MAPLE_SAP_FLOWING.get());
    }
}
