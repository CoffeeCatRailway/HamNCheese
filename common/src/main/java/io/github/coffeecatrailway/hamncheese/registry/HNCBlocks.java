package io.github.coffeecatrailway.hamncheese.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import gg.moonflower.pollen.core.Pollen;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.*;
import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.Tier;
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

    public static final Supplier<RotatedPillarBlock> MAPLE_LOG = registerLog("maple_log", MaterialColor.SAND, MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> MAPLE_WOOD = registerLog("maple_wood", MaterialColor.COLOR_BROWN);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_LOG = registerLog("stripped_maple_log", MaterialColor.SAND);
    public static final Supplier<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = registerLog("stripped_maple_wood", MaterialColor.SAND);
    public static final Supplier<LeavesBlock> MAPLE_LEAVES = register("maple_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_RED).strength(.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, reader, blockPos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT).isSuffocating((state, reader, blockPos) -> false).isViewBlocking((state, reader, blockPos) -> false)), prop -> prop);
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

    public static <T extends Block> Supplier<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
    {
        return registerWithItem(id, block, (object, prop) -> new BlockItem(object.get(), properties.apply(prop)));
    }

    private static <T extends Block> Supplier<T> registerWithItem(String id, Supplier<T> block, @Nullable BiFunction<Supplier<T>, Item.Properties, Item> item)
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
