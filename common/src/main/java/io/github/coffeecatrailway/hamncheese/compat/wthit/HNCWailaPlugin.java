package io.github.coffeecatrailway.hamncheese.compat.wthit;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.GrillBlock;
import io.github.coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import io.github.coffeecatrailway.hamncheese.common.block.PopcornMachineBlock;
import io.github.coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
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
        reg.addComponent(new FoodCookerComponent(), TooltipPosition.BODY, PizzaOvenBlock.class);
        reg.addComponent(new FoodCookerComponent(), TooltipPosition.BODY, GrillBlock.class);
        reg.addComponent(new IBlockComponentProvider()
        {
            @Override
            public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
            {
                if (accessor.getBlockEntity() instanceof PopcornMachineBlockEntity)
                {
                    int flavourTime = ((PopcornMachineBlockEntity) accessor.getBlockEntity()).data.get(0);
                    tooltip.addLine(HNCLanguage.getPopcorn(((PopcornMachineBlockEntity) accessor.getBlockEntity()).data.get(1)));
                    if (flavourTime > 0)
                        tooltip.addLine(HNCLanguage.getFlavour(flavourTime));
                }
            }
        }, TooltipPosition.BODY, PopcornMachineBlock.class);
    }
}
