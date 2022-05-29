package io.github.coffeecatrailway.hamncheese.registry;

import com.mojang.datafixers.util.Pair;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;

/**
 * @author CoffeeCatRailway
 * Created: 9/02/2021
 */
public class HNCFoods
{
    private static final MobEffectInstance HUNGER_EFFECT = new MobEffectInstance(MobEffects.HUNGER, 400, 1);

    public static final FoodProperties BLOCK_OF_CHEESE = new FoodProperties.Builder().nutrition(6).saturationMod(.1f).build();
    public static final FoodProperties CHEESE_SLICE = divide(BLOCK_OF_CHEESE, 4f, 2f).build();

    public static final FoodProperties BLOCK_OF_BLUE_CHEESE = add(BLOCK_OF_CHEESE, 1, .1f).build();
    public static final FoodProperties BLUE_CHEESE_SLICE = add(CHEESE_SLICE, 1, .1f).build();

    public static final FoodProperties BLOCK_OF_GOUDA_CHEESE = add(BLOCK_OF_CHEESE, 0, .25f).build();
    public static final FoodProperties GOUDA_CHEESE_SLICE = add(CHEESE_SLICE, 0, .25f).build();

    public static final FoodProperties BLOCK_OF_SWISS_CHEESE = add(BLOCK_OF_CHEESE, -1, -.3f).build();
    public static final FoodProperties SWISS_CHEESE_SLICE = add(CHEESE_SLICE, -1, -.3f).build();
    public static final FoodProperties SWISS_CHEESE_BITS = divide(SWISS_CHEESE_SLICE, 2f, 1f).build();

    public static final FoodProperties BLOCK_OF_GOAT_CHEESE = times(BLOCK_OF_CHEESE, 1.5f).build();
    public static final FoodProperties GOAT_CHEESE_SLICE = times(CHEESE_SLICE, 1.5f).build();

    public static final FoodProperties INGREDIENT = new FoodProperties.Builder().nutrition(1).saturationMod(.5f).build();
    public static final FoodProperties DOUGH = times(INGREDIENT, 3f).build();

    public static final FoodProperties PIZZA = times(DOUGH, 1.5f).build();

    public static final FoodProperties BREAD_SLICE = times(divide(Foods.BREAD, 3f).build(), 1f, 2f).build();
    public static final FoodProperties TOAST = times(BREAD_SLICE, 1.5f).build();

    public static final FoodProperties CRACKER = combine(.5f, false, INGREDIENT, INGREDIENT, divide(Foods.BREAD, 3f).build()).build();

    public static final FoodProperties CRACKED_EGG = new FoodProperties.Builder().nutrition(3).saturationMod(.1f).build();
    public static final FoodProperties COOKED_EGG = times(CRACKED_EGG, 2f).build();
    public static final FoodProperties GREEN_EGG = copyFoodProperties(CRACKED_EGG).effect(HUNGER_EFFECT, 1f).build();

    public static final FoodProperties HAM_SLICE = divide(Foods.PORKCHOP, 3f).build();
    public static final FoodProperties COOKED_HAM_SLICE = divide(Foods.COOKED_PORKCHOP, 3f).build();
    public static final FoodProperties GREEN_HAM_SLICE = copyFoodProperties(HAM_SLICE).effect(HUNGER_EFFECT, 1f).meat().build();

    public static final FoodProperties BACON = divide(HAM_SLICE, 1.5f).build();
    public static final FoodProperties COOKED_BACON = divide(COOKED_HAM_SLICE, 1.5f).build();

    public static final FoodProperties PINEAPPLE = new FoodProperties.Builder().nutrition(12).saturationMod(.5f).build();
    public static final FoodProperties PINEAPPLE_RING = divide(PINEAPPLE, 4f).build();
    public static final FoodProperties PINEAPPLE_BIT = divide(PINEAPPLE_RING, 3f).build();

    public static final FoodProperties TOMATO = new FoodProperties.Builder().nutrition(4).saturationMod(.3f).build();
    public static final FoodProperties TOMATO_SAUCE = combine(.5f, false, TOMATO, INGREDIENT, INGREDIENT).build();
    public static final FoodProperties TOMATO_SLICE = divide(TOMATO, 2f).build();

