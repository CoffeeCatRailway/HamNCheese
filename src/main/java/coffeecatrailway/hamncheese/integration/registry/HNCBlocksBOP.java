package coffeecatrailway.hamncheese.integration.registry;

import biomesoplenty.api.block.BOPBlocks;
import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.AbstractBlock;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 31/05/2021
 */
public class HNCBlocksBOP
{
    private static final Logger LOGGER = HNCMod.getLogger("Blocks-BOP");

    public static void load()
    {
//        HNCBlocks.FIR_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("fir_chopping_board", () -> BOPBlocks.fir_planks);
//        HNCBlocks.REDWOOD_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("redwood_chopping_board", () -> BOPBlocks.redwood_planks);
//        HNCBlocks.CHERRY_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("cherry_chopping_board", () -> BOPBlocks.cherry_planks);
//        HNCBlocks.MAHOGANY_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("mahogany_chopping_board", () -> BOPBlocks.mahogany_planks);
//        HNCBlocks.JACARANDA_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("jacaranda_chopping_board", () -> BOPBlocks.jacaranda_planks);
//        HNCBlocks.PALM_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("palm_chopping_board", () -> BOPBlocks.palm_planks);
//        HNCBlocks.WILLOW_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("willow_chopping_board", () -> BOPBlocks.willow_planks);
//        HNCBlocks.DEAD_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("dead_chopping_board", () -> BOPBlocks.dead_planks);
//        HNCBlocks.MAGIC_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("magic_chopping_board", () -> BOPBlocks.magic_planks);
//        HNCBlocks.UMBRAN_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("umbran_chopping_board", () -> BOPBlocks.umbran_planks);
//        HNCBlocks.HELLBARK_CHOPPING_BOARD = HNCBlocks.registerChoppingBoard("hellbark_chopping_board", () -> BOPBlocks.hellbark_planks);

        LOGGER.debug("Loaded");
    }
}
