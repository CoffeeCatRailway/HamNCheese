package io.github.coffeecatrailway.hamncheese.common.block.entity;

import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author CoffeeCatRailway
 * Created: 27/07/2022
 */
public class CheeseBlockEntity extends BlockEntity
{
    private UUID uuid = null;

    public CheeseBlockEntity(BlockPos pos, BlockState state)
    {
        super(HNCBlockEntities.CHEESE.get(), pos, state);
    }

    public void setPlayerUUID(UUID uuid)
    {
        this.uuid = uuid;
        this.setChanged();
        if (this.hasLevel())
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Nullable
    public UUID getPlayerUUID()
    {
        return this.uuid;
    }

    @Override
    public void load(CompoundTag tag)
    {
        if (tag.contains("Placer", NbtConstants.INT_ARRAY))
            this.uuid = tag.getUUID("Placer");
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        if (this.uuid != null)
            tag.putUUID("Placer", this.uuid);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
