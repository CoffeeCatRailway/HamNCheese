package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
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
        VariantBlockStateBuilder.PartialBlockstate pineapplePlant = this.getVariantBuilder(HNCBlocks.PINEAPPLE_PLANT.get()).partialState();

        for (int age = 0; age <= 4; age++)
        {
            ResourceLocation plantTexture = HNCMod.getLocation("block/pineapple_plant_stage_" + age);
            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_bottom_" + age, HNCMod.getLocation("block/pineapple_plant_bottom"))
                    .texture("plant", plantTexture).texture("particle", plantTexture)).addModel();

            ResourceLocation pineappleTexture = HNCMod.getLocation("block/pineapple_stage_" + age);
            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_" + age, HNCMod.getLocation("block/pineapple_plant_top"))
                    .texture("pineapple", pineappleTexture).texture("particle", pineappleTexture)).addModel();
        }
    }
}
