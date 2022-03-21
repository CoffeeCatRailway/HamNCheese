package io.github.coffeecatrailway.hamncheese;

import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.resources.ResourceLocation;

public class HamNCheese
{
    public static final String MOD_ID = "hamncheese";
//    public static PlusConfig.Server CONFIG_SERVER = ConfigManager.register(MOD_ID, PollinatedConfigType.SERVER, PlusConfig.Server::new);
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(HamNCheese::onClientInit)
            .clientPostInit(HamNCheese::onClientPostInit)
            .commonInit(HamNCheese::onCommonInit)
            .commonPostInit(HamNCheese::onCommonPostInit)
//            .dataInit(Plus::onDataInit)
            .build();

    public static void onClientInit()
    {
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx)
    {
//        ctx.enqueueWork(() -> {
//            ScreenRegistry.register(PlusMenuTypes.SAW_BENCH.get(), SawBenchScreen::new);
//        });
//
//        RenderTypeRegistry.register(PlusBlocks.SAW_BENCH.get(), RenderType.cutoutMipped());
//        RenderTypeRegistry.register(PlusBlocks.GLOW_LANTERN.get(), RenderType.cutout());
    }

    public static void onCommonInit()
    {
//        PlusBlocks.load(PLATFORM);
//        PlusItems.load(PLATFORM);
//        PlusEnchantments.load(PLATFORM);
//        PlusMenuTypes.load(PLATFORM);
//        PlusRecipes.load(PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx)
    {
    }

//    public static void onDataInit(Platform.DataSetupContext ctx)
//    {
//        DataGenerator generator = ctx.getGenerator();
//        PollinatedModContainer container = ctx.getMod();
//        PlusBlockTags blockTags = new PlusBlockTags(generator, container);
//        generator.addProvider(blockTags);
//        generator.addProvider(new PlusItemTags(generator, container, blockTags));
//        generator.addProvider(new PlusItemModels(generator, container));
//        generator.addProvider(new PlusLanguage(generator, container));
//    }

    public static ResourceLocation getLocation(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }
}
