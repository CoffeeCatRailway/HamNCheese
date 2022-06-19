package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.compat.CompatTextures;
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
        this.background = guiHelper.drawableBuilder(CompatTextures.CHOPPING_BOARD, 4, 4, 78, 54).setTextureSize(86, 62).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(HNCBlocks.OAK_CHOPPING_BOARD.get()));
    }

    @Override
    public ResourceLocation getUid()
    {
        return HNCJEIPlugin.CHOPPING_BOARD_UID;
    }

    @Override
    public Class<? extends ChoppingBoardRecipe> getRecipeClass()
    {
        return ChoppingBoardRecipe.class;
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
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 31, 30).addIngredients(VanillaTypes.ITEM, HNCJEIPlugin.CHOPPING_BOARDS);
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 19).addIngredient(VanillaTypes.ITEM, recipe.getResultItem());
    }
}
