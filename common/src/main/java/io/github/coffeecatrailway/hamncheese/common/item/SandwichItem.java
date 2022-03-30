package io.github.coffeecatrailway.hamncheese.common.item;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCFoods;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
public class SandwichItem extends AbstractSandwichItem
{
    public SandwichItem(Item.Properties properties)
    {
        super(properties, new SandwichProperties(HNCFoods.BREAD_SLICE, HNCFoods.TOAST, HNCItems.BREAD_SLICE, HNCItems.TOAST, true));
    }

    @Override
    public Component getName(ItemStack stack)
    {
        Component name = super.getName(stack);
        if (this.sandwichProperties.isToasted(stack.getOrCreateTag()))
            name = new TranslatableComponent("item." + HamNCheese.MOD_ID + ".sandwich.toasted").append(" ").append(name);
        return name;
    }

    public static boolean isUntoastedSandwich(ItemStack stack)
    {
        return stack.getItem() instanceof SandwichItem || stack.getItem() == HNCItems.SANDWICH.get() && !stack.getOrCreateTag().getBoolean(TAG_TOASTED);
    }
}
