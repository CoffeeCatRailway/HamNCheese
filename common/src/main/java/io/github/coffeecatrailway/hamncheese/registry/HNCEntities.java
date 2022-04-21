package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
//@Mod.EventBusSubscriber(modid = HNCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HNCEntities
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<EntityType<?>> ENTITIES = PollinatedRegistry.create(Registry.ENTITY_TYPE, HamNCheese.MOD_ID);

    public static final Supplier<EntityType<HNCBoatEntity>> MAPLE_BOAT = register("maple_boat", HNCBoatEntity::new, MobCategory.MISC, builder -> builder.sized(1.375f, 0.5625f));

    private static <E extends Entity> Supplier<EntityType<E>> register(String id, BiFunction<EntityType<E>, Level, E> entityFactory, MobCategory category, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        Supplier<EntityType<E>> object = ENTITIES.register(id, () -> factory.apply(EntityType.Builder.<E>of(entityFactory::apply, category)).build(HamNCheese.getLocation(id).toString()));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        ENTITIES.register(platform);
    }
}
