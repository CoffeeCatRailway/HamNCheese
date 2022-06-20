package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.compat.CompatCommon;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.ChoppingBoardDisplay;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 20/06/2022
 */
public class ChoppingBoardCategory implements DisplayCategory<ChoppingBoardDisplay>
{
    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(new ItemStack(HNCBlocks.OAK_CHOPPING_BOARD.get()));
    }

    @Override
    public Component getTitle()
    {
        return new TranslatableComponent("jei." + HamNCheese.MOD_ID + ".category.chopping_board");
    }

    @Override
    public List<Widget> setupDisplay(ChoppingBoardDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX() - 43, bounds.getCenterY() - 31);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createTexturedWidget(CompatCommon.JEI_REI_SHEET, startPoint.x, startPoint.y, 0, 0, 86, 62)); // Background

        // Ingredients
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12, startPoint.y + 23)).entries(display.getInputEntries().get(0)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 35, startPoint.y + 12)).entries(display.getInputEntries().get(1)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 35, startPoint.y + 34)).entries(CompatCommon.CHOPPING_BOARDS.stream().map(EntryStacks::of).collect(Collectors.toList())).disableBackground().unmarkInputOrOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 58, startPoint.y + 23)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayWidth(ChoppingBoardDisplay display)
    {
        return 86;
    }

    @Override
    public int getDisplayHeight()
    {
        return 62;
    }

    @Override
    public CategoryIdentifier<? extends ChoppingBoardDisplay> getCategoryIdentifier()
    {
        return HNCREIPlugin.CHOPPING_BOARD;
    }
}
