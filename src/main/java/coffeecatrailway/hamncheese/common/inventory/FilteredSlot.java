package coffeecatrailway.hamncheese.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
public class FilteredSlot extends Slot
{
    private final Function<ItemStack, Boolean> filter;

    public FilteredSlot(IInventory inventory, int index, int x, int y, Function<ItemStack, Boolean> filter)
    {
        super(inventory, index, x, y);
        this.filter = filter;
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return this.filter.apply(stack);
    }
}
