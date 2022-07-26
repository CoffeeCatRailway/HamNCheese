package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.PollinatedAdvancementProvider;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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
    }

    private Advancement register(String id, Function<Advancement.Builder, Advancement.Builder> factory, String title, String description, Consumer<Advancement> registry)
    {
        Advancement advancement = factory.apply(Advancement.Builder.advancement()).save(registry, HamNCheese.MOD_ID + "/" + id);
        HNCLanguage.EXTRA.put(((TranslatableComponent) advancement.getDisplay().getTitle()).getKey(), title);
        HNCLanguage.EXTRA.put(((TranslatableComponent) advancement.getDisplay().getDescription()).getKey(), description);
        return advancement;
    }
}
