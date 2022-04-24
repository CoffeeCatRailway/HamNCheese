package io.github.coffeecatrailway.hamncheese.common.block;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 12/04/2021
 */
public abstract class AbstractDoubleCropBlock extends BushBlock implements BonemealableBlock
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public AbstractDoubleCropBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public abstract IntegerProperty getAgeProperty();

    public abstract int getMaxAge();

    protected abstract VoxelShape[] getTopShapes();

    protected abstract VoxelShape[] getBottomShapes();

    protected abstract boolean placeableOn(BlockState state, BlockGetter level, BlockPos pos);

    protected abstract boolean needsFertileLand();

    protected abstract Item getPickBlock(BlockState state);

    protected abstract void bonemeal(int age, ServerLevel level, BlockPos pos, BlockState state);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(HALF))
                {
                    case UPPER -> this.getTopShapes()[state.getValue(this.getAgeProperty())];
                    case LOWER -> this.getBottomShapes()[state.getValue(this.getAgeProperty())];
                };
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return this.placeableOn(state, level, pos) && (level.getBlockState(pos.above(2)).isAir() || level.getBlockState(pos.above(2)).getBlock() == this);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos below = pos.below();
        BlockState stateBelow = level.getBlockState(below);
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && (this.mayPlaceOn(stateBelow, level, below) ||
                (state.getBlock() == this && stateBelow.getBlock() == this && state.getValue(HALF) == DoubleBlockHalf.UPPER && stateBelow.getValue(HALF) == DoubleBlockHalf.LOWER));
    }

    protected BlockState getStateForAge(int age, DoubleBlockHalf half)
    {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age).setValue(HALF, half);
    }

    protected boolean isYoung(BlockState state)
    {
        return state.getValue(this.getAgeProperty()) < this.getMaxAge();
    }

    protected float getGrowthSpeed(ServerLevel level, BlockPos pos)
    {
        float speed = 1.125f;
        if (level.canSeeSky(pos))
            speed += 2f;
        if (this.needsFertileLand() && (this.isFertile(level.getBlockState(pos.below())) || this.isFertile(level.getBlockState(pos.below(2)))))
            speed *= 1.5f;
        return speed;
    }

    private boolean isFertile(BlockState state)
    {
        return state.is(Blocks.FARMLAND) && state.getValue(FarmBlock.MOISTURE) > 0;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (entity instanceof Ravager && mobGriefing(level, entity))
            level.destroyBlock(pos, true, entity);
        super.entityInside(state, level, pos, entity);
    }
    
    @ExpectPlatform
    private static boolean mobGriefing(Level level, Entity entity)
    {
        return Platform.error();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos pos, BlockState state)
    {
        return new ItemStack(this.getPickBlock(state));
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean bl)
    {
        return this.isYoung(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state)
    {
        int age = state.getValue(this.getAgeProperty()) + Mth.nextInt(random, 2, 3);
        if (age > this.getMaxAge())
            age = this.getMaxAge();
        this.bonemeal(age, level, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(this.getAgeProperty()).add(HALF);
    }
}
