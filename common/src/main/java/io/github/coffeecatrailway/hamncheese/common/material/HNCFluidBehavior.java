package io.github.coffeecatrailway.hamncheese.common.material;

import gg.moonflower.pollen.api.fluid.PollenFluidBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 22/04/2022
 *
 * Idk how this works...
 */
public class HNCFluidBehavior implements PollenFluidBehavior
{
    @Override
    public double getMotionScale(Entity entity)
    {
        return .01d;
    }

    @Override
    public boolean canDescend(Player player)
    {
        return true;
    }

    @Override
    public boolean canAscend(LivingEntity entity)
    {
        return true;
    }

    @Override
    public boolean canSprint(Player player)
    {
        return true;
    }

    @Override
    public void applyPhysics(LivingEntity entity, Vec3 travelVector, double fallSpeed, boolean falling)
    {
        double entityY = entity.getY();
        float g = .02f;

        entity.moveRelative(g, travelVector);
        travelVector.multiply(.4d, .1d, .4d);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        Vec3 vec3 = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.onClimbable())
            vec3 = new Vec3(vec3.x, .2d, vec3.z);

        entity.setDeltaMovement(vec3.scale(.001d));
        Vec3 vec32 = entity.getFluidFallingAdjustedMovement(fallSpeed, falling, entity.getDeltaMovement());
        entity.setDeltaMovement(vec32);
        if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + .6f - entity.getY() + entityY, vec32.z))
            entity.setDeltaMovement(vec32.x, .3f, vec32.z);
    }

    @Override
    public void doSplashEffect(Entity entity, Random random)
    {
    }

    @Nullable
    @Override
    public SoundEvent getAmbientLoop(Player player)
    {
        return null;
    }
}
