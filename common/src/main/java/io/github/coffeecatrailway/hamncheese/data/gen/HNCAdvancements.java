package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.PollinatedAdvancementProvider;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.*;
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

        Advancement curdler = this.register("curdler", builder -> builder.parent(root)
                        .display(
                                HNCItems.STONE_CURDLER.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".curdler.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".curdler.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("curdler", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(HNCItemTags.CURDLERS).build())),
                "Cheese making time!", "Craft a cheese curdler", registry);
        Advancement cheese = this.register("cheese", builder -> builder.parent(curdler)
                        .display(
                                HNCBlocks.BLOCK_OF_CHEESE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".cheese.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".cheese.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("cheese", InventoryChangeTrigger.TriggerInstance.hasItems(HNCBlocks.BLOCK_OF_CHEESE.get())),
                "Cheese!", "Craft a block of cheese", registry);
        Advancement stinky = this.register("stinky", builder -> builder.parent(cheese)
                        .display(
                                HNCBlocks.BLOCK_OF_BLUE_CHEESE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".stinky.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".stinky.description"),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                true
                        ).addCriterion("blue_cheese", BlueCheeseTrigger.TriggerInstance.waitForBlueCheese()),
                "Stinky!", "Wait for a block of cheese to turn into blue cheese", registry);
        Advancement waxOn = this.register("wax_on", builder -> builder.parent(cheese)
                        .display(
                                HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".wax_on.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".wax_on.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("gouda_cheese", InventoryChangeTrigger.TriggerInstance.hasItems(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get())),
                "Wax on! Wax off!", "Craft gouda cheese", registry);
        Advancement holy = this.register("holy", builder -> builder.parent(cheese)
                        .display(
                                HNCBlocks.BLOCK_OF_SWISS_CHEESE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".holy.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".holy.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("swiss_cheese", SwissCheeseTrigger.TriggerInstance.makeSwissCheese()),
                "Holy cheese!", "Divine! Right-click a block of cheese with shears", registry);
        Advancement goatCheese = this.register("goat_cheese", builder -> builder.parent(curdler)
                        .display(
                                HNCBlocks.BLOCK_OF_GOAT_CHEESE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".goat_cheese.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".goat_cheese.description"),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                true
                        ).addCriterion("gouda_cheese", InventoryChangeTrigger.TriggerInstance.hasItems(HNCBlocks.BLOCK_OF_GOAT_CHEESE.get())),
                "Horny cheese!", "I meant goat horns, what did you think?", registry);

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
        Advancement crudeChop = this.register("curde_chop", builder -> builder.parent(woodChop)
                        .display(
                                HNCItems.STONE_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".curde_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".curde_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("stone_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.STONE_KNIFE.get())),
                "A bit crude...", "Craft a stone knife", registry);
        Advancement staticChop = this.register("static_chop", builder -> builder.parent(crudeChop)
                        .display(
                                HNCItems.COPPER_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".static_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".static_chop.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("copper_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.COPPER_KNIFE.get())),
                "Electrifying food", "Craft a copper knife", registry);
        Advancement expensiveChop = this.register("expensive_chop", builder -> builder.parent(crudeChop)
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
        Advancement diamondChop = this.register("diamond_chop", builder -> builder.parent(ironChop)
                        .display(
                                HNCItems.DIAMOND_KNIFE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".diamond_chop.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".diamond_chop.description"),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                false
                        ).addCriterion("diamond_knife", InventoryChangeTrigger.TriggerInstance.hasItems(HNCItems.DIAMOND_KNIFE.get())),
                "Now that's a knife!", "Craft a diamond knife", registry);
        Advancement netheriteChop = this.register("netherite_chop", builder -> builder.parent(diamondChop)
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

        Advancement hamSlice = this.register("ham_slice", builder -> builder.parent(woodChop)
                        .display(
                                HNCItems.HAM_SLICE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".ham_slice.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".ham_slice.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("ham_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(HNCItemTags.HAM_COMMON).build())),
                "Poor piggy...", "Craft a ham slice", registry);
        Advancement sizzle = this.register("sizzle", builder -> builder.parent(hamSlice)
                        .display(
                                HNCItems.COOKED_BACON.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".sizzle.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".sizzle.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("bacon", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(HNCItemTags.BACON_COMMON).build())),
                "Sizzle...", "Craft a bacon", registry);

        Advancement cheeseSlice = this.register("cheese_slice", builder -> builder.parent(woodChop)
                        .display(
                                HNCItems.CHEESE_SLICE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".cheese_slice.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".cheese_slice.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("cheese_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(HNCItemTags.CHEESE_SLICE_COMMON).build())),
                "Best thing since sliced cheese", "Craft a cheese slice", registry);

        Advancement pestControl = this.register("pest_control", builder -> builder.parent(root)
                        .display(
                                HNCItems.MOUSE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".pest_control.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".pest_control.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("pest_control", PestControlTrigger.TriggerInstance.killOrScareMouse(PestControlTrigger.Type.NORMAL)),
                "Pest control", "Kill a mouse or scare one with a tamed cat", registry);
        Advancement goodKitty = this.register("good_kitty", builder -> builder.parent(pestControl)
                        .display(
                                Items.SALMON,
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".good_kitty.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".good_kitty.description"),
                                null,
                                FrameType.TASK,
                                true,
                                true,
                                false
                        ).addCriterion("good_kitty", GoodKittyTrigger.TriggerInstance.killMouse()),
                "Good kitty!", "Kill a mouse with a tamed cat", registry);
        Advancement antiPestControl = this.register("anti_pest_control", builder -> builder.parent(pestControl)
                        .display(
                                HNCItems.MOUSE.get(),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".anti_pest_control.title"),
                                new TranslatableComponent("advancements." + HamNCheese.MOD_ID + ".anti_pest_control.description"),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                true
                        ).addCriterion("anti_pest_control", PestControlTrigger.TriggerInstance.killOrScareMouse(PestControlTrigger.Type.ANTI)),
                "Anti Pest Control", "Breed two mice", registry);
    }

    private Advancement register(String id, Function<Advancement.Builder, Advancement.Builder> factory, String title, String description, Consumer<Advancement> registry)
    {
        Advancement advancement = factory.apply(Advancement.Builder.advancement()).save(registry, HamNCheese.MOD_ID + ":" + id);
        if (advancement.getDisplay().getTitle() instanceof TranslatableComponent component)
            HNCLanguage.EXTRA.put(component.getKey(), title);
        if (advancement.getDisplay().getDescription() instanceof TranslatableComponent component)
            HNCLanguage.EXTRA.put(component.getKey(), description);
        return advancement;
    }
}
