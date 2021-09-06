package coffeecatrailway.hamncheese.data.gen;

import biomesoplenty.api.block.BOPBlocks;
import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.SoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.block.TFBlocks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author vemerion
 */
public class ChoppingBoardDataGen implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, JsonElement> toSerialize = new HashMap<>();
    private DataGenerator generator;

    public ChoppingBoardDataGen(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path folder = generator.getOutputFolder();
        start();

        toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + HNCMod.MOD_ID + "/chopping_boards/" + name + ".json");
            try {
                IDataProvider.save(GSON, cache, json, path);
            } catch (IOException e) {
                LOGGER.error("Couldn't save chopping board {}", path, e);
            }
        });
    }

    private void start() {
        addChoppingBoard(new ChoppingBoard(Blocks.OAK_PRESSURE_PLATE, HNCBlocks.OAK_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.BIRCH_PRESSURE_PLATE, HNCBlocks.BIRCH_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.SPRUCE_PRESSURE_PLATE, HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.JUNGLE_PRESSURE_PLATE, HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.ACACIA_PRESSURE_PLATE, HNCBlocks.ACACIA_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.DARK_OAK_PRESSURE_PLATE, HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.CRIMSON_PRESSURE_PLATE, HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));
        addChoppingBoard(new ChoppingBoard(Blocks.WARPED_PRESSURE_PLATE, HNCBlocks.WARPED_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"));

        addChoppingBoard(new ChoppingBoard(Blocks.STONE_PRESSURE_PLATE, HNCBlocks.STONE_CHOPPING_BOARD.get(), SoundEvents.STONE_PLACE, "pickaxe"));
        addChoppingBoard(new ChoppingBoard(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE, HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), SoundEvents.STONE_PLACE, "pickaxe"));
        addChoppingBoard(new ChoppingBoard(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, HNCBlocks.GOLD_CHOPPING_BOARD.get(), SoundEvents.METAL_PLACE, "pickaxe"));
        addChoppingBoard(new ChoppingBoard(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, HNCBlocks.IRON_CHOPPING_BOARD.get(), SoundEvents.METAL_PLACE, "pickaxe"));

        addChoppingBoard(new ChoppingBoard(BOPBlocks.fir_pressure_plate, HNCBlocks.FIR_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.redwood_pressure_plate, HNCBlocks.REDWOOD_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.cherry_pressure_plate, HNCBlocks.CHERRY_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.mahogany_pressure_plate, HNCBlocks.MAHOGANY_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.jacaranda_pressure_plate, HNCBlocks.JACARANDA_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.palm_pressure_plate, HNCBlocks.PALM_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.willow_pressure_plate, HNCBlocks.WILLOW_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.dead_pressure_plate, HNCBlocks.DEAD_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.magic_pressure_plate, HNCBlocks.MAGIC_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.umbran_pressure_plate, HNCBlocks.UMBRAN_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");
        addChoppingBoard(new ChoppingBoard(BOPBlocks.hellbark_pressure_plate, HNCBlocks.HELLBARK_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "biomesoplenty");

        addChoppingBoard(new ChoppingBoard(TFBlocks.twilight_oak_plate.get(), HNCBlocks.TWILIGHT_OAK_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.canopy_plate.get(), HNCBlocks.CANOPY_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.mangrove_plate.get(), HNCBlocks.MANGROVE_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.dark_plate.get(), HNCBlocks.DARK_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.time_plate.get(), HNCBlocks.TIME_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.trans_plate.get(), HNCBlocks.TRANS_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.mine_plate.get(), HNCBlocks.MINE_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");
        addChoppingBoard(new ChoppingBoard(TFBlocks.sort_plate.get(), HNCBlocks.SORT_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "twilightforest");

        addChoppingBoard(new ChoppingBoard(BlockRegistry.darkwood_pressure_plate, HNCBlocks.DARKWOOD_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "druidcraft");
        addChoppingBoard(new ChoppingBoard(BlockRegistry.elder_pressure_plate, HNCBlocks.ELDER_CHOPPING_BOARD.get(), SoundEvents.WOOD_PLACE, "axe"), "druidcraft");
    }

    private void addChoppingBoard(ChoppingBoard board) {
        addChoppingBoard(board, "minecraft");
    }

    private void addChoppingBoard(ChoppingBoard board, String modid) {
        Optional<JsonElement> opt = JsonOps.INSTANCE.withEncoder(ChoppingBoard.CODEC).apply(board).result();
        if (opt.isPresent() && opt.get().isJsonObject()) {
            JsonObject json = opt.get().getAsJsonObject();
            json.addProperty("modId", modid);
            String name = board.getBoard().getRegistryName().getPath();
            toSerialize.put((modid.equals("minecraft") ? "" : modid + "/") + name, json);
        } else {
            LOGGER.error("Couldn't save chopping board {}", board);
        }
    }

    @Override
    public String getName() {
        return "Chopping Boards: " + HNCMod.MOD_ID;
    }

}
