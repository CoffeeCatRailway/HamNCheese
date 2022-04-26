package io.github.coffeecatrailway.hamncheese.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.coffeecatrailway.hamncheese.common.world.feature.configurations.TallCropFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 25/04/2022
 */
public class TallCropFeature extends Feature<TallCropFeatureConfiguration>
{
    public TallCropFeature(Codec<TallCropFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TallCropFeatureConfiguration> context)
    {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos mutableBlockPos = origin.mutable();
        Random random = context.random();
        TallCropFeatureConfiguration config = context.config();
        float probability = config.probability;

        if (level.isEmptyBlock(mutableBlockPos) && random.nextFloat() < probability)
        {
            int spread = random.nextInt(config.spread) + 1;
            for(int x = origin.getX() - spread; x <= origin.getX() + spread; x++)
            {
                for (int z = origin.getZ() - spread; z <= origin.getZ() + spread; z++)
                {
                    if (random.nextFloat() < probability)
                    {
                        int i = x - origin.getX();
                        int j = z - origin.getZ();
                        if (i * i + j * j <= spread * spread)
                        {
                            mutableBlockPos.set(x, level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - 1, z);
                            if (this.validGround(level.getBlockState(mutableBlockPos)))
                            {
                                level.setBlock(mutableBlockPos.above(), config.bottomState.getState(random, mutableBlockPos.above()), 2);
                                level.setBlock(mutableBlockPos.above(2), config.topState.getState(random, mutableBlockPos.above(2)), 2);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean validGround(BlockState state)
    {
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT);
    }
}
