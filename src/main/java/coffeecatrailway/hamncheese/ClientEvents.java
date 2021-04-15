package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author CoffeeCatRailway
 * Created: 7/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class ClientEvents
{
    public static void init(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ClientEvents.renderLayers();
        });
    }

    public static void renderLayers()
    {
        RenderType cutoutMipped = RenderType.cutoutMipped();

        RenderTypeLookup.setRenderLayer(HNCBlocks.PINEAPPLE_PLANT.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.TOMATO_PLANT.get(), cutoutMipped);
    }
}
