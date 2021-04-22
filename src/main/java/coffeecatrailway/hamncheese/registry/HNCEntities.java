package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import coffeecatrailway.hamncheese.datagen.HNCLanguage;
import io.github.ocelot.sonar.common.item.SpawnEggItemBase;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
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

    public static final RegistryObject<EntityType<MouseEntity>> MOUSE = register("mouse", MouseEntity::new, EntityClassification.AMBIENT, builder -> builder.sized(.8f, .5f).clientTrackingRange(10));

    private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, BiFunction<EntityType<E>, World, E> entityFactory, EntityClassification classification, Function<EntityType.Builder<E>, EntityType.Builder<E>> factory)
    {
        RegistryObject<EntityType<E>> object = ENTITIES.register(id, () -> factory.apply(EntityType.Builder.<E>of(entityFactory::apply, classification)).build(HNCMod.getLocation(id).toString()));
        HNCLanguage.ENTITIES.put(object, HNCLanguage.capitalize(id));
        HNCItems.registerIdAsName(id + "_spawn_egg", prop -> new SpawnEggItemBase<>(object, 0xffffff, 0x7a3205, true, prop));
        return object;
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(HNCEntities.MOUSE.get(), MouseEntity.registerAttributeMap().build());
    }

    public static void registerSpawnPlacements()
    {
        EntitySpawnPlacementRegistry.register(MOUSE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
    }

    public static void addEntitySpawns(BiomeLoadingEvent event)
    {
        event.getSpawns().getSpawner(MOUSE.get().getCategory()).add(new MobSpawnInfo.Spawners(MOUSE.get(), 10, 4, 4));
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        ENTITIES.register(bus);
    }
}
