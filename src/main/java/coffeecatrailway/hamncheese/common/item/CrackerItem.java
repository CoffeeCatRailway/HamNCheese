package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import coffeecatrailway.hamncheese.registry.HNCItems;

/**
 * @author CoffeeCatRailway
 * Created: 5/04/2021
 */
public class CrackerItem extends AbstractSandwichItem
{
    public CrackerItem(Properties properties)
    {
        super(properties, new SandwichProperties(HNCFoods.CRACKER, HNCItems.CRACKER_DUMMY, false));
    }
}
