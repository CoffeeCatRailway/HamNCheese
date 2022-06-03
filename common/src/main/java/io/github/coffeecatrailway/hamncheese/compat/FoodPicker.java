package io.github.coffeecatrailway.hamncheese.compat;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 19/07/2021
 */
public class FoodPicker
{
    private static final List<ItemStack> FOODS = new ArrayList<>();
    private static final Map<String, ItemStack> SANDWICH_CACHE = new HashMap<>();

    /**
     * Generate a random {@link AbstractSandwichItem}
     * @param base Base {@link AbstractSandwichItem}
     * @param ingredientCount Number of ingredients to use
     * @param toasted If the sandwich should be toasted or not
     * @param generateNew If the sandwich should be cached or not
     * @return Generated {@link AbstractSandwichItem}
     */
    public static ItemStack generateSandwich(Item base, int ingredientCount, boolean toasted, boolean generateNew)
    {
        if (SANDWICH_CACHE.containsKey(base.getDescriptionId()) && !generateNew)
            return SANDWICH_CACHE.get(base.getDescriptionId()).copy();

        if (!(base instanceof AbstractSandwichItem))
            throw new RuntimeException("Item " + base.getDescriptionId() + " was not of type SandwichItem");

        ItemStack sandwich = new ItemStack(base);
        for (ItemStack stack : FoodPicker.pickFoods(ingredientCount))
            sandwich = AbstractSandwichItem.addIngredient(sandwich, stack);

        sandwich.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, toasted);
        if (!generateNew)
            SANDWICH_CACHE.put(base.getDescriptionId(), sandwich);
        return sandwich;
    }

    /**
     * Generates a {@link Pair} containing a toasted & untoasted sandwich
     * @param base Base {@link AbstractSandwichItem}
     * @param ingredientCount Number of ingredients to use
     * @param generateNew If the sandwich should be cached or not
     * @return Pair of toasted & untoasted sandwich(s)
     */
    public static Pair<ItemStack, ItemStack> generateSandwichPair(Item base, int ingredientCount, boolean generateNew)
    {
        return generateSandwichPairMapped(base, ingredientCount, generateNew, stack -> stack);
    }

    /**
     * Generates a {@link Pair} containing a toasted & untoasted sandwich
     * @param base Base {@link AbstractSandwichItem}
     * @param ingredientCount Number of ingredients to use
     * @param generateNew If the sandwich should be cached or not
     * @param map What the pair type(s) should be mapped to
     * @return Pair of toasted & untoasted sandwich(s)
     */
    public static <F2> Pair<F2, F2> generateSandwichPairMapped(Item base, int ingredientCount, boolean generateNew, final Function<ItemStack, F2> map)
    {
        ItemStack sandwich = generateSandwich(base, ingredientCount, false, generateNew);
        ItemStack toasted = sandwich.copy();
        toasted.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, true);
        return Pair.of(sandwich, toasted).mapFirst(map).mapSecond(map);
    }

    /**
     * Picks random foods
     * @param amount Number of foods to pick
     * @return List of chosen foods
     */
    public static List<ItemStack> pickFoods(int amount)
    {
        Random random = new Random(41L);
        if (Minecraft.getInstance().level != null)
            random = Minecraft.getInstance().level.random;

        if (FOODS.isEmpty())
            Registry.ITEM.stream().filter(item -> item.isEdible() && !HNCItemTags.JEI_FOOD_BLACKLIST.contains(item) && !(item instanceof AbstractSandwichItem)).map(ItemStack::new).forEach(FOODS::add);
        return pickNRandomElements(FOODS, random.nextInt(amount) + 1, random);
    }

    /**
     * @param list List to choose from
     * @param amount Amount of elements to choose
     * @param random Random
     * @return List of chosen elements
     */
    private static <E> List<E> pickNRandomElements(List<E> list, int amount, Random random)
    {
        int length = list.size();
        List<E> elements = new ArrayList<>();
        if (length < amount) return elements;

        for (int i = length - 1; i >= length - amount; i--)
            elements.add(list.get(random.nextInt(length - 1)));
        return elements;
    }
}
