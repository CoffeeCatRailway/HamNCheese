package coffeecatrailway.hamncheese.common.inventory;

import coffeecatrailway.hamncheese.common.tileentity.HNCCookerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.hooks.BasicEventHooks;

/**
 * @author CoffeeCatRailway
 * Created: 21/05/2021
 */
public class ResultSlot extends FurnaceResultSlot
{
    private final PlayerEntity player;
    private int removeCount;

    public ResultSlot(PlayerEntity player, IInventory inventory, int index, int xPosition, int yPosition) {
        super(player, inventory, index, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem())
            this.removeCount += Math.min(amount, this.getItem().getCount());
        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide() && this.container instanceof HNCCookerTileEntity) {
            ((HNCCookerTileEntity) this.container).giveExperience(this.player);
        }

        this.removeCount = 0;
        BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }
}
