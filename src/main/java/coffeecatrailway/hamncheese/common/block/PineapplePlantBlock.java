package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class PineapplePlantBlock extends BushBlock implements IGrowable
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

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
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
    {
        switch (state.getValue(HALF))
        {
            case UPPER:
                return SHAPES_TOP[state.getValue(AGE)];
            default:
            case LOWER:
                return SHAPES_BOTTOM[state.getValue(AGE)];
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos)
    {
        return super.mayPlaceOn(state, world, pos) && !state.is(Blocks.FARMLAND);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockPos below = pos.below();
        BlockState stateBelow = world.getBlockState(below);
        return (world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && (this.mayPlaceOn(stateBelow, world, below) ||
                (state.getBlock() == this && stateBelow.getBlock() == this && state.getValue(HALF) == DoubleBlockHalf.UPPER && stateBelow.getValue(HALF) == DoubleBlockHalf.LOWER));
    }

    private BlockState getStateForAge(int age, DoubleBlockHalf half)
    {
        return this.defaultBlockState().setValue(AGE, age).setValue(HALF, half);
    }

    private boolean isYoung(BlockState state)
    {
        return state.getValue(AGE) < 4;
    }

    private void grow(BlockState state, BlockState newState, ServerWorld world, BlockPos pos, Random random)
    {
        float growthSpeed = this.getGrowthSpeed(state, world, pos);
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

    private float getGrowthSpeed(BlockState state, ServerWorld world, BlockPos pos)
    {
        float speed = 1.125f;
        if (world.canSeeSky(pos))
            speed += 2f;
        return speed;
    }

    //    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity)
    {
        if (entity instanceof RavagerEntity && ForgeEventFactory.getMobGriefingEvent(world, entity))
            world.destroyBlock(pos, true, entity);
        super.entityInside(state, world, pos, entity);
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader world, BlockPos pos, BlockState state)
    {
        return new ItemStack(HNCItems.PINEAPPLE_PLANT.get());
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean p_176473_4_)
    {
        return this.isYoung(state);
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        int age = state.getValue(AGE) + MathHelper.nextInt(random, 2, 3);
        if (age > 4)
            age = 4;
        world.setBlock(pos, this.getStateForAge(age, state.getValue(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE).add(HALF);
    }
}
