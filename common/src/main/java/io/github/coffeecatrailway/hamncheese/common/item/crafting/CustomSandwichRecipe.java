package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

/**
 * @author CoffeeCatRailway
 * Created: 12/04/2022
 */
public interface CustomSandwichRecipe<T extends Container> extends Recipe<T>
{
    Tag.Named<Item> getBunTag();

    ItemLike getDefaultBunItem();

    @Nullable
    ItemLike getNeededItem();

    default Pair<Integer, Integer> getCheckSlots(T inventory)
    {
        return Pair.of(0, inventory.getContainerSize());
    }

    @Override
    default boolean matches(T container, Level level)
    {
        ItemStack bunStack = hasBun(container);
        if (!bunStack.isEmpty())
        {
            AbstractSandwichItem sandwich = (AbstractSandwichItem) this.assemble(container).getItem();
            int maxBunCount = sandwich.sandwichProperties.hasTwoBuns() ? 2 : 1;
            int bunCount = 0;
            int ingredientCount = 0;
            boolean hasNeededItem = this.getNeededItem() == null;
            int neededItemCount = 0;

            Pair<Integer, Integer> checkSlots = this.getCheckSlots(container);
            for (int i = checkSlots.getFirst(); i < checkSlots.getSecond(); i++)
            {
                ItemStack stack = container.getItem(i);
                if (!stack.isEmpty())
                {
                    if (!stack.isEdible())
                        return false;
                    else
                    {
                        if (stack.getItem() == this.getNeededItem())
                        {
                            neededItemCount++;
                            hasNeededItem = neededItemCount <= 1;
                        } else if (this.getBunTag().contains(stack.getItem()))
                            bunCount++;
                        else
                            ingredientCount++;
                    }
                }
            }
            return bunCount == maxBunCount && ingredientCount > 0 && ingredientCount <= HamNCheese.CONFIG_SERVER.maxSandwichIngredientCraftCount.get() && hasNeededItem;
        }
        return false;
    }

    @Override
    default ItemStack assemble(T container)
    {
        ItemStack bunStack = hasBun(container);
        if (!bunStack.isEmpty())
        {
            int neededItemCount = 0;
            ItemStack sandwich = new ItemStack(this.getDefaultBunItem());
            Pair<Integer, Integer> checkSlots = this.getCheckSlots(container);
            for (int i = checkSlots.getFirst(); i < checkSlots.getSecond(); i++)
            {
                ItemStack ingredient = container.getItem(i);
                if (ingredient.getItem() == this.getNeededItem())
                    neededItemCount++;
                if (ingredient.getItem() != this.getNeededItem() && neededItemCount <= 1)
                    if (!ingredient.isEmpty() && ingredient.isEdible() && !this.getBunTag().contains(ingredient.getItem()))
                        AbstractSandwichItem.addIngredient(sandwich, ingredient);
            }
            return sandwich;
        }
        return bunStack;
    }

    default ItemStack hasBun(T inventory)
    {
        Pair<Integer, Integer> checkSlots = this.getCheckSlots(inventory);
        for (int i = checkSlots.getFirst(); i < checkSlots.getSecond(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (this.getBunTag().contains(stack.getItem()))
            {
                if (stack.getItem() instanceof AbstractSandwichItem)
                {
                    ListTag ingredients = stack.getOrCreateTag().getList(AbstractSandwichItem.TAG_INGREDIENTS, NbtConstants.COMPOUND);
                    if (ingredients.size() <= 0)
                        return stack;
                }
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
