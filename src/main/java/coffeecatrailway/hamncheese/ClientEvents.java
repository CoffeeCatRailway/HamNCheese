package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.client.entity.MouseRenderer;
import coffeecatrailway.hamncheese.client.gui.screen.GrillScreen;
import coffeecatrailway.hamncheese.client.gui.screen.PizzaOvenScreen;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCContainers;
import coffeecatrailway.hamncheese.registry.HNCEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
            renderLayers();
        });
        entityRenderers();
        registerScreen();
    }

    private static void renderLayers()
    {
        RenderType cutoutMipped = RenderType.cutoutMipped();

        RenderTypeLookup.setRenderLayer(HNCBlocks.PINEAPPLE_PLANT.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.TOMATO_PLANT.get(), cutoutMipped);

        RenderTypeLookup.setRenderLayer(HNCBlocks.OAK_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.BIRCH_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.ACACIA_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.WARPED_CHOPPING_BOARD.get(), cutoutMipped);

        RenderTypeLookup.setRenderLayer(HNCBlocks.STONE_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.GOLD_CHOPPING_BOARD.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.IRON_CHOPPING_BOARD.get(), cutoutMipped);

        RenderTypeLookup.setRenderLayer(HNCBlocks.PIZZA_OVEN.get(), cutoutMipped);
        RenderTypeLookup.setRenderLayer(HNCBlocks.GRILL.get(), cutoutMipped);
    }

    private static void entityRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(HNCEntities.MOUSE.get(), MouseRenderer::new);
    }

    private static void registerScreen()
    {
        ScreenManager.register(HNCContainers.PIZZA_OVEN.get(), PizzaOvenScreen::new);
        ScreenManager.register(HNCContainers.GRILL.get(), GrillScreen::new);
    }
}
