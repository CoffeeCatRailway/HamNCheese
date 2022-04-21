package io.github.coffeecatrailway.hamncheese.common.item;

import com.mojang.math.Vector3d;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class HNCBoatItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final HNCBoatEntity.ModType type;

    public HNCBoatItem(HNCBoatEntity.ModType type, Item.Properties properties)
    {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS)
            return InteractionResultHolder.pass(itemstack);
        else
        {
            Vec3 vector3d = player.getViewVector(1f);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5d)).inflate(1d), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vec3 vector3d1 = player.getEyePosition(1f);

                for (Entity entity : list)
                {
                    AABB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1))
                        return InteractionResultHolder.pass(itemstack);
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK)
            {
                HNCBoatEntity boat = new HNCBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                boat.setModType(this.type);
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox().inflate(-.1d)))
                {
                    return InteractionResultHolder.fail(itemstack);
                } else
                {
                    if (!level.isClientSide)
                    {
                        level.addFreshEntity(boat);
                        if (!player.getAbilities().instabuild)
                            itemstack.shrink(1);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            } else
                return InteractionResultHolder.pass(itemstack);
        }
    }
}
