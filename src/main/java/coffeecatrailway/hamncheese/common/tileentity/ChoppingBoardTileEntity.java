package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author CoffeeCatRailway
 * Created: 18/09/2021
 */
public class ChoppingBoardTileEntity extends TileEntity
{
    public static final ModelProperty<ResourceLocation> BOARD_ID = new ModelProperty<>();

    private ResourceLocation boardId;

    public ChoppingBoardTileEntity()
    {
        super(HNCTileEntities.CHOPPING_BOARD.get());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.saveBoardId(super.getUpdateTag());
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
        ResourceLocation oldBoardId = this.boardId;
        CompoundNBT nbt = pkt.getTag();
        this.handleUpdateTag(this.getBlockState(), nbt);
        if (!Objects.equals(oldBoardId, this.boardId))
        {
            ModelDataManager.requestModelDataRefresh(this);
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData()
    {
        return new ModelDataMap.Builder().withInitial(BOARD_ID, this.boardId).build();
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt)
    {
        return this.saveBoardId(super.save(nbt));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt)
    {
        super.load(state, nbt);
        this.loadBoardId(nbt);
    }

    @Nullable
    public ResourceLocation getBoardId()
    {
        return this.boardId;
    }

    public void setBoardId(ResourceLocation boardId)
    {
        this.boardId = boardId;
        this.setChanged();
        if (this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    public ItemStack getItem()
    {
        ItemStack stack = new ItemStack(HNCBlocks.CHOPPING_BOARD.get());
        this.saveBoardId(stack.getOrCreateTagElement("BlockEntityTag"));
        return stack;
    }

    private CompoundNBT saveBoardId(CompoundNBT nbt)
    {
        if (this.boardId != null)
            nbt.putString("BoardId", this.boardId.toString());
        else
            nbt.putString("BoardId", ChoppingBoard.DEFAULT.getId().toString());
        return nbt;
    }

    private void loadBoardId(CompoundNBT nbt)
    {
        if (nbt.contains("BoardId", Constants.NBT.TAG_STRING))
            this.setBoardId(new ResourceLocation(nbt.getString("BoardId")));
        else
            this.setBoardId(ChoppingBoard.DEFAULT.getId());
    }
}
