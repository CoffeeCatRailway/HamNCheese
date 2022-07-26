package io.github.coffeecatrailway.hamncheese.common.item;

import io.github.coffeecatrailway.hamncheese.registry.HNCItems;

/**
 * @author CoffeeCatRailway
 * Created: 13/04/2021
 */
public class PizzaItem extends AbstractSandwichItem
{
    public PizzaItem(Properties properties)
    {
        super(properties.tab(null).stacksTo(16), new SandwichProperties(HNCItems.UNBAKED_PIZZA_BASE, HNCItems.BAKED_PIZZA_DUMMY).canBeToasted());
    }
}
