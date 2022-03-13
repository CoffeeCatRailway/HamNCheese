package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCConfig;
import coffeecatrailway.hamncheese.common.world.DoubleCropBlockPlacer;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

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
    public static final BlockClusterFeatureConfig WILD_CORN_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HNCBlocks.CORN_PLANT.get().defaultBlockState()),
            new DoubleCropBlockPlacer()).tries(10).xspread(2).zspread(2).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();

    // Configured Feature
    public static final ConfiguredFeature<?, ?> WILD_PINEAPPLE_PATCH = Feature.RANDOM_PATCH.configured(WILD_PINEAPPLE_PATCH_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP).chance(HNCConfig.SERVER.chanceWildPineapples.get());
    public static final ConfiguredFeature<?, ?> WILD_TOMATO_PATCH = Feature.RANDOM_PATCH.configured(WILD_TOMATO_PATCH_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP).chance(HNCConfig.SERVER.chanceWildTomatoes.get());
    public static final ConfiguredFeature<?, ?> WILD_CORN_PATCH = Feature.RANDOM_PATCH.configured(WILD_CORN_PATCH_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP).chance(HNCConfig.SERVER.chanceWildCorn.get());

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE = Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(HNCBlocks.MAPLE_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(HNCBlocks.MAPLE_LEAVES.get().defaultBlockState()),
            new SpruceFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(0, 2), FeatureSpread.of(1, 1)),
            new StraightTrunkPlacer(10, 1, 2),
            new TwoLayerFeature(2, 0, 2)).ignoreVines().build());
    public static final ConfiguredFeature<?, ?> PLAINS_RIVER_MAPLE_TREE = MAPLE_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, .01f, 1)));

    // Configured Feature
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String id, ConfiguredFeature<FC, ?> configuredFeature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public static void registerConfiguredFeatures()
    {
        registerConfiguredFeature("wild_pineapple_patch", WILD_PINEAPPLE_PATCH);
        registerConfiguredFeature("wild_tomato_patch", WILD_TOMATO_PATCH);
        registerConfiguredFeature("wild_corn_patch", WILD_CORN_PATCH);

        registerConfiguredFeature("maple_tree", MAPLE_TREE);
        registerConfiguredFeature("plains_river_maple_tree", PLAINS_RIVER_MAPLE_TREE);
    }
}
