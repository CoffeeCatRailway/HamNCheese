package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 11/04/2021
 */
public class TomatoPlantBlock extends AbstractDoubleCropBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 9);

    private static final VoxelShape SHAPE = Shapes.block();

    public TomatoPlantBlock(Properties properties)
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
        return 9;
    }

    @Override
    protected VoxelShape[] getTopShapes()
    {
        return new VoxelShape[]{};
    }

    @Override
    protected VoxelShape[] getBottomShapes()
    {
        return new VoxelShape[]{};
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
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
        return HNCItems.TOMATO_SEEDS.get();
    }

    @Override
    protected void bonemeal(int age, ServerLevel level, BlockPos pos, BlockState state)
    {
        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
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
                level.setBlock(pos.above(), this.getStateForAge(age, DoubleBlockHalf.UPPER), 2);
                level.setBlock(pos, this.getStateForAge(age, DoubleBlockHalf.LOWER), 2);
//                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 2);
    }
}
