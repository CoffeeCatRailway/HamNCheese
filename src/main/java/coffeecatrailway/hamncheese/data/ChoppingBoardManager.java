package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.HNCMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 3/05/2021
 */
@Mod.EventBusSubscriber(modid = HNCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

    @SubscribeEvent
    public static void onReloadListener(AddReloadListenerEvent event)
    {
        event.addListener(INSTANCE);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, IResourceManager resourceManager, IProfiler profiler)
    {
        CHOPPING_BOARDS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet())
        {
            final ResourceLocation key = entry.getKey();
            try
            {
                final Optional<ChoppingBoard> result = JsonOps.INSTANCE.withParser(ChoppingBoard.CODEC).apply(entry.getValue()).result();
                if (result.isPresent())
                    CHOPPING_BOARDS.put(key, result.get());
                else
                    LOGGER.info("Failed to load JSON for {}", key);
            } catch (Exception e)
            {
                LOGGER.error("Exception occurred while processing JSON for {}", key, e);
            }
        }
    }
}
