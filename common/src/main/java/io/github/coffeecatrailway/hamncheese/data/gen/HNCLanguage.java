package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.PollinatedLanguageProvider;
import gg.moonflower.pollen.api.util.PollinatedModContainer;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCLanguage extends PollinatedLanguageProvider
{
    public static final Map<String, String> EXTRA = new HashMap<>();
    public static final Map<Supplier<? extends Item>, String> ITEMS = new HashMap<>();
    public static final Map<Supplier<? extends Block>, String> BLOCKS = new HashMap<>();
    public static final Map<Supplier<? extends EntityType<?>>, String> ENTITIES = new HashMap<>();
    public static final Map<Supplier<VillagerProfession>, String> PROFESSIONS = new HashMap<>();

    public HNCLanguage(DataGenerator generator, PollinatedModContainer container)
    {
        super(generator, container, "en_us");
    }

    @Override
    protected void registerTranslations()
    {
        this.add("itemGroup." + HamNCheese.MOD_ID + ".tab", "Ham N' Cheese");

        this.add("item." + HamNCheese.MOD_ID + ".sandwich.toasted", "Toasted");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.hunger", "Hunger: %s");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.saturation", "Saturation: %s");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.info", "to show ingredients");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.week_old_sandwich", "Week Old Sandwich");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.stew_cracker", "Stew Cracker");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.toastie", "Ham N' Cheese Toastie");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.bkt", "BKT");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.cheesy_pizza", "Cheesy Pizza");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.carnivore_pizza", "Carnivore Pizza");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.vegetarian_pizza", "Vegetarian Pizza");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.carnivore_sandwich", "Carnivore Sandwich");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.bacon_egg_roll", "Bacon & Egg Roll");
        this.add("item." + HamNCheese.MOD_ID + ".sandwich.trade.pineapple_pizza", "Pineapple Pizza");

        this.add("item." + HamNCheese.MOD_ID + ".green_food.main", "Yay to Dr. Seuss for his green eggs and ham!");
        this.add("item." + HamNCheese.MOD_ID + ".green_food.birthday_line1", "And a happy birthday to the great Dr. Seuss!");
        this.add("item." + HamNCheese.MOD_ID + ".green_food.birthday_line2", "Born 2 March 1904, Died 24 September 1991");

        this.add("tooltip." + HamNCheese.MOD_ID + ".hold_shift", "[Hold SHIFT %s]");

        this.add("block." + HamNCheese.MOD_ID + ".from", "From: ");
        this.add("block." + HamNCheese.MOD_ID + ".maple_sign", "Maple Sign");
        this.add("block." + HamNCheese.MOD_ID + ".tree_tap.description", "Used to collect maple sap from maple trees");

        this.add("waila." + HamNCheese.MOD_ID + ".tree_tap.level", "Sap Level: %s");
        this.add("waila." + HamNCheese.MOD_ID + ".progress", "Progress: %s");

        this.add("jei." + HamNCheese.MOD_ID + ".maple_sap", "Maple sap is made by placing a tree tap on a maple log, then by right-clicking with an empty bucket");
        this.add("jei." + HamNCheese.MOD_ID + ".category.chopping_board", "Chopping Board");

        this.add("container." + HamNCheese.MOD_ID + ".popcorn_machine.flavour", "Flavour: %s/" + PopcornMachineBlockEntity.MAX_FLAVOUR_TIME + "mb");
        this.add("container." + HamNCheese.MOD_ID + ".popcorn_machine.popcorn", "Popcorn: %s/" + PopcornMachineBlockEntity.MAX_POPCORN);

        this.add("fluid." + HamNCheese.MOD_ID + ".maple_sap", "Maple Sap");
        this.add("fluid." + HamNCheese.MOD_ID + ".milk", "Milk");
        this.add("fluid." + HamNCheese.MOD_ID + ".goat_milk", "Goat Milk");

        EXTRA.forEach(this::add);
        ITEMS.forEach(this::addItem);
        BLOCKS.forEach(this::addBlock);
        ENTITIES.forEach(this::addEntityType);
        PROFESSIONS.forEach((profession, name) -> {
            this.add("entity.minecraft.villager." + HamNCheese.MOD_ID + "." + profession.get(), name);
            this.add("entity.minecraft.villager." + profession.get(), name);
        });
    }

    public static Component shiftInfo(Object info)
    {
        return new TranslatableComponent("tooltip." + HamNCheese.MOD_ID + ".hold_shift", info).withStyle(ChatFormatting.DARK_GRAY);
    }

    public static TranslatableComponent getFlavour(int flavour)
    {
        return new TranslatableComponent("container." + HamNCheese.MOD_ID + ".popcorn_machine.flavour", flavour);
    }

    public static TranslatableComponent getPopcorn(int popcorn)
    {
        return new TranslatableComponent("container." + HamNCheese.MOD_ID + ".popcorn_machine.popcorn", popcorn);
    }

    public static String capitalize(String id)
    {
        String[] names = id.split("_");
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String name : names)
        {
            builder.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
            i++;
            if (i != names.length)
                builder.append(" ");
        }
        return builder.toString();
    }
}
