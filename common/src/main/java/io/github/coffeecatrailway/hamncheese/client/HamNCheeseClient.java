package io.github.coffeecatrailway.hamncheese.client;

import com.google.common.collect.ImmutableList;
import gg.moonflower.pollen.api.event.events.registry.client.RegisterAtlasSpriteEvent;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.*;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.client.blockentity.ChoppingBoardRenderer;
import io.github.coffeecatrailway.hamncheese.client.entity.MouseModel;
import io.github.coffeecatrailway.hamncheese.client.entity.MouseRenderer;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.GrillScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PizzaOvenScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PopcornMachineScreen;
import io.github.coffeecatrailway.hamncheese.client.item.SandwichItemRenderer;
import io.github.coffeecatrailway.hamncheese.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

/**
 * @author CoffeeCatRailway
 * Created: 30/04/2022
 */
@Environment(EnvType.CLIENT)
public class HamNCheeseClient
{
    public static final ModelLayerLocation MOUSE = new ModelLayerLocation(HamNCheese.getLocation("mouse"), "main");

    public static final ResourceLocation EMPTY_SLOT_BAG = HamNCheese.getLocation("item/empty_bag_slot");
    public static final ResourceLocation EMPTY_SLOT_KERNELS = HamNCheese.getLocation("item/empty_kernels_slot");
    public static final ResourceLocation EMPTY_SLOT_SEASONING = HamNCheese.getLocation("item/empty_seasoning_slot");
    public static final ResourceLocation EMPTY_SLOT_FLAVOUR = HamNCheese.getLocation("item/empty_flavour_slot");

    private static BlockTintCache MAPLE_TINT_CACHE;

    public static void onClientInit()
    {
        SandwichItemRenderer.init();

        BlockEntityRendererRegistry.register(HNCBlockEntities.CHOPPING_BOARD, ChoppingBoardRenderer::new); // Broken on pollen 1.4.9 (forge)

        EntityRendererRegistry.registerLayerDefinition(MOUSE, MouseModel::createLayer);
        EntityRendererRegistry.register(HNCEntities.MOUSE, MouseRenderer::new);

        final ColorResolver mapleColorResolver = (biome, d, e) -> {
            double f = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0)).getValue(d * .0225d, e * .0225d, false);
            return f < -.1d ? 0xEC4400 : 0xAE1800;
        };

        ColorRegistry.register((state, tintGetter, pos, tintIndex) -> {
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (((state.is(HNCBlocks.MAPLE_SAPLING.get()) || state.is(HNCBlocks.POTTED_MAPLE_SAPLING.get())) && tintIndex != 0) || clientLevel == null || pos == null)
                return -1;
            if (MAPLE_TINT_CACHE == null)
                MAPLE_TINT_CACHE = new BlockTintCache(blockPos -> clientLevel.calculateBlockTint(blockPos, mapleColorResolver));
            return MAPLE_TINT_CACHE.getColor(pos);
        }, HNCBlocks.MAPLE_LEAVES, HNCBlocks.MAPLE_SAPLING, HNCBlocks.POTTED_MAPLE_SAPLING);
        ColorRegistry.register((itemStack, layer) -> 0xEC4400, HNCBlocks.MAPLE_LEAVES);
        ColorRegistry.register((itemStack, layer) -> layer == 0 ? 0xEC4400 : -1, HNCBlocks.MAPLE_SAPLING);

        RegisterAtlasSpriteEvent.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) -> {
            registry.accept(EMPTY_SLOT_BAG);
            registry.accept(EMPTY_SLOT_KERNELS);
            registry.accept(EMPTY_SLOT_SEASONING);
            registry.accept(EMPTY_SLOT_FLAVOUR);
        });
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx)
    {
        ctx.enqueueWork(() -> {
            ScreenRegistry.register(HNCMenus.PIZZA_OVEN.get(), PizzaOvenScreen::new);
            ScreenRegistry.register(HNCMenus.GRILL.get(), GrillScreen::new);
            ScreenRegistry.register(HNCMenus.POPCORN_MACHINE.get(), PopcornMachineScreen::new);
        });

        RenderTypeRegistry.register(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCBlocks.PINEAPPLE_PLANT.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.TOMATO_PLANT.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.CORN_PLANT.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCBlocks.MAPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.MAPLE_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.MAPLE_DOOR.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCBlocks.TREE_TAP.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCBlocks.POPCORN_MACHINE.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCFluids.MAPLE_SAP.get(), RenderType.translucent());
        RenderTypeRegistry.register(HNCFluids.MAPLE_SAP_FLOWING.get(), RenderType.translucent());

        RenderTypeRegistry.register(HNCFluids.MILK.get(), RenderType.translucent());
        RenderTypeRegistry.register(HNCFluids.MILK_FLOWING.get(), RenderType.translucent());

        RenderTypeRegistry.register(HNCFluids.GOAT_MILK.get(), RenderType.translucent());
        RenderTypeRegistry.register(HNCFluids.GOAT_MILK_FLOWING.get(), RenderType.translucent());

        ItemRendererRegistry.registerRenderer(HNCItems.PIZZA.get(), SandwichItemRenderer.INSTANCE);
        ItemRendererRegistry.registerRenderer(HNCItems.CRACKER.get(), SandwichItemRenderer.INSTANCE);
        ItemRendererRegistry.registerRenderer(HNCItems.SANDWICH.get(), SandwichItemRenderer.INSTANCE);
    }
}
