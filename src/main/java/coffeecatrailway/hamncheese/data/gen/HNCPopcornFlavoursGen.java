package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.data.PopcornFlavour;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 11/08/2021
 * Based on: {@link HNCChoppingBoards} by Vemerion
 */
public class HNCPopcornFlavoursGen implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, JsonElement> toSerialize = new HashMap<>();
    private DataGenerator generator;

    public HNCPopcornFlavoursGen(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException
    {
        Path folder = this.generator.getOutputFolder();
        start();

        this.toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + HNCMod.MOD_ID + "/popcorn_flavours/" + name + ".json");
            try
            {
                IDataProvider.save(GSON, cache, json, path);
            } catch (IOException e)
            {
                LOGGER.error("Couldn't save popcorn flavour {}", path, e);
            }
        });
    }

    private void start()
    {
        this.addFlavour("cheese", new PopcornFlavour("popcorn_flavour.hamncheese.cheese", HNCMod.getLocation("gui/container/popcorn_flavour/cheese.png"), HNCItems.CHEESE_SLICE.get()));
    }

    private void addFlavour(String name, PopcornFlavour flavour)
    {
        Optional<JsonElement> optional = JsonOps.INSTANCE.withEncoder(PopcornFlavour.CODEC).apply(flavour).result();
        if (optional.isPresent() && optional.get().isJsonObject())
        {
            HNCLanguage.EXTRA.put(flavour.getName().getKey(), HNCLanguage.capitalize(name));
            this.toSerialize.put(name, optional.get().getAsJsonObject());
        } else
            LOGGER.error("Couldn't save popcorn flavour {}", flavour);
    }

    @Override
    public String getName()
    {
        return "Popcorn Flavours: " + HNCMod.MOD_ID;
    }
}
