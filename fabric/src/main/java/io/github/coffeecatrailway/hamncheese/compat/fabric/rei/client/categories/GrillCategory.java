package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories;

import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.GrillDisplay;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 1/06/2022
 */
public class GrillCategory implements DisplayCategory<GrillDisplay>
{
    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(HNCBlocks.GRILL.get());
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.GRILL.get().getName();
    }

    @Override
    public List<Widget> setupDisplay(GrillDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX() - 31, bounds.getCenterY() - 13);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 17, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 10 - 22, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 36;
    }

    @Override
    public CategoryIdentifier<? extends GrillDisplay> getCategoryIdentifier()
    {
        return HNCREIPlugin.GRILL;
    }
}
