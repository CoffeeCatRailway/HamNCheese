package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.*;
import coffeecatrailway.hamncheese.common.block.fluid.MapleSapFluidBlock;
import coffeecatrailway.hamncheese.common.block.trees.MapleTree;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksBOP;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksDC;
import coffeecatrailway.hamncheese.integration.registry.HNCBlocksTF;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.util.Direction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

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

    public static final WoodType MAPLE_WOOD_TYPE = WoodType.create(HNCMod.getLocation("maple").toString());

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

    public static final RegistryObject<RotatedPillarBlock> MAPLE_LOG = registerLog("maple_log", MaterialColor.SAND, MaterialColor.COLOR_BROWN);
    public static final RegistryObject<RotatedPillarBlock> MAPLE_WOOD = registerLog("maple_wood", MaterialColor.COLOR_BROWN);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_MAPLE_LOG = registerLog("stripped_maple_log", MaterialColor.SAND);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = registerLog("stripped_maple_wood", MaterialColor.SAND);
    public static final RegistryObject<LeavesBlock> MAPLE_LEAVES = register("maple_leaves", () -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES, MaterialColor.COLOR_RED).strength(.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT).isSuffocating((state, reader, blockPos) -> false).isViewBlocking((state, reader, blockPos) -> false)), prop -> prop);
    public static final RegistryObject<SaplingBlock> MAPLE_SAPLING = register("maple_sapling", () -> new SaplingBlock(new MapleTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), prop -> prop);
    public static final RegistryObject<FlowerPotBlock> POTTED_MAPLE_SAPLING = registerWithItem("potted_maple_sapling", () -> new FlowerPotBlock(null, MAPLE_SAPLING, AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()), null);
    public static final RegistryObject<Block> MAPLE_PLANKS = register("maple_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<StairsBlock> MAPLE_STAIRS = register("maple_stairs", () -> new StairsBlock(() -> MAPLE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(MAPLE_PLANKS.get())), prop -> prop);
    public static final RegistryObject<SlabBlock> MAPLE_SLAB = register("maple_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<HNCStandingSignBlock> MAPLE_SIGN = registerWithItem("maple_sign", () -> new HNCStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1f).sound(SoundType.WOOD), MAPLE_WOOD_TYPE), (sign, prop) -> new SignItem(prop.stacksTo(16), sign.get(), HNCBlocks.MAPLE_WALL_SIGN.get()));
    public static final RegistryObject<HNCWallSignBlock> MAPLE_WALL_SIGN = registerWithItemAndName("maple_wall_sign", () -> new HNCWallSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(MAPLE_SIGN), MAPLE_WOOD_TYPE), null, null);
    public static final RegistryObject<PressurePlateBlock> MAPLE_PRESSURE_PLATE = register("maple_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> MAPLE_CHOPPING_BOARD = registerChoppingBoard("maple_chopping_board", MAPLE_PLANKS);
    public static final RegistryObject<WoodButtonBlock> MAPLE_BUTTON = register("maple_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<FenceBlock> MAPLE_FENCE = register("maple_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<FenceGateBlock> MAPLE_FENCE_GATE = register("maple_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final RegistryObject<TrapDoorBlock> MAPLE_TRAPDOOR = register("maple_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(3f).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> false)), prop -> prop);
    public static final RegistryObject<DoorBlock> MAPLE_DOOR = register("maple_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(3f).sound(SoundType.WOOD).noOcclusion()), prop -> prop);

    public static final RegistryObject<TreeTapBlock> TREE_TAP = register("tree_tap", () -> new TreeTapBlock(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), prop -> prop.stacksTo(16));
    public static final RegistryObject<MapleSapFluidBlock> MAPLE_SAP = registerWithItem("maple_sap", () -> new MapleSapFluidBlock(HNCFluids.MAPLE_SAP_FLOWING, AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100f)), null);

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

    private static RegistryObject<RotatedPillarBlock> registerLog(String id, MaterialColor color)
    {
        return registerLog(id, color, color);
    }

    private static RegistryObject<RotatedPillarBlock> registerLog(String id, MaterialColor topColor, MaterialColor sideColor)
    {
        Function<BlockState, MaterialColor> colorFunction = (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor;
        if (topColor.equals(sideColor))
            colorFunction = (state) -> topColor;
        Function<BlockState, MaterialColor> finalColorFunction = colorFunction;
        return register(id, () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, finalColorFunction)
                .strength(2f).sound(SoundType.WOOD)), prop -> prop);
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
        return registerWithItemAndName(id, block, item, HNCLanguage.capitalize(id));
    }

    private static <T extends Block> RegistryObject<T> registerWithItemAndName(String id, Supplier<T> block, @Nullable BiFunction<RegistryObject<T>, Item.Properties, Item> item, @Nullable String name)
    {
        RegistryObject<T> object = BLOCKS.register(id, block);
        if (item != null)
            HNCItems.ITEMS.register(id, () -> item.apply(object, new Item.Properties().tab(HNCMod.GROUP_ALL)));
        if (name != null)
            HNCLanguage.BLOCKS.put(object, name);
        return object;
    }

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(bus);
    }
}
