package io.github.coffeecatrailway.hamncheese.forge;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HamNCheese.MOD_ID)
public class HamNCheeseForge
{
    public HamNCheeseForge()
    {
        HamNCheese.PLATFORM.setup();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onCommonSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event)
    {
    }

    private void onCommonSetup(final FMLCommonSetupEvent event)
    {
    }
}
