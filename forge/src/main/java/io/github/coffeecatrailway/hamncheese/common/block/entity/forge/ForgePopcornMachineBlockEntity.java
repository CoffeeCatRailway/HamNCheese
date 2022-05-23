package io.github.coffeecatrailway.hamncheese.common.block.entity.forge;

import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 23/05/2022
 */
public class ForgePopcornMachineBlockEntity extends PopcornMachineBlockEntity
{
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.WEST, Direction.EAST);

    public ForgePopcornMachineBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (!this.isRemoved() && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return switch (side)
                    {
                        case DOWN -> this.handlers[0].cast();
                        case UP -> this.handlers[1].cast();
                        case WEST -> this.handlers[3].cast();
                        case EAST -> this.handlers[4].cast();
                        default -> this.handlers[2].cast();
                    };
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers) handler.invalidate();
    }
}
