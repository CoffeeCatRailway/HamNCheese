package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.registry.resource.CriterionRegistry;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.CheeseTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.ChoppingBoardTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.GoodKittyTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.PestControlTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 28/07/2022
 */
public class HNCCriterionTriggers
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static final ChoppingBoardTrigger CHOPPING_BOARD_TRIGGER = CriterionRegistry.register(new ChoppingBoardTrigger());
    public static final CheeseTrigger CHEESE_TRIGGER = CriterionRegistry.register(new CheeseTrigger());
    public static final PestControlTrigger PEST_CONTROL_TRIGGER = CriterionRegistry.register(new PestControlTrigger());
    public static final GoodKittyTrigger GOOD_KITTY_TRIGGER = CriterionRegistry.register(new GoodKittyTrigger());

    public static void load()
    {
        LOGGER.debug("Loaded");
    }
}
