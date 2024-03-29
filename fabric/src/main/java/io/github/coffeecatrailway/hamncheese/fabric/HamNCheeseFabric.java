package io.github.coffeecatrailway.hamncheese.fabric;

import io.github.coffeecatrailway.hamncheese.HNCConfig;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class HamNCheeseFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        HamNCheese.PLATFORM.setup();

        final HNCConfig.Server config = HamNCheese.CONFIG_SERVER;
        if (config.mapleTreeWeight.get() > 0)
            BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.MAPLE_TREE_KEY);

        if (config.wildPineapplesGenerate.get())
            BiomeModifications.addFeature(BiomeSelectors.categories(config.wildPineapplesCategoryWhitelist.get().toArray(Biome.BiomeCategory[]::new)), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.WILD_PINEAPPLE_KEY);

        if (config.wildTomatoesGenerate.get())
            BiomeModifications.addFeature(BiomeSelectors.categories(config.wildTomatoesCategoryWhitelist.get().toArray(Biome.BiomeCategory[]::new)), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.WILD_TOMATO_KEY);

        if (config.wildCornGenerate.get())
            BiomeModifications.addFeature(BiomeSelectors.categories(config.wildCornCategoryWhitelist.get().toArray(Biome.BiomeCategory[]::new)), GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.WILD_CORN_KEY);

        if (config.canSpawnMouse())
            BiomeModifications.addSpawn(BiomeSelectors.categories(config.mouseCategoryWhitelist.get().toArray(Biome.BiomeCategory[]::new)), MobCategory.CREATURE, HNCEntities.MOUSE.get(), config.mouseSpawnWeight.get(), config.mouseMinCount.get(), config.mouseMaxCount.get());
    }
}
