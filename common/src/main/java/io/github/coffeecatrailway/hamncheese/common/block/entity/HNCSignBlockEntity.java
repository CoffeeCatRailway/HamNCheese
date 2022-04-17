package io.github.coffeecatrailway.hamncheese.common.block.entity;

import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author CoffeeCatRailway
 * Created: 7/09/2021
 */
public class HNCSignBlockEntity extends SignBlockEntity
{
    public HNCSignBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return HNCBlockEntities.SIGN.get();
    }
}
