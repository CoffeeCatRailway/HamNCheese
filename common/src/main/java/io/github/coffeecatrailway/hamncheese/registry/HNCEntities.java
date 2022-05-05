package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.PollenRegistries;
import gg.moonflower.pollen.api.entity.PollinatedBoatType;
import gg.moonflower.pollen.api.item.SpawnEggItemBase;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedEntityRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.entity.MouseEntity;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
public class HNCEntities
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PollinatedEntityRegistry ENTITIES = PollinatedRegistry.createEntity(HamNCheese.MOD_ID);
    private static final PollinatedRegistry<PollinatedBoatType> BOATS = PollinatedRegistry.create(PollenRegistries.BOAT_TYPE_REGISTRY, HamNCheese.MOD_ID);

    public static final Supplier<EntityType<MouseEntity>> MOUSE = registerWithEgg("mouse", MouseEntity::new, MobCategory.CREATURE, 0xffffff, 0x7a3205, builder -> builder.sized(.8f, .5f).clientTrackingRange(10));
    public static final Supplier<PollinatedBoatType> MAPLE_BOAT = BOATS.register("maple", () -> new PollinatedBoatType(HamNCheese.getLocation("textures/entity/boat/maple.png")));

    private static <E extends Mob> Supplier<EntityType<E>> registerWithEgg(String id, BiFunction<EntityType<E>, Level, E> entityFactory, MobCategory category, int bgColor, int spotColor, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        Supplier<EntityType<E>> object = register(id, entityFactory, category, factory);
        HNCItems.registerIdAsName(id + "_spawn_egg", prop -> new SpawnEggItemBase<>(object, bgColor, spotColor, prop));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    private static <E extends Entity> Supplier<EntityType<E>> register(String id, BiFunction<EntityType<E>, Level, E> entityFactory, MobCategory category, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        Supplier<EntityType<E>> object = ENTITIES.register(id, () -> factory.apply(EntityType.Builder.of(entityFactory::apply, category)).build(HamNCheese.getLocation(id).toString()));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void registerSpawnPlacements()
    {
        SpawnPlacements.register(MOUSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> Mob.checkMobSpawnRules(type, level, reason, pos, random) && HamNCheese.CONFIG_SERVER.canSpawnMouse());
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        ENTITIES.register(platform);
    }
}
