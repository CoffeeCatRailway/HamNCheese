package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.GrillBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PizzaOvenBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCBlockEntities
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<BlockEntityType<?>> TILE_ENTITIES = PollinatedRegistry.create(Registry.BLOCK_ENTITY_TYPE, HamNCheese.MOD_ID);

    public static final Supplier<BlockEntityType<PizzaOvenBlockEntity>> PIZZA_OVEN = register("pizza_oven", getPizzaOven(), HNCBlocks.PIZZA_OVEN);
    public static final Supplier<BlockEntityType<GrillBlockEntity>> GRILL = register("grill", getGrill(), HNCBlocks.GRILL);
    public static final Supplier<BlockEntityType<PopcornMachineBlockEntity>> POPCORN_MACHINE = register("popcorn_machine", getPopcornMachine(), HNCBlocks.POPCORN_MACHINE);

    @SafeVarargs
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> tileEntity, Supplier<? extends Block>... blocks)
    {
        return TILE_ENTITIES.register(id, () -> BlockEntityType.Builder.of(tileEntity, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null));
    }

    // Mod loader sided items
    @ExpectPlatform
    private static BlockEntityType.BlockEntitySupplier<PizzaOvenBlockEntity> getPizzaOven()
    {
        return Platform.error();
    }

    @ExpectPlatform
    private static BlockEntityType.BlockEntitySupplier<GrillBlockEntity> getGrill()
    {
        return Platform.error();
    }

    @ExpectPlatform
    private static BlockEntityType.BlockEntitySupplier<PopcornMachineBlockEntity> getPopcornMachine()
    {
        return Platform.error();
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        TILE_ENTITIES.register(platform);
    }
}
