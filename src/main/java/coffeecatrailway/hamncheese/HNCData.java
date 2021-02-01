package coffeecatrailway.hamncheese;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 1/02/2021
 */
public class HNCData
{
    public static class Lang implements NonNullConsumer<RegistrateLangProvider>
    {
        public static final Map<String, String> EXTRA_LANGS = new HashMap<>();

        @Override
        public void accept(RegistrateLangProvider provider)
        {
            EXTRA_LANGS.forEach(provider::add);
        }
    }

    public static class TagItems implements NonNullConsumer<RegistrateTagsProvider<Item>>
    {
        @Override
        public void accept(RegistrateTagsProvider<Item> provider)
        {
        }
    }

    public static class TagBlocks implements NonNullConsumer<RegistrateTagsProvider<Block>>
    {
        @Override
        public void accept(RegistrateTagsProvider<Block> provider)
        {
        }
    }

    public static class LootTables implements NonNullConsumer<RegistrateLootTableProvider>
    {
        @Override
        public void accept(RegistrateLootTableProvider provider)
        {
        }
    }
}
