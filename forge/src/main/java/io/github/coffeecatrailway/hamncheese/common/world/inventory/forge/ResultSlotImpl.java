package io.github.coffeecatrailway.hamncheese.common.world.inventory.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * @author CoffeeCatRailway
 * Created: 18/05/2022
 */
public class ResultSlotImpl
{
    public static void playerSmeltedEvent(Player player, ItemStack stack)
    {
        ForgeEventFactory.firePlayerSmeltedEvent(player, stack);
    }
}
