package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.HNCMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 3/05/2021
 */
public class ChoppingBoardManager extends JsonReloadListener
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = HNCMod.getLogger("ChoppingBoardManager");

    public static final ChoppingBoardManager INSTANCE = new ChoppingBoardManager();
    private static final Map<ResourceLocation, ChoppingBoard> BOARDS = new Object2ObjectArrayMap<>();

    public ChoppingBoardManager()
    {
        super(GSON, "chopping_boards");
    }

    public Map<ResourceLocation, ChoppingBoard> getBoards()
    {
        return BOARDS;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, IResourceManager resourceManager, IProfiler profiler)
    {
        BOARDS.clear();
        elements.forEach((location, element) -> {
            if (BOARDS.containsKey(location))
            {
                LOGGER.error("Recipe {} already exists", location);
                return;
            }
            try
            {
                JsonObject object = JSONUtils.convertToJsonObject(element, "top element");
                String modId = "minecraft";
                boolean loadFlag = true;
                if (object.has("mod"))
                {
                    modId = JSONUtils.getAsString(object, "mod");
                    loadFlag = ModList.get().isLoaded(modId);
                }

                if (loadFlag)
                {
                    final Optional<ChoppingBoard> result = JsonOps.INSTANCE.withParser(ChoppingBoard.CODEC).apply(element).result();
                    if (result.isPresent())
                        BOARDS.put(location, result.get());
                    else
                        LOGGER.info("Failed to load JSON for {}", location);
                } else
                    LOGGER.warn("Skipped recipe {} as mod \"{}\" was not present", location, modId);
            } catch (Exception e)
            {
                LOGGER.error("Exception occurred while processing JSON for {}", location, e);
            }
        });
    }
}
