package io.github.coffeecatrailway.hamncheese.registry;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.block.PollinatedStandingSignBlock;
import gg.moonflower.pollen.api.block.PollinatedWallSignBlock;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedBlockRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.common.block.*;
import io.github.coffeecatrailway.hamncheese.common.block.grower.MapleTreeGrower;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class HNCBlocks
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedBlockRegistry BLOCKS = PollinatedRegistry.createBlock(HNCItems.ITEMS);

    public static final Supplier<PineapplePlantBlock> PINEAPPLE_PLANT = registerPlant("pineapple_plant", PineapplePlantBlock::new);
    public static final Supplier<TomatoPlantBlock> TOMATO_PLANT = registerPlant("tomato_plant", TomatoPlantBlock::new);
    public static final Supplier<CornPlantBlock> CORN_PLANT = registerPlant("corn_plant", CornPlantBlock::new);

    public static final Supplier<ChoppingBoardBlock> OAK_CHOPPING_BOARD = registerChoppingBoard("oak_chopping_board", () -> Blocks.OAK_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> BIRCH_CHOPPING_BOARD = registerChoppingBoard("birch_chopping_board", () -> Blocks.BIRCH_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> SPRUCE_CHOPPING_BOARD = registerChoppingBoard("spruce_chopping_board", () -> Blocks.SPRUCE_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> JUNGLE_CHOPPING_BOARD = registerChoppingBoard("jungle_chopping_board", () -> Blocks.JUNGLE_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> ACACIA_CHOPPING_BOARD = registerChoppingBoard("acacia_chopping_board", () -> Blocks.ACACIA_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> DARK_OAK_CHOPPING_BOARD = registerChoppingBoard("dark_oak_chopping_board", () -> Blocks.DARK_OAK_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> CRIMSON_CHOPPING_BOARD = registerChoppingBoard("crimson_chopping_board", () -> Blocks.CRIMSON_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> WARPED_CHOPPING_BOARD = registerChoppingBoard("warped_chopping_board", () -> Blocks.WARPED_PRESSURE_PLATE);

    public static final Supplier<ChoppingBoardBlock> STONE_CHOPPING_BOARD = registerChoppingBoard("stone_chopping_board", () -> Blocks.STONE_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> POLISHED_BLACKSTONE_CHOPPING_BOARD = registerChoppingBoard("polished_blackstone_chopping_board", () -> Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> GOLD_CHOPPING_BOARD = registerChoppingBoard("gold_chopping_board", () -> Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
    public static final Supplier<ChoppingBoardBlock> IRON_CHOPPING_BOARD = registerChoppingBoard("iron_chopping_board", () -> Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);

    public static final Supplier<PizzaOvenBlock> PIZZA_OVEN = registerWithItem("pizza_oven", () -> new PizzaOvenBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(2f, 6f)), prop -> prop);
    public static final Supplier<GrillBlock> GRILL = registerWithItem("grill", () -> new GrillBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.METAL)), prop -> prop);
    public static final Supplier<PopcornMachineBlock> POPCORN_MACHINE = registerWithItem("popcorn_machine", () -> new PopcornMachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.METAL)), prop -> prop);

    public static final Supplier<CheeseBlock> BLOCK_OF_CHEESE = registerWithItem("block_of_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_BLUE_CHEESE = registerWithItem("block_of_blue_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_BLUE_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_GOUDA_CHEESE = registerWithItem("block_of_gouda_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_GOUDA_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_SWISS_CHEESE = registerWithItem("block_of_swiss_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_SWISS_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_GOAT_CHEESE = registerWithItem("block_of_goat_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_GOAT_CHEESE).stacksTo(16));

    public static final Supplier<RotatedPillarBlock> MAPLE_LOG = registerLog("maple_log", MaterialColor.SAND, MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> MAPLE_WOOD = registerLog("maple_wood", MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_LOG = registerLog("stripped_maple_log", MaterialColor.SAND);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = registerLog("stripped_maple_wood", MaterialColor.SAND);
    public static final Supplier<LeavesBlock> MAPLE_LEAVES = registerWithItem("maple_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_RED).strength(.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT).isSuffocating((state, reader, blockPos) -> false).isViewBlocking((state, reader, blockPos) -> false)), prop -> prop);
    public static final Supplier<SaplingBlock> MAPLE_SAPLING = registerWithItem("maple_sapling", () -> new SaplingBlockOverride(new MapleTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), prop -> prop);
    public static final Supplier<FlowerPotBlock> POTTED_MAPLE_SAPLING = registerWithOutItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final Supplier<Block> MAPLE_PLANKS = registerWithItem("maple_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<StairBlock> MAPLE_STAIRS = registerWithItem("maple_stairs", () -> new StairBlockOverride(MAPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(MAPLE_PLANKS.get())), prop -> prop);
    public static final Supplier<SlabBlock> MAPLE_SLAB = registerWithItem("maple_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Pair<Supplier<PollinatedStandingSignBlock>, Supplier<PollinatedWallSignBlock>> MAPLE_SIGN = BLOCKS.registerSign("maple", BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1f).sound(SoundType.WOOD), HNCItems.DEFAULT_PROP.get().stacksTo(16));
    public static final Supplier<PressurePlateBlock> MAPLE_PRESSURE_PLATE = registerWithItem("maple_pressure_plate", () -> new PressurePlateBlockOverride(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<WoodButtonBlock> MAPLE_BUTTON = registerWithItem("maple_button", () -> new WoodButtonBlockOverride(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<FenceBlock> MAPLE_FENCE = registerWithItem("maple_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<FenceGateBlock> MAPLE_FENCE_GATE = registerWithItem("maple_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<TrapDoorBlock> MAPLE_TRAPDOOR = registerWithItem("maple_trapdoor", () -> new TrapDoorBlockOverride(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(3f).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> false)), prop -> prop);
    public static final Supplier<DoorBlock> MAPLE_DOOR = registerWithItem("maple_door", () -> new DoorBlockOverride(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(3f).sound(SoundType.WOOD).noOcclusion()), prop -> prop);

    public static final Supplier<ChoppingBoardBlock> MAPLE_CHOPPING_BOARD = registerChoppingBoard("maple_chopping_board", HNCBlocks.MAPLE_PRESSURE_PLATE);

    public static final Supplier<TreeTapBlock> TREE_TAP = registerWithItem("tree_tap", () -> new TreeTapBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.METAL)), prop -> prop.stacksTo(16));

    public static final Set<Block> CHOPPING_BOARDS = Registry.BLOCK.stream().filter(block -> block instanceof ChoppingBoardBlock).collect(Collectors.toSet());

    private static Supplier<RotatedPillarBlock> registerLog(String id, MaterialColor color)
    {
        return registerLog(id, color, color);
    }

    private static Supplier<RotatedPillarBlock> registerLog(String id, MaterialColor topColor, MaterialColor sideColor)
    {
        Function<BlockState, MaterialColor> colorFunction = (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor;
        if (topColor.equals(sideColor))
            colorFunction = (state) -> topColor;
        Function<BlockState, MaterialColor> finalColorFunction = colorFunction;
        return registerWithItem(id, () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, finalColorFunction)
                .strength(2f).sound(SoundType.WOOD)), prop -> prop);
    }

    public static <T extends Block> Supplier<ChoppingBoardBlock> registerChoppingBoard(String id, Supplier<T> base)
    {
        return registerWithItem(id, () -> new ChoppingBoardBlock(BlockBehaviour.Properties.copy(base.get()).strength(.5f)), prop -> prop);
    }

    private static <T extends Block> Supplier<T> registerPlant(String id, Function<BlockBehaviour.Properties, T> factory)
    {
        return registerWithOutItem(id, () -> factory.apply(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    }

    public static <T extends Block> Supplier<T> registerWithItem(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        Supplier<T> object = BLOCKS.registerWithItem(id, block, properties.apply(HNCItems.DEFAULT_PROP.get()));
        HNCLanguage.BLOCKS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static <T extends Block> Supplier<T> registerWithOutItem(String id, Supplier<T> block)
    {
        Supplier<T> object = BLOCKS.register(id, block);
        HNCLanguage.BLOCKS.put(object, HNCLanguage.capitalize(id));
        return object;
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(platform);
    }
}
