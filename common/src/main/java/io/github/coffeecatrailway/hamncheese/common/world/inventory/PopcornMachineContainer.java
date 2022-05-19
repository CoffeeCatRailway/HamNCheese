package io.github.coffeecatrailway.hamncheese.common.world.inventory;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCMenus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
public class PopcornMachineContainer extends AbstractContainerMenu
{
    protected final Container container;
    protected final ContainerData data;

    public PopcornMachineContainer(int id, Inventory playerInventory)
    {
        this(id, playerInventory, new SimpleContainer(5), new SimpleContainerData(2));
    }

    public PopcornMachineContainer(int id, Inventory playerInventory, Container container, ContainerData data)
    {
        super(HNCMenus.POPCORN_MACHINE.get(), id);
        checkContainerSize(container, 5);
        checkContainerDataCount(data, 2);

        this.container = container;
        this.data = data;

        this.addSlot(new Slot(this.container, 0, 46, 12)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.getItem().equals(HNCItems.DRIED_CORN_KERNELS.get());
            }

            @Environment(EnvType.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, HamNCheese.EMPTY_SLOT_KERNELS);
            }
        });
        this.addSlot(new Slot(this.container, 1, 42, 32)
        {
            @Environment(EnvType.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, HamNCheese.EMPTY_SLOT_FLAVOUR);
            }
        });
        this.addSlot(new Slot(this.container, 2, 46, 52)
        {
            @Environment(EnvType.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, HamNCheese.EMPTY_SLOT_SEASONING);
            }
        });
        this.addSlot(new Slot(this.container, 3, 121, 55)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.getItem().equals(HNCItems.POPCORN_BAG.get());
            }

            @Environment(EnvType.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, HamNCheese.EMPTY_SLOT_BAG);
            }
        });
        this.addSlot(new ResultSlot(playerInventory.player, this.container, 4, 121, 29));

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));

        for (int k = 0; k < 9; k++)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 152));
        this.addDataSlots(this.data);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.container.stillValid(player);
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
            if (index == 4)
            {
                if (!this.moveItemStackTo(itemstack1, 5, 41, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index > 3)
            {
                if (itemstack1.isEdible())
                {
                    if (!this.moveItemStackTo(itemstack1, 0, 3, false))
                        return ItemStack.EMPTY;
                } else if (AbstractFurnaceBlockEntity.isFuel(itemstack1))
                {
                    if (!this.moveItemStackTo(itemstack1, 3, 4, false))
                        return ItemStack.EMPTY;
                } else if (index >= 5 && index < 32)
                {
                    if (!this.moveItemStackTo(itemstack1, 32, 41, false))
                        return ItemStack.EMPTY;
                } else if (index >= 32 && index < 41 && !this.moveItemStackTo(itemstack1, 5, 32, false))
                {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 5, 41, false))
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

    @Environment(EnvType.CLIENT)
    public int getFlavourScaled()
    {
        return this.data.get(0) * 22 / PopcornMachineBlockEntity.MAX_FLAVOUR_TIME;
    }

    @Environment(EnvType.CLIENT)
    public int getPopcornScaled()
    {
        return getPopcornScaled(this.data.get(1));
    }

    public static int getPopcornScaled(int popcorn)
    {
        return popcorn * 26 / PopcornMachineBlockEntity.MAX_POPCORN;
    }

    @Environment(EnvType.CLIENT)
    public int getFlavour()
    {
        return this.data.get(0);
    }

    @Environment(EnvType.CLIENT)
    public int getPopcorn()
    {
        return this.data.get(1);
    }
}
