package io.github.coffeecatrailway.hamncheese.compat.appleskin.forge;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.common.MinecraftForge;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

/**
 * @author CoffeeCatRailway
 * Created: 25/07/2022
 */
public class HNCAppleSkinForge
{
    public static void load()
    {
        MinecraftForge.EVENT_BUS.addListener(HNCAppleSkinForge::foodValuesEvent);
    }

    public static void foodValuesEvent(FoodValuesEvent event)
    {
        if (event.itemStack.getItem() instanceof AbstractSandwichItem)
        {
            FoodProperties properties = ((AbstractSandwichItem) event.itemStack.getItem()).getFood(event.itemStack);
            event.defaultFoodValues = new FoodValues(properties.getNutrition(), properties.getSaturationModifier());
            event.modifiedFoodValues = event.defaultFoodValues;
        }
    }
}
