package coffeecatrailway.hamncheese.common.inventory;

import com.mojang.datafixers.TypeRewriteRule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IIntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 10/06/2021
 */
public abstract class CookerContainer extends Container
{
    protected final IInventory inventory;
    private final IIntArray data;

    public CookerContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, IInventory inventory, IIntArray data)
    {
        super(type, id);
        checkContainerSize(inventory, inventory.getContainerSize());
        checkContainerDataCount(data, data.getCount());

        this.inventory = inventory;
        this.data = data;

        this.addSlots(playerInventory);
    }

    protected abstract void addSlots(PlayerInventory playerInventory);

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return this.inventory.stillValid(player);
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
