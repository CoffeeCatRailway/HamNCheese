package io.github.coffeecatrailway.hamncheese;

import gg.moonflower.pollen.api.block.StrippedBlock;
import gg.moonflower.pollen.api.client.util.CreativeModeTabBuilder;
import gg.moonflower.pollen.api.config.ConfigManager;
import gg.moonflower.pollen.api.config.PollinatedConfigType;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.FluidBehaviorRegistry;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.BlockEntityRendererRegistry;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;
import gg.moonflower.pollen.api.registry.client.ItemRendererRegistry;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.client.entity.HNCBoatEntityRenderer;
import io.github.coffeecatrailway.hamncheese.client.item.SandwichItemRenderer;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.HNCDispenseBoatBehavior;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.MapleSapDispenseBehavior;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.SandwichExplodeBehavior;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.TreeTapDispenseBehavior;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import io.github.coffeecatrailway.hamncheese.common.material.MapleSapFluidBehavior;
import io.github.coffeecatrailway.hamncheese.data.gen.*;
import io.github.coffeecatrailway.hamncheese.registry.*;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

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

        RenderTypeRegistry.register(HNCBlocks.TREE_TAP.get(), RenderType.cutout());

        RenderTypeRegistry.register(HNCFluids.MAPLE_SAP.get(), RenderType.translucent());
        RenderTypeRegistry.register(HNCFluids.MAPLE_SAP_FLOWING.get(), RenderType.translucent());

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
        HNCFluids.load(PLATFORM);

        FluidBehaviorRegistry.register(HNCFluidTags.MAPLE_SAP, new MapleSapFluidBehavior());
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx)
    {
        ctx.enqueueWork(() -> {
            HNCFeatures.load(PLATFORM);

            // Dispenser Behaviors
            DispenserBlock.registerBehavior(HNCItems.MAPLE_BOAT.get(), new HNCDispenseBoatBehavior(HNCBoatEntity.ModType.MAPLE));

            DispenserBlock.registerBehavior(HNCItems.SANDWICH.get(), new SandwichExplodeBehavior(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get(), true));
            DispenserBlock.registerBehavior(HNCItems.CRACKER.get(), new SandwichExplodeBehavior(HNCItems.CRACKER.get(), HNCItems.CRACKER.get(), false));
            DispenserBlock.registerBehavior(HNCItems.PIZZA.get(), new SandwichExplodeBehavior(HNCItems.UNBAKED_PIZZA_BASE.get(), HNCItems.BAKED_PIZZA_DUMMY.get(), false, HamNCheese.CONFIG_SERVER.dispenseTomatoSauce.get() ? new Item[]{HNCItems.TOMATO_SAUCE.get()} : new Item[]{}));

            DispenserBlock.registerBehavior(HNCFluids.MAPLE_SAP_BUCKET.get(), new MapleSapDispenseBehavior());

            DispenseItemBehavior behavior = getBehavior(Items.GLASS_BOTTLE);
            DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new TreeTapDispenseBehavior.GlassBottle(behavior));

            behavior = getBehavior(Items.BUCKET);
            DispenserBlock.registerBehavior(Items.BUCKET, new TreeTapDispenseBehavior.Bucket(behavior));

            DispenserBlock.registerBehavior(HNCItems.MAPLE_SAP_BOTTLE.get(), new TreeTapDispenseBehavior.MapleSapBottle());

            behavior = getBehavior(HNCFluids.MAPLE_SAP_BUCKET.get());
            DispenserBlock.registerBehavior(HNCFluids.MAPLE_SAP_BUCKET.get(), new TreeTapDispenseBehavior.MapleSapBucket(behavior));
        });
        StrippingRegistry.register(HNCBlocks.MAPLE_LOG.get(), HNCBlocks.STRIPPED_MAPLE_LOG.get());
        StrippingRegistry.register(HNCBlocks.MAPLE_WOOD.get(), HNCBlocks.STRIPPED_MAPLE_WOOD.get());
    }

    private static DispenseItemBehavior getBehavior(Item item)
    {
        return ((DispenserBlock) Blocks.DISPENSER).getDispenseMethod(new ItemStack(item));
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
