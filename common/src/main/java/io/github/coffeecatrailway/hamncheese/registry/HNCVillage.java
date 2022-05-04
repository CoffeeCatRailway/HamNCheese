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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.structures.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
     * All of the credit for this method goes to the creators of the Waystones mod.
     * <a href="https://github.com/ModdingForBlockheads/Waystones/blob/1.18.x/shared/src/main/java/net/blay09/mods/waystones/worldgen/ModWorldGen.java">ModWorldGen.java</a>
     */
    public static void addVillageStructure(MinecraftServer server, String villagePiece, String structureName, int weight)
    {
        ResourceLocation structure = HamNCheese.getLocation(structureName);

        LegacySinglePoolElement piece = StructurePoolElement.legacy(structure.toString()).apply(StructureTemplatePool.Projection.RIGID);
        StructureTemplatePool pool = server.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).getOptional(new ResourceLocation(villagePiece)).orElse(null);

        if (pool != null)
        {
            var poolAccessor = (StructureTemplatePoolAccessor) pool;
            // pretty sure this can be an immutable list (when datapacked) so gotta make a copy to be safe.
            List<StructurePoolElement> listOfPieces = new ArrayList<>(poolAccessor.getTemplates());

            for (int i = 0; i < weight; i++)
                listOfPieces.add(piece);
            LOGGER.info("Added weight for '" + structure + "' to " + weight);
            poolAccessor.setTemplates(listOfPieces);

            List<Pair<StructurePoolElement, Integer>> listOfWeightedPieces = new ArrayList<>(poolAccessor.getRawTemplates());
            listOfWeightedPieces.add(new Pair<>(piece, weight));
            poolAccessor.setRawTemplates(listOfWeightedPieces);
        }
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        POI_TYPES.register(platform);
        PROFESSIONS.register(platform);
    }
}
