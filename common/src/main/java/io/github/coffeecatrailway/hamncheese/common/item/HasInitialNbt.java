package io.github.coffeecatrailway.hamncheese.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 27/08/2022
 */
public interface HasInitialNbt
{
    void saveInitialNbt(ItemStack stack, CompoundTag tag);
}
