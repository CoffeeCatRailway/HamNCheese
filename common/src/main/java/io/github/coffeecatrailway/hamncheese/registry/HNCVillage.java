package io.github.coffeecatrailway.hamncheese.registry;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import io.github.coffeecatrailway.hamncheese.mixins.StructureTemplatePoolAccessor;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.github.coffeecatrailway.hamncheese.HamNCheese.CONFIG_SERVER;

/**
 * @author CoffeeCatRailway
 * Created: 24/04/2021
 */
public class HNCVillage
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

    /**
     * All of the credit for this class goes to the creators of the Etched mod.
     * <a href="https://github.com/MoonflowerTeam/etched/blob/1.18.x/common/src/main/java/gg/moonflower/etched/core/registry/EtchedVillagers.java">EtchedVillagers.java</a>
     * <p>
     * Start
     */
    public static void addStructurePieces() {
        PlainVillagePools.bootstrap();
        DesertVillagePools.bootstrap();
        SavannaVillagePools.bootstrap();
        SnowyVillagePools.bootstrap();
        TaigaVillagePools.bootstrap();

        if (CONFIG_SERVER.plainsRestaurantWeight.get() > 0)
            createVillagePiece("plains", "restaurant", 1, CONFIG_SERVER.plainsRestaurantWeight.get(), ProcessorLists.MOSSIFY_10_PERCENT, ProcessorLists.ZOMBIE_PLAINS);
        if (CONFIG_SERVER.desertRestaurantWeight.get() > 0)
            createVillagePiece("desert", "restaurant", 1, CONFIG_SERVER.desertRestaurantWeight.get(), ProcessorLists.ZOMBIE_DESERT);
        if (CONFIG_SERVER.savannaRestaurantWeight.get() > 0)
            createVillagePiece("savanna", "restaurant", 1, CONFIG_SERVER.savannaRestaurantWeight.get(), ProcessorLists.ZOMBIE_SAVANNA);
        if (CONFIG_SERVER.snowyRestaurantWeight.get() > 0)
            createVillagePiece("snowy", "restaurant", 1, CONFIG_SERVER.snowyRestaurantWeight.get(), ProcessorLists.ZOMBIE_SNOWY);
        if (CONFIG_SERVER.taigaRestaurantWeight.get() > 0)
            createVillagePiece("taiga", "restaurant", 1, CONFIG_SERVER.taigaRestaurantWeight.get(), ProcessorLists.MOSSIFY_10_PERCENT, ProcessorLists.ZOMBIE_TAIGA);
    }
    
    private static void createVillagePiece(String village, String name, int houseId, int weight, StructureProcessorList zombieProcessor)
    {
        createVillagePiece(village, name, houseId, weight, ProcessorLists.EMPTY, zombieProcessor);
    }

    private static void createVillagePiece(String village, String name, int houseId, int weight, StructureProcessorList normalProcessor, StructureProcessorList zombieProcessor)
    {
        addToPool(new ResourceLocation("village/" + village + "/houses"), new ResourceLocation(HamNCheese.MOD_ID, "village/" + village + "/houses/" + village + "_" + name + "_" + houseId), normalProcessor, weight);
        addToPool(new ResourceLocation("village/" + village + "/zombie/houses"), new ResourceLocation(HamNCheese.MOD_ID, "village/" + village + "/houses/" + village + "_" + name + "_" + houseId), zombieProcessor, weight);
    }

    private static void addToPool(ResourceLocation poolId, ResourceLocation pieceId, StructureProcessorList processorList, int weight)
    {
        StructureTemplatePool pool = BuiltinRegistries.TEMPLATE_POOL.get(poolId);
        if (pool == null)
            return;

        StructurePoolElement piece = StructurePoolElement.legacy(pieceId.toString(), processorList).apply(StructureTemplatePool.Projection.RIGID);
        List<StructurePoolElement> templates = ((StructureTemplatePoolAccessor) pool).getTemplates();
        List<Pair<StructurePoolElement, Integer>> rawTemplates = ((StructureTemplatePoolAccessor) pool).getRawTemplates();
        if (templates == null || rawTemplates == null)
            return;

        for (int i = 0; i < weight; i++)
            templates.add(piece);
        rawTemplates.add(Pair.of(piece, weight));
    }
    /*
     * End
     */

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        POI_TYPES.register(platform);
        PROFESSIONS.register(platform);
    }
}
