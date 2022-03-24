package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.tags.PollinatedFluidTagsProvider;
import gg.moonflower.pollen.api.registry.resource.TagRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.material.Fluid;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCFluidTags extends PollinatedFluidTagsProvider
{
    public static final Tag.Named<Fluid> MAPLE_SAP = TagRegistry.bindFluid(HamNCheese.getLocation("maple_sap"));

    public HNCFluidTags(DataGenerator dataGenerator, PollinatedModContainer container)
    {
        super(dataGenerator, container);
    }

    @Override
    protected void addTags()
    {
        this.tag(MAPLE_SAP);//.add(HNCFluids.MAPLE_SAP.get(), HNCFluids.MAPLE_SAP_FLOWING.get());
    }
}