    public static final FoodProperties CORN_COB = new FoodProperties.Builder().nutrition(8).saturationMod(.5f).build();
    public static final FoodProperties CORN_KERNELS = divide(CORN_COB, 8f, 2f).build();

    public static final FoodProperties POPCORN = times(combine(1f, false, CORN_KERNELS, INGREDIENT).build(), 1f, .5f).build();
    public static final FoodProperties CHEESY_POPCORN = times(combine(1f, false, POPCORN, CHEESE_SLICE).build(), 1f, .5f).build();
    public static final FoodProperties CARAMEL_POPCORN = times(combine(1f, false, POPCORN, INGREDIENT).build(), 1f, .5f).build();

    public static final FoodProperties MOUSE = new FoodProperties.Builder().nutrition(2).saturationMod(.3f).meat().effect(HUNGER_EFFECT, .3f).build();
    public static final FoodProperties COOKED_MOUSE = new FoodProperties.Builder().nutrition(4).saturationMod(.6f).meat().build();

    public static final FoodProperties FOOD_SCRAPS = new FoodProperties.Builder().nutrition(1).saturationMod(.2f).build();

    public static final FoodProperties MAPLE_SAP_BOTTLE = new FoodProperties.Builder().nutrition(6).saturationMod(.1f).build();
    public static final FoodProperties MAPLE_SYRUP = combine(1f, false, MAPLE_SAP_BOTTLE, INGREDIENT).build();
    public static final FoodProperties MAPLE_POPCORN = times(combine(1f, false, MAPLE_SYRUP, INGREDIENT).build(), 1f, .5f).build();

    private static FoodProperties.Builder add(FoodProperties copy, int nutrition, float saturationMod)
    {
        return copyFoodProperties(copy).nutrition(copy.getNutrition() + nutrition).saturationMod(copy.getSaturationModifier() + saturationMod);
    }

    private static FoodProperties.Builder divide(FoodProperties copy, float amount)
    {
        return divide(copy, amount, amount);
    }

    private static FoodProperties.Builder divide(FoodProperties copy, float nutrition, float saturationMod)
    {
        return copyFoodProperties(copy).nutrition(Math.round(copy.getNutrition() / nutrition)).saturationMod(copy.getSaturationModifier() / saturationMod);
    }

    private static FoodProperties.Builder times(FoodProperties copy, float amount)
    {
        return times(copy, (int) amount, amount);
    }

    private static FoodProperties.Builder times(FoodProperties copy, float nutrition, float saturationMod)
    {
        return copyFoodProperties(copy).nutrition(Math.round(copy.getNutrition() * nutrition)).saturationMod(copy.getSaturationModifier() * saturationMod);
    }

    public static FoodProperties.Builder combine(float combinationMultiplier, boolean cooked, FoodProperties... foods)
    {
        if (foods.length == 1)
            return copyFoodProperties(foods[0]);

        FoodProperties.Builder combination = new FoodProperties.Builder();
        int nutrition = 0;
        float saturationMod = 0f;
        for (FoodProperties food : foods)
        {
            nutrition += food.getNutrition();
            saturationMod += food.getSaturationModifier();
            copyFoodProperties(food, combination);
        }
        return combination.nutrition((int) (nutrition * combinationMultiplier * cookedModifier(cooked))).saturationMod(saturationMod * combinationMultiplier * cookedModifier(cooked));
    }

    private static float cookedModifier(boolean cooked)
    {
        return (float) (cooked ? HamNCheese.CONFIG_SERVER.cookedFoodModifier.get() : 1f);
    }

    private static FoodProperties.Builder copyFoodProperties(FoodProperties copy)
    {
        return copyFoodProperties(copy, new FoodProperties.Builder());
    }

    private static FoodProperties.Builder copyFoodProperties(FoodProperties copy, FoodProperties.Builder copied)
    {
        FoodProperties copiedTmp = copied.build();
        if (copy.isMeat() && !copiedTmp.isMeat()) copied.meat();
        if (copy.isFastFood() && !copiedTmp.isFastFood()) copied.fast();
        if (copy.canAlwaysEat() && !copiedTmp.canAlwaysEat()) copied.alwaysEat();

        for (Pair<MobEffectInstance, Float> effect : copy.getEffects())
            if (!copiedTmp.getEffects().contains(effect))
                copied.effect(effect.getFirst(), effect.getSecond());

        return copied;
    }
}
