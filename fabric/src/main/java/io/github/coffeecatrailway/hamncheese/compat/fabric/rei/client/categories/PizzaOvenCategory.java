package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories;

import com.google.common.collect.Lists;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.PizzaOvenDisplay;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 3/06/2022
 */
@Environment(EnvType.CLIENT)
public class PizzaOvenCategory implements DisplayCategory<PizzaOvenDisplay>
{
    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(HNCBlocks.PIZZA_OVEN.get());
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.PIZZA_OVEN.get().getName();
    }

    @Override
    public List<Widget> setupDisplay(PizzaOvenDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX() - 67, bounds.getCenterY() - 31);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createTexturedWidget(CompatCommon.PIZZA_OVEN, startPoint.x, startPoint.y, 0, 0, 134, 62, 134, 62));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 69, startPoint.y + 21)).animationDurationTicks(200));

        // Create slot grid
        List<Slot> slots = Lists.newArrayList();
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                slots.add(Widgets.createSlot(new Point(startPoint.x + 5 + x * 18, startPoint.y + 5 + y * 18)).disableBackground().markInput());
        // Fill slots
        for (InputIngredient<EntryStack<?>> ingredient : display.getInputIngredients())
            slots.get(ingredient.getIndex()).entries(ingredient.get());
        // Add slots
        widgets.addAll(slots);

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 109, startPoint.y + 24)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayWidth(PizzaOvenDisplay display)
    {
        return 134;
    }

    @Override
    public int getDisplayHeight()
    {
        return 62;
    }

    @Override
    public CategoryIdentifier<? extends PizzaOvenDisplay> getCategoryIdentifier()
    {
        return HNCREIPlugin.PIZZA_OVEN;
    }
}
