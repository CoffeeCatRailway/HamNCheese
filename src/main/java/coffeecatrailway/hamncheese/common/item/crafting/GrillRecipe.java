package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.common.item.SandwichItem;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 10/06/2021
 */
public class GrillRecipe implements IRecipe<IInventory>
{
    private final ResourceLocation id;

    public GrillRecipe(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public boolean matches(IInventory inventory, World level)
    {
        for (int i = 0; i < 4; i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (SandwichItem.isUntoastedSandwich(stack))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(IInventory inventory)
    {
        for (int i = 0; i < 4; i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (SandwichItem.isUntoastedSandwich(stack))
            {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                copy.getOrCreateTag().putBoolean(SandwichItem.TAG_TOASTED, true);
                return copy;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(HNCBlocks.GRILL.get());
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.GRILL_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return HNCRecipes.GRILL_TYPE;
    }
}
