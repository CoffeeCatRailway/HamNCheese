package coffeecatrailway.hamncheese.common.item;

import coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class HNCBoatItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    private final HNCBoatEntity.ModType type;

    public HNCBoatItem(HNCBoatEntity.ModType type, Item.Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS)
            return ActionResult.pass(itemstack);
        else
        {
            Vector3d vector3d = player.getViewVector(1f);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5d)).inflate(1d), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vector3d vector3d1 = player.getEyePosition(1f);

                for (Entity entity : list)
                {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1))
                        return ActionResult.pass(itemstack);
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK)
            {
                HNCBoatEntity boat = new HNCBoatEntity(level, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boat.setModType(this.type);
                boat.yRot = player.yRot;
                if (!level.noCollision(boat, boat.getBoundingBox().inflate(-.1d)))
                {
                    return ActionResult.fail(itemstack);
                } else
                {
                    if (!level.isClientSide)
                    {
                        level.addFreshEntity(boat);
                        if (!player.abilities.instabuild)
                            itemstack.shrink(1);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, level.isClientSide());
                }
            } else
                return ActionResult.pass(itemstack);
        }
    }
}
