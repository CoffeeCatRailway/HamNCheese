package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCItemTags extends ItemTagsProvider
{
    public static final ITag.INamedTag<Item> GEARS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "gears"));
    public static final ITag.INamedTag<Item> GEARS_WOODEN = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "gears/wooden"));

    public static final ITag.INamedTag<Item> WHEAT = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "wheat"));
    public static final ITag.INamedTag<Item> SUGAR = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "sugar"));

    public static final ITag.INamedTag<Item> DOUGH = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "dough"));
    public static final ITag.INamedTag<Item> SALT = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "salt"));
    public static final ITag.INamedTag<Item> FLOUR = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "flour"));

    public static final ITag.INamedTag<Item> BREAD_SLICE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "bread_slice"));
    public static final ITag.INamedTag<Item> CRACKER = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "cracker"));

    public HNCItemTags(DataGenerator generator, BlockTagsProvider provider, @Nullable ExistingFileHelper fileHelper)
    {
        super(generator, provider, HNCMod.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(GEARS_WOODEN).add(HNCItems.WOODEN_GEAR.get());
        this.tag(GEARS).addTag(GEARS_WOODEN);

        this.tag(WHEAT).add(Items.WHEAT);
        this.tag(SUGAR).add(Items.SUGAR);

        this.tag(DOUGH).add(HNCItems.DOUGH.get(), HNCItems.PIZZA_BASE.get());
        this.tag(SALT).add(HNCItems.ROCK_SALT.get());
        this.tag(FLOUR).add(HNCItems.FLOUR.get());

        this.tag(BREAD_SLICE).add(HNCItems.BREAD_SLICE.get());
        this.tag(CRACKER).add(HNCItems.CRACKER.get());

        this.tag(Tags.Items.EGGS).add(HNCItems.CRACKED_EGG.get(), HNCItems.COOKED_EGG.get(), HNCItems.GREEN_EGG.get());
    }
}
