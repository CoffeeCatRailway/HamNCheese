package coffeecatrailway.hamncheese.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 27/04/2021
 */
public class ChoppingBoardBlock extends HorizontalBlock
{
    private static final VoxelShape SHAPE_NS = Block.box(4d, 0d, 2d, 12d, 1d, 14d);
    private static final VoxelShape SHAPE_EW = Block.box(2d, 0d, 4d, 14d, 1d, 12d);

    public ChoppingBoardBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        switch (state.getValue(FACING))
        {
            default:
            case NORTH:
            case SOUTH:
                return SHAPE_NS;
            case EAST:
            case WEST:
                return SHAPE_EW;
        }
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
