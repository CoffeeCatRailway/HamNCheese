package io.github.coffeecatrailway.hamncheese.common.block.entity.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 18/05/2022
 */
public class FoodCookerBlockEntityImpl
{
    public static int getBurnTime(ItemStack fuelStack, @Nullable RecipeType<?> recipeType)
    {
        if (fuelStack == null || fuelStack.isEmpty())
            return 0;
        return FuelRegistry.INSTANCE.get(fuelStack.getItem());
    }
}
