package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class ChoppingBoardDataGen implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<String, JsonElement> toSerialize = new HashMap<>();
    private final DataGenerator generator;

    public ChoppingBoardDataGen(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException
    {
        Path folder = this.generator.getOutputFolder();
        start();

        this.toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + HNCMod.MOD_ID + "/chopping_boards/" + name + ".json");
            try
            {
                IDataProvider.save(GSON, cache, json, path);
            } catch (IOException e)
            {
                LOGGER.error("Couldn't save chopping board {}", path, e);
            }
        });
    }

    private void start()
    {
        this.addChoppingBoard("oak_chopping_board", Blocks.OAK_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/oak"), "axe");
        this.addChoppingBoard("birch_chopping_board", Blocks.BIRCH_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/birch"), "axe");
        this.addChoppingBoard("spruce_chopping_board", Blocks.SPRUCE_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/spruce"), "axe");
        this.addChoppingBoard("jungle_chopping_board", Blocks.JUNGLE_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/jungle"), "axe");
        this.addChoppingBoard("acacia_chopping_board", Blocks.ACACIA_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/acacia"), "axe");
        this.addChoppingBoard("dark_oak_chopping_board", Blocks.DARK_OAK_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/dark_oak"), "axe");
        this.addChoppingBoard("crimson_chopping_board", Blocks.CRIMSON_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/crimson"), "axe");
        this.addChoppingBoard("warped_chopping_board", Blocks.WARPED_PRESSURE_PLATE, SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/warped"), "axe");

//        this.addChoppingBoard("stone_chopping_board", Blocks.STONE_PRESSURE_PLATE, SoundEvents.STONE_PLACE, new ResourceLocation("chopping_board/stone"), "pickaxe");
        this.addChoppingBoard("polished_blackstone_chopping_board", Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE, SoundEvents.STONE_PLACE, HNCMod.getLocation("chopping_board/polished_blackstone"), "pickaxe");
        this.addChoppingBoard("light_weighted_chopping_board", Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, SoundEvents.METAL_PLACE, HNCMod.getLocation("chopping_board/gold"), "pickaxe");
        this.addChoppingBoard("heavy_weighted_chopping_board", Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, SoundEvents.METAL_PLACE, HNCMod.getLocation("chopping_board/iron"), "pickaxe");

        this.addChoppingBoard("maple_chopping_board", HNCBlocks.MAPLE_PRESSURE_PLATE.get(), SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/maple"), "axe");

        this.addChoppingBoard("fir_chopping_board", "biomesoplenty", "fir_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/fir"), "axe");
        this.addChoppingBoard("redwood_chopping_board", "biomesoplenty", "redwood_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/redwood"), "axe");
        this.addChoppingBoard("cherry_chopping_board", "biomesoplenty", "cherry_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/cherry"), "axe");
        this.addChoppingBoard("mahogany_chopping_board", "biomesoplenty", "mahogany_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/mahogany"), "axe");
        this.addChoppingBoard("jacaranda_chopping_board", "biomesoplenty", "jacaranda_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/jacaranda"), "axe");
        this.addChoppingBoard("palm_chopping_board", "biomesoplenty", "palm_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/palm"), "axe");
        this.addChoppingBoard("willow_chopping_board", "biomesoplenty", "willow_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/willow"), "axe");
        this.addChoppingBoard("dead_chopping_board", "biomesoplenty", "dead_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/dead"), "axe");
        this.addChoppingBoard("magic_chopping_board", "biomesoplenty", "magic_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/magic"), "axe");
        this.addChoppingBoard("umbran_chopping_board", "biomesoplenty", "umbran_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/umbran"), "axe");
        this.addChoppingBoard("hellbark_chopping_board", "biomesoplenty", "hellbark_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/hellbark"), "axe");

        this.addChoppingBoard("twilight_oak_plate_chopping_board", "twilightforest", "twilight_oak_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/twilight_oak"), "axe");
        this.addChoppingBoard("canopy_chopping_board", "twilightforest", "canopy_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/canopy"), "axe");
        this.addChoppingBoard("mangrove_chopping_board", "twilightforest", "mangrove_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/mangrove"), "axe");
        this.addChoppingBoard("dark_chopping_board", "twilightforest", "dark_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/dark"), "axe");
        this.addChoppingBoard("time_chopping_board", "twilightforest", "time_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/time"), "axe");
        this.addChoppingBoard("trans_chopping_board", "twilightforest", "trans_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/trans"), "axe");
        this.addChoppingBoard("mine_chopping_board", "twilightforest", "mine_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/mine"), "axe");
        this.addChoppingBoard("sort_chopping_board", "twilightforest", "sort_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/sort"), "axe");

        this.addChoppingBoard("darkwood_chopping_board", "druidcraft", "darkwood_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/darkwood"), "axe");
        this.addChoppingBoard("elder_chopping_board", "druidcraft", "elder_pressure_plate", SoundEvents.WOOD_PLACE, HNCMod.getLocation("chopping_board/elder"), "axe");
    }

    private void addChoppingBoard(String name, Block pressurePlate, SoundEvent convertSound, ResourceLocation texture, String toolType)
    {
        this.addChoppingBoard(name, "minecraft", pressurePlate.getRegistryName().toString(), convertSound, texture, toolType);
    }

    private void addChoppingBoard(String name, String modId, String pressurePlate, SoundEvent convertSound, ResourceLocation texture, String toolType)
    {
        ChoppingBoard board = new ChoppingBoard(modId, Blocks.STONE_PRESSURE_PLATE, convertSound, texture, toolType);
        Optional<JsonElement> opt = JsonOps.INSTANCE.withEncoder(ChoppingBoard.CODEC).apply(board).result();
        if (opt.isPresent() && opt.get().isJsonObject())
        {
            JsonObject object = opt.get().getAsJsonObject();
            if (object.has("pressure_plate"))
                object.remove("pressure_plate");
            object.addProperty("pressure_plate", (pressurePlate.contains(":") ? "" : modId + ":") + pressurePlate);
            toSerialize.put((modId.equals("minecraft") ? "" : modId + "/") + name, object);
        } else
            LOGGER.error("Couldn't save chopping board {}", board);
    }

    @Override
    public String getName()
    {
        return "Chopping Boards: " + HNCMod.MOD_ID;
    }
}
