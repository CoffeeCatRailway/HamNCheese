package coffeecatrailway.hamncheese.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
public abstract class HNCLockableTileEntity extends LockableTileEntity
{
    public HNCLockableTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    protected void sendUpdates(TileEntity tile)
    {
        super.setChanged();
        if (tile.hasLevel())
        {
            BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
            tile.getLevel().sendBlockUpdated(tile.getBlockPos(), state, state, 8);
        }
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.worldPosition, 9, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.load(this.getBlockState(), pkt.getTag());
    }

    public abstract void giveExperience(PlayerEntity player);

    protected void giveExperience(PlayerEntity player, float amount, double experience)
    {
        if (experience <= 0f)
        {
            amount = 0;
        } else if (experience < 1f)
        {
            int i = MathHelper.floor(amount * experience);
            if (i < MathHelper.ceil(amount * experience) && Math.random() < (double) (amount * experience - (float) i))
                i++;

            amount = i;
        }

        while (amount > 0)
        {
            int j = ExperienceOrbEntity.getExperienceValue((int) amount);
            amount -= j;
            player.level.addFreshEntity(new ExperienceOrbEntity(player.level, player.position().x, player.position().y + .5d, player.position().z + .5d, j));
        }
    }
}
