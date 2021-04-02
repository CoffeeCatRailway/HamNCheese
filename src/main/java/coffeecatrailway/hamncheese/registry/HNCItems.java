package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 1/02/2021
 */
public class HNCItems
{
    private static final Logger LOGGER = HNCMod.getLogger("Items");
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HNCMod.MOD_ID);

//    public static final RegistryEntry<Item> EXAMPLE = REGISTRATE.item("example", Item::new).defaultLang().defaultModel().register();
    private static <T extends Item> RegistryObject<T> register(String id, Function<Item.Properties, T> factory)
    {
        return register(id, null, factory);
    }

    private static <T extends Item> RegistryObject<T> register(String id, @Nullable String name, Function<Item.Properties, T> factory)
    {
        Supplier<T> item = () -> factory.apply(new Item.Properties().tab(HNCMod.GROUP_ALL));
        RegistryObject<T> object = ITEMS.register(id, item);
        if (name == null)
            HNCLanguage.ITEMS.put(object, HNCLanguage.capitalize(id));
        else
            HNCLanguage.ITEMS.put(object, name);
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        ITEMS.register(bus);
    }
}
