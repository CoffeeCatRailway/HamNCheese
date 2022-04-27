package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 1/08/2021
 */
public class CornPlantBlock extends AbstractDoubleCropBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);

    private static final VoxelShape[] SHAPES_TOP = new VoxelShape[]{
            Block.box(0d, 0d, 0d, 16d, 1d, 16d),
            Block.box(0d, 0d, 0d, 16d, 1d, 16d),
            Block.box(0d, 0d, 0d, 16d, 1d, 16d),
            Block.box(4d, 0d, 4d, 12d, 5d, 12d),
            Block.box(4d, 0d, 4d, 12d, 9d, 12d),
            Block.box(4d, 0d, 4d, 12d, 14d, 12d),
            Block.box(3d, 0d, 3d, 13d, 15d, 13d)
    };
    private static final VoxelShape[] SHAPES_BOTTOM = new VoxelShape[]{
            Block.box(6d, 0d, 6d, 10d, 7d, 10d),
            Block.box(4d, 0d, 4d, 12d, 11d, 12d),
            Block.box(3d, 0d, 3d, 12d, 16d, 12d),
            Block.box(3d, 0d, 3d, 13d, 16d, 13d),
            Block.box(3d, 0d, 3d, 13d, 16d, 13d),
            Block.box(3d, 0d, 3d, 13d, 16d, 13d),
            Block.box(2d, 0d, 2d, 14d, 16d, 14d)
    };

    public CornPlantBlock(Properties properties)
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
        return 6;
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
        return state.is(Blocks.FARMLAND);
    }

    @Override
    protected boolean needsFertileLand()
    {
        return true;
    }

    @Override
    protected Item getPickBlock(BlockState state)
    {
        return HNCItems.CORN_COB.get();
    }

    @Override
    protected void bonemeal(int age, ServerLevel level, BlockPos pos, BlockState state)
    {
        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
        if (age > 2)
            level.setBlock(isLower ? pos.above() : pos, this.getStateForAge(age, DoubleBlockHalf.UPPER), 2);
        level.setBlock(isLower ? pos : pos.below(), this.getStateForAge(age, DoubleBlockHalf.LOWER), 2);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_)
    {
        if (level.getBlockState(pos).getBlock() == this)
            return;
        if (level.getBlockState(pos.above()).getBlock() == this)
            level.destroyBlock(pos.above(), true);
        else if (level.getBlockState(pos.below()).getBlock() == this)
            level.destroyBlock(pos.below(), true);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && this.isYoung(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
//        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9)
        {
            float growthSpeed = this.getGrowthSpeed(level, pos);
            if (random.nextInt((int) (25f / growthSpeed) + 1) == 0) // ForgeHooks.onCropsGrowPre(level, pos, state, )
            {
                int age = state.getValue(AGE) + 1;
                if (age > 2)
                    level.setBlock(pos.above(), this.getStateForAge(age, DoubleBlockHalf.UPPER), 2);
                level.setBlock(pos, this.getStateForAge(age, DoubleBlockHalf.LOWER), 2);
//                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }
}
