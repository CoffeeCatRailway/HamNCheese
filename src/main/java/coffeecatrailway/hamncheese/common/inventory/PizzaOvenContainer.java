package coffeecatrailway.hamncheese.common.inventory;

import coffeecatrailway.hamncheese.registry.HNCContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class PizzaOvenContainer extends Container
{
    private final IInventory inventory;
    private final IIntArray data;

    public PizzaOvenContainer(int id, PlayerInventory playerInventory)
    {
        this(id, playerInventory, new Inventory(13), new IntArray(4));
    }

    public PizzaOvenContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray data)
    {
        super(HNCContainers.PIZZA_OVEN.get(), id);
        checkContainerSize(inventory, inventory.getContainerSize());
        checkContainerDataCount(data, data.getCount());

        this.inventory = inventory;
        this.data = data;

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                this.addSlot(new Slot(this.inventory, j * 3 + i, 8 + j * 18, 8 + i * 18));

        this.addSlot(new FuelSlot(this.inventory, 9, 70, 52));
        this.addSlot(new FuelSlot(this.inventory, 10, 88, 52));
        this.addSlot(new FuelSlot(this.inventory, 11, 106, 52));
        this.addSlot(new ResultSlot(playerInventory.player, this.inventory, 12, 136, 19));

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 89 + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 147));

        this.addDataSlots(this.data);
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return this.inventory.stillValid(player);
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
                } else if (AbstractFurnaceTileEntity.isFuel(itemstack1))
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

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled()
    {
        int cookTime = this.data.get(2);
        int cookTimeTotal = this.data.get(3);
        return cookTimeTotal != 0 && cookTime != 0 ? cookTime * 24 / cookTimeTotal : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled()
    {
        int burnTimeTotal = this.data.get(1);
        if (burnTimeTotal <= 0)
            burnTimeTotal = 200;
        return this.data.get(0) * 13 / burnTimeTotal;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning()
    {
        return this.data.get(0) > 0;
    }
}
