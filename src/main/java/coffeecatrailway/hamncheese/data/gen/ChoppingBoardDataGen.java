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
