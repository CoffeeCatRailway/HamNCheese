package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.client.gui.screen.PizzaOvenScreen;
import coffeecatrailway.hamncheese.client.gui.screen.PopcornMachineScreen;
import coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import coffeecatrailway.hamncheese.common.tileentity.PopcornMachineTileEntity;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 30/08/2021
 */
public class PopcornRecipeCategory implements IRecipeCategory<PopcornRecipe>
{
    private final IDrawable background;
    private final IDrawable icon;

    public PopcornRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(PopcornMachineScreen.TEXTURE, 16, 11, 127, 61);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(HNCBlocks.POPCORN_MACHINE.get()));
    }

    @Override
    public ResourceLocation getUid()
    {
        return HNCJustEnoughItems.POPCORN_UID;
    }

    @Override
    public Class<? extends PopcornRecipe> getRecipeClass()
    {
        return PopcornRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return HNCBlocks.POPCORN_MACHINE.get().getName().getString();
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setIngredients(PopcornRecipe recipe, IIngredients ingredients)
    {
        ItemStack result = recipe.getResultItem().copy();
        List<Ingredient> ingredientsList = new ArrayList<>();
        ingredientsList.add(PopcornMachineTileEntity.SLOT_KERNELS, Ingredient.of(new ItemStack(HNCItems.DRIED_CORN_KERNELS.get())));
        ingredientsList.add(PopcornMachineTileEntity.SLOT_FLAVOURING, recipe.getFlavouring());
        ingredientsList.add(PopcornMachineTileEntity.SLOT_SEASONING, recipe.getSeasoning());
        ingredientsList.add(PopcornMachineTileEntity.SLOT_BAG, Ingredient.of(new ItemStack(HNCItems.POPCORN_BAG.get(), result.getCount())));

        ingredients.setInputIngredients(ingredientsList);
        ingredients.setOutput(VanillaTypes.ITEM, result);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, PopcornRecipe recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        guiItemStacks.init(PopcornMachineTileEntity.SLOT_KERNELS, true, 29, 0);
        guiItemStacks.init(PopcornMachineTileEntity.SLOT_FLAVOURING, true, 25, 20);
        guiItemStacks.init(PopcornMachineTileEntity.SLOT_SEASONING, true, 29, 40);
        guiItemStacks.init(PopcornMachineTileEntity.SLOT_BAG, true, 104, 43);
        guiItemStacks.init(PopcornMachineTileEntity.SLOT_DOWN, false, 104, 17);
        guiItemStacks.set(ingredients);
    }
}
