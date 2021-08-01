package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

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
    protected boolean placeableOn(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.is(Blocks.FARMLAND) || state.is(Blocks.GRASS_BLOCK);
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
    protected void bonemeal(int age, ServerWorld world, BlockPos pos, BlockState state)
    {
        Block above = world.getBlockState(pos.above()).getBlock();
        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
        if (age > 2)
            world.setBlock(isLower ? pos.above() : pos, this.getStateForAge(age, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
        world.setBlock(isLower ? pos : pos.below(), this.getStateForAge(age, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_)
    {
        if (world.getBlockState(pos).getBlock() == this)
            return;
        if (world.getBlockState(pos.above()).getBlock() == this)
            world.destroyBlock(pos.above(), true);
        else if (world.getBlockState(pos.below()).getBlock() == this)
            world.destroyBlock(pos.below(), true);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && this.isYoung(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getRawBrightness(pos, 0) >= 9)
        {
            float growthSpeed = this.getGrowthSpeed(world, pos);
            if (ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25f / growthSpeed) + 1) == 0))
            {
                int age = state.getValue(AGE) + 1;
                if (age > 3)
                    world.setBlock(pos.above(), this.getStateForAge(age, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
                world.setBlock(pos, this.getStateForAge(age, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
                ForgeHooks.onCropsGrowPost(world, pos, state);
            }
        }
    }
}
