package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.CrackerRecipe;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.MapleSyrupRecipe;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PizzaRecipe;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.SandwichRecipe;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 12/04/2022
 */
@JeiPlugin
public class HNCJEIPlugin implements IModPlugin
{
    private static final ResourceLocation UID = HamNCheese.getLocation("plugin/jei");

//    protected static final ResourceLocation GRILL_UID = HamNCheese.getLocation("category/grill");
//    protected static final ResourceLocation OVEN_UID = HamNCheese.getLocation("category/oven");
//    protected static final ResourceLocation POPCORN_UID = HamNCheese.getLocation("category/popcorn");

    @Override
    public ResourceLocation getPluginUid()
    {
        return UID;
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
                helper.setInputs(builder, VanillaTypes.ITEM_STACK, List.of(List.of(new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get())), Arrays.asList(Ingredient.of(HNCItemTags.SUGAR_COMMON).getItems())), 2, 1);
                helper.setOutputs(builder, VanillaTypes.ITEM_STACK, List.of(new ItemStack(HNCItems.MAPLE_SYRUP.get())));
            }

            @Override
            public @Nullable ResourceLocation getRegistryName()
            {
                return recipe.getId();
            }
        });
    }

//    @Override
//    public void registerCategories(IRecipeCategoryRegistration reg)
//    {
//        IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();
//        reg.addRecipeCategories(new GrillRecipeCategory(guiHelper));
//        reg.addRecipeCategories(new OvenRecipeCategory(guiHelper));
//        reg.addRecipeCategories(new PopcornRecipeCategory(guiHelper));
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration reg)
//    {
//        reg.addIngredientInfo(Lists.<ItemLike>newArrayList(HNCItems.MAPLE_SAP_BOTTLE.get(), HNCItems.MAPLE_SAP_BUCKET.get(), HNCBlocks.TREE_TAP.get()).stream().map(ItemStack::new).collect(Collectors.toList()),
//                VanillaTypes.ITEM, new TranslatableComponent("jei." + HamNCheese.MOD_ID + ".maple_sap"));
//
//        reg.addRecipes(getRecipesOfType(HNCRecipes.GRILL_TYPE), GRILL_UID);
//        reg.addRecipes(getRecipesOfType(HNCRecipes.PIZZA_OVEN_TYPE), OVEN_UID);
//        reg.addRecipes(getRecipesOfType(HNCRecipes.POPCORN_TYPE), POPCORN_UID);
//    }
//
//    private static List<Recipe<?>> getRecipesOfType(RecipeType<?> type)
//    {
//        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg)
//    {
//        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.GRILL.get()), GRILL_UID);
//        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.PIZZA_OVEN.get()), OVEN_UID);
//        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.POPCORN_MACHINE.get()), POPCORN_UID);
//    }
//
//    @Override
//    public void registerGuiHandlers(IGuiHandlerRegistration reg)
//    {
//        reg.addRecipeClickArea(GrillScreen.class, 76, 26, 24, 17, GRILL_UID);
//        reg.addRecipeClickArea(PizzaOvenScreen.class, 88, 18, 24, 17, OVEN_UID);
//        reg.addRecipeClickArea(PopcornMachineScreen.class, 109, 4, 61, 12, POPCORN_UID);
//    }
}
