package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.datagen.*;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import io.github.ocelot.sonar.Sonar;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HNCMod.MOD_ID)
public class HNCMod
{
    public static final String MOD_ID = "hamncheese";
    private static final Logger LOGGER = getLogger("");

    public static HNCConfig.Client CLIENT_CONFIG;
    public static HNCConfig.Common COMMON_CONFIG;
    public static HNCConfig.Server SERVER_CONFIG;

    public static final ItemGroup GROUP_ALL = new ItemGroup(HNCMod.MOD_ID)
    {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon()
        {
            return new ItemStack(HNCItems.BLOCK_OF_CHEESE.get());
        }
    };

    public HNCMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Sonar.init(bus);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onGatherData);

        final Pair<HNCConfig.Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(HNCConfig.Client::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, client.getRight());
        CLIENT_CONFIG = client.getLeft();

        final Pair<HNCConfig.Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(HNCConfig.Common::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, common.getRight());
        COMMON_CONFIG = common.getLeft();

        final Pair<HNCConfig.Server, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(HNCConfig.Server::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, server.getRight());
        SERVER_CONFIG = server.getLeft();
        LOGGER.info("Register configs");

        MinecraftForge.EVENT_BUS.register(this);

        HNCItems.load(bus);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        HNCBlockTags blockTags = new HNCBlockTags(generator, existingFileHelper);

        generator.addProvider(new HNCLanguage(generator));
        generator.addProvider(new HNCItemTags(generator, blockTags, existingFileHelper));
        generator.addProvider(blockTags);
        generator.addProvider(new HNCLootTables(generator));
        generator.addProvider(new HNCRecipeGen(generator));
        generator.addProvider(new HNCItemModels(generator));
    }

    public static ResourceLocation getLocation(String path)
    {
        return new ResourceLocation(HNCMod.MOD_ID, path);
    }

    public static Logger getLogger(String name)
    {
        return LogManager.getLogger(HNCMod.MOD_ID + (!StringUtils.isNullOrEmpty(name) ? "-" + name : ""));
    }
}
