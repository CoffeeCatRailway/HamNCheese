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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.SoundEvent;
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
 * <p>
 * Later changed by CoffeeCatRailway for "chopping-board-revamp"
 * </p>
 */
public class ChoppingBoardDataGen implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<String, JsonElement> toSerialize = new HashMap<>();
    private final DataGenerator generator;

    public ChoppingBoardDataGen(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path folder = this.generator.getOutputFolder();
        start();

        this.toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + HNCMod.MOD_ID + "/chopping_boards/" + name + ".json");
            try {
                IDataProvider.save(GSON, cache, json, path);
            } catch (IOException e) {
                LOGGER.error("Couldn't save chopping board {}", path, e);
            }
        });
    }

    private void start() {
        this.addChoppingBoard("oak_chopping_board", Blocks.OAK_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("birch_chopping_board", Blocks.BIRCH_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("spruce_chopping_board", Blocks.SPRUCE_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("jungle_chopping_board", Blocks.JUNGLE_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("acacia_chopping_board", Blocks.ACACIA_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("dark_oak_chopping_board", Blocks.DARK_OAK_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("crimson_chopping_board", Blocks.CRIMSON_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("warped_chopping_board", Blocks.WARPED_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, "axe");

        this.addChoppingBoard("stone_chopping_board", Blocks.STONE_PRESSURE_PLATE, SoundEvents.STONE_PLACE, "pickaxe");
        this.addChoppingBoard("polished_blackstone_chopping_board", Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE, SoundEvents.STONE_PLACE, "pickaxe");
        this.addChoppingBoard("light_weighted_chopping_board", Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, SoundEvents.METAL_PLACE, "pickaxe");
        this.addChoppingBoard("heavy_weighted_chopping_board", Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, SoundEvents.METAL_PLACE, "pickaxe");

        this.addChoppingBoard("maple_chopping_board", HNCBlocks.MAPLE_PRESSURE_PLATE.get(), SoundEvents.WOOD_PLACE, "axe");

        this.addChoppingBoard("fir_chopping_board", "biomesoplenty", BOPBlocks.fir_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("redwood_chopping_board", "biomesoplenty", BOPBlocks.redwood_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("cherry_chopping_board", "biomesoplenty", BOPBlocks.cherry_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("mahogany_chopping_board", "biomesoplenty", BOPBlocks.mahogany_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("jacaranda_chopping_board", "biomesoplenty", BOPBlocks.jacaranda_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("palm_chopping_board", "biomesoplenty", BOPBlocks.palm_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("willow_chopping_board", "biomesoplenty", BOPBlocks.willow_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("dead_chopping_board", "biomesoplenty", BOPBlocks.dead_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("magic_chopping_board", "biomesoplenty", BOPBlocks.magic_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("umbran_chopping_board", "biomesoplenty", BOPBlocks.umbran_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("hellbark_chopping_board", "biomesoplenty", BOPBlocks.hellbark_pressure_plate, SoundEvents.WOOD_PLACE, "axe");

        this.addChoppingBoard("twilight_oak_plate_chopping_board", "twilightforest", TFBlocks.twilight_oak_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("canopy_chopping_board", "twilightforest", TFBlocks.canopy_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("mangrove_chopping_board", "twilightforest", TFBlocks.mangrove_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("dark_chopping_board", "twilightforest", TFBlocks.dark_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("time_chopping_board", "twilightforest", TFBlocks.time_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("trans_chopping_board", "twilightforest", TFBlocks.trans_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("mine_chopping_board", "twilightforest", TFBlocks.mine_plate.get(), SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("sort_chopping_board", "twilightforest", TFBlocks.sort_plate.get(), SoundEvents.WOOD_PLACE, "axe");

        this.addChoppingBoard("darkwood_chopping_board", "druidcraft", BlockRegistry.darkwood_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
        this.addChoppingBoard("elder_chopping_board", "druidcraft", BlockRegistry.elder_pressure_plate, SoundEvents.WOOD_PLACE, "axe");
    }

    private void addChoppingBoard(String name, Block pressurePlate, SoundEvent convertSound, String toolType) {
        this.addChoppingBoard(name, "minecraft", pressurePlate, convertSound, toolType);
    }

    private void addChoppingBoard(String name, String modId, Block pressurePlate, SoundEvent convertSound, String toolType) {
        ChoppingBoard board = new ChoppingBoard(modId, pressurePlate, convertSound, toolType);
        Optional<JsonElement> opt = JsonOps.INSTANCE.withEncoder(ChoppingBoard.CODEC).apply(board).result();
        if (opt.isPresent() && opt.get().isJsonObject())
            toSerialize.put((modId.equals("minecraft") ? "" : modId + "/") + name, opt.get().getAsJsonObject());
        else
            LOGGER.error("Couldn't save chopping board {}", board);
    }

    @Override
    public String getName() {
        return "Chopping Boards: " + HNCMod.MOD_ID;
    }
}
