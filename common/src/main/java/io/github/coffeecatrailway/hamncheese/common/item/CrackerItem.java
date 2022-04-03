package io.github.coffeecatrailway.hamncheese.common.item;

import io.github.coffeecatrailway.hamncheese.registry.HNCFoods;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;

/**
 * @author CoffeeCatRailway
 * Created: 5/04/2021
 */
public class CrackerItem extends AbstractSandwichItem
{
    public CrackerItem(Properties properties)
    {
        super(properties.stacksTo(32), new SandwichProperties(HNCFoods.CRACKER, HNCItems.CRACKER_DUMMY, false));
    }
}
