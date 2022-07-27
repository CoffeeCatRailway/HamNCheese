package io.github.coffeecatrailway.hamncheese;

import com.google.common.collect.ImmutableList;
import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.client.util.CreativeModeTabBuilder;
import gg.moonflower.pollen.api.config.ConfigManager;
import gg.moonflower.pollen.api.config.PollinatedConfigType;
import gg.moonflower.pollen.api.event.events.entity.EntityEvents;
import gg.moonflower.pollen.api.event.events.entity.ModifyTradesEvents;
import gg.moonflower.pollen.api.event.events.registry.client.RegisterAtlasSpriteEvent;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.EntityAttributeRegistry;
import gg.moonflower.pollen.api.registry.FluidBehaviorRegistry;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.*;
import gg.moonflower.pollen.api.registry.content.CompostablesRegistry;
import gg.moonflower.pollen.api.registry.content.DispenseItemBehaviorRegistry;
import gg.moonflower.pollen.api.registry.resource.CriterionRegistry;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.client.HNCModelLayers;
import io.github.coffeecatrailway.hamncheese.client.blockentity.ChoppingBoardRenderer;
import io.github.coffeecatrailway.hamncheese.client.entity.MouseModel;
import io.github.coffeecatrailway.hamncheese.client.entity.MouseRenderer;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.GrillScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PizzaOvenScreen;
import io.github.coffeecatrailway.hamncheese.client.gui.screens.PopcornMachineScreen;
import io.github.coffeecatrailway.hamncheese.client.item.SandwichItemRenderer;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.ChoppingBoardTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.SwissCheeseTrigger;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.MapleSapDispenseBehavior;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.SandwichExplodeBehavior;
import io.github.coffeecatrailway.hamncheese.common.block.dispenser.TreeTapDispenseBehavior;
import io.github.coffeecatrailway.hamncheese.common.entity.MouseEntity;
import io.github.coffeecatrailway.hamncheese.common.entity.villager.HNCVillagerTrades;
import io.github.coffeecatrailway.hamncheese.common.material.HNCFluidBehavior;
import io.github.coffeecatrailway.hamncheese.data.gen.*;
import io.github.coffeecatrailway.hamncheese.mixins.MobAccessor;
import io.github.coffeecatrailway.hamncheese.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

/**
 * @author CoffeeCatRailway
 * Created: 21/03/2022
 */
public class HamNCheese
{
    public static final String MOD_ID = "hamncheese";
    public static HNCConfig.Server CONFIG_SERVER = ConfigManager.register(MOD_ID, PollinatedConfigType.SERVER, HNCConfig.Server::new);
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(() -> HamNCheese::onClientInit)
            .clientPostInit(() -> HamNCheese::onClientPostInit)
            .commonInit(HamNCheese::onCommonInit)
            .commonPostInit(HamNCheese::onCommonPostInit)
            .dataInit(HamNCheese::onDataInit)
            .build();

    public static final CreativeModeTab TAB = CreativeModeTabBuilder.builder(getLocation("tab")).setIcon(() -> new ItemStack(HNCBlocks.BLOCK_OF_CHEESE.get())).build();

    public static final ResourceLocation EMPTY_SLOT_BAG = getLocation("item/empty_bag_slot");
    public static final ResourceLocation EMPTY_SLOT_KERNELS = getLocation("item/empty_kernels_slot");
    public static final ResourceLocation EMPTY_SLOT_SEASONING = getLocation("item/empty_seasoning_slot");
    public static final ResourceLocation EMPTY_SLOT_FLAVOUR = getLocation("item/empty_flavour_slot");

    public static ChoppingBoardTrigger CHOPPING_BOARD_TRIGGER;
    public static SwissCheeseTrigger SWISS_CHEESE_TRIGGER;

    private static BlockTintCache MAPLE_TINT_CACHE;

