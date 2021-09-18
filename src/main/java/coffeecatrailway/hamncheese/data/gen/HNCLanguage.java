package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.PopcornMachineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
@SuppressWarnings("unchecked")
public class HNCLanguage extends LanguageProvider
{
    public static final Map<String, String> EXTRA = new HashMap<>();
    public static final Map<RegistryObject<? extends Item>, String> ITEMS = new HashMap<>();
    public static final Map<RegistryObject<? extends Block>, String> BLOCKS = new HashMap<>();
    public static final Map<RegistryObject<? extends EntityType<?>>, String> ENTITIES = new HashMap<>();
    public static final Map<RegistryObject<VillagerProfession>, String> PROFESSIONS = new HashMap<>();

    public HNCLanguage(DataGenerator generator)
    {
        super(generator, HNCMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        this.add("itemGroup." + HNCMod.MOD_ID + "", "Ham N' Cheese");
        this.add("item." + HNCMod.MOD_ID + ".sandwich.toasted", "Toasted");
        this.add("block." + HNCMod.MOD_ID + "from", "From: ");
        this.add("top." + HNCMod.MOD_ID + ".progress", "Progress: ");
        this.add("top." + HNCMod.MOD_ID + ".chop_chop", "Chop! Chop!");
        this.add("jei." + HNCMod.MOD_ID + ".chopping_board.how", "A chopping board is made by right-clicking a pressure plate with it's respective tool type.");
        this.add("jei." + HNCMod.MOD_ID + ".chopping_board.example", "Example: Right-clicking a wooden pressure plate with an axe will turn it into a wooden chopping board.");
        this.add("jei." + HNCMod.MOD_ID + ".maple_sap", "Maple sap is made by placing a tree tap on a maple log, then by right-clicking with an empty bucket");
        this.add("container." + HNCMod.MOD_ID + ".popcorn_machine.flavour", "Flavour: %s/" + PopcornMachineTileEntity.MAX_FLAVOUR_TIME + "mb");
        this.add("container." + HNCMod.MOD_ID + ".popcorn_machine.popcorn", "Popcorn: %s/" + PopcornMachineTileEntity.MAX_POPCORN);
        this.add("fluid." + HNCMod.MOD_ID + ".maple_sap", "Maple Sap");
        EXTRA.forEach(this::add);
        ITEMS.forEach((item, name) -> this.add(item.get(), name));
        BLOCKS.forEach((block, name) -> this.add(block.get(), name));
        ENTITIES.forEach((entity, name) -> this.add(entity.get(), name));
        PROFESSIONS.forEach((profession, name) -> this.add("entity.minecraft.villager." + HNCMod.MOD_ID + "." + profession.get(), name));
    }

    public static ITextComponent getFlavour(int flavour)
    {
        return new TranslationTextComponent("container." + HNCMod.MOD_ID + ".popcorn_machine.flavour", flavour);
    }

    public static ITextComponent getPopcorn(int popcorn)
    {
        return new TranslationTextComponent("container." + HNCMod.MOD_ID + ".popcorn_machine.popcorn", popcorn);
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
