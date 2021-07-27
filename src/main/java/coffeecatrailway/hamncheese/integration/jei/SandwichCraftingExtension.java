package coffeecatrailway.hamncheese.integration.jei;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.common.item.crafting.AbstractSandwichRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 17/06/2021
 */
public class SandwichCraftingExtension<T extends AbstractSandwichRecipe> implements ICraftingCategoryExtension
{
    private final T recipe;
    private final ITag.INamedTag<Item> bunTag;
    private final Supplier<? extends IItemProvider> defaultItem;
    private boolean hasTwoBuns = true;
    private ItemStack neededItem = ItemStack.EMPTY;

    public SandwichCraftingExtension(T recipe, ITag.INamedTag<Item> bunTag, Supplier<? extends IItemProvider> defaultItem)
    {
        this.recipe = recipe;
        this.bunTag = bunTag;
        this.defaultItem = defaultItem;
    }

    @Override
    public void setIngredients(IIngredients ingredients)
    {
        List<ItemStack> breadSlice = this.bunTag.getValues().stream().map(ItemStack::new).collect(Collectors.toList());
        Random random = new Random(42L);
        if (Minecraft.getInstance().level != null)
            random = Minecraft.getInstance().level.random;
        List<ItemStack> selected = FoodsGetter.pickFoods(random.nextInt(6) + 1, random);
        List<List<ItemStack>> inputs = new ArrayList<>();

        inputs.add(breadSlice);
        if (!this.neededItem.isEmpty())
            inputs.add(Lists.newArrayList(this.neededItem));
        selected.forEach(stack -> inputs.add(Lists.newArrayList(stack)));
        if (this.hasTwoBuns)
            inputs.add(breadSlice);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        ItemStack sandwich = new ItemStack(this.defaultItem.get());
        selected.forEach(stack -> AbstractSandwichItem.addIngredient(sandwich, stack));
        ingredients.setOutput(VanillaTypes.ITEM, sandwich);
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
