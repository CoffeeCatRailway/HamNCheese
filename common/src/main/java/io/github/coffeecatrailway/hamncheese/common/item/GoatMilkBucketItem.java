package io.github.coffeecatrailway.hamncheese.common.item;

import gg.moonflower.pollen.api.item.BucketItemBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 30/05/2022
 */
public class GoatMilkBucketItem extends BucketItemBase
{
    private static final MilkBucketItem MILK_BUCKET = (MilkBucketItem) Items.MILK_BUCKET;

    public GoatMilkBucketItem(Supplier<? extends Fluid> fluid, Properties builder)
    {
        super(fluid, builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if (entity.isCrouching())
            return MILK_BUCKET.finishUsingItem(stack, level, entity);
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return MILK_BUCKET.getUseDuration(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return MILK_BUCKET.getUseAnimation(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if (player.isCrouching())
            return MILK_BUCKET.use(level, player, hand);
        return super.use(level, player, hand);
    }
}
