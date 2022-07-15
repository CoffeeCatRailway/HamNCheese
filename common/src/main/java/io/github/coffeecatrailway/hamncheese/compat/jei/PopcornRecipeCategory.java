package io.github.coffeecatrailway.hamncheese.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PopcornMachineContainer;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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

    private final IDrawable popcornWindow;
    private final IDrawableStatic popcorn;
    private final IDrawable flavourWindow;
    private final IDrawableAnimated flavour;

    public PopcornRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(CompatCommon.JEI_SHEET, 2, 64, 129, 63);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(HNCBlocks.POPCORN_MACHINE.get()));

        this.popcornWindow = guiHelper.createDrawable(CompatCommon.JEI_SHEET, 226, 2, 28, 26);
        this.popcorn = guiHelper.createDrawable(CompatCommon.JEI_SHEET, 226, 34, 28, 26);
        this.flavourWindow = guiHelper.createDrawable(CompatCommon.JEI_SHEET, 240, 64, 8, 22);
        this.flavour = guiHelper.drawableBuilder(CompatCommon.JEI_SHEET, 248, 64, 8, 22)
                .buildAnimated(200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getUid()
    {
        return HNCJeiPlugin.POPCORN.getUid();
    }

    @Override
    @SuppressWarnings("removal")
    public Class<? extends PopcornRecipe> getRecipeClass()
    {
        return HNCJeiPlugin.POPCORN.getRecipeClass();
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.POPCORN_MACHINE.get().getName();
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
    public void setRecipe(IRecipeLayoutBuilder builder, PopcornRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 31, 2).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(HNCItems.DRIED_CORN_KERNELS.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 22).addIngredients(recipe.getFlavouring());
        builder.addSlot(RecipeIngredientRole.INPUT, 31, 42).addIngredients(recipe.getSeasoning());

        ItemStack result = recipe.getResultItem();
        builder.addSlot(RecipeIngredientRole.INPUT, 106, 45).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(HNCItems.POPCORN_BAG.get(), result.getCount()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 19).addIngredient(VanillaTypes.ITEM_STACK, result);
    }

    @Override
    public void draw(PopcornRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY)
    {
        this.popcorn.draw(poseStack, 59, 13, PopcornMachineContainer.getPopcornScaled(PopcornMachineBlockEntity.MAX_POPCORN - recipe.getPopcorn()), 0, 0, 0);
        this.popcornWindow.draw(poseStack, 59, 13);
        this.flavour.draw(poseStack, 5, 19);
        this.flavourWindow.draw(poseStack, 5, 19);
    }

    @Override
    public List<Component> getTooltipStrings(PopcornRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY)
    {
        List<Component> tooltips = new ArrayList<>();
        if (mouseX >= 58 && mouseX < 58 + this.popcornWindow.getWidth() && mouseY >= 12 && mouseY < 12 + this.popcornWindow.getHeight())
            tooltips.add(HNCLanguage.getPopcorn(recipe.getPopcorn()));
        return tooltips;
    }
}
