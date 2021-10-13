package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author CoffeeCatRailway
 * Created: 13/10/2021
 */
public class CurdlerTileEntity extends TileEntity implements ITickableTileEntity
{
    private float crankAngle = 0f;
    private float velocity = 0f;

    public CurdlerTileEntity()
    {
        super(HNCTileEntities.CURDLER.get());
    }

    @Override
    public void tick()
    {
        if (this.level.isClientSide())
            return;

        if (this.velocity > 0f)
            this.velocity = Math.max(0f, this.velocity - .5f);

        float oldCrankAngle = this.crankAngle;
        this.crankAngle += this.velocity;
        if (this.crankAngle > 360f)
            this.crankAngle = 0f;

        if (oldCrankAngle != this.crankAngle)
        {
            this.setChanged();
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        }
    }

    public void turn()
    {
        if (this.level.isClientSide())
            return;
        this.velocity = Math.min(this.velocity + 10f, 20f);
    }

    @OnlyIn(Dist.CLIENT)
    public float getCrankAngle()
    {
        return this.crankAngle;
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.saveAdditional(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.worldPosition, 1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        float oldCrankAngle = this.crankAngle;
        CompoundNBT nbt = pkt.getTag();
        this.handleUpdateTag(this.getBlockState(), nbt);
        if (!Objects.equals(oldCrankAngle, this.crankAngle))
        {
            ModelDataManager.requestModelDataRefresh(this);
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt)
    {
        return this.saveAdditional(super.save(nbt));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt)
    {
        super.load(state, nbt);
        this.crankAngle = nbt.getFloat("CrankAngle");
    }

    private CompoundNBT saveAdditional(CompoundNBT nbt)
    {
        nbt.putFloat("CrankAngle", this.crankAngle);
        return nbt;
    }
}
