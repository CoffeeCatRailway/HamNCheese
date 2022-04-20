package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.HNCSignBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
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

    public static final Supplier<BlockEntityType<SignBlockEntity>> SIGN = register("sign", HNCSignBlockEntity::new, HNCBlocks.MAPLE_SIGN, HNCBlocks.MAPLE_WALL_SIGN);

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