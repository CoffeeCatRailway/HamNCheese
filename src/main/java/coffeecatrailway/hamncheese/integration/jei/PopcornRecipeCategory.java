package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.client.gui.screen.PopcornMachineScreen;
import coffeecatrailway.hamncheese.common.inventory.PopcornMachineContainer;
import coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import coffeecatrailway.hamncheese.common.tileentity.PopcornMachineTileEntity;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

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
        this.background = guiHelper.createDrawable(PopcornMachineScreen.TEXTURE, 16, 11, 127, 61);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(HNCBlocks.POPCORN_MACHINE.get()));

        this.popcornWindow = guiHelper.createDrawable(PopcornMachineScreen.TEXTURE, 178, 2, 28, 26);
        this.popcorn = guiHelper.createDrawable(PopcornMachineScreen.TEXTURE, 178, 34, 28, 26);
        this.flavourWindow = guiHelper.createDrawable(PopcornMachineScreen.TEXTURE, 176, 64, 8, 22);
        this.flavour = guiHelper.drawableBuilder(PopcornMachineScreen.TEXTURE, 184, 64, 8, 22)
                .buildAnimated(200, IDrawableAnimated.StartDirection.BOTTOM, false);
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

    @Override
    public void draw(PopcornRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        this.popcorn.draw(matrixStack, 58, 12, PopcornMachineContainer.getPopcornScaled(PopcornMachineTileEntity.MAX_POPCORN - recipe.getPopcorn()), 0, 0, 0);
        this.popcornWindow.draw(matrixStack, 58, 12);
        this.flavour.draw(matrixStack, 4, 18);
        this.flavourWindow.draw(matrixStack, 4, 18);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(PopcornRecipe recipe, double mouseX, double mouseY)
    {
        List<ITextComponent> tooltips = new ArrayList<>();
        if (mouseX >= 58 && mouseX < 58 + this.popcornWindow.getWidth() && mouseY >= 12 && mouseY < 12 + this.popcornWindow.getHeight())
            tooltips.add(HNCLanguage.getPopcorn(recipe.getPopcorn()));
        return tooltips;
    }
}
