package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import coffeecatrailway.hamncheese.registry.HNCItems;

/**
 * @author CoffeeCatRailway
 * Created: 13/04/2021
 */
public class PizzaItem extends AbstractSandwichItem
{
    public PizzaItem(Properties properties)
    {
        super(properties, new SandwichProperties(HNCFoods.DOUGH, HNCFoods.PIZZA, HNCItems.UNBAKED_PIZZA_BASE, HNCItems.BAKED_PIZZA_DUMMY, false));
    }
}
