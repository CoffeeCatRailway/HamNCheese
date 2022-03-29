package io.github.coffeecatrailway.hamncheese.registry.fabric;

import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
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
    public static Function<Item.Properties, CraftingToolItem> getCraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable Tag<Block> mineableBlocks)
    {
        return prop -> new CraftingToolItem(attackModifier, attackSpeed, tier, mineableBlocks, prop);
    }
}
