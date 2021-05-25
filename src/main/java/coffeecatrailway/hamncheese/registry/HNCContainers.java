package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.PizzaOvenContainer;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class HNCContainers
{
    private static final Logger LOGGER = HNCMod.getLogger("Containers");
    protected static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, HNCMod.MOD_ID);

    public static final RegistryObject<ContainerType<PizzaOvenContainer>> PIZZA_OVEN = register("pizza_oven", () -> new ContainerType<>(PizzaOvenContainer::new));

    private static <T extends ContainerType<?>> RegistryObject<T> register(String id, Supplier<T> container)
    {
        RegistryObject<T> object = CONTAINERS.register(id, container);
        HNCLanguage.EXTRA.put("container." + HNCMod.MOD_ID + "." + object.getId().getPath(), HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        CONTAINERS.register(bus);
    }
}
