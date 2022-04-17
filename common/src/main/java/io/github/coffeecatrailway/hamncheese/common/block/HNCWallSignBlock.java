package io.github.coffeecatrailway.hamncheese.common.block;

import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * @author CoffeeCatRailway
 * Created: 7/09/2021
 */
public class HNCWallSignBlock extends WallSignBlock
{
    public HNCWallSignBlock(Properties properties, WoodType woodType)
    {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return HNCBlockEntities.SIGN.get().create(blockPos, blockState);
    }
}
