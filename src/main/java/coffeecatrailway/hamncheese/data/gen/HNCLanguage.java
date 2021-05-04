package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCLanguage extends LanguageProvider
{
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
        this.add("itemGroup." + HNCMod.MOD_ID, "Ham N' Cheese");
        this.add("item.hamncheese.sandwich.toasted", "Toasted");
        ITEMS.forEach((item, name) -> this.add(item.get(), name));
        BLOCKS.forEach((block, name) -> this.add(block.get(), name));
        ENTITIES.forEach((entity, name) -> this.add(entity.get(), name));
        PROFESSIONS.forEach((profession, name) -> this.add("entity.minecraft.villager." + HNCMod.MOD_ID + "." + profession.get(), name));
    }

    public static String capitalize(String id) {
        String[] names = id.split("_");
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String name : names)
        {
            builder.append(name.substring(0, 1).toUpperCase() + name.substring(1));
            i++;
            if (i != names.length)
                builder.append(" ");
        }
        return builder.toString();
    }
}
