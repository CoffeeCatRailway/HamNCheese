package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.GrillContainer;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PizzaOvenContainer;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PopcornMachineContainer;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class HNCMenus
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<MenuType<?>> CONTAINERS = PollinatedRegistry.create(Registry.MENU, HamNCheese.MOD_ID);

    public static final Supplier<MenuType<PizzaOvenContainer>> PIZZA_OVEN = register("pizza_oven", () -> new MenuType<>(PizzaOvenContainer::new));
    public static final Supplier<MenuType<GrillContainer>> GRILL = register("grill", () -> new MenuType<>(GrillContainer::new));
    public static final Supplier<MenuType<PopcornMachineContainer>> POPCORN_MACHINE = register("popcorn_machine", () -> new MenuType<>(PopcornMachineContainer::new), "Popcorn");

    private static <T extends MenuType<?>> Supplier<T> register(String id, Supplier<T> container)
    {
        return register(id, container, HNCLanguage.capitalize(id));
    }

    private static <T extends MenuType<?>> Supplier<T> register(String id, Supplier<T> container, String name)
        {
            Supplier<T> object = CONTAINERS.register(id, container);
        HNCLanguage.EXTRA.put("container." + HamNCheese.MOD_ID + "." + id, name);
        return object;
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        CONTAINERS.register(platform);
    }
}
