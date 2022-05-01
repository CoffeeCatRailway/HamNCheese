package io.github.coffeecatrailway.hamncheese.mixins;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author CoffeeCatRailway
 * Created: 1/05/2022
 */
@Mixin(Mob.class)
public interface MobAccessor
{
    @Accessor("targetSelector")
    GoalSelector getTargetSelector();
}
