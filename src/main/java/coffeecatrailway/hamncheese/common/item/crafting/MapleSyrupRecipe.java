package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 13/03/2022
 */
public class MapleSyrupRecipe extends SpecialRecipe
{
    public MapleSyrupRecipe(ResourceLocation id)
    {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world)
    {
        boolean hasSap = false;
        boolean hasSugar = false;
        int emptyCount = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem().equals(HNCItems.MAPLE_SAP_BOTTLE.get()))
                hasSap = true;
            if (stack.getItem().equals(Items.SUGAR))
                hasSugar = true;

            if (stack.isEmpty())
                emptyCount++;
        }

        return hasSap && hasSugar && emptyCount >= inventory.getContainerSize() - 2;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory p_179532_1_)
    {
        NonNullList<ItemStack> items = super.getRemainingItems(p_179532_1_);
        items.forEach(stack -> {
            if (stack.getItem().equals(Items.GLASS_BOTTLE))
                stack.setCount(0);
        });
        return items;
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory)
    {
        return new ItemStack(HNCItems.MAPLE_SYRUP.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 2 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.MAPLE_SYRUP_SERIALIZER.get();
    }
}
