package coffeecatrailway.hamncheese;

import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.block.dispenser.HNCDispenseBoatBehavior;
import coffeecatrailway.hamncheese.common.block.dispenser.MapleSapDispenseBehavior;
import coffeecatrailway.hamncheese.common.block.dispenser.TreeTapDispenseBehavior;
import coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import coffeecatrailway.hamncheese.common.entity.villager.HNCVillagerTrades;
import coffeecatrailway.hamncheese.common.world.VillagePoolsHelper;
import coffeecatrailway.hamncheese.data.ChoppingBoardManager;
import coffeecatrailway.hamncheese.data.gen.HNCFluidTags;
import coffeecatrailway.hamncheese.integration.top.HNCTheOneProbe;
import coffeecatrailway.hamncheese.mixin.AccessorDispenserBlock;
import coffeecatrailway.hamncheese.registry.*;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
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
        if (ModList.get().isLoaded("theoneprobe"))
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", HNCTheOneProbe::new);

        if (HNCConfig.SERVER.generateVillageRestaurants.get())
            VillagePoolsHelper.bootstrap();

        event.enqueueWork(() -> {
            registerCompostables();
            HNCFeatures.registerConfiguredFeatures();

            DispenserBlock.registerBehavior(HNCItems.MAPLE_BOAT.get(), new HNCDispenseBoatBehavior(HNCBoatEntity.ModType.MAPLE));
            DispenserBlock.registerBehavior(HNCItems.MAPLE_SAP_BUCKET.get(), new MapleSapDispenseBehavior());

            IDispenseItemBehavior behavior = AccessorDispenserBlock.getDispenseBehaviorRegistry().get(Items.GLASS_BOTTLE);
            DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new TreeTapDispenseBehavior.GlassBottle(behavior));

            behavior = AccessorDispenserBlock.getDispenseBehaviorRegistry().get(Items.BUCKET);
            DispenserBlock.registerBehavior(Items.BUCKET, new TreeTapDispenseBehavior.Bucket(behavior));

            DispenserBlock.registerBehavior(HNCItems.MAPLE_SAP_BOTTLE.get(), new TreeTapDispenseBehavior.MapleSapBottle());

            behavior = AccessorDispenserBlock.getDispenseBehaviorRegistry().get(HNCItems.MAPLE_SAP_BUCKET.get());
            DispenserBlock.registerBehavior(HNCItems.MAPLE_SAP_BUCKET.get(), new TreeTapDispenseBehavior.MapleSapBucket(behavior));
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
        ComposterBlock.COMPOSTABLES.put(HNCItems.FOOD_SCRAPS.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.CORN_KERNELS.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCBlocks.MAPLE_LEAVES.get(), .3f);
        ComposterBlock.COMPOSTABLES.put(HNCBlocks.MAPLE_SAPLING.get(), .3f);

        // 40% chance
        ComposterBlock.COMPOSTABLES.put(HNCItems.CRACKED_EGG.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.COOKED_EGG.get(), .4f);
        ComposterBlock.COMPOSTABLES.put(HNCItems.GREEN_EGG.get(), .4f);

        // 50% chance
        ComposterBlock.COMPOSTABLES.put(HNCBlocks.BLOCK_OF_CHEESE.get(), .5f);
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
        ComposterBlock.COMPOSTABLES.put(HNCItems.CORN_COB.get(), .65f);
    }

    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event)
    {
        World level = event.getWorld();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        if (!stack.getItem().equals(Items.GLASS_BOTTLE))
            return;

        RayTraceResult rayTraceResult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
        {
            BlockPos pos = ((BlockRayTraceResult) rayTraceResult).getBlockPos();
            if (!level.mayInteract(player, pos))
                return;
            if (level.getFluidState(pos).is(HNCFluidTags.MAPLE_SAP))
            {
                if (level.isClientSide())
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundCategory.NEUTRAL, 1f, 1f);
                else
                {
                    stack.shrink(1);
                    ItemStack sapStack = new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get());
                    if (!player.inventory.add(sapStack))
                        player.drop(sapStack, false);
                }
            }
        }
    }

    // From: net.minecraft.item.GlassBottleItem
    private static BlockRayTraceResult getPlayerPOVHitResult(World level, PlayerEntity player, RayTraceContext.FluidMode mode)
    {
        float f = player.xRot;
        float f1 = player.yRot;
        Vector3d vector3d = player.getEyePosition(1f);
        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180f));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180f));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        Vector3d vector3d1 = vector3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return level.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, mode, player));
    }

    @SubscribeEvent
    public static void onReloadListener(AddReloadListenerEvent event)
    {
        event.addListener(ChoppingBoardManager.INSTANCE);
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.Climate climate = event.getClimate();

        if (climate.temperature >= .5f && climate.temperature < 1f)
        {
            if (HNCConfig.SERVER.generateWildPineapples.get())
                builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.WILD_PINEAPPLE_PATCH);

            if (HNCConfig.SERVER.generateWildTomatoes.get())
                builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.WILD_TOMATO_PATCH);

            if (HNCConfig.SERVER.generateWildCorn.get())
                builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.WILD_CORN_PATCH);
        }

        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.RIVER)
            builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, HNCFeatures.PLAINS_RIVER_MAPLE_TREE);

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

            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(Items.MUSHROOM_STEW, Items.SUSPICIOUS_STEW, Items.RABBIT_STEW), false, "Stew Cracker", 2, 3, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.POISONOUS_POTATO), false, 1, 2, 16, 2));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "Ham N' Cheese Toastie", 3, 2, 16, 5));
            trades.get(2).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.BEEF, Items.DRIED_KELP, HNCItems.CHEESE_SLICE.get(), HNCItems.TOMATO_SLICE.get()), false, 1, 1, 16, 5));

            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get()), false, "BKT", 3, 2, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "Cheesy Pizza", 3, 4, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_BACON.get(), HNCItems.HAM_SLICE.get()), true, "Carnivore Pizza", 3, 4, 20, 10));
            trades.get(3).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_EGG.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), HNCItems.COOKED_EGG.get()), true, "Vegetarian", 3, 4, 20, 10));

            trades.get(4).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.COOKED_BEEF, HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_HAM_SLICE.get(), Items.COOKED_COD, Items.COOKED_CHICKEN, HNCItems.COOKED_MOUSE.get(), Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON), false, "Carnivore Sandwich", 3, 2, 25, 15));
            trades.get(4).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), HNCItems.COOKED_EGG.get(), HNCItems.CHEESE_SLICE.get()), true, "Bacon & Egg Roll", 2, 1, 25, 15));
            trades.get(4).add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.PINEAPPLE_RING.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get()), true, "Pineapple Pizza", 3, 4, 25, 15));
        }
        // Vanilla
        if (HNCConfig.SERVER.allowButcherTrades.get() && event.getType() == VillagerProfession.BUTCHER)
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
        if (HNCConfig.SERVER.allowFarmerTrades.get() && event.getType() == VillagerProfession.FARMER)
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
                if (MathHelper.nextDouble(level.random, 0d, 1d) <= HNCConfig.SERVER.crackedEggSpawnChance.get())
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

//    @SubscribeEvent
//    public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event)
//    {
//        PlayerEntity player = event.getPlayer();
//        BlockPos pos = event.getPos();
//        ItemStack stack = event.getItemStack();
//        World level = event.getWorld();
//        ChoppingBoardManager.INSTANCE.getBoards().values().forEach(board -> {
//            if (stack.getToolTypes().contains(board.getToolType()) && level.getBlockState(pos).getBlock() == board.getPressurePlate())
//            {
//                level.setBlock(pos, board.getBase().defaultBlockState().setValue(ChoppingBoardBlock.HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(player.getRandom())), Constants.BlockFlags.DEFAULT);
//                level.playSound(player, pos, board.getConvertSound(), SoundCategory.BLOCKS, 1f, 1f);
//                stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(event.getHand()));
//            }
//        });
//    }
}
