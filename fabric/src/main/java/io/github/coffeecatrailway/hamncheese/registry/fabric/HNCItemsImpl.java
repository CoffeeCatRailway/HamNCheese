package io.github.coffeecatrailway.hamncheese.registry.fabric;

import io.github.coffeecatrailway.hamncheese.common.item.CrackerItem;
import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import io.github.coffeecatrailway.hamncheese.common.item.PizzaItem;
import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 30/03/2022
 */
public class HNCItemsImpl
{
    public static Function<Item.Properties, CraftingToolItem> getCraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable TagKey<Block> mineableBlocks, Function<Item.Properties, Item.Properties> factory)
    {
        return prop -> new CraftingToolItem(attackModifier, attackSpeed, tier, mineableBlocks, factory.apply(prop));
    }

    public static Function<Item.Properties, PizzaItem> getPizzaItem()
    {
        return PizzaItem::new;
    }

    public static Function<Item.Properties, CrackerItem> getCrackerItem()
    {
        return CrackerItem::new;
    }

    public static Function<Item.Properties, SandwichItem> getSandwichItem()
    {
        return SandwichItem::new;
    }
}
