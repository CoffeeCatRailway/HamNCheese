package coffeecatrailway.hamncheese.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 9/09/2021
 */
public class FoodBottleItem extends Item
{
    public FoodBottleItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World level, LivingEntity entity)
    {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty())
            return new ItemStack(Items.GLASS_BOTTLE);
        else
        {
            if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.instabuild)
            {
                ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerentity = (PlayerEntity) entity;
                if (!playerentity.inventory.add(glassBottle))
                    playerentity.drop(glassBottle, false);
            }
            return stack;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 40;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack)
    {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound()
    {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound()
    {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand)
    {
        return DrinkHelper.useDrink(level, player, hand);
    }
}
