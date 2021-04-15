package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.registry.HNCFeatures;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author CoffeeCatRailway
 * Created: 15/04/2021
 */
@Mod.EventBusSubscriber(modid = HNCMod.MOD_ID)
public class CommonEvents
{
    public static void init(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            // Compostable
            HNCFeatures.registerConfiguredFeatures();
        });
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.Climate climate = event.getClimate();

        if (climate.temperature >= .5f && climate.temperature < 1f)
        {
            if (HNCMod.COMMON_CONFIG.generateWildPineapples.get())
                builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.WILD_PINEAPPLE_PATCH);

            if (HNCMod.COMMON_CONFIG.generateWildTomatoes.get())
                builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.WILD_TOMATO_PATCH);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent.Throwable event) {
        if (event.getThrowable() instanceof EggEntity && event.getRayTraceResult().getType() != RayTraceResult.Type.MISS) {
            EggEntity egg = (EggEntity) event.getThrowable();
            World level = egg.level;
            if (!level.isClientSide)
                if (MathHelper.nextDouble(level.random, 0d, 1d) <= HNCMod.SERVER_CONFIG.crackedEggSpawnChance.get())
                    level.addFreshEntity(new ItemEntity(level, egg.getX(), egg.getY(), egg.getZ(), new ItemStack(HNCItems.CRACKED_EGG.get())));
        }
    }
}
