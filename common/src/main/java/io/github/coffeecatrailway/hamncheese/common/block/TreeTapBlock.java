package io.github.coffeecatrailway.hamncheese.common.block;

import com.mojang.math.Constants;
import gg.moonflower.pollen.api.util.VoxelShapeHelper;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 9/09/2021
 */
public class TreeTapBlock extends Block
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape[] SHAPES = createShapes();

    private static VoxelShape[] createShapes()
    {
        List<VoxelShapeHelper.Builder> shapes = new ArrayList<>();

        // No Bucket
        for (Direction direction : Direction.Plane.HORIZONTAL)
            shapes.add(new VoxelShapeHelper.Builder().append(VoxelShapeHelper.makeCuboidShape(6.5d, 10d, 0d, 9.5d, 15d, 4d, direction)));
        // Bucket
        for (Direction direction : Direction.Plane.HORIZONTAL)
            shapes.add(new VoxelShapeHelper.Builder().append(
                    VoxelShapeHelper.makeCuboidShape(6.5d, 10d, 0d, 9.5d, 15d, 4d, direction),
                    VoxelShapeHelper.makeCuboidShape(4d, 1d, 1d, 12, 12d, 7d, direction)
            ));

        return shapes.stream().map(VoxelShapeHelper.Builder::build).toArray(VoxelShape[]::new);
    }

    public static final IntegerProperty SAP_LEVEL = IntegerProperty.create("sap_level", 0, 3);
    public static final BooleanProperty BUCKET = BooleanProperty.create("bucket");

    public TreeTapBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SAP_LEVEL, 0).setValue(BUCKET, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(FACING).getOpposite().get2DDataValue() + (state.getValue(BUCKET) ? 4 : 0)];
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1)
    {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();

        return super.updateShape(state, direction, state1, level, pos, pos1);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> drops = super.getDrops(state, builder);
        if (!state.getValue(BUCKET))
            return drops;

        if (state.getValue(SAP_LEVEL) == 3)
            drops.add(new ItemStack(HNCFluids.MAPLE_SAP_BUCKET.get()));
        else
            drops.add(new ItemStack(Items.BUCKET));
        return drops;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(BUCKET);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        int sapLevel = state.getValue(SAP_LEVEL);
        if (sapLevel < 3)
        {
            if (sapLevel + 1 == 3)
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1f, 1f);
            this.setSapLevel(level, pos, state, sapLevel + 1, true);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult)
    {
        ItemStack handStack = player.getItemInHand(hand);
        int sapLevel = state.getValue(SAP_LEVEL);
        boolean hasBucket = state.getValue(BUCKET);
        if (handStack.isEmpty())
        {
            if (hasBucket)
            {
                if (sapLevel == 0)
                {
                    this.givePlayerItem(player, new ItemStack(Items.BUCKET));
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, false);
                    return InteractionResult.SUCCESS;
                }
                if (sapLevel == 3)
                {
                    handStack.shrink(1);
                    this.givePlayerItem(player, new ItemStack(HNCFluids.MAPLE_SAP_BUCKET.get()));
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, false);
                    return InteractionResult.SUCCESS;
                }
            }
        } else
        {
            if (hasBucket)
            {
                if (handStack.getItem().equals(HNCItems.MAPLE_SAP_BOTTLE.get()) && sapLevel + 1 <= 3)
                {
                    handStack.shrink(1);
                    this.givePlayerItem(player, new ItemStack(Items.GLASS_BOTTLE));
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, sapLevel + 1, true);
                    return InteractionResult.SUCCESS;
                }
                if (handStack.getItem().equals(Items.GLASS_BOTTLE) && sapLevel > 0)
                {
                    handStack.shrink(1);
                    this.givePlayerItem(player, new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get()));
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, sapLevel - 1, true);
                    return InteractionResult.SUCCESS;
                }
            } else
            {
                if (handStack.getItem().equals(Items.BUCKET))
                {
                    handStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, true);
                    return InteractionResult.SUCCESS;
                }
                if (handStack.getItem().equals(HNCFluids.MAPLE_SAP_BUCKET.get()))
                {
                    handStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 3, true);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private void givePlayerItem(Player player, ItemStack stack)
    {
        if (!player.getInventory().add(stack))
            player.drop(stack, false);
    }

    public void setSapLevel(Level level, BlockPos pos, BlockState state, int sap, boolean hasBucket)
    {
        level.setBlock(pos, state.setValue(SAP_LEVEL, Mth.clamp(sap, 0, 3)).setValue(BUCKET, hasBucket), 2);
        level.updateNeighbourForOutputSignal(pos, this);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        Direction dir = state.getValue(FACING).getOpposite();
        BlockState relative = level.getBlockState(pos.relative(dir));
        return relative.is(HNCBlocks.MAPLE_LOG.get()) || relative.is(HNCBlocks.MAPLE_WOOD.get()) && FaceAttachedHorizontalDirectionalBlock.canAttach(level, pos, dir);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return state.getValue(SAP_LEVEL) + (state.getValue(BUCKET) ? 1 : 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, SAP_LEVEL, BUCKET);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }
}
