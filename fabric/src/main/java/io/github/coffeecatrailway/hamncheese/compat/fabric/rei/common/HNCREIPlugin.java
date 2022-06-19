package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.ChoppingBoardDisplay;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.GrillDisplay;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.PizzaOvenDisplay;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.PopcornDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;

/**
 * @author CoffeeCatRailway
 * Created: 1/06/2022
 */
public class HNCREIPlugin implements REIServerPlugin
{
    public static final CategoryIdentifier<GrillDisplay> GRILL = CategoryIdentifier.of(HamNCheese.getLocation("plugins/grill"));
    public static final CategoryIdentifier<PizzaOvenDisplay> PIZZA_OVEN = CategoryIdentifier.of(HamNCheese.getLocation("plugins/pizza_oven"));
    public static final CategoryIdentifier<PopcornDisplay> POPCORN = CategoryIdentifier.of(HamNCheese.getLocation("plugins/popcorn"));
    public static final CategoryIdentifier<ChoppingBoardDisplay> CHOPPING_BOARD = CategoryIdentifier.of(HamNCheese.getLocation("plugins/chopping_board"));

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry)
    {
        registry.register(GRILL, GrillDisplay.serializer());
        registry.register(PIZZA_OVEN, PizzaOvenDisplay.serializer());
        registry.register(POPCORN, PopcornDisplay.Serializer.of());
        registry.register(CHOPPING_BOARD, ChoppingBoardDisplay.Serializer.of());
    }
}
