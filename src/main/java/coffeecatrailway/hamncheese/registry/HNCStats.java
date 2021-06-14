package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCStats
{
    private static final Logger LOGGER = HNCMod.getLogger("Stats");

    public static ResourceLocation INTERACT_PIZZA_OVEN;
    public static ResourceLocation INTERACT_GRILL;

    public static void register(RegistryEvent.Register<StatType<?>> event)
    {
        if (!event.getName().equals(ForgeRegistries.STAT_TYPES.getRegistryName())) return;

        INTERACT_PIZZA_OVEN = register("interact_pizza_oven", "Interactions with Pizza Oven", IStatFormatter.DEFAULT);
        INTERACT_GRILL = register("interact_grill", "Interactions with Grill", IStatFormatter.DEFAULT);

        LOGGER.debug("Custom stats registered");
    }

    private static ResourceLocation register(String name, String localizedName, IStatFormatter formatter)
    {
        ResourceLocation id = HNCMod.getLocation(name);
        Registry.register(Registry.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, formatter);
        HNCLanguage.EXTRA.put("stat." + HNCMod.MOD_ID + "." + name, localizedName);
        return id;
    }
}
