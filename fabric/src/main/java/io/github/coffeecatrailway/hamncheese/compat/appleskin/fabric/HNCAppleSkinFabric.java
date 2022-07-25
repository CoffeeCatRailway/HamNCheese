package io.github.coffeecatrailway.hamncheese.compat.appleskin.fabric;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.world.food.FoodProperties;
import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

/**
 * @author CoffeeCatRailway
 * Created: 24/07/2022
 */
public class HNCAppleSkinFabric implements AppleSkinApi
{
    @Override
    public void registerEvents()
    {
        FoodValuesEvent.EVENT.register(event -> {
            if (event.itemStack.getItem() instanceof AbstractSandwichItem)
            {
                FoodProperties properties = ((AbstractSandwichItem) event.itemStack.getItem()).getFood(event.itemStack);
                event.defaultFoodValues = new FoodValues(properties.getNutrition(), properties.getSaturationModifier());
                event.modifiedFoodValues = event.defaultFoodValues;
            }
        });
    }
}
