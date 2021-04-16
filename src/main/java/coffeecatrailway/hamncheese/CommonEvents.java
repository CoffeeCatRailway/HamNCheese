package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.common.entity.villager.HNCVillagerTrades;
import coffeecatrailway.hamncheese.registry.HNCFeatures;
import coffeecatrailway.hamncheese.registry.HNCItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;
import java.util.Random;

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
    public static void onVillagerTrades(VillagerTradesEvent event)
    {
        if (!HNCMod.COMMON_CONFIG.allowFarmerTrades.get()) return;

        Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
        if (event.getType() == VillagerProfession.BUTCHER)
        {
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.HAM_SLICE.get(), 14, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.BACON.get(), 14, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_HAM_SLICE.get(), 9, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_BACON.get(), 9, 16, 5));

            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.CURDLER.get(), 15, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.ROLLING_PIN.get(), 15, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.GRIND_STONES.get(), 15, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.KNIFE.get(), 18, 8, 5));
        }
        if (event.getType() == VillagerProfession.FARMER)
        {
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.TOMATO_SEEDS.get(), 18, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.PINEAPPLE_PLANT.get(), 18, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.TOMATO.get(), 20, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.PINEAPPLE.get(), 20, 16, 5));

            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOMATO_SEEDS.get(), 1, 9, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.PINEAPPLE_PLANT.get(), 1, 9, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOMATO.get(), 2, 15, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.PINEAPPLE.get(), 2, 15, 16, 5));
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
