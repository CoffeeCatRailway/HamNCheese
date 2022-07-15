package io.github.coffeecatrailway.hamncheese.compat.jei;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 19/06/2022
 */
public class ChoppingBoardRecipeCategory implements IRecipeCategory<ChoppingBoardRecipe>
{
    private final IDrawable background;
    private final IDrawable icon;

    public ChoppingBoardRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(CompatCommon.JEI_SHEET, 4, 4, 78, 54);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(HNCBlocks.OAK_CHOPPING_BOARD.get()));
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getUid()
    {
        return HNCJeiPlugin.CHOPPING_BOARD.getUid();
    }

    @Override
    @SuppressWarnings("removal")
    public Class<? extends ChoppingBoardRecipe> getRecipeClass()
    {
        return HNCJeiPlugin.CHOPPING_BOARD.getRecipeClass();
    }

    @Override
    public Component getTitle()
    {
        return new TranslatableComponent("jei." + HamNCheese.MOD_ID + ".category.chopping_board");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ChoppingBoardRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 19).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 31, 8).addIngredients(recipe.getTool());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 31, 30).addIngredients(VanillaTypes.ITEM_STACK, CompatCommon.CHOPPING_BOARDS);
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 19).addIngredient(VanillaTypes.ITEM_STACK, recipe.getResultItem());
    }
}
