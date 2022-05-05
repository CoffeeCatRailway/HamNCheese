package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public abstract class AbstractSandwichRecipe extends CustomRecipe implements CustomSandwichRecipe<CraftingContainer>
{
    private final TagKey<Item> bunTag;
    private final Supplier<? extends ItemLike> defaultItem;

    public AbstractSandwichRecipe(ResourceLocation id, TagKey<Item> bunTag, Supplier<? extends ItemLike> defaultItem)
    {
        super(id);
        this.bunTag = bunTag;
        this.defaultItem = defaultItem;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 2 && height >= 2;
    }

    @Override
    public TagKey<Item> getBunTag()
    {
        return this.bunTag;
    }

    @Override
    public ItemLike getDefaultBunItem()
    {
        return this.defaultItem.get();
    }
}
