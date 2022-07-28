package io.github.coffeecatrailway.hamncheese.common.entity.ai.goal;

import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.PestControlTrigger;
import io.github.coffeecatrailway.hamncheese.common.entity.MouseEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCCriterionTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Cat;

/**
 * @author CoffeeCatRailway
 * Created: 28/07/2022
 */
public class MouseAvoidCatGoal extends AvoidEntityGoal<Cat>
{
    public MouseAvoidCatGoal(MouseEntity mouse)
    {
        super(mouse, Cat.class, 12f, .8d, 1d);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.toAvoid != null && this.mob.distanceToSqr(this.toAvoid) < 49d && this.toAvoid.isTame() && this.toAvoid.getOwner() instanceof ServerPlayer player)
            HNCCriterionTriggers.PEST_CONTROL_TRIGGER.trigger(player, PestControlTrigger.Type.NORMAL);
    }
}
