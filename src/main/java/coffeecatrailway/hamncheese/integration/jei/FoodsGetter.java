package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 19/07/2021
 */
public class FoodsGetter
{
    private static List<ItemStack> FOODS = new ArrayList<>();

    public static List<ItemStack> pickFoods(int amount, Random random)
    {
        if (FOODS.isEmpty())
            FOODS = ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.isEdible() && !HNCItemTags.JEI_FOOD_BLACKLIST.contains(item) && !(item instanceof AbstractSandwichItem)).map(ItemStack::new).collect(Collectors.toList());
        return pickNRandomElements(FOODS, amount, random);
    }

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
