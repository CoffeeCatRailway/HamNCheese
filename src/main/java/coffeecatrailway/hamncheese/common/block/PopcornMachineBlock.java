package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCStats;
import io.github.ocelot.sonar.common.util.VoxelShapeHelper;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

/**
 * @author CoffeeCatRailway
 * Created: 2/08/2021
 */
public class PopcornMachineBlock extends ContainerBaseBlock
{
    private static final VoxelShape SHAPE = new VoxelShapeHelper.Builder().append(
            VoxelShapeHelper.makeCuboidShape(0d, 1d, 0d, 16d, 16d, 16d, Direction.NORTH),
            VoxelShapeHelper.makeCuboidShape(1d, 0d, 1d, 3d, 1d, 3d, Direction.NORTH),
            VoxelShapeHelper.makeCuboidShape(1d, 0d, 13d, 3d, 1d, 15d, Direction.NORTH),
            VoxelShapeHelper.makeCuboidShape(13d, 0d, 13d, 15d, 1d, 15d, Direction.NORTH),
            VoxelShapeHelper.makeCuboidShape(13d, 0d, 1d, 15d, 1d, 3d, Direction.NORTH)).build();

    public PopcornMachineBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    protected TileEntity getTileEntity(BlockState state, IBlockReader world)
    {
        return null;
    }

    @Override
    protected ResourceLocation getInteractWithStat()
    {
        return HNCStats.INTERACT_POPCORN_MACHINE;
    }
}
