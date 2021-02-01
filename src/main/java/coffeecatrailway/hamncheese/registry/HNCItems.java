package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 1/02/2021
 */
public class HNCItems
{
    private static final Logger LOGGER = HNCMod.getLogger("Items");

//    public static final RegistryEntry<Item> EXAMPLE = REGISTRATE.item("example", Item::new).defaultLang().defaultModel().register();

    public static void load()
    {
        LOGGER.debug("Loaded");
    }
}
