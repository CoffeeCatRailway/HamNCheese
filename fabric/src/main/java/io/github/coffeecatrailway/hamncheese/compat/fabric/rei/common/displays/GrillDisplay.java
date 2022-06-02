package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.GrillRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 1/06/2022
 */
public class GrillDisplay extends BasicDisplay
{
    private static Pair<List<EntryIngredient>, List<EntryIngredient>> SANDWICH;

    public GrillDisplay(GrillRecipe recipe)
    {
        this(getSandwich().getFirst(), getSandwich().getSecond(), Optional.ofNullable(recipe.getId()));
    }

    private static Pair<List<EntryIngredient>, List<EntryIngredient>> getSandwich()
    {
        if (SANDWICH != null)
            return SANDWICH;
        ItemStack sandwich = new ItemStack(HNCItems.SANDWICH.get());
        for (ItemStack stack : FoodPicker.pickFoods(7))
            sandwich = SandwichItem.addIngredient(sandwich, stack);

        ItemStack toasted = sandwich.copy();
        toasted.getOrCreateTag().putBoolean(SandwichItem.TAG_TOASTED, true);

        SANDWICH = Pair.of(Collections.singletonList(EntryIngredients.of(sandwich)), Collections.singletonList(EntryIngredients.of(toasted)));
        return SANDWICH;
    }

    public GrillDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location)
    {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return HNCREIPlugin.GRILL;
    }

    public static BasicDisplay.Serializer<GrillDisplay> serializer()
    {
        return BasicDisplay.Serializer.ofSimple(GrillDisplay::new);
    }
}
