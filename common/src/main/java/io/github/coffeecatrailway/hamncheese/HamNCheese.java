package io.github.coffeecatrailway.hamncheese;

import gg.moonflower.pollen.api.block.StrippedBlock;
import gg.moonflower.pollen.api.client.util.CreativeModeTabBuilder;
import gg.moonflower.pollen.api.config.ConfigManager;
import gg.moonflower.pollen.api.config.PollinatedConfigType;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.BlockEntityRendererRegistry;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;
import gg.moonflower.pollen.api.registry.client.ItemRendererRegistry;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.client.entity.HNCBoatEntityRenderer;
import io.github.coffeecatrailway.hamncheese.client.item.SandwichItemRenderer;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import io.github.coffeecatrailway.hamncheese.data.gen.*;
import io.github.coffeecatrailway.hamncheese.registry.*;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 21/03/2022
 */
public class HamNCheese
{
    public static final String MOD_ID = "hamncheese";
    public static HNCConfig.Server CONFIG_SERVER = ConfigManager.register(MOD_ID, PollinatedConfigType.SERVER, HNCConfig.Server::new);
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(HamNCheese::onClientInit)
            .clientPostInit(HamNCheese::onClientPostInit)
            .commonInit(HamNCheese::onCommonInit)
            .commonPostInit(HamNCheese::onCommonPostInit)
            .dataInit(HamNCheese::onDataInit)
            .build();

    public static final CreativeModeTab TAB = CreativeModeTabBuilder.builder(getLocation("tab")).setIcon(() -> new ItemStack(HNCBlocks.BLOCK_OF_CHEESE.get())).build();

    public static void onClientInit()
    {
        SandwichItemRenderer.init();

        EntityRendererRegistry.register(HNCEntities.MAPLE_BOAT, HNCBoatEntityRenderer::new);
        Supplier<LayerDefinition> boatDefinition = BoatModel::createBodyModel;
        for (HNCBoatEntity.ModType type : HNCBoatEntity.ModType.values())
            EntityRendererRegistry.registerLayerDefinition(HNCBoatEntityRenderer.createModelLayer(type), boatDefinition);

        BlockEntityRendererRegistry.register(HNCBlockEntities.SIGN, SignRenderer::new);
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx)
    {
//        ctx.enqueueWork(() -> {
//            ScreenRegistry.register(PlusMenuTypes.SAW_BENCH.get(), SawBenchScreen::new);
//        });

        RenderTypeRegistry.register(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCBlocks.MAPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.MAPLE_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeRegistry.register(HNCBlocks.MAPLE_DOOR.get(), RenderType.cutout());

        ItemRendererRegistry.registerRenderer(HNCItems.PIZZA.get(), SandwichItemRenderer.INSTANCE);
        ItemRendererRegistry.registerRenderer(HNCItems.CRACKER.get(), SandwichItemRenderer.INSTANCE);
        ItemRendererRegistry.registerRenderer(HNCItems.SANDWICH.get(), SandwichItemRenderer.INSTANCE);
    }

    public static void onCommonInit()
    {
        HNCBlocks.load(PLATFORM);
        HNCItems.load(PLATFORM);
        HNCRecipes.load(PLATFORM);
//        HNCBlockPlacerTypes.load(PLATFORM);
        HNCEntities.load(PLATFORM);
//        HNCProfessions.load(PLATFORM);
//        bus.addGenericListener(StatType.class, HNCStats::register);
        HNCBlockEntities.load(PLATFORM);
//        HNCContainers.load(PLATFORM);
//        HNCFluids.load(PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx)
    {
        ctx.enqueueWork(() -> HNCFeatures.load(PLATFORM));
        StrippingRegistry.register(HNCBlocks.MAPLE_LOG.get(), HNCBlocks.STRIPPED_MAPLE_LOG.get());
        StrippingRegistry.register(HNCBlocks.MAPLE_WOOD.get(), HNCBlocks.STRIPPED_MAPLE_WOOD.get());
    }

    public static void onDataInit(Platform.DataSetupContext ctx)
    {
        DataGenerator generator = ctx.getGenerator();
        PollinatedModContainer container = ctx.getMod();
        HNCBlockTags blockTags = new HNCBlockTags(generator, container);
        generator.addProvider(blockTags);
        generator.addProvider(new HNCItemTags(generator, container, blockTags));
        generator.addProvider(new HNCFluidTags(generator, container));
        generator.addProvider(new HNCModels(generator, container));
        generator.addProvider(new HNCLanguage(generator, container));
        generator.addProvider(new HNCRecipeProvider(generator));
        generator.addProvider(new HNCLootTableProvider(generator));
    }

    public static ResourceLocation getLocation(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }
}
