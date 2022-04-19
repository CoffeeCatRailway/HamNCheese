package io.github.coffeecatrailway.hamncheese.common.block;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

/**
 * @author CoffeeCatRailway
 * Created: 18/04/2022
 */
public class SaplingBlockOverride extends SaplingBlock
{
    public SaplingBlockOverride(AbstractTreeGrower abstractTreeGrower, Properties properties)
    {
        super(abstractTreeGrower, properties);
    }
}
