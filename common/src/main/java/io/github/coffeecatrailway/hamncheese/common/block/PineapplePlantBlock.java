package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class PineapplePlantBlock extends AbstractDoubleCropBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);

    private static final VoxelShape[] SHAPES_TOP = new VoxelShape[]{
            Block.box(4d, 0d, 4d, 12d, 7d, 12d),
            Block.box(4d, 0d, 4d, 12d, 11d, 12d),
            Block.box(4d, 0d, 4d, 12d, 13d, 12d),
            Block.box(4d, 0d, 4d, 12d, 14d, 12d),
            Block.box(3d, 0d, 3d, 13d, 16d, 13d)
    };
    private static final VoxelShape[] SHAPES_BOTTOM = new VoxelShape[]{
            Block.box(6.5d, 0d, 6.5d, 9.5d, 6d, 9.5d),
            Block.box(6.5d, 0d, 6.5d, 9.5d, 8d, 9.5d),
            Block.box(6.5d, 0d, 6.5d, 9.5d, 12d, 9.5d),
            Block.box(6.5d, 0d, 6.5d, 9.5d, 14d, 9.5d),
            Block.box(5.5d, 0d, 5.5d, 10.5d, 16d, 10.5d)
    };

    public PineapplePlantBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public IntegerProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return 4;
    }

    @Override
    protected VoxelShape[] getTopShapes()
    {
        return SHAPES_TOP;
    }

    @Override
    protected VoxelShape[] getBottomShapes()
    {
        return SHAPES_BOTTOM;
    }

    @Override
    protected boolean placeableOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL)) && !state.is(Blocks.FARMLAND);
    }

    @Override
    protected boolean needsFertileLand()
    {
        return false;
    }

    @Override
    protected Item getPickBlock(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER && !this.isYoung(state) ? HNCItems.PINEAPPLE.get() : HNCItems.PINEAPPLE_PLANT.get();
    }

    @Override
    protected void bonemeal(int age, ServerLevel level, BlockPos pos, BlockState state)
    {
        level.setBlock(pos, this.getStateForAge(age, state.getValue(HALF)), 2);
    }

    private void grow(BlockState state, BlockState newState, ServerLevel level, BlockPos pos, Random random)
    {
        float growthSpeed = this.getGrowthSpeed(level, pos);
        if (random.nextInt((int) (25f / growthSpeed) + 1) == 0) // ForgeHooks.onCropsGrowPre(level, pos, state,
        {
            level.setBlock(pos, newState, 2);
//            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER || isYoung(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
//        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9)
        {
            DoubleBlockHalf half = state.getValue(HALF);
            if (this.isYoung(state))
                this.grow(state, this.getStateForAge(state.getValue(AGE) + 1, half), level, pos, random);
            else
            {
                if (half == DoubleBlockHalf.LOWER && level.isEmptyBlock(pos.above()))
                    this.grow(state, this.getStateForAge(0, DoubleBlockHalf.UPPER), level, pos.above(), random);
            }
        }
    }
}
