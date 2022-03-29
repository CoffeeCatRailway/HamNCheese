package io.github.coffeecatrailway.hamncheese.data.gen.fabric;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2022
 */
public class HNCDataEntryPoint implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        HamNCheese.PLATFORM.dataSetup(generator);
    }
}
