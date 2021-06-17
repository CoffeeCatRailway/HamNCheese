package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.common.item.crafting.SandwichRecipe;
import coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author CoffeeCatRailway
 * Created: 17/06/2021
 */
public class SandwichCraftingExtension<T extends SandwichRecipe> implements ICraftingCategoryExtension
{
    private static final List<ItemStack> FOODS = ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.isEdible() && !HNCItemTags.BREAD_SLICE.contains(item) && !(item instanceof AbstractSandwichItem)).map(ItemStack::new).collect(Collectors.toList());
    private final T recipe;

    public SandwichCraftingExtension(T recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void setIngredients(IIngredients ingredients)
    {
        List<ItemStack> breadSlice = HNCItemTags.BREAD_SLICE.getValues().stream().map(ItemStack::new).collect(Collectors.toList());
        Random random = new Random(42L);
        if (Minecraft.getInstance().level != null)
            random = Minecraft.getInstance().level.random;
        List<ItemStack> selected = this.pickNRandomElements(new ArrayList<>(FOODS), random.nextInt(6) + 1, random);
        List<List<ItemStack>> inputs = new ArrayList<>();

        inputs.add(breadSlice);
        selected.forEach(stack -> inputs.add(Lists.newArrayList(stack)));
        inputs.add(breadSlice);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        ItemStack sandwich = new ItemStack(HNCItems.SANDWICH.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        ingredients.setOutput(VanillaTypes.ITEM, sandwich);
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return recipe.getId();
    }

    protected  <E> List<E> pickNRandomElements(List<E> list, int amount, Random random) {
        int length = list.size();
        if (length < amount) return new ArrayList<>();

        for (int i = length - 1; i >= length - amount; i--)
            Collections.swap(list, i , random.nextInt(i + 1));
        return list.subList(length - amount, length);
    }
}
