package io.github.coffeecatrailway.hamncheese.compat;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 5/06/2022
 */
public class CompatCommon
{
    public static final ResourceLocation JEI_REI_SHEET = HamNCheese.getLocation("textures/gui/jei_rei.png");
    public static final List<ItemStack> CHOPPING_BOARDS = Registry.BLOCK.stream().filter(block -> block instanceof ChoppingBoardBlock).map(ItemStack::new).collect(Collectors.toList());
}
