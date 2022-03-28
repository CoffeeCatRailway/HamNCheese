package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.gson.JsonElement;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedBlockModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedItemModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedModelProvider;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCModels extends PollinatedModelProvider
{
    public HNCModels(DataGenerator generator, PollinatedModContainer container)
    {
        super(generator, container);
        this.addGenerator((blockStateOutput, modelOutput, skippedAutoModelsOutput) -> new ItemModelGenerator(modelOutput));
    }

    private static class ItemModelGenerator extends PollinatedItemModelGenerator
    {
        public ItemModelGenerator(BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
        {
            super(modelOutput);
        }

        @Override
        public void run()
        {
//            // Crafting Ingredients
//            this.generateFlatItem(HNCItems.WOODEN_GEAR.get(), ModelTemplates.FLAT_ITEM);
//
//            // Tools
//            this.generateFlatItem(HNCItems.CURDLER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
//            this.generateFlatItem(HNCItems.ROLLING_PIN.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
//            this.generateFlatItem(HNCItems.GRIND_STONES.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
//            this.generateFlatItem(HNCItems.KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
//
//            // Foods
//            this.generateFlatItem(HNCBlocks.BLOCK_OF_CHEESE.get().asItem(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.ROCK_SALT.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.FLOUR.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.DOUGH.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.UNBAKED_PIZZA_BASE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.BAKED_PIZZA_DUMMY.get(), HNCMod.getLocation("item/pizza_base"), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.UNBAKED_BREAD.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.BREAD_SLICE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.TOAST.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.UNBAKED_CRACKER.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.CRACKER_DUMMY.get(), HNCMod.getLocation("item/cracker"), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.CRACKED_EGG.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.COOKED_EGG.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.GREEN_EGG.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.COOKED_HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.GREEN_HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.BACON.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.COOKED_BACON.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.PINEAPPLE_PLANT.get(), HNCMod.getLocation("block/pineapple_stage_0"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.PINEAPPLE.get(), HNCMod.getLocation("block/pineapple_stage_4"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.PINEAPPLE_RING.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.PINEAPPLE_BIT.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.TOMATO_SEEDS.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.TOMATO.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.TOMATO_SAUCE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.TOMATO_SLICE.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.CORN_COB.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.CORN_KERNELS.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.DRIED_CORN_KERNELS.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.POPCORN.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.CHEESY_POPCORN.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.CARAMEL_POPCORN.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.MAPLE_POPCORN.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.POPCORN_BAG.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.MOUSE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.COOKED_MOUSE.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.FOOD_SCRAPS.get(), ModelTemplates.FLAT_ITEM);
//
//            // Misc
//            this.generateFlatItem(HNCBlocks.MAPLE_SAPLING.get().asItem(), HNCMod.getLocation("block/maple_sapling"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCBlocks.MAPLE_SIGN.get().asItem(), HNCMod.getLocation("item/maple_sign"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCBlocks.MAPLE_DOOR.get().asItem(), HNCMod.getLocation("item/maple_door"), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);
//
//            this.generateFlatItem(HNCItems.MAPLE_SAP_BUCKET.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.MAPLE_SAP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCItems.MAPLE_SYRUP.get(), ModelTemplates.FLAT_ITEM);
        }

        private void generateFlatItem(Item item, ResourceLocation texture, ModelTemplate modelTemplate)
        {
            modelTemplate.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(texture), this.getModelOutput());
        }
    }
}
