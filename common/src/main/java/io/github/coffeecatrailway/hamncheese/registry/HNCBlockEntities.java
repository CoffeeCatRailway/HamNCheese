package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.CheeseBlock;
import io.github.coffeecatrailway.hamncheese.common.block.entity.*;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCBlockEntities
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<BlockEntityType<?>> BLOCK_ENTITIES = PollinatedRegistry.create(Registry.BLOCK_ENTITY_TYPE, HamNCheese.MOD_ID);

    public static final Supplier<BlockEntityType<PizzaOvenBlockEntity>> PIZZA_OVEN = register("pizza_oven", getPizzaOven(), HNCBlocks.PIZZA_OVEN);
    public static final Supplier<BlockEntityType<GrillBlockEntity>> GRILL = register("grill", getGrill(), HNCBlocks.GRILL);
    public static final Supplier<BlockEntityType<PopcornMachineBlockEntity>> POPCORN_MACHINE = register("popcorn_machine", getPopcornMachine(), HNCBlocks.POPCORN_MACHINE);

    public static final Supplier<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = register("chopping_board", getChoppingBoard(), HNCBlocks.CHOPPING_BOARDS.stream());

    public static final Supplier<BlockEntityType<CheeseBlockEntity>> CHEESE = register("cheese", CheeseBlockEntity::new, Registry.BLOCK.stream().filter(block -> block instanceof CheeseBlock));

    @SafeVarargs
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> blockEntity, Supplier<? extends Block>... blocks)
    {
        return register(id, blockEntity, Arrays.stream(blocks).map(Supplier::get));
    }

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> blockEntity, Stream<? extends Block> blocks)
    {
        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(blockEntity, blocks.toArray(Block[]::new)).build(null));
    }

    // Mod loader sided block entities
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

    @ExpectPlatform
    private static BlockEntityType.BlockEntitySupplier<ChoppingBoardBlockEntity> getChoppingBoard()
    {
        return Platform.error();
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        BLOCK_ENTITIES.register(platform);
    }
}
