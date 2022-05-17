package io.github.coffeecatrailway.hamncheese.compat.wthit;

import io.github.coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;

/**
 * @author CoffeeCatRailway
 * Created: 16/05/2022
 */
public class HNCWailaPlugin implements IWailaPlugin
{
    @Override
    public void register(IRegistrar reg)
    {
        reg.addComponent(new TreeTapBlockOverride(), TooltipPosition.BODY, TreeTapBlock.class);
    }
}
