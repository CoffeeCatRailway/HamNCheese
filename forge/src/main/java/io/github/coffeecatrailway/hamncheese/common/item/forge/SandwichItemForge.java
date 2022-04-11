package io.github.coffeecatrailway.hamncheese.common.item.forge;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 31/03/2022
 */
public class SandwichItemForge extends SandwichItem
{
    public SandwichItemForge(Properties properties)
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
