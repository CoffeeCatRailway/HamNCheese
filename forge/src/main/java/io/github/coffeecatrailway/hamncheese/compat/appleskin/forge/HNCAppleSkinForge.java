package io.github.coffeecatrailway.hamncheese.compat.appleskin.forge;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

/**
 * @author CoffeeCatRailway
 * Created: 25/07/2022
 */
@Mod.EventBusSubscriber(modid = HamNCheese.MOD_ID)
public class HNCAppleSkinForge
{
    @SubscribeEvent
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
