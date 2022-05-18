package io.github.coffeecatrailway.hamncheese.common.block;

import gg.moonflower.pollen.api.util.VoxelShapeHelper;
import io.github.coffeecatrailway.hamncheese.common.block.entity.FoodCookerBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.GrillBlockEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 3/06/2021
 */
public class GrillBlock extends BaseFoodMakerBlock
{
    private static final VoxelShape[] SHAPES = createShapes();

    private static VoxelShape[] createShapes()
    {
        List<VoxelShapeHelper.Builder> shapes = new ArrayList<>();

        for (Direction direction : Direction.Plane.HORIZONTAL)
            shapes.add(new VoxelShapeHelper.Builder().append(
                    VoxelShapeHelper.makeCuboidShape(0d, 11d, 0d, 16d, 16d, 16d, direction),
                    VoxelShapeHelper.makeCuboidShape(1d, 0d, 1d, 3d, 11d, 15d, direction),
                    VoxelShapeHelper.makeCuboidShape(13d, 0d, 1d, 15d, 11d, 15d, direction),
                    VoxelShapeHelper.makeCuboidShape(3d, 6.5d, 4.5d, 12.5d, 8.5d, 13d, direction)
            ));

        return shapes.stream().map(VoxelShapeHelper.Builder::build).toArray(VoxelShape[]::new);
    }

    public GrillBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(FACING).getOpposite().get2DDataValue()];
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new GrillBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, HNCBlockEntities.GRILL.get(), FoodCookerBlockEntity::tick);
    }

    @Override
    protected ResourceLocation getInteractWithStat()
    {
        return HNCStats.INTERACT_GRILL;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        if (state.getValue(LIT)) {
            double x = pos.getX();
            double y = (double) pos.getY() + .95d;
            double z = pos.getZ();
            if (random.nextDouble() < .1d)
                level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);

            double xo = random.nextDouble() * .9d + .1d;
            double zo = random.nextDouble() * .9d + .1d;
            level.addParticle(ParticleTypes.SMOKE, x + xo, y, z + zo, 0d, 0d, 0d);
            level.addParticle(ParticleTypes.FLAME, x + xo, y, z + zo, 0d, 0d, 0d);
        }
    }
}
