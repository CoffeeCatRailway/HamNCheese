package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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
    private static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, HamNCheese.MOD_ID);
    private static final PollinatedRegistry<PlacedFeature> PLACEMENTS = PollinatedRegistry.create(BuiltinRegistries.PLACED_FEATURE, HamNCheese.MOD_ID);

    public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE = registerConfiguredFeature("maple_tree", () -> Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(HNCBlocks.MAPLE_LOG.get()),
            new StraightTrunkPlacer(10, 1, 2),
            BlockStateProvider.simple(HNCBlocks.MAPLE_LEAVES.get()),
            new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(0, 2)),
            new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build()));

    public static final Supplier<PlacedFeature> MAPLE_TREE_CHECKED = registerPlacedFeature("maple_tree", () -> MAPLE_TREE.get().placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, HamNCheese.CONFIG_SERVER.mapleTreeWeight.get().floatValue(), 1), HNCBlocks.MAPLE_SAPLING.get())));
    public static final ResourceKey<PlacedFeature> MAPLE_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, HamNCheese.getLocation("maple_tree"));

    private static <FC extends FeatureConfiguration> Supplier<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, Supplier<ConfiguredFeature<FC, ?>> feature)
    {
        return CONFIGURED_FEATURES.register(id, feature);
    }

    private static Supplier<PlacedFeature> registerPlacedFeature(String id, Supplier<PlacedFeature> feature)
    {
        return PLACEMENTS.register(id, feature);
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        CONFIGURED_FEATURES.register(platform);
        PLACEMENTS.register(platform);
    }
}
