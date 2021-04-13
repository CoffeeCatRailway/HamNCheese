package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class PineapplePlantBlock extends AbstractDoublePlantBlock
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
    protected IntegerProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    protected int getMaxAge()
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
    protected boolean placeableOn(BlockState state, IBlockReader world, BlockPos pos)
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
    protected void bonemeal(int age, ServerWorld world, BlockPos pos, BlockState state)
    {
        world.setBlock(pos, this.getStateForAge(age, state.getValue(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
    }

    private void grow(BlockState state, BlockState newState, ServerWorld world, BlockPos pos, Random random)
    {
        float growthSpeed = this.getGrowthSpeed(world, pos);
        if (ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25f / growthSpeed) + 1) == 0))
        {
            world.setBlock(pos, newState, Constants.BlockFlags.BLOCK_UPDATE);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER || isYoung(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getRawBrightness(pos, 0) >= 9)
        {
            DoubleBlockHalf half = state.getValue(HALF);
            if (this.isYoung(state))
                this.grow(state, this.getStateForAge(state.getValue(AGE) + 1, half), world, pos, random);
            else
            {
                if (half == DoubleBlockHalf.LOWER && world.isEmptyBlock(pos.above()))
                    this.grow(state, this.getStateForAge(0, DoubleBlockHalf.UPPER), world, pos.above(), random);
            }
        }
    }
}
