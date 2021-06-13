package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
public class SandwichItem extends AbstractSandwichItem
{
    public SandwichItem(Properties properties)
    {
        super(properties, new SandwichProperties(HNCFoods.BREAD_SLICE, HNCFoods.TOAST, HNCItems.BREAD_SLICE, HNCItems.TOAST, true));
    }

    public static boolean isUntoastedSandwich(ItemStack stack)
    {
        return stack.getItem() instanceof SandwichItem || stack.getItem() == HNCItems.SANDWICH.get() && !stack.getOrCreateTag().getBoolean(TAG_TOASTED);
    }
}
