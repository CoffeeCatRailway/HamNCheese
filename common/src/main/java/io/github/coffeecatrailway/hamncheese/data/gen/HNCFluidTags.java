package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.tags.PollinatedFluidTagsProvider;
import gg.moonflower.pollen.api.registry.resource.TagRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.material.Fluid;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCFluidTags extends PollinatedFluidTagsProvider
{
    public static final Tag.Named<Fluid> MAPLE_SAP = TagRegistry.bindFluid(HamNCheese.getLocation("maple_sap"));

    public static final Tag.Named<Fluid> MILK = TagRegistry.bindFluid(HamNCheese.getLocation("milk"));
    public static final Tag.Named<Fluid> GOAT_MILK = TagRegistry.bindFluid(HamNCheese.getLocation("goat_milk"));
    private static final Tag.Named<Fluid> MILK_FORGE = TagRegistry.bindFluid(new ResourceLocation("forge", "milk"));
    public static final Tag.Named<Fluid> MILK_COMMON = TagRegistry.bindFluid(new ResourceLocation("c", "milk"));

    public HNCFluidTags(DataGenerator dataGenerator, PollinatedModContainer container)
    {
        super(dataGenerator, container);
    }

    @Override
    protected void addTags()
    {
        this.tag(MAPLE_SAP).add(HNCFluids.MAPLE_SAP.get(), HNCFluids.MAPLE_SAP_FLOWING.get());

        this.tag(MILK).add(HNCFluids.MILK.get(), HNCFluids.MILK_FLOWING.get());
        this.tag(GOAT_MILK).add(HNCFluids.GOAT_MILK.get(), HNCFluids.GOAT_MILK_FLOWING.get());
        this.tag(MILK_FORGE).addTag(MILK, GOAT_MILK);
        this.tag(MILK_COMMON).addTag(MILK_FORGE);
    }
}
