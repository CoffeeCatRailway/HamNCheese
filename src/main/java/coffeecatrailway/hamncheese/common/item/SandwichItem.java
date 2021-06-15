package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

    @Override
    public ITextComponent getName(ItemStack stack)
    {
        ITextComponent name = super.getName(stack);
        if (this.sandwichProperties.isToasted(stack.getOrCreateTag()))
            name = new TranslationTextComponent("item.hamncheese.sandwich.toasted").append(" ").append(name);
        return name;
    }

    public static boolean isUntoastedSandwich(ItemStack stack)
    {
        return stack.getItem() instanceof SandwichItem || stack.getItem() == HNCItems.SANDWICH.get() && !stack.getOrCreateTag().getBoolean(TAG_TOASTED);
    }
}
