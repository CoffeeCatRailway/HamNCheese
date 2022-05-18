package io.github.coffeecatrailway.hamncheese.common.block.entity.forge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 18/05/2022
 */
public class FoodCookerBlockEntityImpl
{
    public static int getBurnTime(ItemStack fuelStack, @Nullable RecipeType<?> recipeType)
    {
        return ForgeHooks.getBurnTime(fuelStack, recipeType);
    }
}
