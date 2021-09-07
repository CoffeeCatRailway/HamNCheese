package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 7/09/2021
 */
public class HNCStandingSignBlock extends StandingSignBlock
{
    public HNCStandingSignBlock(Properties properties, WoodType woodType)
    {
        super(properties, woodType);
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
        return HNCTileEntities.SIGN.get().create();
    }
}
