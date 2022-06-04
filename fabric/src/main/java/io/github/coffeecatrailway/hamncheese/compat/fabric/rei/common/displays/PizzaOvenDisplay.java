package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PizzaOvenRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 2/06/2022
 */
public class PizzaOvenDisplay extends BasicDisplay implements SimpleGridMenuDisplay
{
    private List<EntryIngredient> inputEntry;
    private List<EntryIngredient> outputEntry;

    public PizzaOvenDisplay(PizzaOvenRecipe recipe)
    {
        this(getPizza().getFirst(), getPizza().getSecond(), Optional.ofNullable(recipe.getId()));
    }

    public PizzaOvenDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location)
    {
        super(inputs, outputs, location);
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return this.getInputEntries(true);
    }

    private List<EntryIngredient> getInputEntries(boolean newPizza)
    {
        if (newPizza || this.inputEntry == null || this.outputEntry == null)
        {
            Pair<List<EntryIngredient>, List<EntryIngredient>> sandwich = getPizza();
            this.inputEntry = sandwich.getFirst();
            this.outputEntry = sandwich.getSecond();
        }
        return this.inputEntry;
    }

    public List<InputIngredient<EntryStack<?>>> getInputIngredients()
    {
        InputIngredient<EntryStack<?>>[][] grid = new InputIngredient[getWidth()][getHeight()];
        List<EntryIngredient> inputEntries = getInputEntries(false);
        int i, n;
        for (i = 0; i < inputEntries.size(); i++)
        {
            int x = i % getWidth();
            int y = (i - x) / getWidth();
            grid[i % getWidth()][i / getWidth()] = InputIngredient.of(getWidth() * y + x, inputEntries.get(i));
        }

        List<InputIngredient<EntryStack<?>>> list = new ArrayList<>(getWidth() * getHeight());
        for (i = 0, n = getWidth() * getHeight(); i < n; i++)
            list.add(InputIngredient.empty(i));

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (grid[x][y] != null) {
                    int index = getWidth() * y + x;
                    list.set(index, grid[x][y]);
                }
            }
        }

        return list;
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        if (this.outputEntry == null)
            this.getInputEntries();
        return this.outputEntry;
    }

    @Override
    public int getWidth()
    {
        return 3;
    }

    @Override
    public int getHeight()
    {
        return 3;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return HNCREIPlugin.PIZZA_OVEN;
    }

    private static Pair<List<EntryIngredient>, List<EntryIngredient>> getPizza()
    {
        ItemStack pizza = FoodPicker.generateSandwich(HNCItems.PIZZA.get(), 7, true, true);
        List<EntryIngredient> ingredients = AbstractSandwichItem.getIngredients(pizza).stream().map(EntryIngredients::of).collect(Collectors.toList());
        ingredients.add(0, EntryIngredients.of(HNCItems.UNBAKED_PIZZA_BASE.get()));
        ingredients.add(1, EntryIngredients.of(HNCItems.TOMATO_SAUCE.get()));
        return Pair.of(ingredients, Collections.singletonList(EntryIngredients.of(pizza)));
    }

    public static BasicDisplay.Serializer<PizzaOvenDisplay> serializer()
    {
        return BasicDisplay.Serializer.ofSimple(PizzaOvenDisplay::new);
    }
}
