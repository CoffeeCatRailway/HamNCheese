package coffeecatrailway.hamncheese.registry;

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

    public static final Food BLOCK_OF_CHEESE = new Food.Builder().hunger(6).saturation(1f).build();
    public static final Food CHEESE_SLICE = divide(BLOCK_OF_CHEESE, 3, 2f).fastToEat().build();

    public static final Food INGREDIENT = new Food.Builder().hunger(1).saturation(.5f).build();
    public static final Food DOUGH = times(INGREDIENT, 3f).build();

    public static final Food BREAD_SLICE = times(divide(Foods.BREAD, 3f).build(), 1, 2f).build();
    public static final Food TOAST = times(BREAD_SLICE, 1.5f).build();

    public static final Food CRACKER = combine(.5f, INGREDIENT, INGREDIENT, divide(Foods.BREAD, 3f).build()).build();

    public static final Food CRACKED_EGG = new Food.Builder().hunger(3).saturation(1f).build();
    public static final Food COOKED_EGG = times(CRACKED_EGG, 2f).build();
    public static final Food GREEN_EGG = copyFood(CRACKED_EGG).effect(HUNGER_EFFECT, 1f).build();

    public static final Food HAM_SLICE = divide(Foods.PORKCHOP, 2f).build();
    public static final Food COOKED_HAM_SLICE = divide(Foods.COOKED_PORKCHOP, 2f).build();
    public static final Food GREEN_HAM_SLICE = copyFood(HAM_SLICE).effect(HUNGER_EFFECT, 1f).meat().build();

    public static final Food BACON = divide(HAM_SLICE, 1.5f).build();
    public static final Food COOKED_BACON = divide(COOKED_HAM_SLICE, 1.5f).build();

    private static Food.Builder divide(Food copy, float amount)
    {
        return divide(copy, (int) amount, amount);
    }

    private static Food.Builder divide(Food copy, int hunger, float saturation)
    {
        return copyFood(copy).hunger(copy.getHealing() / hunger).saturation(copy.getSaturation() / saturation);
    }

    private static Food.Builder times(Food copy, float amount)
    {
        return times(copy, (int) amount, amount);
    }

    private static Food.Builder times(Food copy, int hunger, float saturation)
    {
        return copyFood(copy).hunger(copy.getHealing() * hunger).saturation(copy.getSaturation() * saturation);
    }

    private static Food.Builder combine(float combinationMultiplier, Food... foods)
    {
        if (foods.length == 1)
            return copyFood(foods[0]);

        Food.Builder combination = new Food.Builder();
        int hunger = 0;
        float saturation = 0f;
        for (Food food : foods)
        {
            hunger += food.getHealing();
            saturation += food.getSaturation();
            copyFood(food, combination);
        }
        return combination.hunger((int) (hunger * combinationMultiplier)).saturation(saturation * combinationMultiplier);
    }

    private static Food.Builder copyFood(Food copy)
    {
        return copyFood(copy, new Food.Builder());
    }

    private static Food.Builder copyFood(Food copy, Food.Builder copied)
    {
        Food copiedTmp = copied.build();
        if (copy.isMeat() && !copiedTmp.isMeat()) copied.meat();
        if (copy.isFastEating() && !copiedTmp.isFastEating()) copied.fastToEat();
        if (copy.canEatWhenFull() && !copiedTmp.canEatWhenFull()) copied.setAlwaysEdible();

        for (Pair<EffectInstance, Float> effect : copy.getEffects())
            if (!copiedTmp.getEffects().contains(effect))
                copied.effect(effect::getFirst, effect.getSecond());

        return copied;
    }
}
