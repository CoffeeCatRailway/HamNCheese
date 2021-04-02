package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCFoods;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 19/03/2021
 */
public class AbstractSandwichItem extends Item
{
    public static final String TAG_INGREDIENTS = "Ingredients";
    public static final String TAG_TOASTED = "Toasted";

    public final FoodProperties foodProperties;
    private ItemStack foodStack;
    private Food food;

    public AbstractSandwichItem(Properties properties, FoodProperties foodProperties)
    {
        super(properties.food(foodProperties.getBunFood()));
        this.foodProperties = foodProperties;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        CompoundNBT stackNbt = stack.getOrCreateTag();

        if (!stackNbt.contains(TAG_INGREDIENTS))
            stackNbt.put(TAG_INGREDIENTS, new ListNBT());

        if (!stackNbt.contains(TAG_TOASTED))
            stackNbt.putBoolean(TAG_TOASTED, false);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public ITextComponent getName(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        ListNBT ingredients = nbt.getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
        ITextComponent name = super.getName(stack);
        if (ingredients.size() > 0)
            name = ItemStack.of((CompoundNBT) ingredients.get(0)).getDisplayName().copy().append(" ").append(super.getName(stack));
        if (nbt.getBoolean(TAG_TOASTED))
            name = new TranslationTextComponent("item.hamncheese.sandwich.toasted").append(" ").append(name);
        return name;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        ListNBT ingredients = stack.getOrCreateTag().getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
        for (INBT ingredient : ingredients)
            tooltip.add(ItemStack.of((CompoundNBT) ingredient).getDisplayName().copy().withStyle(TextFormatting.GRAY));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        Food food = this.getFood(stack.copy());
        if (this.food != food)
            this.food = food;
    }

    @Nullable
    @Override
    public Food getFoodProperties()
    {
        if (this.food != null)
            return this.food;
        else
            return super.getFoodProperties();
    }

    @Override
    public boolean isEdible()
    {
        return true;
    }

//    @Override
//    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
//    {
//        if (!world.isClientSide)
//        {
//            SoundEvent sound = SoundEvents.GENERIC_EAT;
//            Supplier<Float> pitch = () -> 1f + (world.random.nextFloat() - world.random.nextFloat()) * .4f;
//
//            entity.playSound(sound, 1f, pitch.get());
//            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundCategory.NEUTRAL, 1f, pitch.get());
//
//            Food food = this.getFood(stack);
//            if (entity instanceof PlayerEntity)
//            {
//                PlayerEntity player = (PlayerEntity) entity;
//                if (!player.isCreative()) stack.shrink(1);
//                player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
//            }
//
//            for (Pair<EffectInstance, Float> pair : food.getEffects())
//                if (pair.getFirst() != null && world.random.nextFloat() < pair.getSecond())
//                    entity.addEffect(new EffectInstance(pair.getFirst()));
//        }
//        return stack;
//    }

    private Food getFood(ItemStack stack)
    {
        if (this.food == null)
        {
            List<Food> foods = new ArrayList<>();
            CompoundNBT nbt = stack.getOrCreateTag();

            Food bread = this.foodProperties.getBunFood();
            if (nbt.getBoolean(TAG_TOASTED))
                bread = this.foodProperties.getToastedBunFood();
            foods.add(bread);

            ListNBT ingredients = nbt.getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
            for (INBT ingredient : ingredients)
            {
                ItemStack foodStack = ItemStack.of((CompoundNBT) ingredient);
                if (foodStack.isEdible())
                    foods.add(foodStack.getItem().getFoodProperties());
            }

            if (this.foodProperties.hasTwoBuns())
                foods.add(bread);

            this.food = HNCFoods.combine(.2f, nbt.getBoolean(TAG_TOASTED), foods.toArray(new Food[]{})).build();
        }

        return this.food;
    }

    public static ItemStack addIngredient(ItemStack sandwich, ItemStack ingredient)
    {
        if (ingredient.isEdible() && !(ingredient.getItem() instanceof AbstractSandwichItem))
        {
            ItemStack added = ingredient.copy();
            added.setCount(1);

            ListNBT ingredients = sandwich.getOrCreateTag().getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
            if (ingredients.size() <= HNCMod.SERVER_CONFIG.maxSandwichIngredientCraftCount.get())
                ingredients.add(added.save(new CompoundNBT()));
        }
        return sandwich;
    }

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2)
    {
        if (!(stack1.getItem() instanceof AbstractSandwichItem) || !(stack2.getItem() instanceof AbstractSandwichItem))
            return false;
        return ItemStack.isSame(stack1, stack2) && stack1.getItem() == stack2.getItem();
    }

    public static class FoodProperties
    {
        private final Food bunFood;
        private final Food toastedBunFood;
        private final Supplier<? extends Item> bunItem;
        private final Supplier<? extends Item> toastedBunItem;

        private final boolean hasTwoBuns;

        public FoodProperties(Food bunFood, Food toastedBunFood, Supplier<? extends Item> bunItem, Supplier<? extends Item> toastedBunItem, boolean hasTwoBuns)
        {
            this.bunFood = bunFood;
            this.toastedBunFood = toastedBunFood;
            this.bunItem = bunItem;
            this.toastedBunItem = toastedBunItem;

            this.hasTwoBuns = hasTwoBuns;
        }

        public Food getBunFood()
        {
            return bunFood;
        }

        public Food getToastedBunFood()
        {
            return toastedBunFood;
        }

        public Supplier<? extends Item> getBunItem()
        {
            return bunItem;
        }

        public Supplier<? extends Item> getToastedBunItem()
        {
            return toastedBunItem;
        }

        public boolean hasTwoBuns()
        {
            return hasTwoBuns;
        }
    }
}
