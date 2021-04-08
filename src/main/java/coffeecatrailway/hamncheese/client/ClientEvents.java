package coffeecatrailway.hamncheese.client;

import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 7/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class ClientEvents
{
    public static void renderLayers()
    {
        RenderType cutoutMipped = RenderType.cutoutMipped();

        RenderTypeLookup.setRenderLayer(HNCBlocks.PINEAPPLE_PLANT.get(), cutoutMipped);
    }
}
