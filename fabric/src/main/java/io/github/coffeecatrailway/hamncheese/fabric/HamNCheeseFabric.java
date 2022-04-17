package io.github.coffeecatrailway.hamncheese.fabric;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HamNCheeseFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        HamNCheese.PLATFORM.setup();
        WoodType.register(HNCBlocks.MAPLE_WOOD_TYPE);
    }
}
