package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * @author CoffeeCatRailway
 * Created: 7/09/2021
 */
public class HNCSignTileEntity extends SignTileEntity
{
    @Override
    public TileEntityType<?> getType()
    {
        return HNCTileEntities.SIGN.get();
    }
}
