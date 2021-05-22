package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.common.tileentity.PizzaOvenTileEntity;
import coffeecatrailway.hamncheese.registry.HNCStats;
import io.github.ocelot.sonar.common.util.VoxelShapeHelper;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 10/05/2021
 */
public class PizzaOvenBlock extends ContainerBaseBlock
{
    private static final VoxelShape[] SHAPES = createShapes();

    private static VoxelShape[] createShapes()
    {
        List<VoxelShapeHelper.Builder> shapes = new ArrayList<>();

        for (Direction direction : Direction.Plane.HORIZONTAL)
            shapes.add(new VoxelShapeHelper.Builder().append(
                    VoxelShapeHelper.makeCuboidShape(1d, 0d, 0d, 15d, 8d, 15d, direction),
                    VoxelShapeHelper.makeCuboidShape(3d, 8d, 3d, 13d, 10d, 13d, direction),
                    VoxelShapeHelper.makeCuboidShape(6.5d, 10d, 3.5d, 9.5d, 14d, 6.5d, direction)
            ));

        return shapes.stream().map(VoxelShapeHelper.Builder::build).toArray(VoxelShape[]::new);
    }

    public PizzaOvenBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.getValue(HORIZONTAL_FACING).getOpposite().get2DDataValue()];
    }

    @Override
    protected TileEntity getTileEntity(BlockState state, IBlockReader world)
    {
        return new PizzaOvenTileEntity();
    }

    @Override
    protected ResourceLocation getInteractWithStat()
    {
        return HNCStats.INTERACT_PIZZA_OVEN;
    }

    @Override
    protected boolean hasLitState()
    {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT)) {
            double x = pos.getX();
            double y = (double) pos.getY() + 0.1d;
            double z = pos.getZ();
            if (rand.nextDouble() < 0.1d)
                world.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);

            double xo1 = rand.nextDouble() * 0.8d + 0.2d;
            double zo1 = rand.nextDouble() * 0.8d + 0.2d;
            world.addParticle(ParticleTypes.SMOKE, x + xo1, y, z + zo1, 0.0d, 0.0d, 0.0d);
            world.addParticle(ParticleTypes.FLAME, x + xo1, y, z + zo1, 0.0d, 0.0d, 0.0d);

            double xo2;
            double zo2;
            switch (state.getValue(HORIZONTAL_FACING)) {
                default:
                case NORTH:
                    xo2 = 0.5d;
                    zo2 = 0.3d;
                    break;
                case SOUTH:
                    xo2 = 0.5d;
                    zo2 = 0.7d;
                    break;
                case EAST:
                    xo2 = 0.7d;
                    zo2 = 0.5d;
                    break;
                case WEST:
                    xo2 = 0.3d;
                    zo2 = 0.5d;
                    break;
            }
            world.addParticle(ParticleTypes.SMOKE, x + xo2, y + 1d, z + zo2, 0.0d, 0.0d, 0.0d);
        }
    }
}
