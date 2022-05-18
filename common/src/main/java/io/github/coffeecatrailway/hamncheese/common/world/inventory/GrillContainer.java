package io.github.coffeecatrailway.hamncheese.common.world.inventory;

import io.github.coffeecatrailway.hamncheese.registry.HNCMenus;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

/**
 * @author CoffeeCatRailway
 * Created: 9/06/2021
 */
public class GrillContainer extends CookerContainer
{
    public GrillContainer(int id, Inventory inventory)
    {
        super(HNCMenus.GRILL.get(), id, inventory, 10);
    }

    public GrillContainer(int id, Inventory inventory, Container container, ContainerData data)
    {
        super(HNCMenus.GRILL.get(), id, inventory, 10, container, data);
    }

    @Override
    protected void addSlots(Inventory inventory)
    {
        int i, j;
        for (i = 0; i < 2; i++)
            for (j = 0; j < 2; j++)
                this.addSlot(new Slot(this.container, j * 2 + i, 31 + j * 18, 18 + i * 18));

        this.addSlot(new FuelSlot(this.container, 4, 71, 61));
        this.addSlot(new FuelSlot(this.container, 5, 89, 61));

        for (i = 0; i < 2; i++)
            for (j = 0; j < 2; j++)
                this.addSlot(new ResultSlot(inventory.player, this.container, j * 2 + i + 6, 111 + j * 18, 18 + i * 18));

        for (i = 0; i < 3; i++)
            for (j = 0; j < 9; j++)
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; k++)
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 6 || index == 7 || index == 8 || index == 9)
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
                } else if (AbstractFurnaceBlockEntity.isFuel(itemstack1))
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
