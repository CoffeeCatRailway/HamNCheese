package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.datagen.provider.SimpleConditionalDataProvider;
import io.github.coffeecatrailway.hamncheese.data.gen.loot.HNCBlockLoot;
import io.github.coffeecatrailway.hamncheese.data.gen.loot.HNCChestLoot;
import io.github.coffeecatrailway.hamncheese.data.gen.loot.HNCEntityLoot;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 * <p>
 * Based from {@link net.minecraft.data.loot.LootTableProvider}, fabric & forge
 */
public class HNCLootTableProvider extends SimpleConditionalDataProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(Pair.of(HNCChestLoot::new, LootContextParamSets.CHEST), Pair.of(HNCEntityLoot::new, LootContextParamSets.ENTITY), Pair.of(HNCBlockLoot::new, LootContextParamSets.BLOCK));

    public HNCLootTableProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(HashCache hashCache) throws IOException
    {
        Path outputFolder = this.generator.getOutputFolder();
        Map<ResourceLocation, LootTable> map = Maps.newHashMap();
        subProviders.forEach(pair -> pair.getFirst().get().accept((resourceLocation, builder) -> {
            if (map.put(resourceLocation, builder.setParamSet(pair.getSecond()).build()) != null)
                throw new IllegalStateException("Duplicate loot table " + resourceLocation);
        }));

        map.forEach((location, table) -> {
            Path path = createPath(outputFolder, location);
            try
            {
                DataProvider.save(GSON, hashCache, LootTables.serialize(table), path);
            } catch (IOException var6)
            {
                LOGGER.error("Couldn't save loot table {}", path, var6);
            }

        });
    }

    private static Path createPath(Path path, ResourceLocation resourceLocation)
    {
        return path.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName()
    {
        return "LootTables";
    }
}
