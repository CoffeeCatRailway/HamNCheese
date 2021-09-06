package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.*;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
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
        int age;
        VariantBlockStateBuilder.PartialBlockstate pineapplePlant = this.getVariantBuilder(HNCBlocks.PINEAPPLE_PLANT.get()).partialState();
        for (age = 0; age < 5; age++)
        {
            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_bottom_stage_" + age, HNCMod.getLocation("block/pineapple_plant_bottom"))
                            .texture("plant", HNCMod.getLocation("block/pineapple_plant_stage_" + age))).addModel();

            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_stage_" + age, HNCMod.getLocation("block/pineapple_plant_top"))
                            .texture("pineapple", HNCMod.getLocation("block/pineapple_stage_" + age))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate tomatoPlant = this.getVariantBuilder(HNCBlocks.TOMATO_PLANT.get()).partialState();
        for (age = 0; age < 10; age++)
        {
            tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_bottom_stage_" + age, HNCMod.getLocation("block/tomato_plant_bottom_stage_" + age))).addModel();

            tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_top_stage_" + age, HNCMod.getLocation("block/tomato_plant_top_stage_" + age))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate cornPlant = this.getVariantBuilder(HNCBlocks.CORN_PLANT.get()).partialState();
        for (age = 0; age < 7; age++)
        {
            cornPlant.with(CornPlantBlock.AGE, age).with(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().cross("block/corn_plant_bottom_stage_" + age, HNCMod.getLocation("block/corn_plant_bottom_stage_" + age))).addModel();

            if (age > 2)
                cornPlant.with(CornPlantBlock.AGE, age).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER)
                        .modelForState().modelFile(this.models().cross("block/corn_plant_top_stage_" + age, HNCMod.getLocation("block/corn_plant_top_stage_" + age))).addModel();
            else
                cornPlant.with(CornPlantBlock.AGE, age).with(CornPlantBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(this.models().getExistingFile(new ResourceLocation("block/air"))).addModel();
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
        for (int i = 1; i < 4; i++)
            cheese.with(CheeseBlock.BITES, i).modelForState().modelFile(this.itemModels().getExistingFile(HNCMod.getLocation("block/block_of_cheese_slice" + i))).addModel();
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
