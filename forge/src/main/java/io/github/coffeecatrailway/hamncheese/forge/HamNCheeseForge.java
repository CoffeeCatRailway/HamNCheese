package io.github.coffeecatrailway.hamncheese.forge;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HamNCheese.MOD_ID)
public class HamNCheeseForge
{
    public HamNCheeseForge()
    {
        HamNCheese.PLATFORM.setup();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(CommonEvents::init);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
