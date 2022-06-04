package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.GrillRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 1/06/2022
 */
public class GrillDisplay extends BasicDisplay
{
    private List<EntryIngredient> outputEntry;

    public GrillDisplay(GrillRecipe recipe)
    {
        this(getSandwich().getFirst(), getSandwich().getSecond(), Optional.ofNullable(recipe.getId()));
    }

    public GrillDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location)
    {
        super(inputs, outputs, location);
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        Pair<List<EntryIngredient>, List<EntryIngredient>> sandwich = getSandwich();
        this.outputEntry = sandwich.getSecond();
        return sandwich.getFirst();
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return this.outputEntry;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return HNCREIPlugin.GRILL;
    }

    private static Pair<List<EntryIngredient>, List<EntryIngredient>> getSandwich()
    {
        return FoodPicker.generateSandwichPairMapped(HNCItems.SANDWICH.get(), 7, true, stack -> Collections.singletonList(EntryIngredients.of(stack)));
    }

    public static BasicDisplay.Serializer<GrillDisplay> serializer()
    {
        return BasicDisplay.Serializer.ofSimple(GrillDisplay::new);
    }
}
