package io.github.coffeecatrailway.hamncheese.registry;

import com.google.common.collect.ImmutableSet;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 24/04/2021
 */
public class HNCProfessions
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<PoiType> POI_TYPES = PollinatedRegistry.create(Registry.POINT_OF_INTEREST_TYPE, HamNCheese.MOD_ID);
    protected static final PollinatedRegistry<VillagerProfession> PROFESSIONS = PollinatedRegistry.create(Registry.VILLAGER_PROFESSION, HamNCheese.MOD_ID);

    public static final Supplier<PoiType> CHEF_POI = registerPoi("chef", () -> Registry.BLOCK.stream().filter(block -> block instanceof ChoppingBoardBlock).flatMap(board -> board.getStateDefinition().getPossibleStates().stream()).collect(Collectors.toSet()), 1, 1);
    public static final Supplier<VillagerProfession> CHEF = registerProfession("chef", CHEF_POI, SoundEvents.VILLAGER_WORK_BUTCHER);

    private static Supplier<VillagerProfession> registerProfession(String id, Supplier<PoiType> poi, SoundEvent sound)
    {
        Supplier<VillagerProfession> object = PROFESSIONS.register(id, () -> new VillagerProfession(id, poi.get(), ImmutableSet.of(), ImmutableSet.of(), sound));
        HNCLanguage.PROFESSIONS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    private static Supplier<PoiType> registerPoi(String id, Supplier<Set<BlockState>> matchingStates, int maxTickets, int validRange)
    {
        return POI_TYPES.register(id, () -> new PoiType(id, matchingStates.get(), maxTickets, validRange));
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        POI_TYPES.register(platform);
        PROFESSIONS.register(platform);
    }
}
