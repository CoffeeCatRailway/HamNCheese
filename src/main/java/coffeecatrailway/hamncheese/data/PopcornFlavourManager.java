package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.HNCMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 10/08/2021
 */
public class PopcornFlavourManager extends JsonReloadListener
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = HNCMod.getLogger("PopcornFlavourManager");

    public static final PopcornFlavourManager INSTANCE = new PopcornFlavourManager();
    public static final Map<ResourceLocation, PopcornFlavour> FLAVOURS = new HashMap<>();

    public static final ResourceLocation NONE_ID = HNCMod.getLocation("none");

    public PopcornFlavourManager()
    {
        super(GSON, "popcorn_flavours");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, IResourceManager resourceManager, IProfiler profiler)
    {
        FLAVOURS.clear();
        elements.forEach((location, element) -> {
            try
            {
                final Optional<PopcornFlavour> result = JsonOps.INSTANCE.withParser(PopcornFlavour.CODEC).apply(element).result();
                if (result.isPresent())
                {
                    if (FLAVOURS.containsKey(result.get().getId()))
                        LOGGER.error("Flavour {} already exists", result.get().getId());
                    else
                        FLAVOURS.put(result.get().getId(), result.get());
                } else
                    LOGGER.warn("Skipped flavour {}", location);
            } catch (Exception e)
            {
                LOGGER.error("Exception occurred while processing JSON for {}", location, e);
            }
        });
    }

    @Nullable
    public static PopcornFlavour getFlavourFromIngredient(ItemStack ingredient)
    {
        if (ingredient.isEmpty())
            return null;
        return FLAVOURS.values().stream().filter(flavour -> flavour.getIngredient().equals(ingredient.getItem())).findFirst().orElse(null);
    }
}
