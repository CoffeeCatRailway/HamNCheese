package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 13/03/2022
 */
public class PopcornItem extends Item
{
    public PopcornItem(Properties properties)
    {
        super(properties.craftRemainder(HNCItems.POPCORN_BAG.get()).stacksTo(32));
    }

    /**
     * Copied from {@link net.minecraft.item.HoneyBottleItem}
     */
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
    {
        super.finishUsingItem(stack, world, entity);
        if (entity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty())
            return new ItemStack(HNCItems.POPCORN_BAG.get());
        else
        {
            if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.instabuild)
            {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerentity = (PlayerEntity) entity;
                if (!playerentity.inventory.add(itemstack))
                    playerentity.drop(itemstack, false);
            }

            return stack;
        }
    }
}
