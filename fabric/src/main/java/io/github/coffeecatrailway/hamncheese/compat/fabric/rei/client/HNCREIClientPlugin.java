package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 13/04/2022
 */
public class HNCREIClientPlugin implements REIClientPlugin
{
    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        this.addSandwichDisplay(registry, HNCItemTags.BREAD_SLICE, true, HNCItems.SANDWICH.get());
        this.addSandwichDisplay(registry, HNCItemTags.CRACKER_COMMON, false, HNCItems.CRACKER.get());
        this.addSandwichDisplay(registry, HNCItemTags.PIZZAS_COMMON, false, HNCItems.PIZZA.get(), HNCItems.TOMATO_SAUCE.get());
    }

    private void addSandwichDisplay(DisplayRegistry registry, Tag.Named<Item> bunTag, boolean hasTwoBuns, ItemLike defaultItem)
    {
        this.addSandwichDisplay(registry, bunTag, hasTwoBuns, defaultItem, null);
    }

    private void addSandwichDisplay(DisplayRegistry registry, Tag.Named<Item> bunTag, boolean hasTwoBuns, ItemLike defaultItem, @Nullable ItemLike neededItem)
    {
        List<EntryIngredient> breadSlice = bunTag.getValues().stream().map(EntryIngredients::of).toList();
        List<ItemStack> selected = FoodPicker.pickFoods(7);
        List<EntryIngredient> inputs = new ArrayList<>(breadSlice);

        if (neededItem != null)
            inputs.add(EntryIngredients.of(neededItem));
        selected.forEach(stack -> inputs.add(EntryIngredients.of(stack)));
        if (hasTwoBuns)
            inputs.addAll(breadSlice);

        ItemStack sandwich = new ItemStack(defaultItem);
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        registry.add(new DefaultCustomDisplay(null, inputs, List.of(EntryIngredients.of(sandwich))));
    }
}