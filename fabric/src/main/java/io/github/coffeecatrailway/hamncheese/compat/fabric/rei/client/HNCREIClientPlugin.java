package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client;

import io.github.coffeecatrailway.hamncheese.client.gui.screens.GrillScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PizzaOvenScreen;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.GrillRecipe;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PizzaOvenRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories.GrillCategory;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.client.categories.PizzaOvenCategory;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.GrillDisplay;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays.PizzaOvenDisplay;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new GrillCategory(), new PizzaOvenCategory());

        registry.removePlusButton(HNCREIPlugin.GRILL);
        registry.removePlusButton(HNCREIPlugin.PIZZA_OVEN);

        registry.addWorkstations(HNCREIPlugin.GRILL, EntryStacks.of(HNCBlocks.GRILL.get()), EntryStacks.of(HNCItems.SANDWICH.get()));
        registry.addWorkstations(HNCREIPlugin.PIZZA_OVEN, EntryStacks.of(HNCBlocks.PIZZA_OVEN.get()), EntryStacks.of(HNCItems.PIZZA.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        this.addSandwichDisplay(registry, HNCItemTags.BREAD_SLICE, true, HNCItems.SANDWICH.get());
        this.addSandwichDisplay(registry, HNCItemTags.CRACKER_COMMON, false, HNCItems.CRACKER.get());
        this.addSandwichDisplay(registry, HNCItemTags.PIZZAS_COMMON, false, HNCItems.PIZZA.get(), HNCItems.TOMATO_SAUCE.get());

        registry.add(new DefaultCustomDisplay(null, List.of(EntryIngredients.of(HNCItems.MAPLE_SAP_BOTTLE.get()), EntryIngredients.ofIngredient(Ingredient.of(HNCItemTags.SUGAR_COMMON))), List.of(EntryIngredients.of(HNCItems.MAPLE_SYRUP.get()))));

        registry.registerFiller(GrillRecipe.class, GrillDisplay::new);
        registry.registerFiller(PizzaOvenRecipe.class, PizzaOvenDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry)
    {
        registry.registerContainerClickArea(new Rectangle(76, 26, 24, 17), GrillScreen.class, HNCREIPlugin.GRILL);
        registry.registerContainerClickArea(new Rectangle(88, 18, 24, 17), PizzaOvenScreen.class, HNCREIPlugin.PIZZA_OVEN);
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
