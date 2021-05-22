package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import coffeecatrailway.hamncheese.common.block.TomatoPlantBlock;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
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

    public static final RegistryObject<PineapplePlantBlock> PINEAPPLE_PLANT = registerPlant("pineapple_plant", PineapplePlantBlock::new);
    public static final RegistryObject<TomatoPlantBlock> TOMATO_PLANT = registerPlant("tomato_plant", TomatoPlantBlock::new);

    public static final RegistryObject<ChoppingBoardBlock> OAK_CHOPPING_BOARD = register("oak_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> BIRCH_CHOPPING_BOARD = register("birch_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> SPRUCE_CHOPPING_BOARD = register("spruce_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> JUNGLE_CHOPPING_BOARD = register("jungle_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> ACACIA_CHOPPING_BOARD = register("acacia_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> DARK_OAK_CHOPPING_BOARD = register("dark_oak_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> CRIMSON_CHOPPING_BOARD = register("crimson_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> WARPED_CHOPPING_BOARD = register("warped_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)), prop -> prop);

    public static final RegistryObject<ChoppingBoardBlock> STONE_CHOPPING_BOARD = register("stone_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.STONE)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> POLISHED_BLACKSTONE_CHOPPING_BOARD = register("polished_blackstone_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.POLISHED_BLACKSTONE)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> GOLD_CHOPPING_BOARD = register("gold_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.GOLD_BLOCK)), prop -> prop);
    public static final RegistryObject<ChoppingBoardBlock> IRON_CHOPPING_BOARD = register("iron_chopping_board", () -> new ChoppingBoardBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)), prop -> prop);

    public static final RegistryObject<PizzaOvenBlock> PIZZA_OVEN = register("pizza_oven", () -> new PizzaOvenBlock(AbstractBlock.Properties.copy(Blocks.WHITE_TERRACOTTA)), prop -> prop);

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> block, Function<Item.Properties, Item.Properties> properties)
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
