package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCItemModels implements IDataProvider
{
    private static final Logger LOGGER = HNCMod.getLogger("Item-Models");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator dataGenerator;
    private BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer;

    public HNCItemModels(DataGenerator dataGenerator)
    {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void run(DirectoryCache cache)
    {
        Path path = this.dataGenerator.getOutputFolder();
        Map<ResourceLocation, Supplier<JsonElement>> map1 = Maps.newHashMap();
        this.consumer = (location, json) ->
        {
            Supplier<JsonElement> supplier = map1.put(location, json);
            if (supplier != null)
            {
                throw new IllegalStateException("Duplicate model definition for " + location);
            }
        };
        this.register();
        this.saveCollection(cache, path, map1, HNCItemModels::createModelPath);
    }

    private void register()
    {
        /*
         * Crafting Ingredients
         */
        this.generateFlatItem(HNCItems.WOODEN_GEAR.get(), StockModelShapes.FLAT_ITEM);

        /*
         * Tools
         */
        this.generateFlatItem(HNCItems.CURDLER.get(), StockModelShapes.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(HNCItems.ROLLING_PIN.get(), StockModelShapes.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(HNCItems.GRIND_STONES.get(), StockModelShapes.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(HNCItems.KNIFE.get(), StockModelShapes.FLAT_HANDHELD_ITEM);

        /*
         * Foods
         */
        this.generateFlatItem(HNCItems.BLOCK_OF_CHEESE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.CHEESE_SLICE.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.ROCK_SALT.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.FLOUR.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.DOUGH.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.UNBAKED_PIZZA_BASE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.BAKED_PIZZA_DUMMY.get(), HNCMod.getLocation("item/pizza_base"), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.UNBAKED_BREAD.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.BREAD_SLICE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.TOAST.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.UNBAKED_CRACKER.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.CRACKER_DUMMY.get(), HNCMod.getLocation("item/cracker"), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.CRACKED_EGG.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.COOKED_EGG.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.GREEN_EGG.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.HAM_SLICE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.COOKED_HAM_SLICE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.GREEN_HAM_SLICE.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.BACON.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.COOKED_BACON.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.PINEAPPLE_PLANT.get(), HNCMod.getLocation("block/pineapple_stage_0"), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.PINEAPPLE.get(), HNCMod.getLocation("block/pineapple_stage_4"), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.PINEAPPLE_RING.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.PINEAPPLE_BIT.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.TOMATO_SEEDS.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.TOMATO.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.TOMATO_SAUCE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.TOMATO_SLICE.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.CORN_COB.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.CORN_KERNELS.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.DRIED_CORN_KERNELS.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.CHEESY_POPCORN.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.POPCORN_BAG.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.MOUSE.get(), StockModelShapes.FLAT_ITEM);
        this.generateFlatItem(HNCItems.COOKED_MOUSE.get(), StockModelShapes.FLAT_ITEM);

        this.generateFlatItem(HNCItems.FOOD_SCRAPS.get(), StockModelShapes.FLAT_ITEM);
    }

    private void generateFlatItem(Item item, ModelsUtil modelsUtil)
    {
        modelsUtil.create(ModelsResourceUtil.getModelLocation(item), ModelTextures.layer0(item), this.consumer);
    }

    private void generateFlatItem(Item item, ResourceLocation texture, ModelsUtil modelsUtil)
    {
        modelsUtil.create(ModelsResourceUtil.getModelLocation(item), ModelTextures.layer0(texture), this.consumer);
    }

    private <T> void saveCollection(DirectoryCache cache, Path dataFolder, Map<T, ? extends Supplier<JsonElement>> models, BiFunction<Path, T, Path> resolver)
    {
        models.forEach((p_240088_3_, p_240088_4_) ->
        {
            Path path = resolver.apply(dataFolder, p_240088_3_);

            try
            {
                IDataProvider.save(GSON, cache, p_240088_4_.get(), path);
            } catch (Exception exception)
            {
                LOGGER.error("Couldn't save {}", path, exception);
            }
        });
    }

    private static Path createModelPath(Path dataFolder, ResourceLocation name)
    {
        return dataFolder.resolve("assets/" + name.getNamespace() + "/models/" + name.getPath() + ".json");
    }

    @Override
    public String getName()
    {
        return HNCMod.MOD_ID + " Block State Definitions";
    }
}
