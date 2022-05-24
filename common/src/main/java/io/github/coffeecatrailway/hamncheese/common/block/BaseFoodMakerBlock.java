package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.common.block.entity.BaseFoodMakerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 10/05/2021
 */
public abstract class BaseFoodMakerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BaseFoodMakerBlock(Properties properties)
    {
        super(properties);
        BlockState state = this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(LIT, false);
        this.registerDefaultState(state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_196243_5_)
    {
        if (!state.is(newState.getBlock()))
        {
            BlockEntity tileentity = level.getBlockEntity(pos);
            if (tileentity instanceof BaseFoodMakerBlockEntity)
            {
                Containers.dropContents(level, pos, (BaseFoodMakerBlockEntity) tileentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, p_196243_5_);
        }
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return state.getValue(LIT) ? super.getLightBlock(state, blockGetter, pos) : 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, WATERLOGGED, LIT);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (stack.hasCustomHoverName())
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseFoodMakerBlockEntity)
                ((BaseFoodMakerBlockEntity) blockEntity).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide())
        {
            MenuProvider provider = this.getMenuProvider(state, level, pos);
            if (provider != null)
            {
                player.openMenu(provider);
                player.awardStat(this.getInteractWithStat());
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected abstract ResourceLocation getInteractWithStat();

    @Nullable
    protected static <T extends BlockEntity, E extends BaseFoodMakerBlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<E> type2, BlockEntityTicker<? super E> ticker)
    {
        return level.isClientSide ? null : createTickerHelper(type, type2, ticker);
    }
}
