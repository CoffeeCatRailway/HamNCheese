package io.github.coffeecatrailway.hamncheese.common.block.entity.forge;

import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 15/06/2022
 */
public class ForgeChoppingBoardBlockEntity extends ChoppingBoardBlockEntity
{
    private LazyOptional<IItemHandlerModifiable> handler;

    public ForgeChoppingBoardBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(blockPos, blockState);
    }

    @Override
    public void setBlockState(BlockState state)
    {
        super.setBlockState(state);
        if (this.handler != null)
        {
            LazyOptional<?> oldHandler = this.handler;
            this.handler = null;
            oldHandler.invalidate();
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (!this.remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (this.handler == null)
                this.handler = LazyOptional.of(() -> new InvWrapper(this));
            return this.handler.cast();
        } else return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        if (this.handler != null)
        {
            this.handler.invalidate();
            this.handler = null;
        }
    }
}
