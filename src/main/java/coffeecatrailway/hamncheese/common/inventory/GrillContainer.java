package coffeecatrailway.hamncheese.common.inventory;

import coffeecatrailway.hamncheese.registry.HNCContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;

/**
 * @author CoffeeCatRailway
 * Created: 9/06/2021
 */
public class GrillContainer extends CookerContainer
{
    public GrillContainer(int id, PlayerInventory playerInventory)
    {
        super(HNCContainers.GRILL.get(), id, playerInventory, 10);
    }

    public GrillContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray data)
    {
        super(HNCContainers.GRILL.get(), id, playerInventory, 10, inventory, data);
    }

    @Override
    protected void addSlots(PlayerInventory playerInventory)
    {
        int i, j;
        for (i = 0; i < 2; i++)
            for (j = 0; j < 2; j++)
                this.addSlot(new Slot(this.container, j * 2 + i, 31 + j * 18, 18 + i * 18));

        this.addSlot(new FuelSlot(this.container, 4, 71, 61));
        this.addSlot(new FuelSlot(this.container, 5, 89, 61));

        for (i = 0; i < 2; i++)
            for (j = 0; j < 2; j++)
                this.addSlot(new ResultSlot(playerInventory.player, this.container, j * 2 + i + 6, 111 + j * 18, 18 + i * 18));

        for (i = 0; i < 3; i++)
            for (j = 0; j < 9; j++)
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; k++)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 6)
            {
                if (!this.moveItemStackTo(itemstack1, 10, 46, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index > 9)
            {
                if (itemstack1.isEdible())
                {
                    if (!this.moveItemStackTo(itemstack1, 0, 4, false))
                        return ItemStack.EMPTY;
                } else if (AbstractFurnaceTileEntity.isFuel(itemstack1))
                {
                    if (!this.moveItemStackTo(itemstack1, 4, 10, false))
                        return ItemStack.EMPTY;
                } else if (index >= 10 && index < 37)
                {
                    if (!this.moveItemStackTo(itemstack1, 37, 46, false))
                        return ItemStack.EMPTY;
                } else if (index >= 37 && index < 46 && !this.moveItemStackTo(itemstack1, 10, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
