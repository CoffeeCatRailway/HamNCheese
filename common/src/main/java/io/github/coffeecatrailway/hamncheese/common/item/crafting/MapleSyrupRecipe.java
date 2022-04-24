package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

/**
 * @author CoffeeCatRailway
 * Created: 13/03/2022
 */
public class MapleSyrupRecipe extends CustomRecipe
{
    public MapleSyrupRecipe(ResourceLocation id)
    {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer inventory, Level level)
    {
        boolean hasSap = false;
        boolean hasSugar = false;
        int emptyCount = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(HNCItems.MAPLE_SAP_BOTTLE.get()))
                hasSap = true;
            if (stack.is(HNCItemTags.SUGAR_COMMON))
                hasSugar = true;

            if (stack.isEmpty())
                emptyCount++;
        }

        return hasSap && hasSugar && emptyCount >= inventory.getContainerSize() - 2;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory)
    {
        NonNullList<ItemStack> items = super.getRemainingItems(inventory);
        items.forEach(stack -> {
            if (stack.is(Items.GLASS_BOTTLE))
                stack.setCount(0);
        });
        return items;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventory)
    {
        return new ItemStack(HNCItems.MAPLE_SYRUP.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.MAPLE_SYRUP_SERIALIZER.get();
    }
}
