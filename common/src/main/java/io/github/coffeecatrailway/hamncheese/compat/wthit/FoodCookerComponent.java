package io.github.coffeecatrailway.hamncheese.compat.wthit;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.entity.FoodCookerBlockEntity;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author CoffeeCatRailway
 * Created: 19/05/2022
 */
public class FoodCookerComponent implements IBlockComponentProvider
{
    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
    {
        if (accessor.getBlockEntity() instanceof FoodCookerBlockEntity)
        {
            float cookTime = ((FoodCookerBlockEntity) accessor.getBlockEntity()).data.get(2);
            float cookTimeTotal = ((FoodCookerBlockEntity) accessor.getBlockEntity()).data.get(3);
            if (cookTime > 0 && cookTimeTotal > 0)
            {
                int progress = Math.round((cookTime / cookTimeTotal) * 100f);
                tooltip.addLine(new TranslatableComponent("waila." + HamNCheese.MOD_ID + ".progress", progress).append("%"));
            }
        }
    }
}
