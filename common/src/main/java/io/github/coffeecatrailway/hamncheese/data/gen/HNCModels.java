package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.gson.JsonElement;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedBlockModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedItemModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedModelProvider;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
        this.addGenerator(BlockModelGenerator::new);
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
            // Crafting Ingredients
            this.generateFlatItem(HNCItems.WOODEN_GEAR.get(), ModelTemplates.FLAT_ITEM);

            // Tools
            this.generateFlatItem(HNCItems.CURDLER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.ROLLING_PIN.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.GRIND_STONES.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

            // Foods
//            this.generateFlatItem(HNCBlocks.BLOCK_OF_CHEESE.get().asItem(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.ROCK_SALT.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.FLOUR.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.DOUGH.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.UNBAKED_PIZZA_BASE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.BAKED_PIZZA_DUMMY.get(), HamNCheese.getLocation("item/pizza_base"), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.UNBAKED_BREAD.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.BREAD_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOAST.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.UNBAKED_CRACKER.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CRACKER_DUMMY.get(), HamNCheese.getLocation("item/cracker"), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.CRACKED_EGG.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.COOKED_EGG.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.GREEN_EGG.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.COOKED_HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.GREEN_HAM_SLICE.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.BACON.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.COOKED_BACON.get(), ModelTemplates.FLAT_ITEM);

//            this.generateFlatItem(HNCItems.PINEAPPLE_PLANT.get(), HamNCheese.getLocation("block/pineapple_stage_0"), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE.get(), HamNCheese.getLocation("block/pineapple_stage_4"), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE_RING.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE_BIT.get(), ModelTemplates.FLAT_ITEM);

//            this.generateFlatItem(HNCItems.TOMATO_SEEDS.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO_SAUCE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO_SLICE.get(), ModelTemplates.FLAT_ITEM);

//            this.generateFlatItem(HNCItems.CORN_COB.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CORN_KERNELS.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.DRIED_CORN_KERNELS.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.POPCORN_BAG.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.POPCORN.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CHEESY_POPCORN.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CARAMEL_POPCORN.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.MAPLE_POPCORN.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.MOUSE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.COOKED_MOUSE.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.FOOD_SCRAPS.get(), ModelTemplates.FLAT_ITEM);

//            // Misc
//            this.generateFlatItem(HNCBlocks.MAPLE_SAPLING.get().asItem(), HamNCheese.getLocation("block/maple_sapling"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCBlocks.MAPLE_SIGN.get().asItem(), HamNCheese.getLocation("item/maple_sign"), ModelTemplates.FLAT_ITEM);
//            this.generateFlatItem(HNCBlocks.MAPLE_DOOR.get().asItem(), HamNCheese.getLocation("item/maple_door"), ModelTemplates.FLAT_ITEM);

//            this.generateFlatItem(HNCItems.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);

//            this.generateFlatItem(HNCItems.MAPLE_SAP_BUCKET.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.MAPLE_SAP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.MAPLE_SYRUP.get(), ModelTemplates.FLAT_ITEM);
        }

        private void generateFlatItem(Item item, ResourceLocation texture, ModelTemplate modelTemplate)
        {
            modelTemplate.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(texture), this.getModelOutput());
        }
    }

    private static class BlockModelGenerator extends PollinatedBlockModelGenerator
    {
        public BlockModelGenerator(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, Consumer<Item> skippedAutoModelsOutput)
        {
            super(blockStateOutput, modelOutput, skippedAutoModelsOutput);
        }

        @Override
        public void run()
        {
            int i;
//            VariantBlockStateBuilder.PartialBlockstate pineapplePlant = this.getVariantBuilder(HNCBlocks.PINEAPPLE_PLANT.get()).partialState();
//            for (i = 0; i < 5; i++)
//            {
//                pineapplePlant.with(PineapplePlantBlock.AGE, i).with(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
//                        .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_bottom_stage_" + i, HamNCheese.getLocation("block/pineapple_plant_bottom"))
//                                .texture("plant", HamNCheese.getLocation("block/pineapple_plant_stage_" + i))).addModel();
//
//                pineapplePlant.with(PineapplePlantBlock.AGE, i).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
//                        .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_stage_" + i, HamNCheese.getLocation("block/pineapple_plant_top"))
//                                .texture("pineapple", HamNCheese.getLocation("block/pineapple_stage_" + i))).addModel();
//            }
//
//            VariantBlockStateBuilder.PartialBlockstate tomatoPlant = this.getVariantBuilder(HNCBlocks.TOMATO_PLANT.get()).partialState();
//            for (i = 0; i < 10; i++)
//            {
//                tomatoPlant.with(TomatoPlantBlock.AGE, i).with(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)
//                        .modelForState().modelFile(this.models().crop("block/tomato_plant_bottom_stage_" + i, HamNCheese.getLocation("block/tomato_plant_bottom_stage_" + i))).addModel();
//
//                tomatoPlant.with(TomatoPlantBlock.AGE, i).with(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER)
//                        .modelForState().modelFile(this.models().crop("block/tomato_plant_top_stage_" + i, HamNCheese.getLocation("block/tomato_plant_top_stage_" + i))).addModel();
//            }
//
//            VariantBlockStateBuilder.PartialBlockstate cornPlant = this.getVariantBuilder(HNCBlocks.CORN_PLANT.get()).partialState();
//            for (i = 0; i < 7; i++)
//            {
//                cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)
//                        .modelForState().modelFile(this.models().cross("block/corn_plant_bottom_stage_" + i, HamNCheese.getLocation("block/corn_plant_bottom_stage_" + i))).addModel();
//
//                if (i > 2)
//                    cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER)
//                            .modelForState().modelFile(this.models().cross("block/corn_plant_top_stage_" + i, HamNCheese.getLocation("block/corn_plant_top_stage_" + i))).addModel();
//                else
//                    cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(this.models().getExistingFile(new ResourceLocation("block/air"))).addModel();
//            }
//
//            // Horizontal Blocks - Initial
//            VariantBlockStateBuilder.PartialBlockstate oven = this.getVariantBuilder(HNCBlocks.PIZZA_OVEN.get()).partialState();
//            ModelFile ovenModel = this.models().getExistingFile(HamNCheese.getLocation("block/pizza_oven"));
//
//            VariantBlockStateBuilder.PartialBlockstate grill = this.getVariantBuilder(HNCBlocks.GRILL.get()).partialState();
//            ModelFile grillModel = this.models().getExistingFile(HamNCheese.getLocation("block/grill"));
//
//            VariantBlockStateBuilder.PartialBlockstate popcorn = this.getVariantBuilder(HNCBlocks.POPCORN_MACHINE.get()).partialState();
//            ModelFile popcornModelFull = this.models().getExistingFile(HamNCheese.getLocation("block/popcorn_machine_full"));
//            ModelFile popcornModelEmpty = this.models().getExistingFile(HamNCheese.getLocation("block/popcorn_machine_empty"));
//
//            // Add models
//            for (Direction direction : Direction.Plane.HORIZONTAL)
//            {
//                // Pizza Oven
//                oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
//                oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
//                oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
//                oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
//
//                // Grill
//                grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, false).with(GrillBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
//                grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, false).with(GrillBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
//                grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, true).with(GrillBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
//                grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, true).with(GrillBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
//
//                // Popcorn Machine
//                popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, false).with(PopcornMachineBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelEmpty).addModel();
//                popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, false).with(PopcornMachineBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelFull).addModel();
//                popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, true).with(PopcornMachineBlock.LIT, false)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelEmpty).addModel();
//                popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, true).with(PopcornMachineBlock.LIT, true)
//                        .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelFull).addModel();
//            }
//
//            this.toItem(HNCBlocks.PIZZA_OVEN.get());
//            this.toItem(HNCBlocks.GRILL.get());
//            this.toItem(HNCBlocks.POPCORN_MACHINE.get(), HamNCheese.getLocation("block/popcorn_machine_full"));
//
//            this.choppingBoard("oak", new ResourceLocation("block/oak_planks"), HNCBlocks.OAK_CHOPPING_BOARD.get());
//            this.choppingBoard("birch", new ResourceLocation("block/birch_planks"), HNCBlocks.BIRCH_CHOPPING_BOARD.get());
//            this.choppingBoard("spruce", new ResourceLocation("block/spruce_planks"), HNCBlocks.SPRUCE_CHOPPING_BOARD.get());
//            this.choppingBoard("jungle", new ResourceLocation("block/jungle_planks"), HNCBlocks.JUNGLE_CHOPPING_BOARD.get());
//            this.choppingBoard("acacia", new ResourceLocation("block/acacia_planks"), HNCBlocks.ACACIA_CHOPPING_BOARD.get());
//            this.choppingBoard("dark_oak", new ResourceLocation("block/dark_oak_planks"), HNCBlocks.DARK_OAK_CHOPPING_BOARD.get());
//            this.choppingBoard("crimson", new ResourceLocation("block/crimson_planks"), HNCBlocks.CRIMSON_CHOPPING_BOARD.get());
//            this.choppingBoard("warped", new ResourceLocation("block/warped_planks"), HNCBlocks.WARPED_CHOPPING_BOARD.get());
//
//            this.choppingBoard("stone", new ResourceLocation("block/stone"), HNCBlocks.STONE_CHOPPING_BOARD.get());
//            this.choppingBoard("polished_blackstone", new ResourceLocation("block/polished_blackstone"), HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get());
//            this.choppingBoard("iron", new ResourceLocation("block/iron_block"), HNCBlocks.IRON_CHOPPING_BOARD.get());
//            this.choppingBoard("gold", new ResourceLocation("block/gold_block"), HNCBlocks.GOLD_CHOPPING_BOARD.get());
//
//            this.choppingBoard("maple", HamNCheese.getLocation("block/maple_planks"), HNCBlocks.MAPLE_CHOPPING_BOARD.get());

            // Misc
//            VariantBlockStateBuilder.PartialBlockstate cheese = this.getVariantBuilder(HNCBlocks.BLOCK_OF_CHEESE.get()).partialState();
//            cheese.with(CheeseBlock.BITES, 0).modelForState().modelFile(this.itemModels().getExistingFile(HamNCheese.getLocation("block/block_of_cheese"))).addModel();
//            for (i = 1; i < 4; i++)
//                cheese.with(CheeseBlock.BITES, i).modelForState().modelFile(this.itemModels().getExistingFile(HamNCheese.getLocation("block/block_of_cheese_slice" + i))).addModel();

            this.createRotatedPillarWithHorizontalVariant(HNCBlocks.MAPLE_LOG.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
            this.createWoodVariant(HNCBlocks.MAPLE_WOOD.get(), HamNCheese.getLocation("block/maple_log_side"));
            this.createRotatedPillarWithHorizontalVariant(HNCBlocks.STRIPPED_MAPLE_LOG.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
            this.createWoodVariant(HNCBlocks.STRIPPED_MAPLE_WOOD.get(), HamNCheese.getLocation("block/stripped_maple_log_side"));

            Variant[] leaves = new Variant[9];
            for (i = 0; i < 9; i++)
            {
                int j = i;
                leaves[i] = Variant.variant().with(VariantProperties.WEIGHT, (i % 2 == 0) ? 75 : 50).with(VariantProperties.MODEL, TexturedModel.LEAVES.get(HNCBlocks.MAPLE_LEAVES.get()).updateTextures(mapping -> mapping.put(TextureSlot.ALL, HamNCheese.getLocation("block/maple_leaves_" + j))).createWithSuffix(HNCBlocks.MAPLE_LEAVES.get(), "_" + i, this.getModelOutput()));
            }
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.MAPLE_LEAVES.get(), leaves));
            this.delegateItemModel(HNCBlocks.MAPLE_LEAVES.get(), HamNCheese.getLocation("block/maple_leaves_0"));

//            this.getVariantBuilder(HNCBlocks.MAPLE_SAPLING.get()).partialState().modelForState().modelFile(this.models().withExistingParent("block/maple_sapling", "block/cross").texture("cross", HamNCheese.getLocation("block/maple_sapling"))).addModel();
//            this.getVariantBuilder(HNCBlocks.POTTED_MAPLE_SAPLING.get()).partialState().modelForState().modelFile(this.models().withExistingParent("block/potted_maple_sapling", "block/flower_pot_cross").texture("plant", HamNCheese.getLocation("block/maple_sapling"))).addModel();
//
//            this.simpleBlock(HNCBlocks.MAPLE_PLANKS.get());
//            this.toItem(HNCBlocks.MAPLE_PLANKS.get());
//
//            this.stairsBlock(HNCBlocks.MAPLE_STAIRS.get(), HamNCheese.getLocation("block/maple_planks"));
//            this.toItem(HNCBlocks.MAPLE_STAIRS.get());
//
//            this.slabBlock(HNCBlocks.MAPLE_SLAB.get(), HamNCheese.getLocation("block/maple_planks"), HamNCheese.getLocation("block/maple_planks"));
//            this.toItem(HNCBlocks.MAPLE_SLAB.get());
//
//            ModelFile signModel = this.models().getBuilder("block/maple_sign").texture("particle", this.blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            this.getVariantBuilder(HNCBlocks.MAPLE_SIGN.get()).partialState().modelForState().modelFile(signModel).addModel();
//            this.getVariantBuilder(HNCBlocks.MAPLE_WALL_SIGN.get()).partialState().modelForState().modelFile(signModel).addModel();
//
//            String platePath = HNCBlocks.MAPLE_PRESSURE_PLATE.get().getRegistryName().getPath();
//            ModelFile plateUp = this.models().singleTexture(platePath, new ResourceLocation("block/pressure_plate_up"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            ModelFile plateDown = this.models().singleTexture(platePath + "_down", new ResourceLocation("block/pressure_plate_down"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            this.getVariantBuilder(HNCBlocks.MAPLE_PRESSURE_PLATE.get()).partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(plateUp).addModel().partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(plateDown).addModel();
//            this.itemModels().getBuilder(platePath).parent(plateUp);
//
//            String buttonPath = HNCBlocks.MAPLE_BUTTON.get().getRegistryName().getPath();
//            ModelFile buttonModel = this.models().singleTexture(buttonPath, new ResourceLocation("block/button"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            ModelFile buttonPressedModel = this.models().singleTexture(buttonPath + "_pressed", new ResourceLocation("block/button_pressed"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            ModelFile buttonInventoryModel = this.models().singleTexture(buttonPath + "_inventory", new ResourceLocation("block/button_inventory"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            VariantBlockStateBuilder button = this.getVariantBuilder(HNCBlocks.MAPLE_BUTTON.get());
//            for (Direction dir : Direction.Plane.HORIZONTAL)
//            {
//                for (AttachFace face : AttachFace.values())
//                {
//                    int rotX = 0;
//                    switch (face)
//                    {
//                        case CEILING:
//                            rotX = 180;
//                            break;
//                        case WALL:
//                            rotX = 90;
//                            break;
//                    }
//                    int rotY = 180;
//                    switch (dir)
//                    {
//                        case EAST:
//                            rotY = 90;
//                            break;
//                        case WEST:
//                            rotY = 270;
//                            break;
//                        case NORTH:
//                            rotY = 0;
//                            break;
//                    }
//                    button.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
//                            .with(WoodButtonBlock.POWERED, true).modelForState().rotationX(rotX).rotationY(rotY).modelFile(buttonPressedModel).addModel();
//                    button.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
//                            .with(WoodButtonBlock.POWERED, false).modelForState().rotationX(rotX).rotationY(rotY).modelFile(buttonModel).addModel();
//                }
//            }
//            this.itemModels().getBuilder(buttonPath).parent(buttonInventoryModel);
//
//            this.fenceBlock(HNCBlocks.MAPLE_FENCE.get(), blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            this.models().withExistingParent("block/maple_fence_inventory", "block/fence_inventory").texture("texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            this.toItem(HNCBlocks.MAPLE_FENCE.get(), HamNCheese.getLocation("block/maple_fence_inventory"));
//
//            this.fenceGateBlock(HNCBlocks.MAPLE_FENCE_GATE.get(), blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
//            this.toItem(HNCBlocks.MAPLE_FENCE_GATE.get(), HamNCheese.getLocation("block/maple_fence_gate"));
//
//            this.trapdoorBlock(HNCBlocks.MAPLE_TRAPDOOR.get(), HamNCheese.getLocation("block/maple_trapdoor"), true);
//            this.toItem(HNCBlocks.MAPLE_TRAPDOOR.get(), HamNCheese.getLocation("block/maple_trapdoor_bottom"));
//
//            this.doorBlock(HNCBlocks.MAPLE_DOOR.get(), HamNCheese.getLocation("block/maple_door_bottom"), HamNCheese.getLocation("block/maple_door_top"));
//
//            VariantBlockStateBuilder.PartialBlockstate tap = this.getVariantBuilder(HNCBlocks.TREE_TAP.get()).partialState();
//            ModelFile tapModel = this.models().getExistingFile(HamNCheese.getLocation("block/tree_tap"));
//            ModelFile[] tapModelLevels = new ModelFile[4];
//            for (i = 0; i < 4; i++)
//                tapModelLevels[i] = this.models().getExistingFile(HamNCheese.getLocation("block/tree_tap_level_" + i));
//            for (Direction direction : Direction.Plane.HORIZONTAL)
//            {
//                for (i = 0; i < 4; i++)
//                {
//                    tap.with(TreeTapBlock.HORIZONTAL_FACING, direction).with(TreeTapBlock.SAP_LEVEL, i).with(TreeTapBlock.BUCKET, false)
//                            .modelForState().rotationY((int) direction.getOpposite().toYRot()).modelFile(tapModel).addModel();
//                    tap.with(TreeTapBlock.HORIZONTAL_FACING, direction).with(TreeTapBlock.SAP_LEVEL, i).with(TreeTapBlock.BUCKET, true)
//                            .modelForState().rotationY((int) direction.getOpposite().toYRot()).modelFile(tapModelLevels[i]).addModel();
//                }
//            }
//            this.toItem(HNCBlocks.TREE_TAP.get(), HamNCheese.getLocation("block/tree_tap_level_3"));
//
//            ModelFile mapleSapModel = this.models().getBuilder("block/maple_sap").texture("particle", HamNCheese.getLocation("block/maple_sap_still"));
//            this.getVariantBuilder(HNCBlocks.MAPLE_SAP.get()).partialState().modelForState().modelFile(mapleSapModel).addModel();
        }

//        private void choppingBoard(String name, ResourceLocation texture, ChoppingBoardBlock board)
//        {
//            VariantBlockStateBuilder.PartialBlockstate choppingBoard = this.getVariantBuilder(board).partialState();
//            ModelFile choppingBoardModel = this.models().withExistingParent("block/" + name + "_chopping_board", HamNCheese.getLocation("block/chopping_board")).texture("board", texture);
//
//            // Add models
//            for (Direction direction : Direction.Plane.HORIZONTAL)
//                choppingBoard.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).modelForState().rotationY((int) direction.toYRot()).modelFile(choppingBoardModel).addModel();
//
//            this.toItem(board);
//        }

        private void createWoodVariant(Block block, ResourceLocation texture)
        {
            ResourceLocation resourceLocation = TexturedModel.CUBE.get(block).updateTextures(mapping -> mapping.put(TextureSlot.ALL, texture)).create(block, this.getModelOutput());
            this.getBlockStateOutput().accept(createRotatedPillarWithHorizontalVariant(block, resourceLocation, resourceLocation));
        }
    }
}
