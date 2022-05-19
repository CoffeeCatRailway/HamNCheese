package io.github.coffeecatrailway.hamncheese.common.block;

import gg.moonflower.pollen.api.util.VoxelShapeHelper;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 2/08/2021
 */
public class PopcornMachineBlock extends BaseFoodMakerBlock
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
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PopcornMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return createTicker(level, type, HNCBlockEntities.POPCORN_MACHINE.get(), PopcornMachineBlockEntity::tick);
    }

    @Override
    protected ResourceLocation getInteractWithStat()
    {
        return HNCStats.INTERACT_POPCORN_MACHINE;
    }
}
