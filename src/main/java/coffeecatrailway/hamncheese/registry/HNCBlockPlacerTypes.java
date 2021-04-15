package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.world.DoubleCropBlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 15/04/2021
 */
public class HNCBlockPlacerTypes
{
    private static final Logger LOGGER = HNCMod.getLogger("BlockPlacerTypes");
    protected static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, HNCMod.MOD_ID);

    public static final RegistryObject<BlockPlacerType<DoubleCropBlockPlacer>> DOUBLE_CROP_PLACER = BLOCK_PLACER_TYPES.register("double_crop_placer", () -> new BlockPlacerType<>(DoubleCropBlockPlacer.CODEC));

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        BLOCK_PLACER_TYPES.register(bus);
    }
}
