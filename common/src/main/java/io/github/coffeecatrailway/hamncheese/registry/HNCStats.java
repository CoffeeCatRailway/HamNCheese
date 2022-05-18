package io.github.coffeecatrailway.hamncheese.registry;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public class HNCStats
{
    private static final Logger LOGGER = LogManager.getLogger();

//    public static ResourceLocation INTERACT_PIZZA_OVEN;
    public static ResourceLocation INTERACT_GRILL;
//    public static ResourceLocation INTERACT_POPCORN_MACHINE;

    public static void register()
    {
//        INTERACT_PIZZA_OVEN = register("interact_pizza_oven", "Interactions with Pizza Oven", StatFormatter.DEFAULT);
        INTERACT_GRILL = register("interact_grill", "Interactions with Grill", StatFormatter.DEFAULT);
//        INTERACT_POPCORN_MACHINE = register("interact_popcorn_machine", "Interactions with Popcorn Machine", StatFormatter.DEFAULT);

        LOGGER.debug("Custom stats registered");
    }

    private static ResourceLocation register(String name, String localizedName, StatFormatter formatter)
    {
        ResourceLocation id = HamNCheese.getLocation(name);
        Registry.register(Registry.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, formatter);
        HNCLanguage.EXTRA.put("stat." + HamNCheese.MOD_ID + "." + name, localizedName);
        return id;
    }
}
