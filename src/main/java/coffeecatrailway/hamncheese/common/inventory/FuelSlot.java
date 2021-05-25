package coffeecatrailway.hamncheese.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class FuelSlot extends FurnaceFuelSlot
{
    public FuelSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(null, inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return AbstractFurnaceTileEntity.isFuel(stack) || isBucket(stack);
    }
}
