package coffeecatrailway.hamncheese.common.inventory;

import coffeecatrailway.hamncheese.client.AtlasStitch;
import coffeecatrailway.hamncheese.registry.HNCContainers;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
public class PopcornMachineContainer extends Container
{
    protected final IInventory container;
    protected final IIntArray data;

    public PopcornMachineContainer(int id, PlayerInventory playerInventory)
    {
        this(id, playerInventory, new Inventory(5), new IntArray(2));
    }

    public PopcornMachineContainer(int id, PlayerInventory playerInventory, IInventory container, IIntArray data)
    {
        super(HNCContainers.POPCORN_MACHINE.get(), id);
        checkContainerSize(container, 5);
        checkContainerDataCount(data, 2);

        this.container = container;
        this.data = data;

        this.addSlot(new Slot(this.container, 0, 46, 12)
        {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, AtlasStitch.KERNELS);
            }
        });
        this.addSlot(new Slot(this.container, 1, 42, 32)
        {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, AtlasStitch.FLAVOUR);
            }
        });
        this.addSlot(new Slot(this.container, 2, 46, 52)
        {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, AtlasStitch.SEASONING);
            }
        });
        this.addSlot(new FilteredSlot(this.container, 3, 121, 55, stack -> stack.getItem() == HNCItems.POPCORN_BAG.get())
        {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
            {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, AtlasStitch.BAG);
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
    public boolean stillValid(PlayerEntity player)
    {
        return this.container.stillValid(player);
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
                } else if (AbstractFurnaceTileEntity.isFuel(itemstack1))
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

    @OnlyIn(Dist.CLIENT)
    public int getFlavourScaled()
    {
        return this.data.get(0) * 22 / 1000;
    }

    @OnlyIn(Dist.CLIENT)
    public int getPopcornScaled()
    {
        return this.data.get(1) * 26 / 500;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFlavour()
    {
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getPopcorn()
    {
        return this.data.get(1);
    }
}
