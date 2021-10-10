package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.HNCMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author CoffeeCatRailway
 * Created: 3/05/2021
 */
public class ChoppingBoardManager extends JsonReloadListener
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = HNCMod.getLogger("ChoppingBoardManager");

    public static final ChoppingBoardManager INSTANCE = new ChoppingBoardManager();
    private static final Object2ObjectMap<ResourceLocation, ChoppingBoard> BOARDS = new Object2ObjectArrayMap<>();

    public ChoppingBoardManager()
    {
        super(GSON, "chopping_boards");
    }

    public int size()
    {
        return BOARDS.size();
    }

    public void forEach(BiConsumer<ResourceLocation, ChoppingBoard> action)
    {
        BOARDS.forEach(action);
    }

    public ChoppingBoard getById(ResourceLocation id)
    {
        return BOARDS.getOrDefault(id, ChoppingBoard.DEFAULT);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, IResourceManager resourceManager, IProfiler profiler)
    {
        BOARDS.clear();
        BOARDS.computeIfAbsent(HNCMod.getLocation("stone_chopping_board"), ChoppingBoard.DEFAULT::setId);
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
                        BOARDS.computeIfAbsent(location, id -> result.get().setId(id));
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

    public static ChoppingBoard getBoardByPressurePlate(Block pressurePlate)
    {
        return BOARDS.values().stream().filter(board -> board.getPressurePlate().is(pressurePlate)).findFirst().orElse(ChoppingBoard.DEFAULT);
    }
}
