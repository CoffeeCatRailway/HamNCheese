package io.github.coffeecatrailway.hamncheese.common.world.inventory;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import io.github.coffeecatrailway.hamncheese.common.block.entity.BaseFoodMakerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class ResultSlot extends FurnaceResultSlot
{
    private final Player player;
    private int removeCount;

    public ResultSlot(Player player, Container inventory, int index, int x, int y)
    {
        super(player, inventory, index, x, y);
        this.player = player;
    }

    @Override
    public ItemStack remove(int amount)
    {
        if (this.hasItem())
            this.removeCount += Math.min(amount, this.getItem().getCount());
        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack)
    {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide() && this.container instanceof BaseFoodMakerBlockEntity)
        {
            ((BaseFoodMakerBlockEntity) this.container).giveExperience(this.player);
        }

        this.removeCount = 0;
        playerSmeltedEvent(this.player, stack);
    }

    @ExpectPlatform
    private static void playerSmeltedEvent(Player player, ItemStack stack)
    {
        Platform.error();
    }
}