    public static void onClientInit()
    {
        SandwichItemRenderer.init();

        BlockEntityRendererRegistry.register(HNCBlockEntities.CHOPPING_BOARD, ChoppingBoardRenderer::new);

        EntityRendererRegistry.registerLayerDefinition(HNCModelLayers.MOUSE, MouseModel::createLayer);
        EntityRendererRegistry.register(HNCEntities.MOUSE, MouseRenderer::new);

        final ColorResolver mapleColorResolver = (biome, d, e) -> {
            double f = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0)).getValue(d * .0225d, e * .0225d, false);
            return f < -.1d ? 0xEC4400 : 0xAE1800;
        };

        ColorRegistry.register((state, tintGetter, pos, tintIndex) -> {
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (((state.is(HNCBlocks.MAPLE_SAPLING.get()) || state.is(HNCBlocks.POTTED_MAPLE_SAPLING.get())) && tintIndex != 0) || clientLevel == null || pos == null)
                return -1;
            if (HamNCheese.MAPLE_TINT_CACHE == null)
                HamNCheese.MAPLE_TINT_CACHE = new BlockTintCache(blockPos -> clientLevel.calculateBlockTint(blockPos, mapleColorResolver));
            return HamNCheese.MAPLE_TINT_CACHE.getColor(pos);
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

    public static void onCommonInit()
    {
        HNCItems.load(PLATFORM);
        HNCBlocks.load(PLATFORM);
        HNCRecipes.load(PLATFORM);
        HNCEntities.load(PLATFORM);
        HNCVillage.load(PLATFORM);
        HNCStats.Localize.load();
        HNCBlockEntities.load(PLATFORM);
        HNCMenus.load(PLATFORM);
        HNCFluids.load(PLATFORM);
        HNCFeatures.load(PLATFORM);
        HNCFeatures.Configured.load(PLATFORM);

        FluidBehaviorRegistry.register(HNCFluidTags.MAPLE_SAP, new HNCFluidBehavior());
        FluidBehaviorRegistry.register(HNCFluidTags.MILK, new HNCFluidBehavior());
        FluidBehaviorRegistry.register(HNCFluidTags.GOAT_MILK, new HNCFluidBehavior());

        CHOPPING_BOARD_TRIGGER = CriterionRegistry.register(new ChoppingBoardTrigger());
        SWISS_CHEESE_TRIGGER = CriterionRegistry.register(new SwissCheeseTrigger());

        EntityAttributeRegistry.register(HNCEntities.MOUSE, MouseEntity::registerAttributeMap);

        EntityEvents.JOIN.register((entity, level) -> {
            if (entity instanceof Ocelot || entity instanceof Cat)
                ((MobAccessor) entity).getTargetSelector().addGoal(1, new NearestAttackableTargetGoal<>((Mob) entity, MouseEntity.class, false));
            return true;
        });
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx)
    {
        ctx.enqueueWork(() -> {
            HNCStats.load(PLATFORM);
            HNCVillage.addStructurePieces();
        });

        ModifyTradesEvents.WANDERER.register(context -> {
            ModifyTradesEvents.TradeRegistry generic = context.getGeneric();
            if (generic.size() > 0)
                generic.add(1, new HNCVillagerTrades.MoldySandwichForEmeralds(ImmutableList.of(HNCItems.GREEN_EGG.get(), Items.ROTTEN_FLESH, HNCItems.BLUE_CHEESE_SLICE.get(), HNCItems.GREEN_HAM_SLICE.get()), "item." + HamNCheese.MOD_ID + ".sandwich.trade.week_old_sandwich", 1, 2));
        });

        ModifyTradesEvents.VILLAGER.register(villagerCtx -> {
            ModifyTradesEvents.TradeRegistry trades;
            VillagerProfession profession = villagerCtx.getProfession();
            // Modded
            if (profession == HNCVillage.CHEF.get())
            {
                trades = villagerCtx.getTrades(1);
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.CRACKER.get(), 2, 4, 10, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.UNBAKED_CRACKER.get(), 2, 4, 10, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.UNBAKED_PIZZA_BASE.get(), 1, 3, 10, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.BREAD_SLICE.get(), 1, 2, 10, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOAST.get(), 1, 2, 10, 2));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get()), false, 2, 3, 8, 2));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.TOMATO_SLICE.get(), HNCItems.HAM_SLICE.get()), false, 1, 2, 8, 2));

                trades = villagerCtx.getTrades(2);
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.CRACKER, ImmutableList.of(Items.MUSHROOM_STEW, Items.SUSPICIOUS_STEW, Items.RABBIT_STEW), false, "item." + HamNCheese.MOD_ID + ".sandwich.trade.stew_cracker", 2, 3, 16, 5));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.POISONOUS_POTATO), false, 1, 2, 16, 2));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.toastie", 3, 2, 16, 5));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.BEEF, Items.DRIED_KELP, HNCItems.CHEESE_SLICE.get(), HNCItems.TOMATO_SLICE.get()), false, 1, 1, 16, 5));

                trades = villagerCtx.getTrades(3);
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get()), false, "item." + HamNCheese.MOD_ID + ".sandwich.trade.bkt", 3, 2, 20, 10));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.CHEESE_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), HNCItems.HAM_SLICE.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.cheesy_pizza", 3, 4, 20, 10));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.CHEESE_SLICE.get(), HNCItems.HAM_SLICE.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_BACON.get(), HNCItems.HAM_SLICE.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.carnivore_pizza", 3, 4, 20, 10));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.COOKED_EGG.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), Items.DRIED_KELP, HNCItems.TOMATO_SLICE.get(), HNCItems.COOKED_EGG.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.vegetarian_pizza", 3, 4, 20, 10));

                trades = villagerCtx.getTrades(4);
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(Items.COOKED_BEEF, HNCItems.COOKED_BACON.get(), Items.COOKED_PORKCHOP, HNCItems.COOKED_HAM_SLICE.get(), Items.COOKED_COD, Items.COOKED_CHICKEN, HNCItems.COOKED_MOUSE.get(), Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON), false, "item." + HamNCheese.MOD_ID + ".sandwich.trade.carnivore_sandwich", 3, 2, 25, 15));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.SANDWICH, ImmutableList.of(HNCItems.COOKED_BACON.get(), HNCItems.COOKED_EGG.get(), HNCItems.CHEESE_SLICE.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.bacon_egg_roll", 2, 1, 25, 15));
                trades.add(new HNCVillagerTrades.SandwichForEmeralds(HNCItems.PIZZA, ImmutableList.of(HNCItems.PINEAPPLE_RING.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get(), HNCItems.CHEESE_SLICE.get(), HNCItems.PINEAPPLE_BIT.get(), HNCItems.HAM_SLICE.get()), true, "item." + HamNCheese.MOD_ID + ".sandwich.trade.pineapple_pizza", 3, 4, 25, 15));
            }
            // Vanilla
            if (CONFIG_SERVER.allowButcherTrades.get() && profession == VillagerProfession.BUTCHER)
            {
                trades = villagerCtx.getTrades(1);
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.HAM_SLICE.get(), 14, 16, 2));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.BACON.get(), 14, 16, 2));

                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.WOODEN_CURDLER.get(), 1, 16, 2));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.WOODEN_ROLLING_PIN.get(), 1, 16, 2));

                trades = villagerCtx.getTrades(2);
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_HAM_SLICE.get(), 9, 16, 5));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.COOKED_BACON.get(), 9, 16, 5));

                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.GRIND_STONES.get(), 1, 16, 5));

                villagerCtx.getTrades(3).add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.IRON_KNIFE.get(), 1, 8, 5));
            }
            if (CONFIG_SERVER.allowFarmerTrades.get() && profession == VillagerProfession.FARMER)
            {
                trades = villagerCtx.getTrades(1);
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.TOMATO_SEEDS.get(), 18, 16, 2));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.PINEAPPLE_PLANT.get(), 18, 16, 2));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.CORN_COB.get(), 18, 16, 2));

                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOMATO_SEEDS.get(), 1, 9, 16, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.PINEAPPLE_PLANT.get(), 1, 9, 16, 2));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.CORN_COB.get(), 1, 9, 16, 2));

                trades = villagerCtx.getTrades(2);
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.TOMATO.get(), 20, 16, 5));
                trades.add(new HNCVillagerTrades.EmeraldForItemsTrade(HNCItems.PINEAPPLE.get(), 20, 16, 5));

                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.TOMATO.get(), 2, 15, 16, 5));
                trades.add(new HNCVillagerTrades.ItemsForEmeraldsTrade(HNCItems.PINEAPPLE.get(), 2, 15, 16, 5));
            }
        });

        // Dispenser Behaviors
        DispenseItemBehaviorRegistry.register(HNCItems.SANDWICH.get(), new SandwichExplodeBehavior(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get(), true));
        DispenseItemBehaviorRegistry.register(HNCItems.CRACKER.get(), new SandwichExplodeBehavior(HNCItems.CRACKER.get(), HNCItems.CRACKER.get(), false));
        DispenseItemBehaviorRegistry.register(HNCItems.PIZZA.get(), new SandwichExplodeBehavior(HNCItems.UNBAKED_PIZZA_BASE.get(), HNCItems.BAKED_PIZZA_DUMMY.get(), false, HamNCheese.CONFIG_SERVER.dispenseTomatoSauce.get() ? new Item[]{HNCItems.TOMATO_SAUCE.get()} : new Item[]{}));

        DispenseItemBehaviorRegistry.register(HNCFluids.MAPLE_SAP_BUCKET.get(), new MapleSapDispenseBehavior());

        DispenseItemBehavior behavior = getBehavior(Items.GLASS_BOTTLE);
        DispenseItemBehaviorRegistry.register(Items.GLASS_BOTTLE, new TreeTapDispenseBehavior.GlassBottle(behavior));

        behavior = getBehavior(Items.BUCKET);
        DispenseItemBehaviorRegistry.register(Items.BUCKET, new TreeTapDispenseBehavior.Bucket(behavior));

        DispenseItemBehaviorRegistry.register(HNCItems.MAPLE_SAP_BOTTLE.get(), new TreeTapDispenseBehavior.MapleSapBottle());

        behavior = getBehavior(HNCFluids.MAPLE_SAP_BUCKET.get());
        DispenseItemBehaviorRegistry.register(HNCFluids.MAPLE_SAP_BUCKET.get(), new TreeTapDispenseBehavior.MapleSapBucket(behavior));


        // Composter
        // 30% chance
        CompostablesRegistry.register(HNCItems.CHEESE_SLICE.get(), .3f);
        CompostablesRegistry.register(HNCItems.HAM_SLICE.get(), .3f);
        CompostablesRegistry.register(HNCItems.COOKED_HAM_SLICE.get(), .3f);
        CompostablesRegistry.register(HNCItems.GREEN_HAM_SLICE.get(), .3f);
        CompostablesRegistry.register(HNCItems.BACON.get(), .3f);
        CompostablesRegistry.register(HNCItems.COOKED_BACON.get(), .3f);
        CompostablesRegistry.register(HNCItems.PINEAPPLE_PLANT.get(), .3f);
        CompostablesRegistry.register(HNCItems.TOMATO_SEEDS.get(), .3f);
        CompostablesRegistry.register(HNCItems.FOOD_SCRAPS.get(), .3f);
        CompostablesRegistry.register(HNCItems.CORN_KERNELS.get(), .3f);
        CompostablesRegistry.register(HNCBlocks.MAPLE_LEAVES.get(), .3f);
        CompostablesRegistry.register(HNCBlocks.MAPLE_SAPLING.get(), .3f);

        // 40% chance
        CompostablesRegistry.register(HNCItems.CRACKED_EGG.get(), .4f);
        CompostablesRegistry.register(HNCItems.COOKED_EGG.get(), .4f);
        CompostablesRegistry.register(HNCItems.GREEN_EGG.get(), .4f);

        // 50% chance
        CompostablesRegistry.register(HNCBlocks.BLOCK_OF_CHEESE.get(), .5f);
        CompostablesRegistry.register(HNCItems.DOUGH.get(), .5f);
        CompostablesRegistry.register(HNCItems.UNBAKED_PIZZA_BASE.get(), .5f);
        CompostablesRegistry.register(HNCItems.UNBAKED_BREAD.get(), .5f);
        CompostablesRegistry.register(HNCItems.BREAD_SLICE.get(), .5f);
        CompostablesRegistry.register(HNCItems.TOAST.get(), .5f);
        CompostablesRegistry.register(HNCItems.UNBAKED_CRACKER.get(), .5f);
        CompostablesRegistry.register(HNCItems.PINEAPPLE_RING.get(), .5f);
        CompostablesRegistry.register(HNCItems.PINEAPPLE_BIT.get(), .5f);
        CompostablesRegistry.register(HNCItems.TOMATO_SLICE.get(), .5f);

        // 65% chance
        CompostablesRegistry.register(HNCItems.PINEAPPLE.get(), .65f);
        CompostablesRegistry.register(HNCItems.TOMATO.get(), .65f);
        CompostablesRegistry.register(HNCItems.CORN_COB.get(), .65f);

        StrippingRegistry.register(HNCBlocks.MAPLE_LOG.get(), HNCBlocks.STRIPPED_MAPLE_LOG.get());
        StrippingRegistry.register(HNCBlocks.MAPLE_WOOD.get(), HNCBlocks.STRIPPED_MAPLE_WOOD.get());

        HNCEntities.registerSpawnPlacements();
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
        generator.addProvider(new HNCAdvancements(generator));
        generator.addProvider(new HNCLanguage(generator, container));
        generator.addProvider(new HNCRecipeProvider(generator));
        generator.addProvider(new HNCLootTableProvider(generator));
    }

    public static ResourceLocation getLocation(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }

    @ExpectPlatform
    public static boolean mobGriefing(Level level, Entity entity)
    {
        return Platform.error();
    }
}
