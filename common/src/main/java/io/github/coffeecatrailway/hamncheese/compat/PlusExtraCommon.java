package io.github.coffeecatrailway.hamncheese.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * @author CoffeeCatRailway
 * Created: 16/09/2022
 */
public class PlusExtraCommon
{
    @ExpectPlatform
    public static Tier getRoseGoldTier()
    {
        return Platform.error();
    }

    @ExpectPlatform
    public static TagKey<Item> getRoseGoldIngotTag()
    {
        return Platform.error();
    }
}
