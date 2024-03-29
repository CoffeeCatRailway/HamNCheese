package io.github.coffeecatrailway.hamncheese.common.block.grower;

import io.github.coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class MapleTreeGrower extends AbstractTreeGrower
{
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean hasFlowers)
    {
        return Holder.direct(HNCFeatures.Configured.MAPLE_TREE.get());
    }
}
