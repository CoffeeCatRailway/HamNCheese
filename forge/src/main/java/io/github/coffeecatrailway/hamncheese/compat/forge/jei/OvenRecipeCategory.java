package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PizzaOvenRecipe;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 27/07/2021
 */
public class OvenRecipeCategory implements IRecipeCategory<PizzaOvenRecipe>
{
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public OvenRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(CompatCommon.JEI_REI_SHEET, 2, 131, 130, 58);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(HNCBlocks.PIZZA_OVEN.get()));
        this.arrow = guiHelper.drawableBuilder(CompatCommon.JEI_REI_SHEET, 200, 0, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getUid()
    {
        return HNCJEIPlugin.OVEN.getUid();
    }

    @Override
    @SuppressWarnings("removal")
    public Class<? extends PizzaOvenRecipe> getRecipeClass()
    {
        return HNCJEIPlugin.OVEN.getRecipeClass();
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.PIZZA_OVEN.get().getName();
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
    public void setRecipe(IRecipeLayoutBuilder builder, PizzaOvenRecipe recipe, IFocusGroup focuses)
    {
        List<ItemStack> selected = FoodPicker.pickFoods(7);
        List<ItemStack> copy = new ArrayList<>(selected);
        copy.add(0, new ItemStack(HNCItems.UNBAKED_PIZZA_BASE.get()));
        copy.add(1, new ItemStack(HNCItems.TOMATO_SAUCE.get()));
        int index;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                index = j * 3 + i;
                if (index + 1 > copy.size())
                    break;
                builder.addSlot(RecipeIngredientRole.INPUT, 3 + j * 18, 3 + i * 18).addIngredient(VanillaTypes.ITEM, copy.get(index));
            }
        }

        ItemStack pizza = new ItemStack(HNCItems.PIZZA.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(pizza, stack));
        pizza.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 21).addIngredient(VanillaTypes.ITEM, pizza);
    }

    @Override
    public void draw(PizzaOvenRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY)
    {
        this.arrow.draw(poseStack, 67, 19);
    }
}
