package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories;

import io.github.coffeecatrailway.hamncheese.client.gui.screens.PopcornMachineScreen;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PopcornMachineContainer;
import io.github.coffeecatrailway.hamncheese.compat.CompatTextures;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.PopcornDisplay;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplayMerger;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2022
 */
@Environment(EnvType.CLIENT)
public class PopcornCategory implements DisplayCategory<PopcornDisplay>
{
    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(HNCBlocks.POPCORN_MACHINE.get());
    }

    @Override
    public Component getTitle()
    {
        return HNCBlocks.POPCORN_MACHINE.get().getName();
    }

    @Override
    public List<Widget> setupDisplay(PopcornDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX() - 66, bounds.getCenterY() - 33);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(CompatTextures.POPCORN_MACHINE, startPoint.x, startPoint.y, 0, 0, 133, 67, 133, 67)); // Background

        // Popcorn
        int popcornScaled = PopcornMachineContainer.getPopcornScaled(display.getPopcorn());
        widgets.add(Widgets.createTexturedWidget(PopcornMachineScreen.TEXTURE, startPoint.x + 61, startPoint.y + 41 - popcornScaled, 178, 60 - popcornScaled, 28, popcornScaled)); // Popcorn
        widgets.add(Widgets.createTexturedWidget(PopcornMachineScreen.TEXTURE, startPoint.x + 61, startPoint.y + 15, 178, 2, 28, 26)); // Window

        // Ingredients
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 33, startPoint.y + 4)).entry(EntryStacks.of(HNCItems.DRIED_CORN_KERNELS.get())).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 29, startPoint.y + 24)).entries(display.getInputEntries().get(0)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 33, startPoint.y + 44)).entries(display.getInputEntries().get(1)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 108, startPoint.y + 46)).entries(display.getInputEntries().get(2)).disableBackground().markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 108, startPoint.y + 21)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayWidth(PopcornDisplay display)
    {
        return 133;
    }

    @Override
    public int getDisplayHeight()
    {
        return 67;
    }

    @Override
    public CategoryIdentifier<? extends PopcornDisplay> getCategoryIdentifier()
    {
        return HNCREIPlugin.POPCORN;
    }

    @Override
    @Nullable
    public DisplayMerger<PopcornDisplay> getDisplayMerger()
    {
        return DisplayCategory.getContentMerger();
    }
}
