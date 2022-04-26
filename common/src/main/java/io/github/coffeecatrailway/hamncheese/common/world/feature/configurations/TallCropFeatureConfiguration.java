package io.github.coffeecatrailway.hamncheese.common.world.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * @author CoffeeCatRailway
 * Created: 25/04/2022
 */
public class TallCropFeatureConfiguration implements FeatureConfiguration
{
    public static final Codec<TallCropFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.floatRange(0f, 1f).fieldOf("probability").forGetter(config -> config.probability),
            Codec.intRange(1, 10).fieldOf("spread").forGetter(config -> config.spread),
            BlockStateProvider.CODEC.fieldOf("topState").forGetter(config -> config.topState),
            BlockStateProvider.CODEC.fieldOf("bottomState").forGetter(config -> config.bottomState)
            ).apply(instance, TallCropFeatureConfiguration::new));

    public final float probability;
    public final int spread;
    public final BlockStateProvider topState;
    public final BlockStateProvider bottomState;

    public TallCropFeatureConfiguration(float probability, int spread, BlockStateProvider topState, BlockStateProvider bottomState) {
        this.probability = probability;
        this.spread = spread;
        this.topState = topState;
        this.bottomState = bottomState;
    }
}
