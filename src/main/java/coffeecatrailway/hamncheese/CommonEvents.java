package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import coffeecatrailway.hamncheese.common.entity.villager.HNCVillagerTrades;
import coffeecatrailway.hamncheese.registry.HNCEntities;
import coffeecatrailway.hamncheese.registry.HNCFeatures;
import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCProfessions;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

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
            registerCompostables();
            HNCFeatures.registerConfiguredFeatures();
        });

        HNCEntities.ATTRIBUTE_MAPS.forEach(Runnable::run);
        HNCEntities.registerSpawnPlacements();
    }

    public static void registerCompostables()
    {
        // 30% chance
        ComposterBlock.COMPOSTABLES.put(HNCItems.CHEESE_SLICE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.HAM_SLICE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.COOKED_HAM_SLICE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.GREEN_HAM_SLICE.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.BACON.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.COOKED_BACON.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.PINEAPPLE_PLANT.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.TOMATO_SEEDS.get(), .3f);

        // 40% chance
        ComposterBlock.COMPOSTABLES.put(HNCItems.CRACKED_EGG.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.COOKED_EGG.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.GREEN_EGG.get(), .4f);

        // 50% chance
        ComposterBlock.COMPOSTABLES.put(HNCItems.BLOCK_OF_CHEESE.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.DOUGH.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.UNBAKED_PIZZA_BASE.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.UNBAKED_BREAD.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.BREAD_SLICE.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.TOAST.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.UNBAKED_CRACKER.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.PINEAPPLE_RING.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.PINEAPPLE_BIT.get(), .5f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.TOMATO_SLICE.get(), .5f);

        // 65% chance
        ComposterBlock.COMPOSTABLES.put(HNCItems.PINEAPPLE.get(), .65f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.TOMATO.get(), .65f);
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

        HNCEntities.addEntitySpawns(event);
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event)
    {
        Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
        // Modded
        if (event.getType() == HNCProfessions.CHEF.get())
        {
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.CRACKER.get(), 2, 4, 10, 2));
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.UNBAKED_CRACKER.get(), 2, 4, 10, 2));
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.UNBAKED_PIZZA_BASE.get(), 1, 3, 10, 2));
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.BREAD_SLICE.get(), 1, 2, 10, 2));
            trades.get(1).add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOAST.get(), 1, 2, 10, 2));
            trades.get(1).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get()), false, 2, 3, 8, 2));
            trades.get(1).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.TOMATO_SLICE.get(), HNCItems.HAM_SLICE.get()), false, 1, 2, 8, 2));

            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.POISONOUS_POTATO), false, 1, 2, 16, 1));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "Ham N' Cheese Toastie", 3, 2, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get()), false, "BKT", 3, 2, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.BEEF, Items.DRIED_KELP, HNCItems.CHEESE_SLICE.get(), HNCItems.TOMATO_SLICE.get()), false, 1, 1, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.COOKED_BEEF, HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_HAM_SLICE.get(), Items.COOKED_COD, Items.COOKED_CHICKEN, HNCItems.COOKED_MOUSE.get(), Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON), false, "Carnivore Sandwich", 3, 2, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), HNCItems.COOKED_EGG.get(), HNCItems.CHEESE_SLICE.get()), true, "Bacon & Egg Roll", 2, 1, 16, 5));

            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "Cheesy Pizza", 3, 4, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_BACON.get(), HNCItems.HAM_SLICE.get()), true, "Meat Lovers", 3, 4, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.PINEAPPLE_RING.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get()), true, "Hawaiian", 3, 4, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_EGG.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), HNCItems.COOKED_EGG.get()), true, "Vegetarian", 3, 4, 20, 10));
        }
        // Vanilla
        if (HNCMod.COMMON_CONFIG.allowButcherTrades.get() && event.getType() == VillagerProfession.BUTCHER)
        {
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.HAM_SLICE.get(), 14, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.BACON.get(), 14, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_HAM_SLICE.get(), 9, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_BACON.get(), 9, 16, 5));

            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.CURDLER.get(), 1, 16, 2));
            trades.get(1).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.ROLLING_PIN.get(), 1, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.GRIND_STONES.get(), 1, 16, 5));
            trades.get(3).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.KNIFE.get(), 1, 8, 5));
        }
        if (HNCMod.COMMON_CONFIG.allowFarmerTrades.get() && event.getType() == VillagerProfession.FARMER)
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
    public static void onProjectileImpact(ProjectileImpactEvent.Throwable event)
    {
        if (event.getThrowable() instanceof EggEntity && event.getRayTraceResult().getType() != RayTraceResult.Type.MISS)
        {
            EggEntity egg = (EggEntity) event.getThrowable();
            World level = egg.level;
            if (!level.isClientSide)
                if (MathHelper.nextDouble(level.random, 0d, 1d) <= HNCMod.SERVER_CONFIG.crackedEggSpawnChance.get())
                    level.addFreshEntity(new ItemEntity(level, egg.getX(), egg.getY(), egg.getZ(), new ItemStack(HNCItems.CRACKED_EGG.get())));
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof CatEntity)
            ((CatEntity) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((CatEntity) entity, MouseEntity.class, false));
        if (entity instanceof OcelotEntity)
            ((OcelotEntity) entity).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((OcelotEntity) entity, MouseEntity.class, false));
    }
}
