package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.ChoppingBoardTileEntity;
import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.data.ChoppingBoardManager;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.ocelot.sonar.common.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 27/04/2021
 */
public class ChoppingBoardBlock extends BaseBlock implements IWaterLoggable
{
    private static final VoxelShape SHAPE_NS = Block.box(4d, 0d, 2d, 12d, 1d, 14d);
    private static final VoxelShape SHAPE_EW = Block.box(2d, 0d, 4d, 14d, 1d, 12d);

    public ChoppingBoardBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks)
    {
        if (group.equals(HNCMod.GROUP_ALL))
        {
            ChoppingBoardManager.INSTANCE.getBoards().forEach((id, board) -> {
                ItemStack stack = new ItemStack(HNCBlocks.CHOPPING_BOARD.get());
                stack.getOrCreateTagElement("BlockEntityTag").putString("BoardId", id.toString());
                stacks.add(stack);
            });
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> lines, ITooltipFlag flag)
    {
        ChoppingBoard board = ChoppingBoardManager.INSTANCE.getBoards().get(new ResourceLocation(stack.getOrCreateTagElement("BlockEntityTag").getString("BoardId")));
        IFormattableTextComponent from = new TranslationTextComponent("block." + HNCMod.MOD_ID + "from");
        if (board != null)
            from.append(board.getPressurePlate().getName());
        else
            from.append("N/A");
        lines.add(from.withStyle(TextFormatting.DARK_GRAY));
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ChoppingBoardTileEntity();
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state)
    {
        TileEntity tile = reader.getBlockEntity(pos);
        return tile instanceof ChoppingBoardTileEntity ? ((ChoppingBoardTileEntity) tile).getItem() : super.getCloneItemStack(reader, pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, World level, BlockPos pos, int p_189539_4_, int p_189539_5_)
    {
        super.triggerEvent(state, level, pos, p_189539_4_, p_189539_5_);
        TileEntity tile = level.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(p_189539_4_, p_189539_5_);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state)
    {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        switch (state.getValue(HORIZONTAL_FACING))
        {
            case NORTH:
            case SOUTH:
                return SHAPE_NS;
            default:
                return SHAPE_EW;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }
}
