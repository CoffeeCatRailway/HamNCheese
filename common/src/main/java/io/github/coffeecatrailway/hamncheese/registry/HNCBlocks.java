package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.*;
import io.github.coffeecatrailway.hamncheese.common.block.grower.MapleTreeGrower;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2021
 */
public class HNCBlocks
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PollinatedRegistry<Block> BLOCKS = PollinatedRegistry.create(Registry.BLOCK, HamNCheese.MOD_ID);

    public static final WoodType MAPLE_WOOD_TYPE = WoodType.register(new WoodType(getWoodTypeId()));

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

    public static final Supplier<CheeseBlock> BLOCK_OF_CHEESE = register("block_of_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_BLUE_CHEESE = register("block_of_blue_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_BLUE_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_GOUDA_CHEESE = register("block_of_gouda_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_GOUDA_CHEESE).stacksTo(16));
    public static final Supplier<CheeseBlock> BLOCK_OF_SWISS_CHEESE = register("block_of_swiss_cheese", () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)), prop -> prop.food(HNCFoods.BLOCK_OF_SWISS_CHEESE).stacksTo(16));

    public static final Supplier<RotatedPillarBlock> MAPLE_LOG = registerLog("maple_log", MaterialColor.SAND, MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> MAPLE_WOOD = registerLog("maple_wood", MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_LOG = registerLog("stripped_maple_log", MaterialColor.SAND);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = registerLog("stripped_maple_wood", MaterialColor.SAND);
    public static final Supplier<LeavesBlock> MAPLE_LEAVES = register("maple_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_RED).strength(.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT).isSuffocating((state, reader, blockPos) -> false).isViewBlocking((state, reader, blockPos) -> false)), prop -> prop);
    public static final Supplier<SaplingBlock> MAPLE_SAPLING = register("maple_sapling", () -> new SaplingBlockOverride(new MapleTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), prop -> prop);
    public static final Supplier<FlowerPotBlock> POTTED_MAPLE_SAPLING = registerWithItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()), null);
    public static final Supplier<Block> MAPLE_PLANKS = register("maple_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<StairBlock> MAPLE_STAIRS = register("maple_stairs", () -> new StairBlockOverride(MAPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(MAPLE_PLANKS.get())), prop -> prop);
    public static final Supplier<SlabBlock> MAPLE_SLAB = register("maple_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<HNCWallSignBlock> MAPLE_WALL_SIGN = registerWithItemAndName("maple_wall_sign", () -> new HNCWallSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1f).sound(SoundType.WOOD), MAPLE_WOOD_TYPE), null, null);
    public static final Supplier<HNCStandingSignBlock> MAPLE_SIGN = registerWithItem("maple_sign", () -> new HNCStandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1f).sound(SoundType.WOOD), MAPLE_WOOD_TYPE), (sign, prop) -> new SignItem(prop.stacksTo(16), sign.get(), HNCBlocks.MAPLE_WALL_SIGN.get()));
    public static final Supplier<PressurePlateBlock> MAPLE_PRESSURE_PLATE = register("maple_pressure_plate", () -> new PressurePlateBlockOverride(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<WoodButtonBlock> MAPLE_BUTTON = register("maple_button", () -> new WoodButtonBlockOverride(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(.5f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<FenceBlock> MAPLE_FENCE = register("maple_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<FenceGateBlock> MAPLE_FENCE_GATE = register("maple_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(2f, 3f).sound(SoundType.WOOD)), prop -> prop);
    public static final Supplier<TrapDoorBlock> MAPLE_TRAPDOOR = register("maple_trapdoor", () -> new TrapDoorBlockOverride(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SAND).strength(3f).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> false)), prop -> prop);
    public static final Supplier<DoorBlock> MAPLE_DOOR = register("maple_door", () -> new DoorBlockOverride(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MAPLE_PLANKS.get().defaultMaterialColor()).strength(3f).sound(SoundType.WOOD).noOcclusion()), prop -> prop);

    public static final Supplier<ChoppingBoardBlock> MAPLE_CHOPPING_BOARD = registerChoppingBoard("maple_chopping_board", HNCBlocks.MAPLE_PRESSURE_PLATE);

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
        return register(id, () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, finalColorFunction)
                .strength(2f).sound(SoundType.WOOD)), prop -> prop);
    }

    public static <T extends Block> Supplier<ChoppingBoardBlock> registerChoppingBoard(String id, Supplier<T> base)
    {
        return register(id, () -> new ChoppingBoardBlock(BlockBehaviour.Properties.copy(base.get()).strength(.5f)), prop -> prop);
    }

    public static <T extends Block> Supplier<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        return registerWithItem(id, block, (object, prop) -> new BlockItem(object.get(), properties.apply(prop)));
    }

    protected static <T extends Block> Supplier<T> registerWithItem(String id, Supplier<T> block, @Nullable BiFunction<Supplier<T>, Item.Properties, Item> item)
    {
        return registerWithItemAndName(id, block, item, HNCLanguage.capitalize(id));
    }

    private static <T extends Block> Supplier<T> registerWithItemAndName(String id, Supplier<T> block, @Nullable BiFunction<Supplier<T>, Item.Properties, Item> item, @Nullable String name)
    {
        Supplier<T> object = BLOCKS.register(id, block);
        if (item != null)
            HNCItems.ITEMS.register(id, () -> item.apply(object, new Item.Properties().tab(HamNCheese.TAB)));
        if (name != null)
            HNCLanguage.BLOCKS.put(object, name);
        return object;
    }

    // Mod loader sided methods
    @ExpectPlatform
    private static String getWoodTypeId()
    {
        return Platform.error();
    }

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        BLOCKS.register(platform);
    }
}
