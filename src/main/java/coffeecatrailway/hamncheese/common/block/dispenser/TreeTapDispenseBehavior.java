package coffeecatrailway.hamncheese.common.block.dispenser;

import coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public abstract class TreeTapDispenseBehavior extends OptionalDispenseBehavior
{
    protected final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
    protected final IDispenseItemBehavior parent;

    public TreeTapDispenseBehavior(IDispenseItemBehavior parent)
    {
        this.parent = parent;
    }

    @Override
    protected void playSound(IBlockSource source)
    {
        if (this.isSuccess())
            super.playSound(source);
    }

    @Override
    protected void playAnimation(IBlockSource source, Direction direction)
    {
        if (this.isSuccess())
            super.playAnimation(source, direction);
    }

    protected abstract ItemStack execute(IBlockSource source, ItemStack stack, World level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket);

    @Override
    protected ItemStack execute(IBlockSource source, ItemStack stack)
    {
        World level = source.getLevel();
        BlockPos relativePos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        BlockState relative = level.getBlockState(relativePos);
        if (!relative.is(HNCBlocks.TREE_TAP.get()))
        {
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
        return this.execute(source, stack, level, relativePos, relative, relative.getValue(TreeTapBlock.SAP_LEVEL), relative.getValue(TreeTapBlock.BUCKET));
    }

    public static class GlassBottle extends TreeTapDispenseBehavior
    {
        public GlassBottle(IDispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(IBlockSource source, ItemStack stack, World level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
        {
            if (sap - 1 >= 0)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, sap - 1, true);
                this.setSuccess(true);
                return new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get());
            }
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
    }
}
