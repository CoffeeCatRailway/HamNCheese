package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 20/05/2021
 */
public class PizzaOvenRecipe implements IRecipe<IInventory>
{
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final float experience;
    private final int cookTime;
    private final boolean isSimple;

    public PizzaOvenRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result, float experience, int cookTime)
    {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.experience = experience;
        this.cookTime = cookTime;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(HNCBlocks.PIZZA_OVEN.get());
    }

    @Override
    public String getGroup()
    {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return this.ingredients;
    }

    @Override
    public boolean matches(IInventory inventory, World level)
    {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for (int j = 0; j < 9; ++j)
        {
            ItemStack itemstack = inventory.getItem(j);
            if (!itemstack.isEmpty())
            {
                i++;
                if (this.isSimple)
                    recipeitemhelper.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (this.isSimple ? recipeitemhelper.canCraft(this, null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(IInventory inventory)
    {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.PIZZA_OVEN_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return HNCRecipes.PIZZA_OVEN_TYPE;
    }

    public float getExperience()
    {
        return this.experience;
    }

    public int getCookTime()
    {
        return this.cookTime;
    }
}
