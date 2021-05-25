package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 20/05/2021
 */
public class PizzaOvenRecipe implements IRecipe<IInventory>, ISandwichRecipe<IInventory>
{
    private final ResourceLocation id;

    public PizzaOvenRecipe(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public ITag.INamedTag<Item> getBunTag()
    {
        return HNCItemTags.PIZZA;
    }

    @Override
    public IItemProvider getDefaultBun()
    {
        return HNCItems.PIZZA.get();
    }

    @Nullable
    @Override
    public IItemProvider getNeededItem()
    {
        return HNCItems.TOMATO_SAUCE.get();
    }

    @Override
    public Pair<Integer, Integer> getCheckSlots(IInventory inventory)
    {
        return Pair.of(0, 9);
    }

    @Override
    public ItemStack assemble(IInventory inventory)
    {
        ItemStack result = ISandwichRecipe.super.assemble(inventory);
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
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.PIZZA_OVEN_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return HNCRecipes.PIZZA_OVEN_TYPE;
    }
}
