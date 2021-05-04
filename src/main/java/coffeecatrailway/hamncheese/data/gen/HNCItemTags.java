package coffeecatrailway.hamncheese.data.gen;

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

    public static final ITag.INamedTag<Item> CHEESE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "cheese"));

    public static final ITag.INamedTag<Item> SALT = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "salt"));
    public static final ITag.INamedTag<Item> FLOUR = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "flour"));

    public static final ITag.INamedTag<Item> DOUGH = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "dough"));

    public static final ITag.INamedTag<Item> PIZZA = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "pizza"));

    public static final ITag.INamedTag<Item> BREAD_SLICE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "bread_slice"));
    public static final ITag.INamedTag<Item> BREAD = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "bread"));

    public static final ITag.INamedTag<Item> CRACKER = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "cracker"));

    public static final ITag.INamedTag<Item> EGG_MOD = net.minecraft.tags.ItemTags.createOptional(HNCMod.getLocation("eggs"));
    public static final ITag.INamedTag<Item> EGG = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "egg"));
    public static final ITag.INamedTag<Item> CRACKED_EGG = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "cracked_egg"));

    public static final ITag.INamedTag<Item> HAM = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "ham"));
    public static final ITag.INamedTag<Item> BACON = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "bacon"));

    public static final ITag.INamedTag<Item> PINEAPPLE_MOD = net.minecraft.tags.ItemTags.createOptional(HNCMod.getLocation("pineapple"));
    public static final ITag.INamedTag<Item> PINEAPPLE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "pineapple"));

    public static final ITag.INamedTag<Item> TOMATO_MOD = net.minecraft.tags.ItemTags.createOptional(HNCMod.getLocation("tomato"));
    public static final ITag.INamedTag<Item> TOMATO = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "tomato"));
    public static final ITag.INamedTag<Item> TOMATO_SAUCE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "tomato_sauce"));

    public static final ITag.INamedTag<Item> MOUSE_BLACKLIST = net.minecraft.tags.ItemTags.createOptional(HNCMod.getLocation("mouse_blacklist"));

    public static final ITag.INamedTag<Item> FRUITS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "fruits"));
    public static final ITag.INamedTag<Item> CROPS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "crops"));
    public static final ITag.INamedTag<Item> CARBS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "carbs"));
    public static final ITag.INamedTag<Item> SEEDS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", "seeds"));

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

        this.tag(CHEESE).add(HNCItems.BLOCK_OF_CHEESE.get(), HNCItems.CHEESE_SLICE.get());

        this.tag(SALT).add(HNCItems.ROCK_SALT.get());
        this.tag(FLOUR).add(HNCItems.FLOUR.get());

        this.tag(DOUGH).add(HNCItems.DOUGH.get(), HNCItems.UNBAKED_PIZZA_BASE.get());

        this.tag(PIZZA).add(HNCItems.UNBAKED_PIZZA_BASE.get());

        this.tag(BREAD_SLICE).add(HNCItems.BREAD_SLICE.get());
        this.tag(BREAD).add(HNCItems.BREAD_SLICE.get());

        this.tag(CRACKER).add(HNCItems.CRACKER.get());

        this.tag(EGG_MOD).add(HNCItems.CRACKED_EGG.get(), HNCItems.COOKED_EGG.get(), HNCItems.GREEN_EGG.get());
        this.tag(Tags.Items.EGGS).addTag(EGG_MOD);
        this.tag(EGG).addTag(EGG_MOD);
        this.tag(CRACKED_EGG).addTag(EGG_MOD);

        this.tag(HAM).add(HNCItems.HAM_SLICE.get(), HNCItems.COOKED_HAM_SLICE.get(), HNCItems.GREEN_HAM_SLICE.get());
        this.tag(BACON).add(HNCItems.BACON.get(), HNCItems.COOKED_BACON.get());

        this.tag(PINEAPPLE_MOD).add(HNCItems.PINEAPPLE.get(), HNCItems.PINEAPPLE_RING.get(), HNCItems.PINEAPPLE_BIT.get());
        this.tag(PINEAPPLE).addTag(PINEAPPLE_MOD);

        this.tag(TOMATO_MOD).add(HNCItems.TOMATO.get(), HNCItems.TOMATO_SLICE.get());
        this.tag(TOMATO).addTag(TOMATO_MOD);
        this.tag(TOMATO_SAUCE).add(HNCItems.TOMATO_SAUCE.get());

        this.tag(FRUITS).addTags(PINEAPPLE_MOD, TOMATO_MOD);
        this.tag(CROPS).addTags(PINEAPPLE_MOD, TOMATO_MOD);
        this.tag(CARBS).add(HNCItems.BREAD_SLICE.get(), HNCItems.TOAST.get());
        this.tag(SEEDS).add(HNCItems.TOMATO_SEEDS.get());

        this.tag(MOUSE_BLACKLIST).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, HNCItems.MOUSE.get(), HNCItems.COOKED_MOUSE.get(), HNCItems.FOOD_SCRAPS.get());
    }
}
