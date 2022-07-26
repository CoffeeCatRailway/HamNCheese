package io.github.coffeecatrailway.hamncheese.common.item;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import io.github.coffeecatrailway.hamncheese.registry.HNCFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
        super(properties.food(Objects.requireNonNull(sandwichProperties.bun.getItem().getFoodProperties())));
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
        if (getIngredients(stack).size() <= 0)
            return;

        if (Screen.hasShiftDown())
        {
            List<Item> ingredients = stack.getOrCreateTag().getList(TAG_INGREDIENTS, NbtConstants.COMPOUND).stream().map(nbt -> ItemStack.of((CompoundTag) nbt).getItem()).toList();
            Set<Item> ingredientSet = new HashSet<>(ingredients);
            ingredientSet.forEach(item -> tooltip.add(new TextComponent("- ").append(new ItemStack(item).getHoverName().copy().append(" x").append(String.valueOf(Collections.frequency(ingredients, item)))).withStyle(ChatFormatting.GRAY)));
            tooltip.add(new TextComponent(""));
        } else
            tooltip.add(HNCLanguage.shiftInfo(new TranslatableComponent("item." + HamNCheese.MOD_ID + ".sandwich.info")));

        if (!Platform.isModLoaded("appleskin"))
        {
            FoodProperties foodProperties = this.getFood(stack);
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

    public FoodProperties getFood(ItemStack stack)
    {
        List<FoodProperties> foods = new ArrayList<>();
        CompoundTag stackTag = stack.getOrCreateTag();

        FoodProperties bun = this.sandwichProperties.getBunFood(stackTag);
        foods.add(bun);
        ListTag ingredients = stack.getOrCreateTag().getList(TAG_INGREDIENTS, NbtConstants.COMPOUND);
        if (ingredients.size() > 0)
            ingredients.stream().map(tag -> ItemStack.of((CompoundTag) tag)).filter(ItemStack::isEdible).map(ingredient -> ingredient.getItem().getFoodProperties()).forEach(foods::add);
        if (this.sandwichProperties.getHasTwoBuns())
            foods.add(bun);

        return HNCFoods.combine(ingredients.size() > 0 ? .5f : 1f, this.sandwichProperties.isToasted(stackTag), foods.toArray(new FoodProperties[]{})).build();
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
        CompoundTag stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_INGREDIENTS, NbtConstants.LIST))
            return new HashSet<>();

        return stackNbt.getList(AbstractSandwichItem.TAG_INGREDIENTS, NbtConstants.COMPOUND).stream().map(nbt -> ItemStack.of((CompoundTag) nbt)).collect(Collectors.toSet());
    }

    public static class SandwichProperties
    {
        private final ItemStack bun;
        private final ItemStack toastedBun;

        private boolean hasTwoBuns = false;
        private boolean canBeToasted = false;

        public SandwichProperties(Supplier<? extends Item> bun)
        {
            this(bun, bun);
        }

        public SandwichProperties(Supplier<? extends Item> bun, Supplier<? extends Item> toastedBun)
        {
            this.bun = new ItemStack(bun.get());
            this.toastedBun = new ItemStack(toastedBun.get());
        }

        public SandwichProperties hasTwoBuns()
        {
            this.hasTwoBuns = true;
            return this;
        }

        public SandwichProperties canBeToasted()
        {
            this.canBeToasted = true;
            return this;
        }

        @Nullable
        public FoodProperties getBunFood(CompoundTag nbt)
        {
            return this.isToasted(nbt) ? this.toastedBun.getItem().getFoodProperties() : this.bun.getItem().getFoodProperties();
        }

        @Nullable
        public ItemStack getBunItem(CompoundTag nbt)
        {
            return this.isToasted(nbt) ? this.toastedBun : this.bun;
        }

        public boolean getHasTwoBuns()
        {
            return this.hasTwoBuns;
        }

        public boolean isToasted(CompoundTag nbt)
        {
            return this.canBeToasted && nbt.contains(AbstractSandwichItem.TAG_TOASTED) && nbt.getBoolean(AbstractSandwichItem.TAG_TOASTED);
        }
    }
}
