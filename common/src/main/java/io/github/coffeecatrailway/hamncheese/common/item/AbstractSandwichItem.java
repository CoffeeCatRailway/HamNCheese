package io.github.coffeecatrailway.hamncheese.common.item;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 19/03/2021
 */
public class AbstractSandwichItem extends Item
{
    public static final String TAG_INGREDIENTS = "Ingredients";
    public static final String TAG_TOASTED = "Toasted";

    public final SandwichProperties sandwichProperties;

    public AbstractSandwichItem(Properties properties, SandwichProperties sandwichProperties)
    {
        super(properties.food(sandwichProperties.bunFood));
        this.sandwichProperties = sandwichProperties;
    }

    public static void init(ItemStack stack)
    {
        if (stack.getItem() instanceof AbstractSandwichItem)
        {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains(AbstractSandwichItem.TAG_INGREDIENTS))
                tag.put(AbstractSandwichItem.TAG_INGREDIENTS, new ListTag());

            if (((AbstractSandwichItem) stack.getItem()).sandwichProperties.canBeToasted && !tag.contains(AbstractSandwichItem.TAG_TOASTED, NbtConstants.BYTE))
                tag.putBoolean(AbstractSandwichItem.TAG_TOASTED, false);
        }
    }

    @Override
    public Component getName(ItemStack stack)
    {
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag ingredients = nbt.getList(TAG_INGREDIENTS, NbtConstants.COMPOUND);
        Component name = super.getName(stack);
        if (ingredients.size() > 0)
            name = ItemStack.of((CompoundTag) ingredients.get(0)).getHoverName().copy().append(" ").append(super.getName(stack));
        return name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        List<Item> ingredients = stack.getOrCreateTag().getList(TAG_INGREDIENTS, NbtConstants.COMPOUND).stream().map(nbt -> ItemStack.of((CompoundTag) nbt).getItem()).toList();
        Set<Item> ingredientSet = new HashSet<>(ingredients);
        ingredientSet.forEach(item -> tooltip.add(new ItemStack(item).getHoverName().copy().withStyle(ChatFormatting.GRAY).append(" x").append(String.valueOf(Collections.frequency(ingredients, item)))));

        FoodProperties foodProperties = this.getFood(stack);
        if (foodProperties.getNutrition() > 0 || foodProperties.getSaturationModifier() > 0f)
        {
            tooltip.add(new TextComponent(""));
            tooltip.add(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".sandwich.hunger", foodProperties.getNutrition()).withStyle(ChatFormatting.GRAY));
            tooltip.add(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".sandwich.saturation", new DecimalFormat("#.##").format(foodProperties.getSaturationModifier())).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean isEdible()
    {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity)
    {
        if (!world.isClientSide)
        {
            SoundEvent sound = SoundEvents.GENERIC_EAT;
            Supplier<Float> pitch = () -> 1f + (world.random.nextFloat() - world.random.nextFloat()) * .4f;

            entity.playSound(sound, 1f, pitch.get());
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.NEUTRAL, 1f, pitch.get());

            FoodProperties food = this.getFood(stack);
            if (entity instanceof Player)
            {
                Player player = (Player) entity;
                if (!player.isCreative()) stack.shrink(1);
                player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            }

            for (Pair<MobEffectInstance, Float> pair : food.getEffects())
                if (pair.getFirst() != null && world.random.nextFloat() < pair.getSecond())
                    entity.addEffect(new MobEffectInstance(pair.getFirst()));
        }
        return stack;
    }

    private FoodProperties getFood(ItemStack stack)
    {
        List<FoodProperties> foods = new ArrayList<>();
        CompoundTag nbt = stack.getOrCreateTag();

        FoodProperties bread = this.sandwichProperties.getBunFood(nbt);
        foods.add(bread);
        stack.getOrCreateTag().getList(TAG_INGREDIENTS, NbtConstants.COMPOUND).stream().map(inbt -> ItemStack.of((CompoundTag) inbt))
                .filter(ItemStack::isEdible).map(ingredient -> ingredient.getItem().getFoodProperties()).forEach(foods::add);
        if (this.sandwichProperties.hasTwoBuns())
            foods.add(bread);

        return HNCFoods.combine(.5f, this.sandwichProperties.isToasted(nbt), foods.toArray(new FoodProperties[]{})).build();
    }

    public static ItemStack addIngredient(ItemStack sandwich, ItemStack ingredient)
    {
        if (ingredient.isEdible() && !(ingredient.getItem() instanceof AbstractSandwichItem))
        {
            ItemStack added = ingredient.copy();
            added.setCount(1);

            ListTag ingredients = sandwich.getOrCreateTag().getList(TAG_INGREDIENTS, NbtConstants.COMPOUND);
            if (ingredients.size() <= HamNCheese.CONFIG_SERVER.maxSandwichIngredientCraftCount.get())
                ingredients.add(added.save(new CompoundTag()));
            sandwich.getOrCreateTag().put(TAG_INGREDIENTS, ingredients);
        }
        return sandwich;
    }

    public static Set<ItemStack> getIngredients(ItemStack stack)
    {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!(stack.getItem() instanceof AbstractSandwichItem) || !nbt.contains(TAG_INGREDIENTS, NbtConstants.LIST))
            return new HashSet<>();

        ListTag nbtIngredients = nbt.getList(TAG_INGREDIENTS, NbtConstants.COMPOUND);
        return nbtIngredients.stream().filter(ingredient -> ingredient instanceof CompoundTag).map(ingredient -> ItemStack.of((CompoundTag) ingredient)).collect(Collectors.toSet());
    }

    public static class SandwichProperties
    {
        private final FoodProperties bunFood;
        @Nullable
        private final FoodProperties toastedBunFood;
        private final ItemStack bunItem;
        @Nullable
        private final ItemStack toastedBunItem;

        private final boolean hasTwoBuns;
        private final boolean canBeToasted;

        public SandwichProperties(FoodProperties bunFood, Supplier<? extends Item> bunItem, boolean hasTwoBuns)
        {
            this(bunFood, null, bunItem, null, hasTwoBuns, false);
        }

        public SandwichProperties(FoodProperties bunFood, FoodProperties toastedBunFood, Supplier<? extends Item> bunItem, Supplier<? extends Item> toastedBunItem, boolean hasTwoBuns)
        {
            this(bunFood, toastedBunFood, bunItem, toastedBunItem, hasTwoBuns, true);
        }

        private SandwichProperties(FoodProperties bunFood, @Nullable FoodProperties toastedBunFood, Supplier<? extends Item> bunItem, @Nullable Supplier<? extends Item> toastedBunItem, boolean hasTwoBuns, boolean canBeToasted)
        {
            this.bunFood = bunFood;
            this.toastedBunFood = toastedBunFood;
            this.bunItem = new ItemStack(bunItem.get());
            this.toastedBunItem = toastedBunItem == null ? ItemStack.EMPTY : new ItemStack(toastedBunItem.get());

            this.hasTwoBuns = hasTwoBuns;
            this.canBeToasted = canBeToasted;
        }

        @Nullable
        public FoodProperties getBunFood(CompoundTag nbt)
        {
            return this.isToasted(nbt) ? this.toastedBunFood : this.bunFood;
        }

        @Nullable
        public ItemStack getBunItem(CompoundTag nbt)
        {
            return this.isToasted(nbt) ? this.toastedBunItem : this.bunItem;
        }

        public boolean hasTwoBuns()
        {
            return this.hasTwoBuns;
        }

        public boolean isToasted(CompoundTag nbt)
        {
            return this.canBeToasted && nbt.contains(AbstractSandwichItem.TAG_TOASTED) && nbt.getBoolean(AbstractSandwichItem.TAG_TOASTED);
        }
    }
}
