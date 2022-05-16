package io.github.coffeecatrailway.hamncheese.registry.fabric;

import io.github.coffeecatrailway.hamncheese.common.item.CrackerItem;
import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import io.github.coffeecatrailway.hamncheese.common.item.PizzaItem;
import io.github.coffeecatrailway.hamncheese.common.item.SandwichItem;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 30/03/2022
 */
public class HNCItemsImpl
{
    public static Function<Item.Properties, CraftingToolItem> getCraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable Tag<Block> mineableBlocks, Function<Item.Properties, Item.Properties> factory)
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
