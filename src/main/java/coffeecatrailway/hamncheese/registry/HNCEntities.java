package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCConfig;
import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import io.github.ocelot.sonar.common.item.SpawnEggItemBase;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
@Mod.EventBusSubscriber(modid = HNCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HNCEntities
{
    private static final Logger LOGGER = HNCMod.getLogger("Entities");
    protected static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, HNCMod.MOD_ID);

    public static final Set<Runnable> ATTRIBUTE_MAPS = new HashSet<>();

    public static final RegistryObject<EntityType<MouseEntity>> MOUSE = registerWithEgg("mouse", MouseEntity::new, EntityClassification.AMBIENT, 0xffffff, 0x7a3205, builder -> builder.sized(.8f, .5f).clientTrackingRange(10));
    public static final RegistryObject<EntityType<HNCBoatEntity>> MAPLE_BOAT = register("maple_boat", HNCBoatEntity::new, EntityClassification.MISC, builder -> builder.sized(1.375f, 0.5625f));

    private static <E extends Entity> RegistryObject<EntityType<E>> registerWithEgg(String id, BiFunction<EntityType<E>, World, E> entityFactory, EntityClassification classification, int bgColor, int spotColor, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        RegistryObject<EntityType<E>> object = register(id, entityFactory, classification, factory);
        HNCItems.registerIdAsName(id + "_spawn_egg", prop -> new SpawnEggItemBase<>(object, bgColor, spotColor, true, prop));
        return object;
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, BiFunction<EntityType<E>, World, E> entityFactory, EntityClassification classification, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        RegistryObject<EntityType<E>> object = ENTITIES.register(id, () -> factory.apply(EntityType.Builder.<E>of(entityFactory::apply, classification)).build(HNCMod.getLocation(id).toString()));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(HNCEntities.MOUSE.get(), MouseEntity.registerAttributeMap().build());
    }

    public static void registerSpawnPlacements()
    {
        EntitySpawnPlacementRegistry.register(MOUSE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> MobEntity.checkMobSpawnRules(type, level, reason, pos, random) && HNCConfig.SERVER.canSpawnMouse());
    }

    public static void addEntitySpawns(BiomeLoadingEvent event)
    {
        if (!HNCConfig.SERVER.biomeCategoryWhitelist.get().contains(event.getCategory().getName()))
            return;
        List<MobSpawnInfo.Spawners> mouseSpawns = event.getSpawns().getSpawner(MOUSE.get().getCategory());
        if (HNCConfig.SERVER.canSpawnMouse())
            mouseSpawns.add(new MobSpawnInfo.Spawners(MOUSE.get(), HNCConfig.SERVER.mouseSpawnWeight.get(), HNCConfig.SERVER.mouseMinCount.get(), HNCConfig.SERVER.mouseMaxCount.get()));
        else
            mouseSpawns.clear();
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        ENTITIES.register(bus);
    }
}
