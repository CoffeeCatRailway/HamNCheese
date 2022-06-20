package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 20/05/2021
 */
public class PizzaOvenRecipe implements Recipe<Container>, CustomSandwichRecipe<Container>
{
    private final ResourceLocation id;

    public PizzaOvenRecipe(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public TagKey<Item> getBunTag()
    {
        return HNCItemTags.PIZZAS_COMMON;
    }

    @Override
    public ItemLike getDefaultBunItem()
    {
        return HNCItems.PIZZA.get();
    }

    @Nullable
    @Override
    public ItemLike getNeededItem()
    {
        return HNCItems.TOMATO_SAUCE.get();
    }

    @Override
    public Pair<Integer, Integer> getCheckSlots(Container inventory)
    {
        return Pair.of(0, 9);
    }

    @Override
    public ItemStack assemble(Container inventory)
    {
        ItemStack result = CustomSandwichRecipe.super.assemble(inventory);
        if (result.getItem() instanceof AbstractSandwichItem)
            result.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        return result;
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(HNCBlocks.PIZZA_OVEN.get());
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.PIZZA_OVEN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return HNCRecipes.PIZZA_OVEN_TYPE;
    }
}
