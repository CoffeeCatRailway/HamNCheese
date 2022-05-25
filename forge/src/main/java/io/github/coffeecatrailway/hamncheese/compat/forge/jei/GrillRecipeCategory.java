package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.GrillScreen;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.GrillRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 18/07/2021
 */
public class GrillRecipeCategory implements IRecipeCategory<GrillRecipe>
{
    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableAnimated flame;
    private final IDrawableAnimated arrow;

    public GrillRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(GrillScreen.TEXTURE, 29, 16, 118, 63);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(HNCBlocks.GRILL.get()));

        this.flame = guiHelper.drawableBuilder(GrillScreen.TEXTURE, 176, 0, 14, 14)
                .buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
        this.arrow = guiHelper.drawableBuilder(GrillScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return HNCJEIPlugin.GRILL_UID;
    }

    @Override
    public Class<? extends GrillRecipe> getRecipeClass()
    {
        return GrillRecipe.class;
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.GRILL.get().getName();
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
    public void setRecipe(IRecipeLayoutBuilder builder, GrillRecipe recipe, IFocusGroup focuses)
    {
        ItemStack sandwich = new ItemStack(HNCItems.SANDWICH.get());
        FoodPicker.pickFoods(7).forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addIngredient(VanillaTypes.ITEM, sandwich);

        ItemStack toasted = sandwich.copy();
        toasted.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 82, 2).addIngredient(VanillaTypes.ITEM, toasted);
    }

    @Override
    public void draw(GrillRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY)
    {
        this.flame.draw(poseStack, 51, 29);
        this.arrow.draw(poseStack, 47, 10);
    }
}
