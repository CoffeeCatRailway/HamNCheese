package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.HNCConfig;
import coffeecatrailway.hamncheese.common.tileentity.CurdlerTileEntity;
import io.github.ocelot.sonar.common.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 12/10/2021
 */
public class CurdlerBlock extends Block
{
    private static final VoxelShape UPPER_SHAPE = new VoxelShapeHelper.Builder().append(VoxelShapeHelper.makeCuboidShape(7.25f, 0f, 7.25f, 8.75f, 14f, 8.75f, Direction.NORTH)).build();
    private static final VoxelShape LOWER_SHAPE = new VoxelShapeHelper.Builder().append(VoxelShapeHelper.makeCuboidShape(1f, 0f, 1f, 15f, 8f, 15f, Direction.NORTH), VoxelShapeHelper.makeCuboidShape(0f, 8f, 0f, 16f, 16f, 16f, Direction.NORTH)).build();

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public CurdlerBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
    {
        TileEntity tile = level.getBlockEntity(state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos);
        if (tile instanceof CurdlerTileEntity)
        {
            CurdlerTileEntity curdler = (CurdlerTileEntity) tile;
            ItemStack heldStack = player.getItemInHand(hand);
            Optional<FluidStack> heldFluid = FluidUtil.getFluidContained(heldStack);
            int emptySpace = curdler.getMilkCapacity() - curdler.getMilk();

            if (heldStack.isEmpty())
            {
                curdler.turn();
                player.causeFoodExhaustion(HNCConfig.SERVER.crankExhaustion.get().floatValue());
                return ActionResultType.SUCCESS;
            } else if (heldFluid.isPresent() && heldFluid.get().getRawFluid() == ForgeMod.MILK.get() && emptySpace >= heldFluid.get().getAmount())
            {
                curdler.addMilk(heldFluid.get().getAmount());
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
                if (!player.isCreative())
                    player.setItemInHand(hand, heldStack.getContainerItem());
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return this.hasTileEntity(state) ? new CurdlerTileEntity() : null;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, IWorld level, BlockPos pos, BlockPos newPos)
    {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP))
            return newState.is(this) && newState.getValue(HALF) != half ? state : Blocks.AIR.defaultBlockState();
        else
            return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, newState, level, pos, newPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos posClicked = context.getClickedPos();
        World level = context.getLevel();
        if (posClicked.getY() < level.getHeight() && level.getBlockState(posClicked.above()).canBeReplaced(context))
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        return null;
    }

    @Override
    public void setPlacedBy(World level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Constants.BlockFlags.DEFAULT_AND_RERENDER);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? stateBelow.isFaceSturdy(level, posBelow, Direction.UP) : stateBelow.is(this);
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos)
    {
        return MathHelper.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HALF);
    }
}
