package io.github.coffeecatrailway.hamncheese.compat.wthit;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.GrillBlock;
import io.github.coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import io.github.coffeecatrailway.hamncheese.common.block.entity.GrillBlockEntity;
import mcp.mobius.waila.api.*;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author CoffeeCatRailway
 * Created: 16/05/2022
 */
public class HNCWailaPlugin implements IWailaPlugin
{
    @Override
    public void register(IRegistrar reg)
    {
        reg.addComponent(new IBlockComponentProvider()
        {
            @Override
            public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
            {
                tooltip.addLine(new TranslatableComponent("waila." + HamNCheese.MOD_ID + ".tree_tap.level", accessor.getBlockState().getValue(TreeTapBlock.SAP_LEVEL)));
            }
        }, TooltipPosition.BODY, TreeTapBlock.class);
        reg.addComponent(new IBlockComponentProvider()
        {
            @Override
            public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
            {
                if (accessor.getBlockEntity() instanceof GrillBlockEntity)
                {
                    float cookTime = ((GrillBlockEntity) accessor.getBlockEntity()).data.get(2);
                    float cookTimeTotal = ((GrillBlockEntity) accessor.getBlockEntity()).data.get(3);
                    if (cookTime > 0 && cookTimeTotal > 0)
                    {
                        int progress = Math.round((cookTime / cookTimeTotal) * 100f);
                        tooltip.addLine(new TranslatableComponent("waila." + HamNCheese.MOD_ID + ".progress", progress).append("%"));
                    }
                }
            }
        }, TooltipPosition.BODY, GrillBlock.class);
    }
}
