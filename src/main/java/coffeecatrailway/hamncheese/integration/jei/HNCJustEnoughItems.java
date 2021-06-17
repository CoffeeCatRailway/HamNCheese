package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.item.crafting.PizzaRecipe;
import coffeecatrailway.hamncheese.common.item.crafting.SandwichRecipe;
import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 16/06/2021
 */
@JeiPlugin
public class HNCJustEnoughItems implements IModPlugin
{
    private static final ResourceLocation UID = HNCMod.getLocation("plugin/jei");

    @Override
    public ResourceLocation getPluginUid()
    {
        return UID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration reg)
    {
        reg.getCraftingCategory().addCategoryExtension(SandwichRecipe.class, SpecialRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.BREAD_SLICE, HNCItems.SANDWICH));
        reg.getCraftingCategory().addCategoryExtension(PizzaRecipe.class, SpecialRecipe::isSpecial, recipe -> new SandwichCraftingExtension<>(recipe, HNCItemTags.PIZZA, HNCItems.PIZZA));
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {
        reg.addIngredientInfo(ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof ChoppingBoardBlock).map(ItemStack::new).collect(Collectors.toList()), VanillaTypes.ITEM,
                new TranslationTextComponent("jei.hamncheese.chopping_board.how"),
                new TranslationTextComponent("jei.hamncheese.chopping_board.example"));
    }
}
