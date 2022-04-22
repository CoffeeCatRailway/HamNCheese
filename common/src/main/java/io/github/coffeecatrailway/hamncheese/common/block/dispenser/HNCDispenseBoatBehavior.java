package io.github.coffeecatrailway.hamncheese.common.block.dispenser;

import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class HNCDispenseBoatBehavior extends DefaultDispenseItemBehavior
{
    private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
    private final HNCBoatEntity.ModType type;

    public HNCDispenseBoatBehavior(HNCBoatEntity.ModType type)
    {
        this.type = type;
    }

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack)
    {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Level level = source.getLevel();
        double d0 = source.x() + (double) ((float) direction.getStepX() * 1.125f);
        double d1 = source.y() + (double) ((float) direction.getStepY() * 1.125f);
        double d2 = source.z() + (double) ((float) direction.getStepZ() * 1.125f);
        BlockPos blockpos = source.getPos().relative(direction);
        double d3;
        if (level.getFluidState(blockpos).is(FluidTags.WATER))
            d3 = 1d;
        else
        {
            if (!level.getBlockState(blockpos).isAir() || !level.getFluidState(blockpos.below()).is(FluidTags.WATER))
                return this.defaultBehavior.dispense(source, stack);

            d3 = 0d;
        }

        HNCBoatEntity boat = new HNCBoatEntity(level, d0, d1 + d3, d2);
        boat.setModType(this.type);
        boat.setYRot(direction.toYRot());
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    @Override
    protected void playSound(BlockSource source)
    {
        source.getLevel().levelEvent(1000, source.getPos(), 0);
    }
}
