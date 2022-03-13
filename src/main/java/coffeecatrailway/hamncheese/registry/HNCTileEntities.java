package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCTileEntities
{
    private static final Logger LOGGER = HNCMod.getLogger("Tile-Entities");
    protected static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HNCMod.MOD_ID);

    public static final RegistryObject<TileEntityType<PizzaOvenTileEntity>> PIZZA_OVEN = register("pizza_oven", PizzaOvenTileEntity::new, HNCBlocks.PIZZA_OVEN);
    public static final RegistryObject<TileEntityType<GrillTileEntity>> GRILL = register("grill", GrillTileEntity::new, HNCBlocks.GRILL);
    public static final RegistryObject<TileEntityType<PopcornMachineTileEntity>> POPCORN_MACHINE = register("popcorn_machine", PopcornMachineTileEntity::new, HNCBlocks.POPCORN_MACHINE);
    public static final RegistryObject<TileEntityType<CurdlerTileEntity>> CURDLER = register("curdler", CurdlerTileEntity::new, HNCBlocks.CURDLER);

    public static final RegistryObject<TileEntityType<HNCSignTileEntity>> SIGN = register("sign", HNCSignTileEntity::new, HNCBlocks.MAPLE_SIGN, HNCBlocks.MAPLE_WALL_SIGN);

    public static final RegistryObject<TileEntityType<ChoppingBoardTileEntity>> CHOPPING_BOARD = register("chopping_board", ChoppingBoardTileEntity::new, HNCBlocks.CHOPPING_BOARD);

    @SafeVarargs
    private static  <T extends TileEntity> RegistryObject<TileEntityType<T>> register (String id, Supplier<T> tileEntity, Supplier<? extends Block>... blocks)
    {
        return TILE_ENTITIES.register(id, () -> TileEntityType.Builder.of(tileEntity, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null));
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        TILE_ENTITIES.register(bus);
    }
}
