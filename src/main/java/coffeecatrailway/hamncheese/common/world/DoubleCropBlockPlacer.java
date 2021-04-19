package coffeecatrailway.hamncheese.common.world;

import coffeecatrailway.hamncheese.common.block.AbstractDoubleCropBlock;
import coffeecatrailway.hamncheese.registry.HNCBlockPlacerTypes;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 15/04/2021
 */
public class DoubleCropBlockPlacer extends BlockPlacer
{
    public static final DoubleCropBlockPlacer INSTANCE = new DoubleCropBlockPlacer();
    public static final Codec<DoubleCropBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(IWorld world, BlockPos pos, BlockState state, Random random)
    {
        AbstractDoubleCropBlock block = (AbstractDoubleCropBlock) state.getBlock();
//        world.setBlock(pos.below(), Blocks.FARMLAND.defaultBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        world.setBlock(pos.above(), block.defaultBlockState().setValue(AbstractDoubleCropBlock.HALF, DoubleBlockHalf.UPPER).setValue(block.getAgeProperty(), block.getMaxAge()), Constants.BlockFlags.BLOCK_UPDATE);
        world.setBlock(pos, block.defaultBlockState().setValue(AbstractDoubleCropBlock.HALF, DoubleBlockHalf.LOWER).setValue(block.getAgeProperty(), block.getMaxAge()), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    protected BlockPlacerType<?> type()
    {
        return HNCBlockPlacerTypes.DOUBLE_CROP_PLACER.get();
    }
}
