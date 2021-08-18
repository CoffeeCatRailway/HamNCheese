package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.HNCMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
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
    public static final Map<ResourceLocation, ChoppingBoard> CHOPPING_BOARDS = new HashMap<>();

    public ChoppingBoardManager()
    {
        super(GSON, "chopping_boards");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, IResourceManager resourceManager, IProfiler profiler)
    {
        CHOPPING_BOARDS.clear();
        elements.forEach((location, element) -> {
            try
            {
                JsonObject object = JSONUtils.convertToJsonObject(element, "top element");
                String modId = "minecraft";
                boolean modLoadedFlag = true;
                if (object.has("modId"))
                {
                    modId = JSONUtils.getAsString(object, "modId");
                    modLoadedFlag = ModList.get().isLoaded(modId);
                }

                if (modLoadedFlag)
                {
                    final Optional<ChoppingBoard> result = JsonOps.INSTANCE.withParser(ChoppingBoard.CODEC).apply(element).result();
                    if (result.isPresent())
                    {
                        if (CHOPPING_BOARDS.containsKey(location))
                            LOGGER.error("Recipe {} already exists", location);
                        else
                            CHOPPING_BOARDS.put(location, result.get());
                    } else
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
