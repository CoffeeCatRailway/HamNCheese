package io.github.coffeecatrailway.hamncheese.fabric;

import io.github.coffeecatrailway.hamncheese.HNCConfig;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.GenerationStep;

public class HamNCheeseFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        HamNCheese.PLATFORM.setup();
        WoodType.register(HNCBlocks.MAPLE_WOOD_TYPE);

        final HNCConfig.Server config = HamNCheese.CONFIG_SERVER;
        if (config.mapleTreeWeight.get() > 0)
            BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.MAPLE_TREE_KEY);

        if (config.wildPineapplesGenerate.get())
            BiomeModifications.addFeature(BiomeSelectors.categories(config.wildPineapplesCategoryWhitelist.get().toArray(Biome.BiomeCategory[]::new)), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.WILD_PINEAPPLE_KEY);
    }
}
