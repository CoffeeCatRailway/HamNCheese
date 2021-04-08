package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.datagen.HNCLanguage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class HNCBlocks
{
    private static final Logger LOGGER = HNCMod.getLogger("Blocks");
    protected static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HNCMod.MOD_ID);

    public static final RegistryObject<PineapplePlantBlock> PINEAPPLE_PLANT = registerWithItem("pineapple_plant", () -> new PineapplePlantBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)), null);

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        return registerWithItem(id, block, (object, prop) -> new BlockItem(object.get(), properties.apply(prop)));
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String id, Supplier<T> block, @Nullable BiFunction<RegistryObject<T>, Item.Properties, Item> item)
    {
        RegistryObject<T> object = BLOCKS.register(id, block);
        if (item != null)
            HNCItems.ITEMS.register(id, () -> item.apply(object, new Item.Properties().tab(HNCMod.GROUP_ALL)));
        HNCLanguage.BLOCKS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(bus);
    }
}
