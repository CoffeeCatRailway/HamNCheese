package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.*;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksBOP;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksDC;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksTF;
import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class HNCBlocks
{
    private static final Logger LOGGER = HNCMod.getLogger("Blocks");
    protected static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HNCMod.MOD_ID);

    public static final RegistryObject<PineapplePlantBlock> PINEAPPLE_PLANT = registerPlant("pineapple_plant", PineapplePlantBlock::new);
    public static final RegistryObject<TomatoPlantBlock> TOMATO_PLANT = registerPlant("tomato_plant", TomatoPlantBlock::new);
    public static final RegistryObject<CornPlantBlock> CORN_PLANT = registerPlant("corn_plant", CornPlantBlock::new);

    public static final RegistryObject<ChoppingBoardBlock> OAK_CHOPPING_BOARD = registerChoppingBoard("oak_chopping_board", () -> Blocks.OAK_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> BIRCH_CHOPPING_BOARD = registerChoppingBoard("birch_chopping_board", () -> Blocks.BIRCH_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> SPRUCE_CHOPPING_BOARD = registerChoppingBoard("spruce_chopping_board", () -> Blocks.SPRUCE_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> JUNGLE_CHOPPING_BOARD = registerChoppingBoard("jungle_chopping_board", () -> Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> ACACIA_CHOPPING_BOARD = registerChoppingBoard("acacia_chopping_board", () -> Blocks.ACACIA_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> DARK_OAK_CHOPPING_BOARD = registerChoppingBoard("dark_oak_chopping_board", () -> Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> CRIMSON_CHOPPING_BOARD = registerChoppingBoard("crimson_chopping_board", () -> Blocks.CRIMSON_PLANKS);
    public static final RegistryObject<ChoppingBoardBlock> WARPED_CHOPPING_BOARD = registerChoppingBoard("warped_chopping_board", () -> Blocks.WARPED_PLANKS);

    public static final RegistryObject<ChoppingBoardBlock> STONE_CHOPPING_BOARD = registerChoppingBoard("stone_chopping_board", () -> Blocks.STONE);
    public static final RegistryObject<ChoppingBoardBlock> POLISHED_BLACKSTONE_CHOPPING_BOARD = registerChoppingBoard("polished_blackstone_chopping_board", () -> Blocks.POLISHED_BLACKSTONE);
    public static final RegistryObject<ChoppingBoardBlock> GOLD_CHOPPING_BOARD = registerChoppingBoard("gold_chopping_board", () -> Blocks.GOLD_BLOCK);
    public static final RegistryObject<ChoppingBoardBlock> IRON_CHOPPING_BOARD = registerChoppingBoard("iron_chopping_board", () -> Blocks.IRON_BLOCK);

    public static final RegistryObject<PizzaOvenBlock> PIZZA_OVEN = register("pizza_oven", () -> new PizzaOvenBlock(AbstractBlock.Properties.copy(Blocks.WHITE_TERRACOTTA)), prop -> prop);
    public static final RegistryObject<GrillBlock> GRILL = register("grill", () -> new GrillBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)), prop -> prop);
    public static final RegistryObject<PopcornMachineBlock> POPCORN_MACHINE = register("popcorn_machine", () -> new PopcornMachineBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)), prop -> prop);

    public static final RegistryObject<CheeseBlock> BLOCK_OF_CHEESE = register("block_of_cheese", () -> new CheeseBlock(AbstractBlock.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_CHEESE).stacksTo(16));

    // Biomes O' Plenty
    public static RegistryObject<ChoppingBoardBlock> FIR_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> REDWOOD_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> CHERRY_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> MAHOGANY_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> JACARANDA_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> PALM_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> WILLOW_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> DEAD_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> MAGIC_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> UMBRAN_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> HELLBARK_CHOPPING_BOARD;

    // Twilight Forest
    public static RegistryObject<ChoppingBoardBlock> TWILIGHT_OAK_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> CANOPY_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> MANGROVE_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> DARK_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> TIME_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> TRANS_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> MINE_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> SORT_CHOPPING_BOARD;

    // Druidcraft
    public static RegistryObject<ChoppingBoardBlock> DARKWOOD_CHOPPING_BOARD;
    public static RegistryObject<ChoppingBoardBlock> ELDER_CHOPPING_BOARD;

    static
    {
        ModList list = ModList.get();
        if (list.isLoaded("biomesoplenty"))
            HNCBlocksBOP.load();
        if (list.isLoaded("twilightforest"))
            HNCBlocksTF.load();
        if (list.isLoaded("druidcraft"))
            HNCBlocksDC.load();
    }

    public static RegistryObject<ChoppingBoardBlock> registerChoppingBoard(String id, Supplier<Block> planks)
    {
        return register(id, () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(planks.get())), prop -> prop);
    }

    public static <T extends Block> RegistryObject<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        return registerWithItem(id, block, (object, prop) -> new BlockItem(object.get(), properties.apply(prop)));
    }

    private static <T extends Block> RegistryObject<T> registerPlant(String id, Function<AbstractBlock.Properties, T> factory)
    {
        return registerWithItem(id, () -> factory.apply(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)), null);
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String id, Supplier<T> block, @Nullable BiFunction<RegistryObject<T>, Item.Properties, Item> item)
    {
        RegistryObject<T> object = BLOCKS.register(id, block);
        if (item != null)
            HNCItems.ITEMS.register(id, () -> item.apply(object, new Item.Properties().tab(HNCMod.GROUP_ALL)));
        HNCLanguage.BLOCKS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(bus);
    }
}
