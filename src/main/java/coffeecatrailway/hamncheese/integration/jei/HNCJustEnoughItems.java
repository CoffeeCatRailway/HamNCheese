package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.item.ItemStack;
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
    public void registerRecipes(IRecipeRegistration reg)
    {
        reg.addIngredientInfo(ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof ChoppingBoardBlock).map(ItemStack::new).collect(Collectors.toList()), VanillaTypes.ITEM,
                new TranslationTextComponent("jei.hamncheese.chopping_board.how"),
                new TranslationTextComponent("jei.hamncheese.chopping_board.example"));
    }
}
