package coffeecatrailway.hamncheese.common.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public abstract class AbstractSandwichRecipe extends SpecialRecipe implements ISandwichRecipe<CraftingInventory>
{
    private final ITag.INamedTag<Item> bunTag;
    private final Supplier<? extends IItemProvider> defaultItem;

    public AbstractSandwichRecipe(ResourceLocation id, ITag.INamedTag<Item> bunTag, Supplier<? extends IItemProvider> defaultItem)
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
    public ITag.INamedTag<Item> getBunTag()
    {
        return this.bunTag;
    }

    @Override
    public IItemProvider getDefaultBun()
    {
        return this.defaultItem.get();
    }
}
