package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.HNCConfig;
import coffeecatrailway.hamncheese.client.item.SandwichItemRenderer;
import coffeecatrailway.hamncheese.registry.HNCFoods;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
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
        super(properties.setISTER(() -> SandwichItemRenderer::new).food(sandwichProperties.bunFood));
        this.sandwichProperties = sandwichProperties;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        CompoundNBT stackNbt = stack.getOrCreateTag();

        if (!stackNbt.contains(TAG_INGREDIENTS))
            stackNbt.put(TAG_INGREDIENTS, new ListNBT());

        if (this.sandwichProperties.canBeToasted && !stackNbt.contains(TAG_TOASTED))
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
            name = ItemStack.of((CompoundNBT) ingredients.get(0)).getHoverName().copy().append(" ").append(super.getName(stack));
        return name;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        List<Item> ingredients = stack.getOrCreateTag().getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND).stream().map(nbt -> ItemStack.of((CompoundNBT) nbt).getItem()).collect(Collectors.toList());
        Set<Item> ingredientSet = new HashSet<>(ingredients);
        ingredientSet.forEach(item -> tooltip.add(new ItemStack(item).getHoverName().copy().withStyle(TextFormatting.GRAY).append(" x").append(String.valueOf(Collections.frequency(ingredients, item)))));
    }

    @Override
    public boolean isEdible()
    {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
    {
        if (!world.isClientSide)
        {
            SoundEvent sound = SoundEvents.GENERIC_EAT;
            Supplier<Float> pitch = () -> 1f + (world.random.nextFloat() - world.random.nextFloat()) * .4f;

            entity.playSound(sound, 1f, pitch.get());
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundCategory.NEUTRAL, 1f, pitch.get());

            Food food = this.getFood(stack);
            if (entity instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) entity;
                if (!player.isCreative()) stack.shrink(1);
                player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            }

            for (Pair<EffectInstance, Float> pair : food.getEffects())
                if (pair.getFirst() != null && world.random.nextFloat() < pair.getSecond())
                    entity.addEffect(new EffectInstance(pair.getFirst()));
        }
        return stack;
    }

    private Food getFood(ItemStack stack)
    {
        List<Food> foods = new ArrayList<>();
        CompoundNBT nbt = stack.getOrCreateTag();

        Food bread = this.sandwichProperties.getBunFood(nbt);
        foods.add(bread);
        stack.getOrCreateTag().getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND).stream().map(inbt -> ItemStack.of((CompoundNBT) inbt))
                .filter(ItemStack::isEdible).map(ingredient -> ingredient.getItem().getFoodProperties()).forEach(foods::add);
        if (this.sandwichProperties.hasTwoBuns())
            foods.add(bread);

        return HNCFoods.combine(.5f, this.sandwichProperties.isToasted(nbt), foods.toArray(new Food[]{})).build();
    }

    public static ItemStack addIngredient(ItemStack sandwich, ItemStack ingredient)
    {
        if (ingredient.isEdible() && !(ingredient.getItem() instanceof AbstractSandwichItem))
        {
            ItemStack added = ingredient.copy();
            added.setCount(1);

            ListNBT ingredients = sandwich.getOrCreateTag().getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
            if (ingredients.size() <= HNCConfig.SERVER.maxSandwichIngredientCraftCount.get())
                ingredients.add(added.save(new CompoundNBT()));
        }
        return sandwich;
    }

    public static Set<ItemStack> getIngredients(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!(stack.getItem() instanceof AbstractSandwichItem) || !nbt.contains(TAG_INGREDIENTS, Constants.NBT.TAG_LIST))
            return new HashSet<>();

        ListNBT nbtIngredients = nbt.getList(TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
        return nbtIngredients.stream().filter(ingredient -> ingredient instanceof CompoundNBT).map(ingredient -> ItemStack.of((CompoundNBT) ingredient)).collect(Collectors.toSet());
    }

    public static class SandwichProperties
    {
        private final Food bunFood;
        @Nullable
        private final Food toastedBunFood;
        private final ItemStack bunItem;
        @Nullable
        private final ItemStack toastedBunItem;

        private final boolean hasTwoBuns;
        private final boolean canBeToasted;

        public SandwichProperties(Food bunFood, Supplier<? extends Item> bunItem, boolean hasTwoBuns)
        {
            this(bunFood, null, bunItem, null, hasTwoBuns, false);
        }

        public SandwichProperties(Food bunFood, Food toastedBunFood, Supplier<? extends Item> bunItem, Supplier<? extends Item> toastedBunItem, boolean hasTwoBuns)
        {
            this(bunFood, toastedBunFood, bunItem, toastedBunItem, hasTwoBuns, true);
        }

        private SandwichProperties(Food bunFood, @Nullable Food toastedBunFood, Supplier<? extends Item> bunItem, @Nullable Supplier<? extends Item> toastedBunItem, boolean hasTwoBuns, boolean canBeToasted)
        {
            this.bunFood = bunFood;
            this.toastedBunFood = toastedBunFood;
            this.bunItem = new ItemStack(bunItem.get());
            this.toastedBunItem = toastedBunItem == null ? ItemStack.EMPTY : new ItemStack(toastedBunItem.get());

            this.hasTwoBuns = hasTwoBuns;
            this.canBeToasted = canBeToasted;
        }

        @Nullable
        public Food getBunFood(CompoundNBT nbt)
        {
            return this.isToasted(nbt) ? this.toastedBunFood : this.bunFood;
        }

        @Nullable
        public ItemStack getBunItem(CompoundNBT nbt)
        {
            return this.isToasted(nbt) ? this.toastedBunItem : this.bunItem;
        }

        public boolean hasTwoBuns()
        {
            return this.hasTwoBuns;
        }

        public boolean isToasted(CompoundNBT nbt)
        {
            return this.canBeToasted && nbt.contains(AbstractSandwichItem.TAG_TOASTED) && nbt.getBoolean(AbstractSandwichItem.TAG_TOASTED);
        }
    }
}
