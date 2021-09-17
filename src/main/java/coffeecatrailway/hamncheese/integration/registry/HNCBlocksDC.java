package coffeecatrailway.hamncheese.integration.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import com.vulp.druidcraft.registry.BlockRegistry;
import org.apache.logging.log4j.Logger;
import twilightforest.block.TFBlocks;

/**
 * @author CoffeeCatRailway
 * Created: 6/09/2021
 */
public class HNCBlocksDC
{
    private static final Logger LOGGER = HNCMod.getLogger("Blocks-DC");

    public static void load()
    {
//        HNCBlocks.DARKWOOD_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("darkwood_chopping_board", () -> BlockRegistry.darkwood_planks);
//        HNCBlocks.ELDER_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("elder_chopping_board", () -> BlockRegistry.elder_planks);

        LOGGER.debug("Loaded");
    }
}
