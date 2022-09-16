package io.github.coffeecatrailway.hamncheese.compat.forge;

import io.github.coffeecatrailway.plus.common.item.PlusTiers;
import io.github.coffeecatrailway.plus.data.gen.PlusItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * @author CoffeeCatRailway
 * Created: 5/06/2022
 */
public class PlusExtraCommonImpl
{
    public static Tier getRoseGoldTier()
    {
        return PlusTiers.ROSE_GOLD;
    }

    public static TagKey<Item> getRoseGoldIngotTag()
    {
        return PlusItemTags.INGOTS_ROSE_GOLD_COMMON;
    }
}
