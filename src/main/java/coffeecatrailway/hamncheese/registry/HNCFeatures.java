package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.world.DoubleCropBlockPlacer;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;

/**
 * @author CoffeeCatRailway
 * Created: 15/04/2021
 */
public class HNCFeatures
{
    // Feature Config
    public static final BlockClusterFeatureConfig WILD_PINEAPPLE_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HNCBlocks.PINEAPPLE_PLANT.get().defaultBlockState()),
            new DoubleCropBlockPlacer()).tries(10).xspread(2).zspread(2).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL)).noProjection().build();
    public static final BlockClusterFeatureConfig WILD_TOMATO_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HNCBlocks.TOMATO_PLANT.get().defaultBlockState()),
            new DoubleCropBlockPlacer()).tries(10).xspread(2).zspread(2).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();

    // Configured Feature
    public static final ConfiguredFeature<?, ?> WILD_PINEAPPLE_PATCH = Feature.RANDOM_PATCH.configured(WILD_PINEAPPLE_PATCH_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP).chance(HNCMod.SERVER_CONFIG.chanceWildPineapples.get());
    public static final ConfiguredFeature<?, ?> WILD_TOMATO_PATCH = Feature.RANDOM_PATCH.configured(WILD_TOMATO_PATCH_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP).chance(HNCMod.SERVER_CONFIG.chanceWildTomatoes.get());

    // Configured Feature
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String id, ConfiguredFeature<FC, ?> configuredFeature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public static void registerConfiguredFeatures()
    {
        registerConfiguredFeature("wild_pineapple_patch", WILD_PINEAPPLE_PATCH);
        registerConfiguredFeature("wild_tomato_patch", WILD_TOMATO_PATCH);
    }
}
