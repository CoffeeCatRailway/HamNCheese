package coffeecatrailway.hamncheese.common.block.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class MapleSapFluidBlock extends FlowingFluidBlock
{
    public MapleSapFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties)
    {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, World level, BlockPos pos, Entity entity)
    {
        entity.makeStuckInBlock(state, new Vector3d(.9d, .8d, .9d));
    }
}
