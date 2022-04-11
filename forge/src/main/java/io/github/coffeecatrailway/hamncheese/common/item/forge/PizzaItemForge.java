package io.github.coffeecatrailway.hamncheese.common.item.forge;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.PizzaItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 01/04/2022
 */
public class PizzaItemForge extends PizzaItem
{
    public PizzaItemForge(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        AbstractSandwichItem.init(stack);
        return super.initCapabilities(stack, nbt);
    }
}
