package io.github.coffeecatrailway.hamncheese.common.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class FuelSlot extends FurnaceFuelSlot
{
    public FuelSlot(Container inventory, int index, int xPosition, int yPosition) {
        super(null, inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return AbstractFurnaceBlockEntity.isFuel(stack) || isBucket(stack);
    }
}
