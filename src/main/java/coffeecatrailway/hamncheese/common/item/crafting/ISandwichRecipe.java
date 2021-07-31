package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.HNCConfig;
import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 23/05/2021
 */
public interface ISandwichRecipe<T extends IInventory> extends IRecipe<T>
{
    ITag.INamedTag<Item> getBunTag();
    IItemProvider getDefaultBun();
    @Nullable
    IItemProvider getNeededItem();

    default Pair<Integer, Integer> getCheckSlots(T inventory)
    {
        return Pair.of(0, inventory.getContainerSize());
    }

    @Override
    default boolean matches(T inventory, World level)
    {
        ItemStack bunStack = hasBun(inventory);
        if (!bunStack.isEmpty())
        {
            AbstractSandwichItem sandwich = (AbstractSandwichItem) this.assemble(inventory).getItem();
            int maxBunCount = sandwich.sandwichProperties.hasTwoBuns() ? 2 : 1;
            int bunCount = 0;
            int ingredientCount = 0;
            boolean hasNeededItem = this.getNeededItem() == null;
            int neededItemCount = 0;

            Pair<Integer, Integer> checkSlots = this.getCheckSlots(inventory);
            for (int i = checkSlots.getFirst(); i < checkSlots.getSecond(); i++)
            {
                ItemStack stack = inventory.getItem(i);
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
            return bunCount == maxBunCount && ingredientCount > 0 && ingredientCount <= HNCConfig.SERVER.maxSandwichIngredientCraftCount.get() && hasNeededItem;
        }
        return false;
    }

    @Override
    default ItemStack assemble(T inventory)
    {
        ItemStack bunStack = hasBun(inventory);
        if (!bunStack.isEmpty())
        {
            int neededItemCount = 0;
            ItemStack sandwich = new ItemStack(this.getDefaultBun());
            Pair<Integer, Integer> checkSlots = this.getCheckSlots(inventory);
            for (int i = checkSlots.getFirst(); i < checkSlots.getSecond(); i++)
            {
                ItemStack ingredient = inventory.getItem(i);
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
                    ListNBT ingredients = stack.getOrCreateTag().getList(AbstractSandwichItem.TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
                    if (ingredients.size() <= 0)
                        return stack;
                }
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
