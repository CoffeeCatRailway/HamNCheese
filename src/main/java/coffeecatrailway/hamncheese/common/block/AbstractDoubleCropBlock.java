package coffeecatrailway.hamncheese.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.Item;
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
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 12/04/2021
 */
public abstract class AbstractDoubleCropBlock extends BushBlock implements IGrowable
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public AbstractDoubleCropBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(this.getAgeProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public abstract IntegerProperty getAgeProperty();
    public abstract int getMaxAge();

    protected abstract VoxelShape[] getTopShapes();
    protected abstract VoxelShape[] getBottomShapes();

    protected abstract boolean placeableOn(BlockState state, IBlockReader world, BlockPos pos);
    protected abstract boolean needsFertileLand();

    protected abstract Item getPickBlock(BlockState state);

    protected abstract void bonemeal(int age, ServerWorld world, BlockPos pos, BlockState state);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
    {
        switch (state.getValue(HALF))
        {
            case UPPER:
                return this.getTopShapes()[state.getValue(this.getAgeProperty())];
            default:
            case LOWER:
                return this.getBottomShapes()[state.getValue(this.getAgeProperty())];
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos)
    {
        return this.placeableOn(state, world, pos) && (world.getBlockState(pos.above(2)).isAir(world, pos.above(2)) || world.getBlockState(pos.above(2)).getBlock() == this);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockPos below = pos.below();
        BlockState stateBelow = world.getBlockState(below);
        return (world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && (this.mayPlaceOn(stateBelow, world, below) ||
                (state.getBlock() == this && stateBelow.getBlock() == this && state.getValue(HALF) == DoubleBlockHalf.UPPER && stateBelow.getValue(HALF) == DoubleBlockHalf.LOWER));
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
        return PlantType.CROP;
    }

    protected BlockState getStateForAge(int age, DoubleBlockHalf half)
    {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age).setValue(HALF, half);
    }

    protected boolean isYoung(BlockState state)
    {
        return state.getValue(this.getAgeProperty()) < this.getMaxAge();
    }

    protected float getGrowthSpeed(ServerWorld world, BlockPos pos)
    {
        float speed = 1.125f;
        if (world.canSeeSky(pos))
            speed += 2f;
        if (this.needsFertileLand() && (world.getBlockState(pos.below()).isFertile(world, pos) || world.getBlockState(pos.below(2)).isFertile(world, pos)))
            speed *= 1.5f;
        return speed;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity)
    {
        if (entity instanceof RavagerEntity && ForgeEventFactory.getMobGriefingEvent(world, entity))
            world.destroyBlock(pos, true, entity);
        super.entityInside(state, world, pos, entity);
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader world, BlockPos pos, BlockState state)
    {
        return new ItemStack(this.getPickBlock(state));
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
        int age = state.getValue(this.getAgeProperty()) + MathHelper.nextInt(random, 2, 3);
        if (age > this.getMaxAge())
            age = this.getMaxAge();
        this.bonemeal(age, world, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(this.getAgeProperty()).add(HALF);
    }
}
