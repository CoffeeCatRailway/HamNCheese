package io.github.coffeecatrailway.hamncheese.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

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
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof ServerPlayer)
        {
            ServerPlayer player = (ServerPlayer) entity;
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty())
            return new ItemStack(Items.GLASS_BOTTLE);
        else
        {
            if (entity instanceof Player && !((Player) entity).getAbilities().instabuild)
            {
                ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
                Player player = (Player) entity;
                if (!player.getInventory().add(glassBottle))
                    player.drop(glassBottle, false);
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
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.DRINK;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
