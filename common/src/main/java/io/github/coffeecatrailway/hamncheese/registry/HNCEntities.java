package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.item.SpawnEggItemBase;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedEntityRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
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
    protected static final PollinatedEntityRegistry ENTITIES = PollinatedRegistry.createEntity(HamNCheese.MOD_ID);

    public static final Supplier<EntityType<MouseEntity>> MOUSE = registerWithEgg("mouse", MouseEntity::new, MobCategory.AMBIENT, 0xffffff, 0x7a3205, builder -> builder.sized(.8f, .5f).clientTrackingRange(10));
    public static final Supplier<EntityType<HNCBoatEntity>> MAPLE_BOAT = register("maple_boat", HNCBoatEntity::new, MobCategory.MISC, builder -> builder.sized(1.375f, 0.5625f));

    private static <E extends Mob> Supplier<EntityType<E>> registerWithEgg(String id, BiFunction<EntityType<E>, Level, E> entityFactory, MobCategory category, int bgColor, int spotColor, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        Supplier<EntityType<E>> object = register(id, entityFactory, category, factory);
        HNCItems.registerIdAsName(id + "_spawn_egg", prop -> new SpawnEggItemBase<>(object, bgColor, spotColor, true, prop));
        return object;
    }

    private static <E extends Entity> Supplier<EntityType<E>> register(String id, BiFunction<EntityType<E>, Level, E> entityFactory, MobCategory category, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        Supplier<EntityType<E>> object = ENTITIES.register(id, () -> factory.apply(EntityType.Builder.of(entityFactory::apply, category)).build(HamNCheese.getLocation(id).toString()));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        ENTITIES.register(platform);
    }
}
