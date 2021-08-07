package coffeecatrailway.hamncheese.client;

import coffeecatrailway.hamncheese.HNCMod;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = HNCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlasStitch
{
    public static final ResourceLocation BAG = HNCMod.getLocation("item/empty_bag_slot");
    public static final ResourceLocation KERNELS = HNCMod.getLocation("item/empty_kernels_slot");
    public static final ResourceLocation SEASONING = HNCMod.getLocation("item/empty_seasoning_slot");
    public static final ResourceLocation FLAVOUR = HNCMod.getLocation("item/empty_flavour_slot");

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event)
    {
        if (!event.getMap().location().equals(PlayerContainer.BLOCK_ATLAS))
            return;
        event.addSprite(BAG);
        event.addSprite(KERNELS);
        event.addSprite(SEASONING);
        event.addSprite(FLAVOUR);
    }
}
