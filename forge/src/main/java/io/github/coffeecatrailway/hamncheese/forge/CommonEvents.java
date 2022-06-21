package io.github.coffeecatrailway.hamncheese.forge;

import io.github.coffeecatrailway.hamncheese.HNCConfig;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 19/04/2022
 */
@Mod.EventBusSubscriber(modid = HamNCheese.MOD_ID)
public class CommonEvents
{
    public static void init(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        final HNCConfig.Server config = HamNCheese.CONFIG_SERVER;

        if (config.mapleTreeWeight.get() > 0 && (event.getCategory() == Biome.BiomeCategory.PLAINS || event.getCategory() == Biome.BiomeCategory.RIVER))
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(HNCFeatures.Configured.MAPLE_TREE_PLACEMENT.get()));

        if (config.wildPineapplesGenerate.get() && config.wildPineapplesCategoryWhitelist.get().contains(event.getCategory()))
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(HNCFeatures.Configured.WILD_PINEAPPLE_PLACEMENT.get()));

        if (config.wildTomatoesGenerate.get() && config.wildTomatoesCategoryWhitelist.get().contains(event.getCategory()))
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(HNCFeatures.Configured.WILD_TOMATO_PLACEMENT.get()));

        if (config.wildCornGenerate.get() && config.wildCornCategoryWhitelist.get().contains(event.getCategory()))
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(HNCFeatures.Configured.WILD_CORN_PLACEMENT.get()));

        List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
        if (config.canSpawnMouse() && config.mouseCategoryWhitelist.get().contains(event.getCategory()))
        {
            creatureSpawns.add(new MobSpawnSettings.SpawnerData(HNCEntities.MOUSE.get(), config.mouseSpawnWeight.get(), config.mouseMinCount.get(), config.mouseMaxCount.get()));
        } else
        {
            List<MobSpawnSettings.SpawnerData> mouseSpawns = creatureSpawns.stream().filter(spawner -> spawner.type.equals(HNCEntities.MOUSE.get())).toList();
            if (mouseSpawns.size() > 0)
                creatureSpawns.removeAll(mouseSpawns);
        }
    }
}
