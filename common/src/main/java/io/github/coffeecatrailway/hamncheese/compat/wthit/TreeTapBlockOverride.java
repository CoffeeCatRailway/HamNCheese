package io.github.coffeecatrailway.hamncheese.compat.wthit;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author CoffeeCatRailway
 * Created: 17/05/2022
 */
public class TreeTapBlockOverride implements IBlockComponentProvider
{
    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
    {
        tooltip.addLine(new TranslatableComponent("waila." + HamNCheese.MOD_ID + ".tree_tap.level", accessor.getBlockState().getValue(TreeTapBlock.SAP_LEVEL)));
    }
}
