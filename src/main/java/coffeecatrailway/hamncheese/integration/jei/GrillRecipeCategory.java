package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.client.gui.screen.GrillScreen;
import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.common.item.crafting.GrillRecipe;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        background = guiHelper.createDrawable(GrillScreen.TEXTURE, 29, 16, 118, 63);
        icon = guiHelper.createDrawableIngredient(new ItemStack(HNCBlocks.GRILL.get()));

        flame = guiHelper.drawableBuilder(GrillScreen.TEXTURE, 176, 0, 14, 14)
                .buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
        arrow = guiHelper.drawableBuilder(GrillScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return HNCJustEnoughItems.GRILL_UID;
    }

    @Override
    public Class<? extends GrillRecipe> getRecipeClass()
    {
        return GrillRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return HNCBlocks.GRILL.get().getName().getString();
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
    public void setIngredients(GrillRecipe recipe, IIngredients ingredients)
    {
        Random random = new Random(41L);
        if (Minecraft.getInstance().level != null)
            random = Minecraft.getInstance().level.random;
        List<ItemStack> selected = FoodsGetter.pickFoods(random.nextInt(6) + 1, random);
        ItemStack sandwich = new ItemStack(HNCItems.SANDWICH.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        ingredients.setInput(VanillaTypes.ITEM, sandwich);

        ItemStack toasted = sandwich.copy();
        toasted.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        ingredients.setOutput(VanillaTypes.ITEM, toasted);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, GrillRecipe recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        guiItemStacks.init(0, true, 1, 1);
        guiItemStacks.init(6, false, 81, 1);
        guiItemStacks.set(ingredients);
    }

    @Override
    public void draw(GrillRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        flame.draw(matrixStack, 51, 29);
        arrow.draw(matrixStack, 47, 10);
    }
}
