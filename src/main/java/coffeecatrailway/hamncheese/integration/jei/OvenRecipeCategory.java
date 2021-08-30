package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.client.gui.screen.PizzaOvenScreen;
import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.common.item.crafting.PizzaOvenRecipe;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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

    private final IDrawableAnimated flame;
    private final IDrawableAnimated arrow;

    public OvenRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(PizzaOvenScreen.TEXTURE, 6, 6, 152, 64);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(HNCBlocks.PIZZA_OVEN.get()));

        this.flame = guiHelper.drawableBuilder(PizzaOvenScreen.TEXTURE, 176, 0, 14, 14)
                .buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
        this.arrow = guiHelper.drawableBuilder(PizzaOvenScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return HNCJustEnoughItems.OVEN_UID;
    }

    @Override
    public Class<? extends PizzaOvenRecipe> getRecipeClass()
    {
        return PizzaOvenRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return HNCBlocks.PIZZA_OVEN.get().getName().getString();
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
    public void setIngredients(PizzaOvenRecipe recipe, IIngredients ingredients)
    {
        List<ItemStack> selected = FoodsGetter.pickFoods(7);
        List<ItemStack> copy = new ArrayList<>(selected);
        copy.add(0, new ItemStack(HNCItems.UNBAKED_PIZZA_BASE.get()));
        copy.add(1, new ItemStack(HNCItems.TOMATO_SAUCE.get()));
        ingredients.setInputs(VanillaTypes.ITEM, copy);

        ItemStack pizza = new ItemStack(HNCItems.PIZZA.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(pizza, stack));
        pizza.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        ingredients.setOutput(VanillaTypes.ITEM, pizza);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, PizzaOvenRecipe recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        int index, count = ingredients.getInputs(VanillaTypes.ITEM).size();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                index = j * 3 + i;
                if (index + 1 > count)
                    break;
                guiItemStacks.init(index, true, 1 + j * 18, 1 + i * 18);
            }
        guiItemStacks.init(9, false, 129, 12);
        guiItemStacks.set(ingredients);
    }

    @Override
    public void draw(PizzaOvenRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        this.flame.draw(matrixStack, 82, 30);
        this.arrow.draw(matrixStack, 82, 12);
    }
}
