package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.datagen.HNCLanguage;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * @author CoffeeCatRailway
 * Created: 24/04/2021
 */
public class HNCProfessions
{
    private static final Logger LOGGER = HNCMod.getLogger("Professions");
    protected static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, HNCMod.MOD_ID);
    protected static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, HNCMod.MOD_ID);

    public static final RegistryObject<PointOfInterestType> CHEF_POI = registerPoi("chef", PointOfInterestType.getBlockStates(Blocks.ANVIL), 1, 1); // Add kitchen block
    public static final RegistryObject<VillagerProfession> CHEF = registerProfession("chef", CHEF_POI, SoundEvents.VILLAGER_WORK_BUTCHER);

    private static RegistryObject<VillagerProfession> registerProfession(String id, RegistryObject<PointOfInterestType> poi, SoundEvent sound)
    {
        RegistryObject<VillagerProfession> object = PROFESSIONS.register(id, () -> new VillagerProfession(id, poi.get(), ImmutableSet.of(), ImmutableSet.of(), sound));
        HNCLanguage.PROFESSIONS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    private static RegistryObject<PointOfInterestType> registerPoi(String id, Set<BlockState> matchingStates, int maxTickets, int validRange)
    {
        return POI_TYPES.register(id, () -> new PointOfInterestType(id, matchingStates, maxTickets, validRange));
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        POI_TYPES.register(bus);
        PROFESSIONS.register(bus);
    }
}
