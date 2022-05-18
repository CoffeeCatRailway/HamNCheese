package io.github.coffeecatrailway.hamncheese.common.world.inventory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 10/06/2021
 */
public abstract class CookerContainer extends AbstractContainerMenu
{
    protected final Container container;
    private final ContainerData data;

    public CookerContainer(@Nullable MenuType<?> type, int id, Inventory inventory, int size)
    {
        this(type, id, inventory, size, new SimpleContainer(size), new SimpleContainerData(4));
    }

    public CookerContainer(@Nullable MenuType<?> type, int id, Inventory inventory, int size, Container container, ContainerData data)
    {
        super(type, id);
        checkContainerSize(container, size);
        checkContainerDataCount(data, 4);

        this.container = container;
        this.data = data;

        this.addSlots(inventory);
        this.addDataSlots(this.data);
    }

    protected abstract void addSlots(Inventory inventory);

    @Override
    public boolean stillValid(Player player)
    {
        return this.container.stillValid(player);
    }

    @Environment(EnvType.CLIENT)
    public int getCookProgressionScaled()
    {
        int cookTime = this.data.get(2);
        int cookTimeTotal = this.data.get(3);
        return cookTimeTotal != 0 && cookTime != 0 ? cookTime * 24 / cookTimeTotal : 0;
    }

    @Environment(EnvType.CLIENT)
    public int getBurnLeftScaled()
    {
        int burnTimeTotal = this.data.get(1);
        if (burnTimeTotal <= 0)
            burnTimeTotal = 200;
        return this.data.get(0) * 13 / burnTimeTotal;
    }

    @Environment(EnvType.CLIENT)
    public boolean isBurning()
    {
        return this.data.get(0) > 0;
    }
}
