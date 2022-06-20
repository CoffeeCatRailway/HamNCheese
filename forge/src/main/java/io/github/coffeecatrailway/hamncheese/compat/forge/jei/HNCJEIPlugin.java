package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import com.google.common.collect.Lists;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.GrillScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PizzaOvenScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PopcornMachineScreen;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.*;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.*;
import mezz.jei.plugins.vanilla.crafting.CategoryRecipeValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 12/04/2022
 */
@JeiPlugin
public class HNCJEIPlugin implements IModPlugin
{
    protected static final RecipeType<GrillRecipe> GRILL = new RecipeType<>(HamNCheese.getLocation("grill"), GrillRecipe.class);
    protected static final RecipeType<PizzaOvenRecipe> OVEN = new RecipeType<>(HamNCheese.getLocation("oven"), PizzaOvenRecipe.class);
    protected static final RecipeType<PopcornRecipe> POPCORN = new RecipeType<>(HamNCheese.getLocation("popcorn"), PopcornRecipe.class);
    protected static final RecipeType<ChoppingBoardRecipe> CHOPPING_BOARD = new RecipeType<>(HamNCheese.getLocation("chopping_board"), ChoppingBoardRecipe.class);

    private GrillRecipeCategory grillCategory;
    private OvenRecipeCategory ovenCategory;
    private PopcornRecipeCategory popcornCategory;
    private ChoppingBoardRecipeCategory choppingBoardCategory;

    @Override
    public ResourceLocation getPluginUid()
    {
        return HamNCheese.getLocation("plugin/jei");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration reg)
    {
        reg.getCraftingCategory().addCategoryExtension(SandwichRecipe.class, CustomRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.BREAD_SLICE, HNCItems.SANDWICH));
        reg.getCraftingCategory().addCategoryExtension(CrackerRecipe.class, CustomRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.CRACKER_COMMON, HNCItems.CRACKER).hasOneBun());
        reg.getCraftingCategory().addCategoryExtension(PizzaRecipe.class, CustomRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.PIZZAS_COMMON, HNCItems.PIZZA).hasOneBun().setNeededItem(HNCItems.TOMATO_SAUCE.get()));

        reg.getCraftingCategory().addCategoryExtension(MapleSyrupRecipe.class, CustomRecipe::isSpecial, recipe -> new ICraftingCategoryExtension()
        {
            @Override
            public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper helper, IFocusGroup focuses)
            {
                helper.setInputs(builder, VanillaTypes.ITEM, List.of(List.of(new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get())), ForgeRegistries.ITEMS.tags().getTag(HNCItemTags.SUGAR_COMMON).stream().map(ItemStack::new).toList()), 2, 1);
                helper.setOutputs(builder, VanillaTypes.ITEM, List.of(new ItemStack(HNCItems.MAPLE_SYRUP.get())));
            }

            @Override
            public @Nullable ResourceLocation getRegistryName()
            {
                return recipe.getId();
            }
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg)
    {
        IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();
        reg.addRecipeCategories(this.grillCategory = new GrillRecipeCategory(guiHelper),
                this.ovenCategory = new OvenRecipeCategory(guiHelper),
                this.popcornCategory = new PopcornRecipeCategory(guiHelper),
                this.choppingBoardCategory = new ChoppingBoardRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {
        reg.addIngredientInfo(Lists.<ItemLike>newArrayList(HNCItems.MAPLE_SAP_BOTTLE.get(), HNCFluids.MAPLE_SAP_BUCKET.get(), HNCBlocks.TREE_TAP.get()).stream().map(ItemStack::new).collect(Collectors.toList()),
                VanillaTypes.ITEM, new TranslatableComponent("jei." + HamNCheese.MOD_ID + ".maple_sap"));

        reg.addRecipes(GRILL, getRecipesOfType(HNCRecipes.GRILL_TYPE, this.grillCategory));
        reg.addRecipes(OVEN, getRecipesOfType(HNCRecipes.PIZZA_OVEN_TYPE, this.ovenCategory));
        reg.addRecipes(POPCORN, getRecipesOfType(HNCRecipes.POPCORN_TYPE, this.popcornCategory));
        reg.addRecipes(CHOPPING_BOARD, getRecipesOfType(HNCRecipes.CHOPPING_BOARD_TYPE, this.choppingBoardCategory));
    }

    private static <C extends Container, R extends Recipe<C>> List<R> getRecipesOfType(net.minecraft.world.item.crafting.RecipeType<R> type, IRecipeCategory<R> category)
    {
        CategoryRecipeValidator<R> validator = new CategoryRecipeValidator<>(category, 1);
        return getValidHandledRecipes(type, validator);
    }

    private static <C extends Container, R extends Recipe<C>> List<R> getValidHandledRecipes(net.minecraft.world.item.crafting.RecipeType<R> recipeType, CategoryRecipeValidator<R> validator)
    {
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(recipeType)
                .stream()
                .filter(r -> validator.isRecipeValid(r) && validator.isRecipeHandled(r))
                .toList();
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg)
    {
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.GRILL.get()), GRILL);
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.PIZZA_OVEN.get()), OVEN);
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.POPCORN_MACHINE.get()), POPCORN);
        CompatCommon.CHOPPING_BOARDS.forEach(stack -> reg.addRecipeCatalyst(stack, CHOPPING_BOARD));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration reg)
    {
        reg.addRecipeClickArea(GrillScreen.class, 76, 26, 24, 17, GRILL);
        reg.addRecipeClickArea(PizzaOvenScreen.class, 88, 18, 24, 17, OVEN);
        reg.addRecipeClickArea(PopcornMachineScreen.class, 109, 4, 61, 12, POPCORN);
    }
}
