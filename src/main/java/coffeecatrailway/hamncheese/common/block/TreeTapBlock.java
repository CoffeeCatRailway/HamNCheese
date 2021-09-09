package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.ocelot.sonar.common.block.BaseBlock;
import io.github.ocelot.sonar.common.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 9/09/2021
 */
public class TreeTapBlock extends BaseBlock
{
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
        this.registerDefaultState(this.getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(SAP_LEVEL, 0).setValue(BUCKET, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.getValue(HORIZONTAL_FACING).getOpposite().get2DDataValue() + (state.getValue(BUCKET) ? 4 : 0)];
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(BUCKET);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random)
    {
        int sapLevel = state.getValue(SAP_LEVEL);
        if (sapLevel < 3)
        {
            if (sapLevel + 1 == 3)
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1f, 1f);
            this.setSapLevel(level, pos, state, sapLevel + 1, true);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result)
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
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundCategory.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, false);
                    return ActionResultType.SUCCESS;
                }
                if (sapLevel == 3)
                {
                    handStack.shrink(1);
//                    this.givePlayerItem(player, new ItemStack(HNCItems.MAPLE_BUCKET.get())); TODO: Add sap fluid
                    this.givePlayerItem(player, new ItemStack(Items.LAVA_BUCKET));
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundCategory.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, false);
                    return ActionResultType.SUCCESS;
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
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, sapLevel + 1, true);
                    return ActionResultType.SUCCESS;
                }
                if (handStack.getItem().equals(Items.GLASS_BOTTLE) && sapLevel > 0)
                {
                    handStack.shrink(1);
                    this.givePlayerItem(player, new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get()));
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, sapLevel - 1, true);
                    return ActionResultType.SUCCESS;
                }
            } else
            {
                if (handStack.getItem().equals(Items.BUCKET))
                {
                    handStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_CHAIN, SoundCategory.BLOCKS, 1f, 1f);
                    this.setSapLevel(level, pos, state, 0, true);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    private void givePlayerItem(PlayerEntity player, ItemStack stack)
    {
        if (!player.inventory.add(stack))
            player.drop(stack, false);
    }

    public void setSapLevel(World level, BlockPos pos, BlockState state, int sap, boolean hasBucket)
    {
        level.setBlock(pos, state.setValue(SAP_LEVEL, MathHelper.clamp(sap, 0, 3)).setValue(BUCKET, hasBucket), Constants.BlockFlags.DEFAULT);
        level.updateNeighbourForOutputSignal(pos, this);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos)
    {
        Direction dir = state.getValue(HORIZONTAL_FACING).getOpposite();
        BlockState relative = reader.getBlockState(pos.relative(dir));
        return (relative.is(HNCBlocks.MAPLE_LOG.get()) || relative.is(HNCBlocks.MAPLE_WOOD.get())) && HorizontalFaceBlock.canAttach(reader, pos, dir);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos)
    {
        return state.getValue(SAP_LEVEL) + (state.getValue(BUCKET) ? 1 : 0);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, SAP_LEVEL, BUCKET);
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader reader, BlockPos pos, PathType type)
    {
        return false;
    }
}
