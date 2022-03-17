package coffeecatrailway.hamncheese.common.block;

import io.github.ocelot.sonar.common.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

/**
 * @author CoffeeCatRailway
 * Created: 27/04/2021
 */
public class ChoppingBoardBlock extends BaseBlock implements IWaterLoggable
{
    private static final VoxelShape SHAPE_NS = Block.box(4d, 0d, 2d, 12d, 1d, 14d);
    private static final VoxelShape SHAPE_EW = Block.box(2d, 0d, 4d, 14d, 1d, 12d);

    public ChoppingBoardBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        switch (state.getValue(HORIZONTAL_FACING))
        {
            case NORTH:
            case SOUTH:
                return SHAPE_NS;
            default:
                return SHAPE_EW;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }
}
