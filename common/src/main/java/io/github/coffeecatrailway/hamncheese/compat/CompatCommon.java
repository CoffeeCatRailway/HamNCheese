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
public class CompatCommon // TODO: Use for jei
{
    public static final ResourceLocation PIZZA_OVEN = HamNCheese.getLocation("textures/gui/jei/pizza_oven.png");
    public static final ResourceLocation POPCORN_MACHINE = HamNCheese.getLocation("textures/gui/jei/popcorn.png");
    public static final ResourceLocation CHOPPING_BOARD = HamNCheese.getLocation("textures/gui/jei/chopping_board.png");

    public static final List<ItemStack> CHOPPING_BOARDS = Registry.BLOCK.stream().filter(block -> block instanceof ChoppingBoardBlock).map(ItemStack::new).collect(Collectors.toList());
}
