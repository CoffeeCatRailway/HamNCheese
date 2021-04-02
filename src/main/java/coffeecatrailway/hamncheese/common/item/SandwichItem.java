package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import coffeecatrailway.hamncheese.registry.HNCItems;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
public class SandwichItem extends AbstractSandwichItem
{
    public SandwichItem(Properties properties)
    {
        super(properties, new FoodProperties(HNCFoods.BREAD_SLICE, HNCFoods.TOAST, HNCItems.BREAD_SLICE, HNCItems.TOAST, true));
    }
}
