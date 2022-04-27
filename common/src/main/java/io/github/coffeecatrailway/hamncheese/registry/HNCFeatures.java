package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.CornPlantBlock;
import io.github.coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import io.github.coffeecatrailway.hamncheese.common.block.TomatoPlantBlock;
import io.github.coffeecatrailway.hamncheese.common.world.feature.TallCropFeature;
import io.github.coffeecatrailway.hamncheese.common.world.feature.configurations.TallCropFeatureConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 15/04/2021
 */
public class HNCFeatures
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, HamNCheese.MOD_ID);

    private static final Supplier<Feature<TallCropFeatureConfiguration>> TALL_CROP = FEATURES.register("tall_crop", () -> new TallCropFeature(TallCropFeatureConfiguration.CODEC));

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        FEATURES.register(platform);
    }

    public static class Configured
    {
        private static final Logger LOGGER = LogManager.getLogger();
        private static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, HamNCheese.MOD_ID);
        private static final PollinatedRegistry<PlacedFeature> PLACEMENTS = PollinatedRegistry.create(BuiltinRegistries.PLACED_FEATURE, HamNCheese.MOD_ID);

        private static final Supplier<ConfiguredFeature<TallCropFeatureConfiguration, ?>> WILD_PINEAPPLE = CONFIGURED_FEATURES.register("wild_pineapple",
                () -> TALL_CROP.get().configured(new TallCropFeatureConfiguration(HamNCheese.CONFIG_SERVER.wildPineapplesProbability.get().floatValue(), HamNCheese.CONFIG_SERVER.wildPineapplesSpread.get(),
                        BlockStateProvider.simple(HNCBlocks.PINEAPPLE_PLANT.get().defaultBlockState().setValue(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER).setValue(PineapplePlantBlock.AGE, 4)),
                        BlockStateProvider.simple(HNCBlocks.PINEAPPLE_PLANT.get().defaultBlockState().setValue(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER).setValue(PineapplePlantBlock.AGE, 4)), false)));
        public static final Supplier<PlacedFeature> WILD_PINEAPPLE_PLACEMENT = PLACEMENTS.register("wild_pineapple", () -> WILD_PINEAPPLE.get().placed(RarityFilter.onAverageOnceEvery(HamNCheese.CONFIG_SERVER.wildPineapplesChance.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE));
        public static final ResourceKey<PlacedFeature> WILD_PINEAPPLE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, HamNCheese.getLocation("wild_pineapple"));

        private static final Supplier<ConfiguredFeature<TallCropFeatureConfiguration, ?>> WILD_TOMATO = CONFIGURED_FEATURES.register("wild_tomato",
                () -> TALL_CROP.get().configured(new TallCropFeatureConfiguration(HamNCheese.CONFIG_SERVER.wildTomatoesProbability.get().floatValue(), HamNCheese.CONFIG_SERVER.wildTomatoesSpread.get(),
                        BlockStateProvider.simple(HNCBlocks.TOMATO_PLANT.get().defaultBlockState().setValue(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER).setValue(TomatoPlantBlock.AGE, 9)),
                        BlockStateProvider.simple(HNCBlocks.TOMATO_PLANT.get().defaultBlockState().setValue(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER).setValue(TomatoPlantBlock.AGE, 9)), true)));
        public static final Supplier<PlacedFeature> WILD_TOMATO_PLACEMENT = PLACEMENTS.register("wild_tomato", () -> WILD_TOMATO.get().placed(RarityFilter.onAverageOnceEvery(HamNCheese.CONFIG_SERVER.wildTomatoesChance.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE));
        public static final ResourceKey<PlacedFeature> WILD_TOMATO_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, HamNCheese.getLocation("wild_tomato"));

        private static final Supplier<ConfiguredFeature<TallCropFeatureConfiguration, ?>> WILD_CORN = CONFIGURED_FEATURES.register("wild_corn",
                () -> TALL_CROP.get().configured(new TallCropFeatureConfiguration(HamNCheese.CONFIG_SERVER.wildCornProbability.get().floatValue(), HamNCheese.CONFIG_SERVER.wildCornSpread.get(),
                        BlockStateProvider.simple(HNCBlocks.CORN_PLANT.get().defaultBlockState().setValue(CornPlantBlock.HALF, DoubleBlockHalf.UPPER).setValue(CornPlantBlock.AGE, 6)),
                        BlockStateProvider.simple(HNCBlocks.CORN_PLANT.get().defaultBlockState().setValue(CornPlantBlock.HALF, DoubleBlockHalf.LOWER).setValue(CornPlantBlock.AGE, 6)), true)));
        public static final Supplier<PlacedFeature> WILD_CORN_PLACEMENT = PLACEMENTS.register("wild_corn", () -> WILD_CORN.get().placed(RarityFilter.onAverageOnceEvery(HamNCheese.CONFIG_SERVER.wildCornChance.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE));
        public static final ResourceKey<PlacedFeature> WILD_CORN_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, HamNCheese.getLocation("wild_corn"));

        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE = CONFIGURED_FEATURES.register("maple_tree", () -> Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(HNCBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(10, 1, 2),
                BlockStateProvider.simple(HNCBlocks.MAPLE_LEAVES.get()),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(0, 2)),
                new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build()));

        public static final Supplier<PlacedFeature> MAPLE_TREE_PLACEMENT = PLACEMENTS.register("maple_tree", () -> MAPLE_TREE.get().placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, HamNCheese.CONFIG_SERVER.mapleTreeWeight.get().floatValue(), 1), HNCBlocks.MAPLE_SAPLING.get())));
        public static final ResourceKey<PlacedFeature> MAPLE_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, HamNCheese.getLocation("maple_tree"));

        public static void load(Platform platform)
        {
            LOGGER.debug("Loaded");
            CONFIGURED_FEATURES.register(platform);
            PLACEMENTS.register(platform);
        }
    }
}
