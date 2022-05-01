package io.github.coffeecatrailway.hamncheese.common.entity.ai.goal;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;

/**
 * @author CoffeeCatRailway
 * Created: 5/09/2021
 */
public abstract class MouseInteractGoal extends MoveToBlockGoal
{
    private int ticker;

    public MouseInteractGoal(PathfinderMob entity, double speedModifier, int searchRange, int verticalSearchRange)
    {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    @Override
    public double acceptedDistance()
    {
        return 2d;
    }

    @Override
    public boolean shouldRecalculatePath()
    {
        return this.tryTicks % 20 == 0;
    }

    @Override
    public boolean canUse()
    {
        return !this.mob.isAggressive() && super.canUse();
    }

    @Override
    public void start()
    {
        this.ticker = 0;
        super.start();
    }

    @Override
    public void tick()
    {
        Level level = this.mob.level;
        if (this.isReachedTarget())
        {
            if (this.ticker >= 40)
            {
                if (this.isValidTarget(level, this.blockPos) && HamNCheese.mobGriefing(level, this.mob))
                    this.interact(level, this.mob);
            } else
                this.ticker++;
        } else
        {
            if (level.random.nextFloat() < .05f)
                this.mob.playSound(SoundEvents.FOX_SNIFF, 1f, 1f);
        }
        super.tick();
    }

    protected abstract void interact(Level level, PathfinderMob mob);
}
