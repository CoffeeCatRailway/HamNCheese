package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
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
    private static final PollinatedRegistry<ResourceLocation> STATS = PollinatedRegistry.createVanilla(Registry.CUSTOM_STAT, HamNCheese.MOD_ID);

    public static final ResourceLocation INTERACT_PIZZA_OVEN = register("interact_pizza_oven", StatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_GRILL = register("interact_grill", StatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_POPCORN_MACHINE = register("interact_popcorn_machine", StatFormatter.DEFAULT);

    public static final ResourceLocation INTERACT_CHOPPING_BOARD = register("interact_chopping_board", StatFormatter.DEFAULT);

    public static void load(Platform platform)
    {
        STATS.register(platform);
        LOGGER.debug("Loaded");
    }

    private static ResourceLocation register(String id, StatFormatter formatter)
    {
        ResourceLocation stat = HamNCheese.getLocation(id);
        STATS.register(id, () -> stat);
        Stats.CUSTOM.get(stat, formatter);
        return stat;
    }

    public static class Localize
    {
        public static void load()
        {
            register("interact_pizza_oven", "Interactions with Pizza Oven");
            register("interact_grill", "Interactions with Grill");
            register("interact_popcorn_machine", "Interactions with Popcorn Machine");

            register("interact_chopping_board", "Interactions with Chopping Board");
        }

        private static void register(String id, String name)
        {
            HNCLanguage.EXTRA.put("stat." + HamNCheese.MOD_ID + "." + id, name);
        }
    }
}
