package coffeecatrailway.hamncheese.common.block.trees;

import coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class MapleTree extends Tree
{
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasFlowers)
    {
        return HNCFeatures.MAPLE_TREE;
    }
}
