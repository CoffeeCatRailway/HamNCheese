package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.*;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 7/04/2021
 */
public class HNCBlockStates extends BlockStateProvider
{
    public HNCBlockStates(DataGenerator gen, ExistingFileHelper existingFileHelper)
    {
        super(gen, HNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        int i;
        VariantBlockStateBuilder.PartialBlockstate pineapplePlant = this.getVariantBuilder(HNCBlocks.PINEAPPLE_PLANT.get()).partialState();
        for (i = 0; i < 5; i++)
        {
            pineapplePlant.with(PineapplePlantBlock.AGE, i).with(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_bottom_stage_" + i, HNCMod.getLocation("block/pineapple_plant_bottom"))
                            .texture("plant", HNCMod.getLocation("block/pineapple_plant_stage_" + i))).addModel();

            pineapplePlant.with(PineapplePlantBlock.AGE, i).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_stage_" + i, HNCMod.getLocation("block/pineapple_plant_top"))
                            .texture("pineapple", HNCMod.getLocation("block/pineapple_stage_" + i))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate tomatoPlant = this.getVariantBuilder(HNCBlocks.TOMATO_PLANT.get()).partialState();
        for (i = 0; i < 10; i++)
        {
            tomatoPlant.with(TomatoPlantBlock.AGE, i).with(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_bottom_stage_" + i, HNCMod.getLocation("block/tomato_plant_bottom_stage_" + i))).addModel();

            tomatoPlant.with(TomatoPlantBlock.AGE, i).with(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_top_stage_" + i, HNCMod.getLocation("block/tomato_plant_top_stage_" + i))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate cornPlant = this.getVariantBuilder(HNCBlocks.CORN_PLANT.get()).partialState();
        ModelFile emptyCorn = this.emptyBlockModelWithParticle("corn_plant_top_stage_0", HNCMod.getLocation("block/corn_plant_bottom_stage_0"));
        for (i = 0; i < 7; i++)
        {
            cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().cross("block/corn_plant_bottom_stage_" + i, HNCMod.getLocation("block/corn_plant_bottom_stage_" + i))).addModel();

            if (i > 2)
                cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER)
                        .modelForState().modelFile(this.models().cross("block/corn_plant_top_stage_" + i, HNCMod.getLocation("block/corn_plant_top_stage_" + i))).addModel();
            else
                cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(emptyCorn).addModel();
        }

        // Horizontal Blocks - Initial
        VariantBlockStateBuilder.PartialBlockstate oven = this.getVariantBuilder(HNCBlocks.PIZZA_OVEN.get()).partialState();
        ModelFile ovenModel = this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"));

        VariantBlockStateBuilder.PartialBlockstate grill = this.getVariantBuilder(HNCBlocks.GRILL.get()).partialState();
        ModelFile grillModel = this.models().getExistingFile(HNCMod.getLocation("block/grill"));

        VariantBlockStateBuilder.PartialBlockstate popcorn = this.getVariantBuilder(HNCBlocks.POPCORN_MACHINE.get()).partialState();
        ModelFile popcornModelFull = this.models().getExistingFile(HNCMod.getLocation("block/popcorn_machine_full"));
        ModelFile popcornModelEmpty = this.models().getExistingFile(HNCMod.getLocation("block/popcorn_machine_empty"));

        VariantBlockStateBuilder.PartialBlockstate choppingBoard = this.getVariantBuilder(HNCBlocks.CHOPPING_BOARD.get()).partialState();
        ModelFile choppingBoardModel = this.models().getExistingFile(HNCMod.getLocation("block/chopping_board"));

        // Add models
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            // Pizza Oven
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(ovenModel).addModel();

            // Grill
            grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, false).with(GrillBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
            grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, false).with(GrillBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
            grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, true).with(GrillBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();
            grill.with(GrillBlock.HORIZONTAL_FACING, direction).with(GrillBlock.WATERLOGGED, true).with(GrillBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(grillModel).addModel();

            // Popcorn Machine
            popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, false).with(PopcornMachineBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelEmpty).addModel();
            popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, false).with(PopcornMachineBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelFull).addModel();
            popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, true).with(PopcornMachineBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelEmpty).addModel();
            popcorn.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).with(PopcornMachineBlock.WATERLOGGED, true).with(PopcornMachineBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(popcornModelFull).addModel();

            // Chopping Board
            choppingBoard.with(PopcornMachineBlock.HORIZONTAL_FACING, direction).modelForState().rotationY((int) direction.toYRot()).modelFile(choppingBoardModel).addModel();
        }

        this.toItem(HNCBlocks.PIZZA_OVEN.get());
        this.toItem(HNCBlocks.GRILL.get());
        this.toItem(HNCBlocks.POPCORN_MACHINE.get(), HNCMod.getLocation("block/popcorn_machine_full"));

        VariantBlockStateBuilder.PartialBlockstate curdler = this.getVariantBuilder(HNCBlocks.CURDLER.get()).partialState();
        curdler.with(CurdlerBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(this.emptyBlockModelWithParticle("cheese_curdler_top", HNCMod.getLocation("block/curdler_side"))).addModel();
        curdler.with(CurdlerBlock.HALF, DoubleBlockHalf.LOWER).modelForState().modelFile(this.models().getExistingFile(HNCMod.getLocation("block/cheese_curdler_basin"))).addModel();
        this.toItem(HNCBlocks.CURDLER.get(), HNCMod.getLocation("block/cheese_curdler_basin"));

        this.choppingBoard("oak", new ResourceLocation("block/oak_planks"));
        this.choppingBoard("birch", new ResourceLocation("block/birch_planks"));
        this.choppingBoard("spruce", new ResourceLocation("block/spruce_planks"));
        this.choppingBoard("jungle", new ResourceLocation("block/jungle_planks"));
        this.choppingBoard("acacia", new ResourceLocation("block/acacia_planks"));
        this.choppingBoard("dark_oak", new ResourceLocation("block/dark_oak_planks"));
        this.choppingBoard("crimson", new ResourceLocation("block/crimson_planks"));
        this.choppingBoard("warped", new ResourceLocation("block/warped_planks"));

        this.choppingBoard("polished_blackstone", new ResourceLocation("block/polished_blackstone"));
        this.choppingBoard("iron", new ResourceLocation("block/iron_block"));
        this.choppingBoard("gold", new ResourceLocation("block/gold_block"));

        this.choppingBoard("maple", HNCMod.getLocation("block/maple_planks"));

        this.choppingBoard("fir", new ResourceLocation("biomesoplenty", "block/fir_planks"));
        this.choppingBoard("redwood", new ResourceLocation("biomesoplenty", "block/redwood_planks"));
        this.choppingBoard("cherry", new ResourceLocation("biomesoplenty", "block/cherry_planks"));
        this.choppingBoard("mahogany", new ResourceLocation("biomesoplenty", "block/mahogany_planks"));
        this.choppingBoard("jacaranda", new ResourceLocation("biomesoplenty", "block/jacaranda_planks"));
        this.choppingBoard("palm", new ResourceLocation("biomesoplenty", "block/palm_planks"));
        this.choppingBoard("willow", new ResourceLocation("biomesoplenty", "block/willow_planks"));
        this.choppingBoard("dead", new ResourceLocation("biomesoplenty", "block/dead_planks"));
        this.choppingBoard("magic", new ResourceLocation("biomesoplenty", "block/magic_planks"));
        this.choppingBoard("umbran", new ResourceLocation("biomesoplenty", "block/umbran_planks"));
        this.choppingBoard("hellbark", new ResourceLocation("biomesoplenty", "block/hellbark_planks"));

        this.choppingBoard("twilight_oak", new ResourceLocation("twilightforest", "block/wood/planks_twilight_oak_0"));
        this.choppingBoard("canopy", new ResourceLocation("twilightforest", "block/wood/planks_canopy_0"));
        this.choppingBoard("mangrove", new ResourceLocation("twilightforest", "block/wood/planks_mangrove_0"));
        this.choppingBoard("dark", new ResourceLocation("twilightforest", "block/wood/planks_darkwood_0"));
        this.choppingBoard("time", new ResourceLocation("twilightforest", "block/wood/planks_time_0"));
        this.choppingBoard("trans", new ResourceLocation("twilightforest", "block/wood/planks_trans_0"));
        this.choppingBoard("mine", new ResourceLocation("twilightforest", "block/wood/planks_mine_0"));
        this.choppingBoard("sort", new ResourceLocation("twilightforest", "block/wood/planks_sort_0"));

        this.choppingBoard("darkwood", new ResourceLocation("druidcraft", "block/darkwood_planks"));
        this.choppingBoard("elder", new ResourceLocation("druidcraft", "block/elder_planks"));

        // Misc
        VariantBlockStateBuilder.PartialBlockstate cheese = this.getVariantBuilder(HNCBlocks.BLOCK_OF_CHEESE.get()).partialState();
        cheese.with(CheeseBlock.BITES, 0).modelForState().modelFile(this.itemModels().getExistingFile(HNCMod.getLocation("block/block_of_cheese"))).addModel();
        for (i = 1; i < 4; i++)
            cheese.with(CheeseBlock.BITES, i).modelForState().modelFile(this.itemModels().getExistingFile(HNCMod.getLocation("block/block_of_cheese_slice" + i))).addModel();

        this.axisBlock(HNCBlocks.MAPLE_LOG.get());
        this.toItem(HNCBlocks.MAPLE_LOG.get());

        this.axisBlock(HNCBlocks.MAPLE_WOOD.get(), HNCMod.getLocation("block/maple_log_side"), HNCMod.getLocation("block/maple_log_side"));
        this.toItem(HNCBlocks.MAPLE_WOOD.get());

        this.axisBlock(HNCBlocks.STRIPPED_MAPLE_LOG.get());
        this.toItem(HNCBlocks.STRIPPED_MAPLE_LOG.get());

        this.axisBlock(HNCBlocks.STRIPPED_MAPLE_WOOD.get(), HNCMod.getLocation("block/stripped_maple_log_side"), HNCMod.getLocation("block/stripped_maple_log_side"));
        this.toItem(HNCBlocks.STRIPPED_MAPLE_WOOD.get());

        VariantBlockStateBuilder.PartialBlockstate leaves = this.getVariantBuilder(HNCBlocks.MAPLE_LEAVES.get()).partialState();
        Function<Integer, ModelFile> leavesFunction = (j) -> this.models().cubeAll("maple_leaves_" + j, HNCMod.getLocation("block/maple_leaves_" + j));
        ConfiguredModel[] leavesModels = new ConfiguredModel[9];
        leavesModels[0] = leaves.modelForState().weight(25).modelFile(leavesFunction.apply(0)).buildLast();
        for (i = 1; i < 9; i++)
            leavesModels[i] = leaves.modelForState().weight((i % 2 == 0) ? 75 : 50).modelFile(leavesFunction.apply(i)).buildLast();
        leaves.addModels(leavesModels);
        this.toItem(HNCBlocks.MAPLE_LEAVES.get(), HNCMod.getLocation("block/maple_leaves_0"));

        this.getVariantBuilder(HNCBlocks.MAPLE_SAPLING.get()).partialState().modelForState().modelFile(this.models().withExistingParent("block/maple_sapling", "block/cross").texture("cross", HNCMod.getLocation("block/maple_sapling"))).addModel();
        this.getVariantBuilder(HNCBlocks.POTTED_MAPLE_SAPLING.get()).partialState().modelForState().modelFile(this.models().withExistingParent("block/potted_maple_sapling", "block/flower_pot_cross").texture("plant", HNCMod.getLocation("block/maple_sapling"))).addModel();

        this.simpleBlock(HNCBlocks.MAPLE_PLANKS.get());
        this.toItem(HNCBlocks.MAPLE_PLANKS.get());

        this.stairsBlock(HNCBlocks.MAPLE_STAIRS.get(), HNCMod.getLocation("block/maple_planks"));
        this.toItem(HNCBlocks.MAPLE_STAIRS.get());

        this.slabBlock(HNCBlocks.MAPLE_SLAB.get(), HNCMod.getLocation("block/maple_planks"), HNCMod.getLocation("block/maple_planks"));
        this.toItem(HNCBlocks.MAPLE_SLAB.get());

        ModelFile signModel = this.models().getBuilder("block/maple_sign").texture("particle", this.blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        this.getVariantBuilder(HNCBlocks.MAPLE_SIGN.get()).partialState().modelForState().modelFile(signModel).addModel();
        this.getVariantBuilder(HNCBlocks.MAPLE_WALL_SIGN.get()).partialState().modelForState().modelFile(signModel).addModel();

        String platePath = HNCBlocks.MAPLE_PRESSURE_PLATE.get().getRegistryName().getPath();
        ModelFile plateUp = this.models().singleTexture(platePath, new ResourceLocation("block/pressure_plate_up"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        ModelFile plateDown = this.models().singleTexture(platePath + "_down", new ResourceLocation("block/pressure_plate_down"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        this.getVariantBuilder(HNCBlocks.MAPLE_PRESSURE_PLATE.get()).partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(plateUp).addModel().partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(plateDown).addModel();
        this.itemModels().getBuilder(platePath).parent(plateUp);

        String buttonPath = HNCBlocks.MAPLE_BUTTON.get().getRegistryName().getPath();
        ModelFile buttonModel = this.models().singleTexture(buttonPath, new ResourceLocation("block/button"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        ModelFile buttonPressedModel = this.models().singleTexture(buttonPath + "_pressed", new ResourceLocation("block/button_pressed"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        ModelFile buttonInventoryModel = this.models().singleTexture(buttonPath + "_inventory", new ResourceLocation("block/button_inventory"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        VariantBlockStateBuilder button = this.getVariantBuilder(HNCBlocks.MAPLE_BUTTON.get());
        for (Direction dir : Direction.Plane.HORIZONTAL)
        {
            for (AttachFace face : AttachFace.values())
            {
                int rotX = 0;
                switch (face)
                {
                    case CEILING:
                        rotX = 180;
                        break;
                    case WALL:
                        rotX = 90;
                        break;
                }
                int rotY = 180;
                switch (dir)
                {
                    case EAST:
                        rotY = 90;
                        break;
                    case WEST:
                        rotY = 270;
                        break;
                    case NORTH:
                        rotY = 0;
                        break;
                }
                button.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
                        .with(WoodButtonBlock.POWERED, true).modelForState().rotationX(rotX).rotationY(rotY).modelFile(buttonPressedModel).addModel();
                button.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
                        .with(WoodButtonBlock.POWERED, false).modelForState().rotationX(rotX).rotationY(rotY).modelFile(buttonModel).addModel();
            }
        }
        this.itemModels().getBuilder(buttonPath).parent(buttonInventoryModel);

        this.fenceBlock(HNCBlocks.MAPLE_FENCE.get(), blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        this.models().withExistingParent("block/maple_fence_inventory", "block/fence_inventory").texture("texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        this.toItem(HNCBlocks.MAPLE_FENCE.get(), HNCMod.getLocation("block/maple_fence_inventory"));

        this.fenceGateBlock(HNCBlocks.MAPLE_FENCE_GATE.get(), blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        this.toItem(HNCBlocks.MAPLE_FENCE_GATE.get(), HNCMod.getLocation("block/maple_fence_gate"));

        this.trapdoorBlock(HNCBlocks.MAPLE_TRAPDOOR.get(), HNCMod.getLocation("block/maple_trapdoor"), true);
        this.toItem(HNCBlocks.MAPLE_TRAPDOOR.get(), HNCMod.getLocation("block/maple_trapdoor_bottom"));

        this.doorBlock(HNCBlocks.MAPLE_DOOR.get(), HNCMod.getLocation("block/maple_door_bottom"), HNCMod.getLocation("block/maple_door_top"));

        VariantBlockStateBuilder.PartialBlockstate tap = this.getVariantBuilder(HNCBlocks.TREE_TAP.get()).partialState();
        ModelFile tapModel = this.models().getExistingFile(HNCMod.getLocation("block/tree_tap"));
        ModelFile[] tapModelLevels = new ModelFile[4];
        for (i = 0; i < 4; i++)
            tapModelLevels[i] = this.models().getExistingFile(HNCMod.getLocation("block/tree_tap_level_" + i));
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            for (i = 0; i < 4; i++)
            {
                tap.with(TreeTapBlock.HORIZONTAL_FACING, direction).with(TreeTapBlock.SAP_LEVEL, i).with(TreeTapBlock.BUCKET, false)
                        .modelForState().rotationY((int) direction.getOpposite().toYRot()).modelFile(tapModel).addModel();
                tap.with(TreeTapBlock.HORIZONTAL_FACING, direction).with(TreeTapBlock.SAP_LEVEL, i).with(TreeTapBlock.BUCKET, true)
                        .modelForState().rotationY((int) direction.getOpposite().toYRot()).modelFile(tapModelLevels[i]).addModel();
            }
        }
        this.toItem(HNCBlocks.TREE_TAP.get(), HNCMod.getLocation("block/tree_tap_level_3"));

        ModelFile mapleSapModel = this.models().getBuilder("block/maple_sap").texture("particle", HNCMod.getLocation("block/maple_sap_still"));
        this.getVariantBuilder(HNCBlocks.MAPLE_SAP.get()).partialState().modelForState().modelFile(mapleSapModel).addModel();
    }

    private BlockModelBuilder emptyBlockModelWithParticle(String name, ResourceLocation particle)
    {
        return this.models().withExistingParent("block/" + name, new ResourceLocation("block/air")).texture("particle", particle);
    }

    private void choppingBoard(String name, ResourceLocation texture)
    {
        this.models().withExistingParent("chopping_board/" + name, HNCMod.getLocation("chopping_board/stone")).texture("particle", texture);
    }

    private void toItem(Block block)
    {
        this.toItem(block, HNCMod.getLocation("block/" + block.getRegistryName().getPath()));
    }

    private void toItem(Block block, ResourceLocation model)
    {
        String path = block.getRegistryName().getPath();
        this.itemModels().withExistingParent(path, model);
    }
}
