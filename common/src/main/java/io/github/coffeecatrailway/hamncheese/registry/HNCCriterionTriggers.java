package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.registry.resource.CriterionRegistry;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.BlueCheeseTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.ChoppingBoardTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.SwissCheeseTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 28/07/2022
 */
public class HNCCriterionTriggers
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static ChoppingBoardTrigger CHOPPING_BOARD_TRIGGER = CriterionRegistry.register(new ChoppingBoardTrigger());
    public static SwissCheeseTrigger SWISS_CHEESE_TRIGGER = CriterionRegistry.register(new SwissCheeseTrigger());
    public static BlueCheeseTrigger BLUE_CHEESE_TRIGGER = CriterionRegistry.register(new BlueCheeseTrigger());

    public static void load()
    {
        LOGGER.debug("Loaded");
    }
}
