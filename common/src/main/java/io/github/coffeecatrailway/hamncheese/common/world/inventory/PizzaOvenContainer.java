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
 * Created: 21/05/2021
 */
public class PizzaOvenContainer extends CookerContainer
{
    public PizzaOvenContainer(int id, Inventory inventory)
    {
        super(HNCMenus.PIZZA_OVEN.get(), id, inventory, 13);
    }

    public PizzaOvenContainer(int id, Inventory inventory, Container container, ContainerData data)
    {
        super(HNCMenus.PIZZA_OVEN.get(), id, inventory, 13, container, data);
    }

    @Override
    protected void addSlots(Inventory inventory)
    {
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                this.addSlot(new Slot(this.container, j * 3 + i, 8 + j * 18, 8 + i * 18));

        this.addSlot(new FuelSlot(this.container, 9, 70, 52));
        this.addSlot(new FuelSlot(this.container, 10, 88, 52));
        this.addSlot(new FuelSlot(this.container, 11, 106, 52));
        this.addSlot(new ResultSlot(inventory.player, this.container, 12, 136, 19));

        for (i = 0; i < 3; i++)
            for (j = 0; j < 9; j++)
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 89 + i * 18));

        for (int k = 0; k < 9; k++)
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 147));
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
            if (index == 12)
            {
                if (!this.moveItemStackTo(itemstack1, 13, 49, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index > 11)
            {
                if (itemstack1.isEdible())
                {
                    if (!this.moveItemStackTo(itemstack1, 0, 9, false))
                        return ItemStack.EMPTY;
                } else if (AbstractFurnaceBlockEntity.isFuel(itemstack1))
                {
                    if (!this.moveItemStackTo(itemstack1, 9, 12, false))
                        return ItemStack.EMPTY;
                } else if (index >= 13 && index < 40)
                {
                    if (!this.moveItemStackTo(itemstack1, 40, 49, false))
                        return ItemStack.EMPTY;
                } else if (index >= 40 && index < 49 && !this.moveItemStackTo(itemstack1, 13, 40, false))
                {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 13, 49, false))
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
