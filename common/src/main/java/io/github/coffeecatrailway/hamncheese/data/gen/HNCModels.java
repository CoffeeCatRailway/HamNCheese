package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.gson.JsonElement;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedBlockModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedItemModelGenerator;
import gg.moonflower.pollen.api.datagen.provider.model.PollinatedModelProvider;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.*;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
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
            this.generateFlatItem(HNCItems.SWISS_CHEESE_BITS.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.GOAT_CHEESE_SLICE.get(), ModelTemplates.FLAT_ITEM);

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

            this.generateFlatItem(HNCItems.PINEAPPLE_PLANT.get(), HamNCheese.getLocation("block/pineapple_stage_0"), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE.get(), HamNCheese.getLocation("block/pineapple_stage_4"), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE_RING.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.PINEAPPLE_BIT.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.TOMATO_SEEDS.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO_SAUCE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.TOMATO_SLICE.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCItems.CORN_COB.get(), ModelTemplates.FLAT_ITEM);
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

            this.generateFlatItem(HNCItems.UNBAKED_CROISSANT.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CROISSANT.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CHEESY_CROISSANT.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.CHEESY_HAM_CROISSANT.get(), ModelTemplates.FLAT_ITEM);

            // Misc
            this.generateFlatItem(HNCItems.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCFluids.MAPLE_SAP_BUCKET.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.MAPLE_SAP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(HNCItems.MAPLE_SYRUP.get(), ModelTemplates.FLAT_ITEM);

            this.generateFlatItem(HNCFluids.GOAT_MILK_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        }

        private void generateFlatItem(Item item, ResourceLocation texture, ModelTemplate modelTemplate)
        {
            modelTemplate.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(texture), this.getModelOutput());
        }
    }

    private static class BlockModelGenerator extends PollinatedBlockModelGenerator
    {
        private static final ModelTemplate CHOPPING_BOARD = new ModelTemplate(Optional.of(HamNCheese.getLocation("block/chopping_board")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

        private static final ModelTemplate PINEAPPLE_BOTTOM = new ModelTemplate(Optional.of(HamNCheese.getLocation("block/pineapple_plant_bottom")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
        private static final ModelTemplate PINEAPPLE_TOP = new ModelTemplate(Optional.of(HamNCheese.getLocation("block/pineapple_plant_top")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

        public BlockModelGenerator(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, Consumer<Item> skippedAutoModelsOutput)
        {
            super(blockStateOutput, modelOutput, skippedAutoModelsOutput);
        }

        @Override
        public void run()
        {
            int i;
            Function<Integer, TextureMapping> plantBottomTexture = stage -> TextureMapping.defaultTexture(HamNCheese.getLocation("block/pineapple_plant_stage_" + stage));
            Function<Integer, TextureMapping> plantTopTexture = stage -> TextureMapping.defaultTexture(HamNCheese.getLocation("block/pineapple_stage_" + stage));
            PropertyDispatch.C2<DoubleBlockHalf, Integer> plantPropertyDispatch = PropertyDispatch.properties(PineapplePlantBlock.HALF, PineapplePlantBlock.AGE);
            for (i = 0; i < 5; i++)
            {
                plantPropertyDispatch = plantPropertyDispatch.select(DoubleBlockHalf.LOWER, i, Variant.variant().with(VariantProperties.MODEL, PINEAPPLE_BOTTOM.createWithOverride(HNCBlocks.PINEAPPLE_PLANT.get(), "pineapple_plant_stage_" + i, plantBottomTexture.apply(i), this.getModelOutput())))
                        .select(DoubleBlockHalf.UPPER, i, Variant.variant().with(VariantProperties.MODEL, PINEAPPLE_TOP.createWithOverride(HNCBlocks.PINEAPPLE_PLANT.get(), "pineapple_stage_" + i, plantTopTexture.apply(i), this.getModelOutput())));
            }
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.PINEAPPLE_PLANT.get()).with(plantPropertyDispatch));

            plantBottomTexture = stage -> TextureMapping.crop(HamNCheese.getLocation("block/tomato_plant_bottom_stage_" + stage));
            plantTopTexture = stage -> TextureMapping.crop(HamNCheese.getLocation("block/tomato_plant_top_stage_" + stage));
            plantPropertyDispatch = PropertyDispatch.properties(TomatoPlantBlock.HALF, TomatoPlantBlock.AGE);
            for (i = 0; i < 10; i++)
            {
                plantPropertyDispatch = plantPropertyDispatch.select(DoubleBlockHalf.LOWER, i, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROP.createWithOverride(HNCBlocks.TOMATO_PLANT.get(), "block/tomato_plant_bottom_stage_" + i, plantBottomTexture.apply(i), this.getModelOutput())))
                        .select(DoubleBlockHalf.UPPER, i, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROP.createWithOverride(HNCBlocks.TOMATO_PLANT.get(), "block/tomato_plant_top_stage_" + i, plantTopTexture.apply(i), this.getModelOutput())));
            }
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.TOMATO_PLANT.get()).with(plantPropertyDispatch));

            plantBottomTexture = stage -> TextureMapping.cross(HamNCheese.getLocation("block/corn_plant_bottom_stage_" + stage));
            plantTopTexture = stage -> TextureMapping.cross(HamNCheese.getLocation("block/corn_plant_top_stage_" + stage));
            plantPropertyDispatch = PropertyDispatch.properties(CornPlantBlock.HALF, CornPlantBlock.AGE);
            for (i = 0; i < 7; i++)
            {
                plantPropertyDispatch = plantPropertyDispatch.select(DoubleBlockHalf.LOWER, i, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROSS.createWithOverride(HNCBlocks.CORN_PLANT.get(), "block/corn_plant_bottom_stage_" + i, plantBottomTexture.apply(i), this.getModelOutput())));

                if (i > 2)
                    plantPropertyDispatch = plantPropertyDispatch.select(DoubleBlockHalf.UPPER, i, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROSS.createWithOverride(HNCBlocks.CORN_PLANT.get(), "block/corn_plant_top_stage_" + i, plantTopTexture.apply(i), this.getModelOutput())));
                else
                    plantPropertyDispatch = plantPropertyDispatch.select(DoubleBlockHalf.UPPER, i, Variant.variant().with(VariantProperties.MODEL, new ResourceLocation("block/air")));
            }
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.CORN_PLANT.get()).with(plantPropertyDispatch));

            // Horizontal Blocks
            ResourceLocation ovenModel = HamNCheese.getLocation("block/pizza_oven");
            PropertyDispatch.C3<Direction, Boolean, Boolean> ovenPropertyDispatch = PropertyDispatch.properties(PizzaOvenBlock.FACING, PizzaOvenBlock.WATERLOGGED, PizzaOvenBlock.LIT);

            ResourceLocation grillModel = HamNCheese.getLocation("block/grill");
            PropertyDispatch.C3<Direction, Boolean, Boolean> grillPropertyDispatch = PropertyDispatch.properties(GrillBlock.FACING, GrillBlock.WATERLOGGED, GrillBlock.LIT);

            ResourceLocation popcornModelFull = HamNCheese.getLocation("block/popcorn_machine_full");
            ResourceLocation popcornModelEmpty = HamNCheese.getLocation("block/popcorn_machine_empty");
            PropertyDispatch.C3<Direction, Boolean, Boolean> popcornPropertyDispatch = PropertyDispatch.properties(PopcornMachineBlock.FACING, PopcornMachineBlock.WATERLOGGED, PopcornMachineBlock.LIT);

            for (Direction direction : Direction.Plane.HORIZONTAL)
            {
                VariantProperties.Rotation yRot = this.yRotationFromDirection(direction.getOpposite());
                // Pizza Oven
                ovenPropertyDispatch = ovenPropertyDispatch.select(direction, false, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, ovenModel))
                        .select(direction, false, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, ovenModel))
                        .select(direction, true, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, ovenModel))
                        .select(direction, true, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, ovenModel));

                // Grill
                grillPropertyDispatch = grillPropertyDispatch.select(direction, false, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, grillModel))
                        .select(direction, false, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, grillModel))
                        .select(direction, true, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, grillModel))
                        .select(direction, true, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, grillModel));

                // Popcorn Machine
                popcornPropertyDispatch = popcornPropertyDispatch.select(direction, false, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, popcornModelEmpty))
                        .select(direction, false, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, popcornModelFull))
                        .select(direction, true, false, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, popcornModelEmpty))
                        .select(direction, true, true, Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, popcornModelFull));
            }

            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.PIZZA_OVEN.get()).with(ovenPropertyDispatch));
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.GRILL.get()).with(grillPropertyDispatch));
            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.POPCORN_MACHINE.get()).with(popcornPropertyDispatch));
            this.delegateItemModel(HNCBlocks.POPCORN_MACHINE.get(), popcornModelFull);

            this.choppingBoard(HNCBlocks.OAK_CHOPPING_BOARD.get(), Blocks.OAK_PLANKS);
            this.choppingBoard(HNCBlocks.BIRCH_CHOPPING_BOARD.get(), Blocks.BIRCH_PLANKS);
            this.choppingBoard(HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), Blocks.SPRUCE_PLANKS);
            this.choppingBoard(HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), Blocks.JUNGLE_PLANKS);
            this.choppingBoard(HNCBlocks.ACACIA_CHOPPING_BOARD.get(), Blocks.ACACIA_PLANKS);
            this.choppingBoard(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), Blocks.DARK_OAK_PLANKS);
            this.choppingBoard(HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), Blocks.CRIMSON_PLANKS);
            this.choppingBoard(HNCBlocks.WARPED_CHOPPING_BOARD.get(), Blocks.WARPED_PLANKS);

            this.choppingBoard(HNCBlocks.STONE_CHOPPING_BOARD.get(), Blocks.STONE);
            this.choppingBoard(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), Blocks.POLISHED_BLACKSTONE);
            this.choppingBoard(HNCBlocks.IRON_CHOPPING_BOARD.get(), Blocks.IRON_BLOCK);
            this.choppingBoard(HNCBlocks.GOLD_CHOPPING_BOARD.get(), Blocks.GOLD_BLOCK);

            this.choppingBoard(HNCBlocks.MAPLE_CHOPPING_BOARD.get(), HNCBlocks.MAPLE_PLANKS.get());

            // Misc
            this.blockOfCheese(HNCBlocks.BLOCK_OF_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_BLUE_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get());
            this.blockOfCheese(HNCBlocks.BLOCK_OF_GOAT_CHEESE.get());

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
            this.skipAutoItemBlock(HNCBlocks.MAPLE_SAPLING.get());

            this.family(HNCBlocks.MAPLE_PLANKS.get())
                    .stairs(HNCBlocks.MAPLE_STAIRS.get())
                    .slab(HNCBlocks.MAPLE_SLAB.get())
                    .sign(HNCBlocks.MAPLE_SIGN.getFirst().get(), HNCBlocks.MAPLE_SIGN.getSecond().get())
                    .pressurePlate(HNCBlocks.MAPLE_PRESSURE_PLATE.get())
                    .button(HNCBlocks.MAPLE_BUTTON.get())
                    .fence(HNCBlocks.MAPLE_FENCE.get())
                    .fenceGate(HNCBlocks.MAPLE_FENCE_GATE.get());
            this.createOrientableTrapdoor(HNCBlocks.MAPLE_TRAPDOOR.get());
            this.createDoor(HNCBlocks.MAPLE_DOOR.get());

            Function<Integer, ResourceLocation> modelLocation = level -> HamNCheese.getLocation("block/tree_tap_level_" + level);
            ResourceLocation tapModel = HamNCheese.getLocation("block/tree_tap");
            PropertyDispatch.C3<Direction, Integer, Boolean> treeTapDispatch = PropertyDispatch.properties(TreeTapBlock.FACING, TreeTapBlock.SAP_LEVEL, TreeTapBlock.BUCKET);
            for (Direction direction : Direction.Plane.HORIZONTAL)
                for (i = 0; i < 4; i++)
                    treeTapDispatch = treeTapDispatch.select(direction, i, true, Variant.variant().with(VariantProperties.MODEL, modelLocation.apply(i)).with(VariantProperties.Y_ROT, this.yRotationFromDirection(direction)))
                            .select(direction, i, false, Variant.variant().with(VariantProperties.MODEL, tapModel).with(VariantProperties.Y_ROT, this.yRotationFromDirection(direction)));

            this.getBlockStateOutput().accept(MultiVariantGenerator.multiVariant(HNCBlocks.TREE_TAP.get()).with(treeTapDispatch));
            this.delegateItemModel(HNCBlocks.TREE_TAP.get(), modelLocation.apply(3));
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
