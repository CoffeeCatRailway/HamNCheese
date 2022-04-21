package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.gson.JsonElement;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedBlockModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedItemModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedModelProvider;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.CheeseBlock;
import io.github.coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;
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

            this.generateFlatItem(HNCItems.WOODEN_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.STONE_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.COPPER_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.GOLDEN_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.IRON_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.DIAMOND_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            this.generateFlatItem(HNCItems.NETHERITE_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

            // Foods
            this.generateFlatItem(HNCItems.CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.BLUE_CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.GOUDA_CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.SWISS_CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);

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

            // Misc
            this.generateFlatItem(HNCItems.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);

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
            this.choppingBoard(HNCBlocks.OAK_CHOPPING_BOARD.get(), Blocks.OAK_PLANKS);
            this.choppingBoard(HNCBlocks.BIRCH_CHOPPING_BOARD.get(), Blocks.BIRCH_PLANKS);
            this.choppingBoard(HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), Blocks.SPRUCE_PLANKS);
            this.choppingBoard(HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), Blocks.JUNGLE_PLANKS);
            this.choppingBoard(HNCBlocks.ACACIA_CHOPPING_BOARD.get(), Blocks.ACACIA_PLANKS);
            this.choppingBoard(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), Blocks.DARK_OAK_PLANKS);
            this.choppingBoard(HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), Blocks.CRIMSON_PLANKS);
            this.choppingBoard(HNCBlocks.WARPED_CHOPPING_BOARD.get(), Blocks.WARPED_PLANKS);
//
            this.choppingBoard(HNCBlocks.STONE_CHOPPING_BOARD.get(), Blocks.STONE);
            this.choppingBoard(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), Blocks.POLISHED_BLACKSTONE);
            this.choppingBoard(HNCBlocks.IRON_CHOPPING_BOARD.get(), Blocks.IRON_BLOCK);
            this.choppingBoard(HNCBlocks.GOLD_CHOPPING_BOARD.get(), Blocks.GOLD_BLOCK);
//
            this.choppingBoard(HNCBlocks.MAPLE_CHOPPING_BOARD.get(), HNCBlocks.MAPLE_PLANKS.get());

            // Misc
            this.blockOfCheese(HNCBlocks.BLOCK_OF_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_BLUE_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get());

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
            this.createPlant(HNCBlocks.MAPLE_SAPLING.get(), HNCBlocks.POTTED_MAPLE_SAPLING.get(), BlockModelGenerators.TintState.NOT_TINTED);

            this.family(HNCBlocks.MAPLE_PLANKS.get())
                    .stairs(HNCBlocks.MAPLE_STAIRS.get())
                    .slab(HNCBlocks.MAPLE_SLAB.get())
                    .sign(HNCBlocks.MAPLE_SIGN.get(), HNCBlocks.MAPLE_WALL_SIGN.get())
                    .pressurePlate(HNCBlocks.MAPLE_PRESSURE_PLATE.get())
                    .button(HNCBlocks.MAPLE_BUTTON.get())
                    .fence(HNCBlocks.MAPLE_FENCE.get())
                    .fenceGate(HNCBlocks.MAPLE_FENCE_GATE.get());
            this.createOrientableTrapdoor(HNCBlocks.MAPLE_TRAPDOOR.get());
            this.createDoor(HNCBlocks.MAPLE_DOOR.get());

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

//            ModelFile mapleSapModel = this.models().getBuilder("block/maple_sap").texture("particle", HamNCheese.getLocation("block/maple_sap_still"));
//            this.getVariantBuilder(HNCBlocks.MAPLE_SAP.get()).partialState().modelForState().modelFile(mapleSapModel).addModel();
        }

        private void blockOfCheese(CheeseBlock block)
        {
            this.createSimpleFlatItemModel(block.asItem());
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(block)
                    .with(PropertyDispatch.property(CheeseBlock.BITES)
                            .select(0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block)))
                            .select(1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_slice1")))
                            .select(2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_slice2")))
                            .select(3, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_slice3")))));
        }

        private static final ModelTemplate CHOPPING_BOARD = new ModelTemplate(Optional.of(HamNCheese.getLocation("block/chopping_board")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

        private void choppingBoard(ChoppingBoardBlock boardBlock, Block planksBlock)
        {
            TextureMapping textureMapping = TextureMapping.defaultTexture(TextureMapping.getBlockTexture(planksBlock));
            ResourceLocation modelLocation = CHOPPING_BOARD.create(boardBlock, textureMapping, this.getModelOutput());

            PropertyDispatch.C1<Direction> propertyDispatch = PropertyDispatch.property(ChoppingBoardBlock.FACING);
            for (Direction direction : Direction.Plane.HORIZONTAL)
                propertyDispatch = propertyDispatch.select(direction, Variant.variant().with(VariantProperties.MODEL, modelLocation).with(VariantProperties.Y_ROT, this.yRotationFromDirection(direction)));

            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(boardBlock).with(propertyDispatch));
            this.delegateItemModel(boardBlock, modelLocation);
        }

        private VariantProperties.Rotation yRotationFromDirection(Direction direction)
        {
            return switch (direction)
                    {
                        default -> VariantProperties.Rotation.R0;
                        case SOUTH -> VariantProperties.Rotation.R180;
                        case WEST -> VariantProperties.Rotation.R270;
                        case EAST -> VariantProperties.Rotation.R90;
                    };
        }

        private void createWoodVariant(Block block, ResourceLocation texture)
        {
            ResourceLocation resourceLocation = TexturedModel.CUBE.get(block).updateTextures(mapping -> mapping.put(TextureSlot.ALL, texture)).create(block, this.getModelOutput());
            this.getBlockStateOutput().accept(createRotatedPillarWithHorizontalVariant(block, resourceLocation, resourceLocation));
        }
    }
}
