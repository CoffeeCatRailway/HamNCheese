package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.common.tileentity.HNCLockableTileEntity;
import io.github.ocelot.sonar.common.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 10/05/2021
 */
public abstract class ContainerBaseBlock extends BaseBlock implements IWaterLoggable
{
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ContainerBaseBlock(Properties properties)
    {
        super(properties);
        BlockState state = this.getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(LIT, false);
        this.registerDefaultState(state);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean p_196243_5_)
    {
        if (!state.is(newState.getBlock()))
        {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof HNCLockableTileEntity)
            {
                InventoryHelper.dropContents(world, pos, (HNCLockableTileEntity) tileentity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, p_196243_5_);
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, LIT);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getBlockEntity(pos);
        return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (stack.hasCustomHoverName())
        {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof LockableTileEntity)
                ((LockableTileEntity) tile).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
    {
        if (!world.isClientSide())
        {
            INamedContainerProvider provider = this.getMenuProvider(state, world, pos);
            if (provider != null)
            {
                player.openMenu(provider);
                player.awardStat(this.getInteractWithStat());
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    protected abstract TileEntity getTileEntity(BlockState state, IBlockReader world);

    protected abstract ResourceLocation getInteractWithStat();

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return this.getTileEntity(state, world);
    }
}
