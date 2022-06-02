package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.GrillDisplay;
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

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry)
    {
        registry.register(GRILL, GrillDisplay.serializer());
    }
}
