package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import io.github.coffeecatrailway.hamncheese.registry.HNCStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 27/04/2021
 */
public class ChoppingBoardBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_NS = Block.box(4d, 0d, 2d, 12d, 1d, 14d);
    private static final VoxelShape SHAPE_EW = Block.box(2d, 0d, 4d, 14d, 1d, 12d);

    public ChoppingBoardBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(FACING))
                {
                    case NORTH, SOUTH -> SHAPE_NS;
                    default -> SHAPE_EW;
                };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChoppingBoardBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean tisABoolean)
    {
        if (!state.is(newState.getBlock()))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ChoppingBoardBlockEntity)
            {
                Containers.dropContents(level, pos, (ChoppingBoardBlockEntity) blockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, tisABoolean);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide)
        {
            ChoppingBoardBlockEntity blockEntity = level.getBlockEntity(pos, HNCBlockEntities.CHOPPING_BOARD.get()).orElse(null);
            if (blockEntity != null)
            {
                boolean award = false;
                ItemStack heldItem = player.getMainHandItem();
                if (!heldItem.isEmpty() && blockEntity.placeIngredient(heldItem, false, player))
                    award = true;
                else
                {
                    ChoppingBoardRecipe recipe = level.getRecipeManager().getRecipesFor(HNCRecipes.CHOPPING_BOARD_TYPE.get(), blockEntity, level).stream().filter(recipeFilter -> recipeFilter.matches(blockEntity, level) && recipeFilter.getTool().test(heldItem)).findFirst().orElse(null);
                    if (recipe != null)
                    {
                        if (!player.isCreative())
                            heldItem.setDamageValue(heldItem.getDamageValue() + 1);
                        blockEntity.setRecipeUsed(recipe);

                        blockEntity.setIngredient(recipe.assemble(blockEntity));
                        award = true;
                    } else if (heldItem.isEmpty() && !blockEntity.getItem(0).isEmpty())
                    {
                        if (blockEntity.getRecipeUsed() != null)
                            blockEntity.awardUsedRecipes(player);

                        blockEntity.dropIngredient(player);
                        award = true;
                    }
                }
                if (award)
                {
                    player.awardStat(HNCStats.INTERACT_CHOPPING_BOARD);
                    if (player instanceof ServerPlayer)
                        HamNCheese.CHOPPING_BOARD_TRIGGER.trigger((ServerPlayer) player);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos clickedPos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(clickedPos);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1)
    {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();
        else
        {
            if (state.getValue(WATERLOGGED))
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

            return super.updateShape(state, direction, state1, level, pos, pos1);
        }
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
