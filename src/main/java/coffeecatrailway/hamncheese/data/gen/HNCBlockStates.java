package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.*;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.Block;
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
        for (i = 0; i < 7; i++)
        {
            cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().cross("block/corn_plant_bottom_stage_" + i, HNCMod.getLocation("block/corn_plant_bottom_stage_" + i))).addModel();

            if (i > 2)
                cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER)
                        .modelForState().modelFile(this.models().cross("block/corn_plant_top_stage_" + i, HNCMod.getLocation("block/corn_plant_top_stage_" + i))).addModel();
            else
                cornPlant.with(CornPlantBlock.AGE, i).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(this.models().getExistingFile(new ResourceLocation("block/air"))).addModel();
        }

        this.choppingBoard(HNCBlocks.OAK_CHOPPING_BOARD.get(), "oak", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.BIRCH_CHOPPING_BOARD.get(), "birch", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), "spruce", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), "jungle", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.ACACIA_CHOPPING_BOARD.get(), "acacia", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), "dark_oak", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), "crimson", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.WARPED_CHOPPING_BOARD.get(), "warped", ChoppingBoardType.PLANKS);

        this.choppingBoard(HNCBlocks.STONE_CHOPPING_BOARD.get(), "stone", ChoppingBoardType.EMPTY);
        this.choppingBoard(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), "polished_blackstone", ChoppingBoardType.EMPTY);
        this.choppingBoard(HNCBlocks.GOLD_CHOPPING_BOARD.get(), "gold", ChoppingBoardType.BLOCK);
        this.choppingBoard(HNCBlocks.IRON_CHOPPING_BOARD.get(), "iron", ChoppingBoardType.BLOCK);

        this.choppingBoard(HNCBlocks.FIR_CHOPPING_BOARD.get(), "fir", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.REDWOOD_CHOPPING_BOARD.get(), "redwood", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.CHERRY_CHOPPING_BOARD.get(), "cherry", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.MAHOGANY_CHOPPING_BOARD.get(), "mahogany", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.JACARANDA_CHOPPING_BOARD.get(), "jacaranda", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.PALM_CHOPPING_BOARD.get(), "palm", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.WILLOW_CHOPPING_BOARD.get(), "willow", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.DEAD_CHOPPING_BOARD.get(), "dead", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.MAGIC_CHOPPING_BOARD.get(), "magic", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.UMBRAN_CHOPPING_BOARD.get(), "umbran", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.HELLBARK_CHOPPING_BOARD.get(), "hellbark", ChoppingBoardType.PLANKS, "biomesoplenty");

        this.choppingBoardTF(HNCBlocks.TWILIGHT_OAK_CHOPPING_BOARD.get(), "twilight_oak");
        this.choppingBoardTF(HNCBlocks.CANOPY_CHOPPING_BOARD.get(), "canopy");
        this.choppingBoardTF(HNCBlocks.MANGROVE_CHOPPING_BOARD.get(), "mangrove");
        this.choppingBoardTF(HNCBlocks.DARK_CHOPPING_BOARD.get(), "dark");
        this.choppingBoardTF(HNCBlocks.TIME_CHOPPING_BOARD.get(), "time");
        this.choppingBoardTF(HNCBlocks.TRANS_CHOPPING_BOARD.get(), "trans");
        this.choppingBoardTF(HNCBlocks.MINE_CHOPPING_BOARD.get(), "mine");
        this.choppingBoardTF(HNCBlocks.SORT_CHOPPING_BOARD.get(), "sort");

        this.choppingBoard(HNCBlocks.DARKWOOD_CHOPPING_BOARD.get(), "darkwood", ChoppingBoardType.PLANKS, "druidcraft");
        this.choppingBoard(HNCBlocks.ELDER_CHOPPING_BOARD.get(), "elder", ChoppingBoardType.PLANKS, "druidcraft");

        // Pizza Oven & Grill - Initial
        VariantBlockStateBuilder.PartialBlockstate oven = this.getVariantBuilder(HNCBlocks.PIZZA_OVEN.get()).partialState();
        ModelFile ovenModel = this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"));

        VariantBlockStateBuilder.PartialBlockstate grill = this.getVariantBuilder(HNCBlocks.GRILL.get()).partialState();
        ModelFile grillModel = this.models().getExistingFile(HNCMod.getLocation("block/grill"));

        VariantBlockStateBuilder.PartialBlockstate popcorn = this.getVariantBuilder(HNCBlocks.POPCORN_MACHINE.get()).partialState();
        ModelFile popcornModelFull = this.models().getExistingFile(HNCMod.getLocation("block/popcorn_machine_full"));
        ModelFile popcornModelEmpty = this.models().getExistingFile(HNCMod.getLocation("block/popcorn_machine_empty"));

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
        }

        // Generate blockstates
        this.simpleBlockItem(HNCBlocks.PIZZA_OVEN.get(), this.itemModels().getExistingFile(HNCMod.getLocation("block/pizza_oven")));
        this.simpleBlockItem(HNCBlocks.GRILL.get(), this.itemModels().getExistingFile(HNCMod.getLocation("block/grill")));
        this.simpleBlockItem(HNCBlocks.POPCORN_MACHINE.get(), this.itemModels().getExistingFile(HNCMod.getLocation("block/popcorn_machine_full")));

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

        this.choppingBoard(HNCBlocks.MAPLE_CHOPPING_BOARD.get(), "maple", ChoppingBoardType.PLANKS, HNCMod.MOD_ID);

        String buttonPath = HNCBlocks.MAPLE_BUTTON.get().getRegistryName().getPath();
        ModelFile buttonModel = this.models().singleTexture(buttonPath, new ResourceLocation("block/button"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        ModelFile buttonPressedModel = this.models().singleTexture(buttonPath + "_pressed", new ResourceLocation("block/button_pressed"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        ModelFile buttonInventoryModel = this.models().singleTexture(buttonPath + "_inventory", new ResourceLocation("block/button_inventory"), "texture", blockTexture(HNCBlocks.MAPLE_PLANKS.get()));
        VariantBlockStateBuilder builder = this.getVariantBuilder(HNCBlocks.MAPLE_BUTTON.get());
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
                builder.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
                        .with(WoodButtonBlock.POWERED, true).modelForState().rotationX(rotX).rotationY(rotY).modelFile(buttonPressedModel).addModel();
                builder.partialState().with(WoodButtonBlock.FACE, face).with(WoodButtonBlock.FACING, dir)
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

    private void choppingBoard(ChoppingBoardBlock choppingBoard, String id, ChoppingBoardType choppingBoardType)
    {
        choppingBoard(choppingBoard, id, choppingBoardType, "minecraft");
    }

    private void choppingBoard(ChoppingBoardBlock choppingBoard, String id, ChoppingBoardType choppingBoardType, String planksModId)
    {
        VariantBlockStateBuilder.PartialBlockstate partialState = this.getVariantBuilder(choppingBoard).partialState();
        String path = "block/" + id + "_chopping_board";
        ResourceLocation parent = HNCMod.getLocation("block/chopping_board");
        BlockModelBuilder model = this.models().withExistingParent(path, parent).texture("planks", new ResourceLocation(planksModId, "block/" + choppingBoardType.apply(id, true)));
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(model).addModel();
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(model).addModel();
        }
        this.simpleBlockItem(choppingBoard, this.itemModels().getExistingFile(HNCMod.getLocation(path)));
    }

    private void choppingBoardTF(ChoppingBoardBlock choppingBoard, String id)
    {
        VariantBlockStateBuilder.PartialBlockstate partialState = this.getVariantBuilder(choppingBoard).partialState();
        String path = "block/" + id + "_chopping_board";
        ResourceLocation parent = HNCMod.getLocation("block/chopping_board");
        Function<Integer, BlockModelBuilder> model = inc -> this.models().withExistingParent(path + "_" + inc, parent).texture("planks", new ResourceLocation("twilightforest", "block/wood/" + ChoppingBoardType.PLANKS.apply(id.equals("dark") ? "darkwood" : id, false) + "_" + inc));
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, false).addModels(
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(0)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(1)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(2)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(3)).weight(25).buildLast()
            );
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, true).addModels(
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(0)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(1)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(2)).weight(25).buildLast(),
                    partialState.modelForState().rotationY((int) direction.toYRot()).modelFile(model.apply(3)).weight(25).buildLast()
            );
        }
        this.simpleBlockItem(choppingBoard, this.itemModels().getExistingFile(HNCMod.getLocation(path + "_0")));
    }

    enum ChoppingBoardType
    {
        EMPTY(""), PLANKS("planks"), BLOCK("block");

        final String fix;

        ChoppingBoardType(String fix)
        {
            this.fix = fix;
        }

        String apply(String id, boolean suffix)
        {
            if (this == EMPTY)
                return id;
            return suffix ? id + "_" + this.fix : this.fix + "_" + id;
        }
    }
}
