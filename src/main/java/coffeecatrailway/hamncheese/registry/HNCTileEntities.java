package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.PizzaOvenTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCTileEntities
{
    private static final Logger LOGGER = HNCMod.getLogger("Tile-Entities");
    protected static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HNCMod.MOD_ID);

    public static final RegistryObject<TileEntityType<PizzaOvenTileEntity>> PIZZA_OVEN = TILE_ENTITIES.register("pizza_oven", () -> TileEntityType.Builder.of(PizzaOvenTileEntity::new, HNCBlocks.PIZZA_OVEN.get()).build(null));

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        TILE_ENTITIES.register(bus);
    }
}
