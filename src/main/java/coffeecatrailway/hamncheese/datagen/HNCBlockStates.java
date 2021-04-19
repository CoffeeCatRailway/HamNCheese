package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.common.block.TomatoPlantBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

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

            ResourceLocation pineappleTexture = HNCMod.getLocation("block/pineapple_stage_" + age);
            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_stage_" + age, HNCMod.getLocation("block/pineapple_plant_top"))
                    .texture("pineapple", HNCMod.getLocation("block/pineapple_stage_" + age))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate tomatoPlant = this.getVariantBuilder(HNCBlocks.TOMATO_PLANT.get()).partialState();
        for (age = 0; age < 10; age++)
        {
            tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().withExistingParent("block/tomato_plant_bottom_stage_" + age, "block/crop")
                    .texture("crop", HNCMod.getLocation("block/tomato_plant_bottom_stage_" + age))).addModel();

            VariantBlockStateBuilder.PartialBlockstate partialState = tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER);
            if (age > 1)
                partialState.modelForState().modelFile(this.models().crop("block/tomato_plant_top_stage_" + age, HNCMod.getLocation("block/tomato_plant_top_stage_" + (age - 2)))).addModel();
            else
                partialState.modelForState().modelFile(this.models().withExistingParent("block/tomato_plant_top_stage_" + age, "block/air")).addModel();
        }
    }
}
