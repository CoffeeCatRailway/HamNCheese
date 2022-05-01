package io.github.coffeecatrailway.hamncheese.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * @author CoffeeCatRailway
 * Created: 24/04/2022
 */
public class HamNCheeseImpl
{
    public static boolean mobGriefing(Level level, Entity entity)
    {
        return ForgeEventFactory.getMobGriefingEvent(level, entity);
    }
}
