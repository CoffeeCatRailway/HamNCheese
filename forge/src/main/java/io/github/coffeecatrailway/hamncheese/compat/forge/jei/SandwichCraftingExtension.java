package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import com.google.common.collect.Lists;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.AbstractSandwichRecipe;
import io.github.coffeecatrailway.hamncheese.compat.FoodPicker;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 17/06/2021
 */
public class SandwichCraftingExtension<T extends AbstractSandwichRecipe> implements ICraftingCategoryExtension
{
    private final T recipe;
    private final Tag.Named<Item> bunTag;
    private final Supplier<? extends ItemLike> defaultItem;
    private boolean hasTwoBuns = true;
    private ItemStack neededItem = ItemStack.EMPTY;

    public SandwichCraftingExtension(T recipe, Tag.Named<Item> bunTag, Supplier<? extends ItemLike> defaultItem)
    {
        this.recipe = recipe;
        this.bunTag = bunTag;
        this.defaultItem = defaultItem;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper helper, IFocusGroup focuses)
    {
        List<ItemStack> breadSlice = this.bunTag.getValues().stream().map(ItemStack::new).toList();
        List<ItemStack> selected = FoodPicker.pickFoods(7);
        List<List<ItemStack>> inputs = new ArrayList<>();

        inputs.add(breadSlice);
        if (!this.neededItem.isEmpty())
            inputs.add(Lists.newArrayList(this.neededItem));
        selected.forEach(stack -> inputs.add(Lists.newArrayList(stack)));
        if (this.hasTwoBuns)
            inputs.add(breadSlice);
        helper.setInputs(builder, VanillaTypes.ITEM, inputs, 3, 3);

        ItemStack sandwich = new ItemStack(this.defaultItem.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        helper.setOutputs(builder, VanillaTypes.ITEM, List.of(sandwich));
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return recipe.getId();
    }

    public SandwichCraftingExtension<T> hasOneBun()
    {
        this.hasTwoBuns = false;
        return this;
    }

    public SandwichCraftingExtension<T> setNeededItem(Item neededItem)
    {
        this.neededItem = new ItemStack(neededItem);
        return this;
    }
}
