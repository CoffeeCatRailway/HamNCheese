package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
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

    private final FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME, stack -> Tags.Fluids.MILK.contains(stack.getRawFluid()));
    private final LazyOptional<FluidTank> fluidHandler = LazyOptional.of(() -> this.tank);

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

    public int addMilk(int amount)
    {
        if (this.level.isClientSide())
            return 0;
        this.setChanged();
        return this.tank.fill(new FluidStack(ForgeMod.MILK.get(), amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public int getMilk()
    {
        return this.tank.getFluidAmount();
    }

    public int getMilkCapacity()
    {
        return this.tank.getCapacity();
    }

    public Fluid getFluid()
    {
        return this.tank.getFluid().getFluid();
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
        int oldFluidAmount = this.tank.getFluidAmount();
        CompoundNBT nbt = pkt.getTag();
        this.handleUpdateTag(this.getBlockState(), nbt);
        if (!Objects.equals(oldCrankAngle, this.crankAngle) || !Objects.equals(oldFluidAmount, this.tank.getFluidAmount()))
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
        this.tank.readFromNBT(nbt.getCompound("Tank"));
    }

    private CompoundNBT saveAdditional(CompoundNBT nbt)
    {
        nbt.putFloat("CrankAngle", this.crankAngle);
        nbt.put("Tank", this.tank.writeToNBT(new CompoundNBT()));
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return this.fluidHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        this.fluidHandler.invalidate();
    }
}
