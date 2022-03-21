package io.github.coffeecatrailway.hamncheese.fabric;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.fabricmc.api.ModInitializer;

public class HamNCheeseFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        HamNCheese.PLATFORM.setup();
    }
}
