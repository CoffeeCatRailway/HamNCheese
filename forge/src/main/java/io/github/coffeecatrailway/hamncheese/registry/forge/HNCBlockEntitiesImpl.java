package io.github.coffeecatrailway.hamncheese.registry.forge;

import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.GrillBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PizzaOvenBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.forge.ForgeChoppingBoardBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.forge.ForgeGrillBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.forge.ForgePizzaOvenBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.forge.ForgePopcornMachineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * @author CoffeeCatRailway
 * Created: 23/05/2022
 */
public class HNCBlockEntitiesImpl
{
    public static BlockEntityType.BlockEntitySupplier<PizzaOvenBlockEntity> getPizzaOven()
    {
        return ForgePizzaOvenBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<GrillBlockEntity> getGrill()
    {
        return ForgeGrillBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<PopcornMachineBlockEntity> getPopcornMachine()
    {
        return ForgePopcornMachineBlockEntity::new;
    }

    public static BlockEntityType.BlockEntitySupplier<ChoppingBoardBlockEntity> getChoppingBoard()
    {
        return ForgeChoppingBoardBlockEntity::new;
    }
}
