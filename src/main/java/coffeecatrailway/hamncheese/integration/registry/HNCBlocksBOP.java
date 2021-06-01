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
        HNCBlocks.FIR_CHOPPING_BOARD = HNCBlocks.register("fir_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.fir_planks)), prop -> prop);
        HNCBlocks.REDWOOD_CHOPPING_BOARD = HNCBlocks.register("redwood_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.redwood_planks)), prop -> prop);
        HNCBlocks.CHERRY_CHOPPING_BOARD = HNCBlocks.register("cherry_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.cherry_planks)), prop -> prop);
        HNCBlocks.MAHOGANY_CHOPPING_BOARD = HNCBlocks.register("mahogany_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.mahogany_planks)), prop -> prop);
        HNCBlocks.JACARANDA_CHOPPING_BOARD = HNCBlocks.register("jacaranda_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.jacaranda_planks)), prop -> prop);
        HNCBlocks.PALM_CHOPPING_BOARD = HNCBlocks.register("palm_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.palm_planks)), prop -> prop);
        HNCBlocks.WILLOW_CHOPPING_BOARD = HNCBlocks.register("willow_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.willow_planks)), prop -> prop);
        HNCBlocks.DEAD_CHOPPING_BOARD = HNCBlocks.register("dead_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.dead_planks)), prop -> prop);
        HNCBlocks.MAGIC_CHOPPING_BOARD = HNCBlocks.register("magic_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.magic_planks)), prop -> prop);
        HNCBlocks.UMBRAN_CHOPPING_BOARD = HNCBlocks.register("umbran_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.umbran_planks)), prop -> prop);
        HNCBlocks.HELLBARK_CHOPPING_BOARD = HNCBlocks.register("hellbark_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(BOPBlocks.hellbark_planks)), prop -> prop);

        LOGGER.debug("Loaded");
    }
}
