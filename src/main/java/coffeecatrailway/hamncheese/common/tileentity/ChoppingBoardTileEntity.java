package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

/**
 * @author CoffeeCatRailway
 * Created: 18/09/2021
 */
public class ChoppingBoardTileEntity extends TileEntity
{
    private ResourceLocation boardId = HNCMod.getLocation("oak_chopping_board");

    public ChoppingBoardTileEntity()
    {
        super(HNCTileEntities.CHOPPING_BOARD.get());
    }

    @OnlyIn(Dist.CLIENT)
    public void fromItem(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTagElement("BlockEntityTag");
        if (nbt.contains("BoardId", Constants.NBT.TAG_STRING))
            this.boardId = new ResourceLocation(nbt.getString("BoardId"));
        else
            this.boardId = HNCMod.getLocation("oak_chopping_board");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt)
    {
        super.save(nbt);
        nbt.putString("BoardId", this.boardId.toString());
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt)
    {
        super.load(state, nbt);
        this.boardId = new ResourceLocation(nbt.getString("BoardId"));
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBoardId()
    {
        return this.boardId;
    }

    public ItemStack getItem()
    {
        ItemStack stack = new ItemStack(HNCBlocks.CHOPPING_BOARD.get());
        stack.getOrCreateTagElement("BlockEntityTag").putString("BoardId", this.boardId.toString());
        return stack;
    }
}
