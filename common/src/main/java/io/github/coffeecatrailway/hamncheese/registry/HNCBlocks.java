package io.github.coffeecatrailway.hamncheese.registry;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class HNCBlocks
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<Block> BLOCKS = PollinatedRegistry.create(Registry.BLOCK, HamNCheese.MOD_ID);

    public static <T extends Block> Supplier<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        return registerWithItem(id, block, (object, prop) -> new BlockItem(object.get(), properties.apply(prop)));
    }

    private static <T extends Block> Supplier<T> registerWithItem(String id, Supplier<T> block, @Nullable BiFunction<Supplier<T>, Item.Properties, Item> item)
    {
        return registerWithItemAndName(id, block, item, HNCLanguage.capitalize(id));
    }

    private static <T extends Block> Supplier<T> registerWithItemAndName(String id, Supplier<T> block, @Nullable BiFunction<Supplier<T>, Item.Properties, Item> item, @Nullable String name)
    {
        Supplier<T> object = BLOCKS.register(id, block);
        if (item != null)
            HNCItems.ITEMS.register(id, () -> item.apply(object, new Item.Properties().tab(HamNCheese.TAB)));
        if (name != null)
            HNCLanguage.BLOCKS.put(object, name);
        return object;
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(platform);
    }
}
