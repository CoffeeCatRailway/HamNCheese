package io.github.coffeecatrailway.hamncheese.registry.forge;

import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import io.github.coffeecatrailway.hamncheese.common.item.forge.CraftingToolItemForge;
import net.minecraft.tags.Tag;
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
    public static Function<Item.Properties, CraftingToolItem> getCraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable Tag<Block> mineableBlocks)
    {
        return prop -> new CraftingToolItemForge(attackModifier, attackSpeed, tier, mineableBlocks, prop);
    }
}
