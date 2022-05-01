package io.github.coffeecatrailway.hamncheese.fabric;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

/**
 * @author CoffeeCatRailway
 * Created: 24/04/2022
 */
public class HamNCheeseImpl
{
    public static boolean mobGriefing(Level level, Entity entity)
    {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }
}
