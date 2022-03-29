package io.github.coffeecatrailway.hamncheese.common.item;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 14/02/2021
 */
public class GreenFoodItem extends Item
{
    public GreenFoodItem(Properties prop, FoodProperties food, int stackSize)
    {
        super(prop.food(food).stacksTo(stackSize));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".green_food.main"));
        if (this.isDate(Calendar.MARCH, 2))
        {
            tooltip.add(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".green_food.birthday_line1"));
            tooltip.add(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".green_food.birthday_line2"));
        }
    }

    private boolean isDate(int month, int day)
    {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int curentMonth = cal.get(Calendar.MONTH);
        int curentDay = cal.get(Calendar.DAY_OF_MONTH);
        boolean inRange = curentDay == day;

        return (curentMonth == month && inRange);
    }
}
