package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.client.gui.screen.GrillScreen;
import coffeecatrailway.hamncheese.client.gui.screen.PizzaOvenScreen;
import coffeecatrailway.hamncheese.client.gui.screen.PopcornMachineScreen;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.item.crafting.CrackerRecipe;
import coffeecatrailway.hamncheese.common.item.crafting.PizzaRecipe;
import coffeecatrailway.hamncheese.common.item.crafting.SandwichRecipe;
import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCFluids;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 16/06/2021
 */
@JeiPlugin
public class HNCJustEnoughItems implements IModPlugin
{
    private static final ResourceLocation UID = HNCMod.getLocation("plugin/jei");

    protected static final ResourceLocation GRILL_UID = HNCMod.getLocation("category/grill");
    protected static final ResourceLocation OVEN_UID = HNCMod.getLocation("category/oven");
    protected static final ResourceLocation POPCORN_UID = HNCMod.getLocation("category/popcorn");

    @Override
    public ResourceLocation getPluginUid()
    {
        return UID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration reg)
    {
        reg.getCraftingCategory().addCategoryExtension(SandwichRecipe.class, SpecialRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.BREAD_SLICE, HNCItems.SANDWICH));
        reg.getCraftingCategory().addCategoryExtension(CrackerRecipe.class, SpecialRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.CRACKER, HNCItems.CRACKER).hasOneBun());
        reg.getCraftingCategory().addCategoryExtension(PizzaRecipe.class, SpecialRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.PIZZA, HNCItems.PIZZA).hasOneBun().setNeededItem(HNCItems.TOMATO_SAUCE.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg)
    {
        IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();
        reg.addRecipeCategories(new GrillRecipeCategory(guiHelper));
        reg.addRecipeCategories(new OvenRecipeCategory(guiHelper));
        reg.addRecipeCategories(new PopcornRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {
        reg.addIngredientInfo(Lists.<IItemProvider>newArrayList(HNCItems.MAPLE_SAP_BOTTLE.get(), HNCItems.MAPLE_SAP_BUCKET.get(), HNCBlocks.TREE_TAP.get()).stream().map(ItemStack::new).collect(Collectors.toList()),
                VanillaTypes.ITEM, new TranslationTextComponent("jei." + HNCMod.MOD_ID + ".maple_sap"));

        reg.addRecipes(getRecipesOfType(HNCRecipes.GRILL_TYPE), GRILL_UID);
        reg.addRecipes(getRecipesOfType(HNCRecipes.PIZZA_OVEN_TYPE), OVEN_UID);
        reg.addRecipes(getRecipesOfType(HNCRecipes.POPCORN_TYPE), POPCORN_UID);
    }

    private static List<IRecipe<?>> getRecipesOfType(IRecipeType<?> type)
    {
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg)
    {
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.GRILL.get()), GRILL_UID);
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.PIZZA_OVEN.get()), OVEN_UID);
        reg.addRecipeCatalyst(new ItemStack(HNCBlocks.POPCORN_MACHINE.get()), POPCORN_UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration reg)
    {
        reg.addRecipeClickArea(GrillScreen.class, 76, 26, 24, 17, GRILL_UID);
        reg.addRecipeClickArea(PizzaOvenScreen.class, 88, 18, 24, 17, OVEN_UID);
        reg.addRecipeClickArea(PopcornMachineScreen.class, 109, 4, 61, 12, POPCORN_UID);
    }
}
