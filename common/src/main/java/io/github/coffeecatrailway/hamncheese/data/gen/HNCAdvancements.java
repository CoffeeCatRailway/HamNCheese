package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.PollinatedAdvancementProvider;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.ChoppingBoardTrigger;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.SwissCheeseTrigger;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 26/07/2022
 */
public class HNCAdvancements extends PollinatedAdvancementProvider
{
    public HNCAdvancements(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> registry)
    {
        Advancement root = this.register("root", builder -> builder.display(
                        Items.MILK_BUCKET,
                        new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".root.title"),
                        new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".root.description"),
                        HamNCheese.getLocation("textures/gui/advancements/backgrounds/cheese.png"),
                        FrameType.TASK,
                        false,
                        true,
                        false
                ).addCriterion("milk_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MILK_BUCKET)),
                "Smells Cheesy", "Obtain a milk bucket", registry);

        Advancement grind = this.register("grind", builder -> builder.parent(root)
                        .display(
                                HNCItems.GRIND_STONES.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".grind.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".grind.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("grind_stones", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.GRIND_STONES.get())),
                "Grind! Grind! Grind!", "Craft gind stones", registry);
        Advancement rise = this.register("rise", builder -> builder.parent(grind)
                        .display(
                                HNCItems.FLOUR.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".rise.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".rise.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("flour", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.FLOUR.get())),
                "RISE!!", "Craft flour", registry);
        Advancement salty = this.register("salty", builder -> builder.parent(grind)
                        .display(
                                HNCItems.ROCK_SALT.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".salty.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".salty.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("salt", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.ROCK_SALT.get())),
                "Ahh! Salty!", "Craft rock salt", registry);

        Advancement rolling = this.register("rolling", builder -> builder.parent(root)
                        .display(
                                HNCItems.STONE_ROLLING_PIN.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".rolling.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".rolling.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("rolling_pin", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(HNCItemTags.ROLLING_PINS).build())),
                "They see me rollin!", "Craft a rolling pin", registry);
        Advancement chopChop = this.register("chop_chop", builder -> builder.parent(rolling)
                        .display(
                                HNCBlocks.OAK_CHOPPING_BOARD.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".chop_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".chop_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("chopping_board", ChoppingBoardTrigger.TriggerInstance.useChoppingBoard()),
                "Chop! Chop!", "Use a chopping board", registry);
        Advancement croissant = this.register("croissant", builder -> builder.parent(chopChop)
                        .display(
                                HNCItems.UNBAKED_CROISSANT.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".croissant.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".croissant.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("croissant", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.UNBAKED_CROISSANT.get())),
                "CROISSANT!", "Are you gonna eat that croissant..?", registry);
        Advancement woodChop = this.register("wood_chop", builder -> builder.parent(root)
                        .display(
                                HNCItems.WOODEN_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".wood_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".wood_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("wooden_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.WOODEN_KNIFE.get())),
                "Cooks first chop!", "Craft a wooden knife", registry);
        Advancement expensiveChop = this.register("expensive_chop", builder -> builder.parent(woodChop)
                        .display(
                                HNCItems.GOLDEN_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".expensive_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".expensive_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("gold_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.GOLDEN_KNIFE.get())),
                "Must be expensive food!", "Craft a golden knife", registry);
        Advancement ironChop = this.register("iron_chop", builder -> builder.parent(expensiveChop)
                        .display(
                                HNCItems.IRON_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".iron_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".iron_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("iron_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.IRON_KNIFE.get())),
                "Upgrade!", "Craft an iron knife", registry);
        Advancement netheriteChop = this.register("netherite_chop", builder -> builder.parent(ironChop)
                        .display(
                                HNCItems.NETHERITE_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".netherite_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".netherite_chop.description"),
                                null,
                                FrameType.CHALLENGE,
                                true,
                                true,
                                false
                        ).addCriterion("netherite_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.NETHERITE_KNIFE.get())),
                "High quality", "Craft a netherite knife", registry);

        Advancement sizzle = this.register("sizzle", builder -> builder.parent(woodChop)
                        .display(
                                HNCItems.COOKED_BACON.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".sizzle.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".sizzle.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("bacon", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.BACON.get())),
                "Sizzle...", "Craft a bacon", registry);
    }

    private Advancement register(String id, Function<Advancement.Builder, Advancement.Builder> factory, String title, String description, Consumer<Advancement> registry)
    {
        Advancement advancement = factory.apply(Advancement.Builder.advancement()).save(registry, HamNCheese.MOD_ID + ":" + id);
        HNCLanguage.EXTRA.put(((TranslatableComponent) advancement.getDisplay().getTitle()).getKey(), title);
        HNCLanguage.EXTRA.put(((TranslatableComponent) advancement.getDisplay().getDescription()).getKey(), description);
        return advancement;
    }
}
