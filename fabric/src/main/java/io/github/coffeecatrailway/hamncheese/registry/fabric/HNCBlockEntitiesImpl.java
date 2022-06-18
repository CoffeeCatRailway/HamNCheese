package io.github.coffeecatrailway.hamncheese.registry.fabric;

import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.GrillBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PizzaOvenBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * @author CoffeeCatRailway
 * Created: 23/05/2022
 */
public class HNCBlockEntitiesImpl
{
    public static BlockEntityType.BlockEntitySupplier<PizzaOvenBlockEntity> getPizzaOven()
    {
        return PizzaOvenBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<GrillBlockEntity> getGrill()
    {
        return GrillBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<PopcornMachineBlockEntity> getPopcornMachine()
    {
        return PopcornMachineBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<ChoppingBoardBlockEntity> getChoppingBoard()
    {
        return ChoppingBoardBlockEntity::new;
    }
}
