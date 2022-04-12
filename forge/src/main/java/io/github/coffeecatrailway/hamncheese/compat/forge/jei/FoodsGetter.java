package io.github.coffeecatrailway.hamncheese.compat.forge.jei;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 19/07/2021
 */
public class FoodsGetter
{
    private static List<ItemStack> FOODS = new ArrayList<>();

    public static List<ItemStack> pickFoods(int amount)
    {
        Random random = new Random(41L);
        if (Minecraft.getInstance().level != null)
            random = Minecraft.getInstance().level.random;

        if (FOODS.isEmpty())
            FOODS = ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.isEdible() && !HNCItemTags.JEI_FOOD_BLACKLIST.contains(item) && !(item instanceof AbstractSandwichItem)).map(ItemStack::new).collect(Collectors.toList());
        return pickNRandomElements(FOODS, random.nextInt(amount) + 1, random);
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
