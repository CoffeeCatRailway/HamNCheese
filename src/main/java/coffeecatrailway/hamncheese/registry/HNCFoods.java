package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCConfig;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 9/02/2021
 */
public class HNCFoods
{
    private static final Supplier<EffectInstance> HUNGER_EFFECT = () -> new EffectInstance(Effects.HUNGER, 400, 1);

    public static final Food BLOCK_OF_CHEESE = new Food.Builder().nutrition(6).saturationMod(.1f).build();
    public static final Food CHEESE_SLICE = divide(BLOCK_OF_CHEESE, 3f, 2f).fast().build();

    public static final Food INGREDIENT = new Food.Builder().nutrition(1).saturationMod(.5f).build();
    public static final Food DOUGH = times(INGREDIENT, 3f).build();

    public static final Food PIZZA = times(DOUGH, 1.5f).build();

    public static final Food BREAD_SLICE = times(divide(Foods.BREAD, 3f).build(), 1f, 2f).build();
    public static final Food TOAST = times(BREAD_SLICE, 1.5f).build();

    public static final Food CRACKER = combine(.5f, false, INGREDIENT, INGREDIENT, divide(Foods.BREAD, 3f).build()).build();

    public static final Food CRACKED_EGG = new Food.Builder().nutrition(3).saturationMod(.1f).build();
    public static final Food COOKED_EGG = times(CRACKED_EGG, 2f).build();
    public static final Food GREEN_EGG = copyFood(CRACKED_EGG).effect(HUNGER_EFFECT, 1f).build();

    public static final Food HAM_SLICE = divide(Foods.PORKCHOP, 3f).build();
    public static final Food COOKED_HAM_SLICE = divide(Foods.COOKED_PORKCHOP, 3f).build();
    public static final Food GREEN_HAM_SLICE = copyFood(HAM_SLICE).effect(HUNGER_EFFECT, 1f).meat().build();

    public static final Food BACON = divide(HAM_SLICE, 1.5f).build();
    public static final Food COOKED_BACON = divide(COOKED_HAM_SLICE, 1.5f).build();

    public static final Food PINEAPPLE = new Food.Builder().nutrition(12).saturationMod(.5f).build();
    public static final Food PINEAPPLE_RING = divide(PINEAPPLE, 4f).build();
    public static final Food PINEAPPLE_BIT = divide(PINEAPPLE_RING, 3f).fast().build();

    public static final Food TOMATO = new Food.Builder().nutrition(4).saturationMod(.3f).build();
    public static final Food TOMATO_SAUCE = combine(.5f, false, TOMATO, INGREDIENT, INGREDIENT).build();
    public static final Food TOMATO_SLICE = divide(TOMATO, 2f).build();

    public static final Food CORN_COB = new Food.Builder().nutrition(8).saturationMod(.5f).build();
    public static final Food CORN_KERNELS = divide(CORN_COB, 8f, 2f).fast().build();

    public static final Food MOUSE = new Food.Builder().nutrition(2).saturationMod(.3f).meat().effect(HUNGER_EFFECT, .3f).build();
    public static final Food COOKED_MOUSE =  new Food.Builder().nutrition(4).saturationMod(.6f).meat().build();

    public static final Food FOOD_SCRAPS = new Food.Builder().nutrition(1).saturationMod(.2f).fast().build();

    private static Food.Builder divide(Food copy, float amount)
    {
        return divide(copy, amount, amount);
    }

    private static Food.Builder divide(Food copy, float nutrition, float saturationMod)
    {
        return copyFood(copy).nutrition(Math.round(copy.getNutrition() / nutrition)).saturationMod(copy.getSaturationModifier() / saturationMod);
    }

    private static Food.Builder times(Food copy, float amount)
    {
        return times(copy, (int) amount, amount);
    }

    private static Food.Builder times(Food copy, float nutrition, float saturationMod)
    {
        return copyFood(copy).nutrition(Math.round(copy.getNutrition() * nutrition)).saturationMod(copy.getSaturationModifier() * saturationMod);
    }

    public static Food.Builder combine(float combinationMultiplier, boolean cooked, Food... foods)
    {
        if (foods.length == 1)
            return copyFood(foods[0]);

        Food.Builder combination = new Food.Builder();
        int nutrition = 0;
        float saturationMod = 0f;
        for (Food food : foods)
        {
            nutrition += food.getNutrition();
            saturationMod += food.getSaturationModifier();
            copyFood(food, combination);
        }
        return combination.nutrition((int) (nutrition * combinationMultiplier * cookedModifier(cooked))).saturationMod(saturationMod * combinationMultiplier * cookedModifier(cooked));
    }

    private static float cookedModifier(boolean cooked)
    {
        return (float) (cooked ? HNCConfig.SERVER.cookedFoodModifier.get() : 1f);
    }

    private static Food.Builder copyFood(Food copy)
    {
        return copyFood(copy, new Food.Builder());
    }

    private static Food.Builder copyFood(Food copy, Food.Builder copied)
    {
        Food copiedTmp = copied.build();
        if (copy.isMeat() && !copiedTmp.isMeat()) copied.meat();
        if (copy.isFastFood() && !copiedTmp.isFastFood()) copied.fast();
        if (copy.canAlwaysEat() && !copiedTmp.canAlwaysEat()) copied.alwaysEat();

        for (Pair<EffectInstance, Float> effect : copy.getEffects())
            if (!copiedTmp.getEffects().contains(effect))
                copied.effect(effect::getFirst, effect.getSecond());

        return copied;
    }
}
