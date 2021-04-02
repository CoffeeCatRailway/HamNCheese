package coffeecatrailway.hamncheese.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

/**
 * @author CoffeeCatRailway
 * Created: 9/02/2021
 */
public class CraftingToolItem extends SwordItem
{
    public CraftingToolItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties)
    {
        super(tier, attackDamage, attackSpeed, properties.stacksTo(1));
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack stack = itemStack.copy();
        stack.setDamageValue(stack.getDamageValue() + 1);
        if (stack.getDamageValue() > stack.getMaxDamage())
            return ItemStack.EMPTY;
        return stack;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}
