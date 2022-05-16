package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
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

//    public static final RegistryObject<TileEntityType<PizzaOvenTileEntity>> PIZZA_OVEN = register("pizza_oven", PizzaOvenTileEntity::new, HNCBlocks.PIZZA_OVEN);
//    public static final RegistryObject<TileEntityType<GrillTileEntity>> GRILL = register("grill", GrillTileEntity::new, HNCBlocks.GRILL);
//    public static final RegistryObject<TileEntityType<PopcornMachineTileEntity>> POPCORN_MACHINE = register("popcorn_machine", PopcornMachineTileEntity::new, HNCBlocks.POPCORN_MACHINE);

    @SafeVarargs
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> tileEntity, Supplier<? extends Block>... blocks)
    {
        return TILE_ENTITIES.register(id, () -> BlockEntityType.Builder.of(tileEntity, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null));
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        TILE_ENTITIES.register(platform);
    }
}
