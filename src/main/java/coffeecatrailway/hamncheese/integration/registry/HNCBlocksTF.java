package coffeecatrailway.hamncheese.integration.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import org.apache.logging.log4j.Logger;
import twilightforest.block.TFBlocks;

/**
 * @author CoffeeCatRailway
 * Created: 6/09/2021
 */
public class HNCBlocksTF
{
    private static final Logger LOGGER = HNCMod.getLogger("Blocks-TF");

    public static void load()
    {
        HNCBlocks.TWILIGHT_OAK_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("twilight_oak_chopping_board", TFBlocks.twilight_oak_planks);
        HNCBlocks.CANOPY_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("canopy_chopping_board", TFBlocks.canopy_planks);
        HNCBlocks.MANGROVE_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("mangrove_chopping_board", TFBlocks.mangrove_planks);
        HNCBlocks.DARK_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("dark_chopping_board", TFBlocks.dark_planks);
        HNCBlocks.TIME_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("time_chopping_board", TFBlocks.time_planks);
        HNCBlocks.TRANS_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("trans_chopping_board", TFBlocks.trans_planks);
        HNCBlocks.MINE_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("mine_chopping_board", TFBlocks.mine_planks);
        HNCBlocks.SORT_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("sort_chopping_board", TFBlocks.sort_planks);

        LOGGER.debug("Loaded");
    }
}
