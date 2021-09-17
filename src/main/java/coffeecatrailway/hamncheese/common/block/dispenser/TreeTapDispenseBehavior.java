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
import net.minecraft.item.Items;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public abstract class TreeTapDispenseBehavior extends OptionalDispenseBehavior
{
    protected static final IDispenseItemBehavior DEFAULT = new DefaultDispenseItemBehavior();
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

    protected ItemStack takeLiquid(IBlockSource source, ItemStack stack)
    {
        return this.takeLiquid(source, stack, ItemStack.EMPTY);
    }

    /**
     * From: {@link IDispenseItemBehavior}
     */
    protected ItemStack takeLiquid(IBlockSource source, ItemStack stack, ItemStack newStack)
    {
        stack.shrink(1);
        if (stack.isEmpty())
            return newStack.copy();
        else
        {
            if (source.<DispenserTileEntity>getEntity().addItem(newStack.copy()) < 0)
                DEFAULT.dispense(source, stack);
            return stack;
        }
    }

    public static class Bucket extends TreeTapDispenseBehavior
    {
        public Bucket(IDispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(IBlockSource source, ItemStack stack, World level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
        {
            if (sap == 3)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, 0, true);
                this.setSuccess(true);
                return this.takeLiquid(source, stack, new ItemStack(HNCItems.MAPLE_SAP_BUCKET.get()));
            } else if (sap > 0 && sap < 3)
            {
                this.setSuccess(false);
                return this.parent.dispense(source, stack);
            } else
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, 0, true);
                this.setSuccess(true);
                return this.takeLiquid(source, stack);
            }
        }
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
            if (hasBucket && sap - 1 >= 0)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, sap - 1, true);
                this.setSuccess(true);
                return this.takeLiquid(source, stack, new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get()));
            }
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
    }

    public static class MapleSapBucket extends TreeTapDispenseBehavior
    {
        public MapleSapBucket(IDispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(IBlockSource source, ItemStack stack, World level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
        {
            if (sap == 0)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, 3, true);
                this.setSuccess(true);
                if (hasBucket)
                    return this.takeLiquid(source, stack, new ItemStack(Items.BUCKET));
                else
                    return this.takeLiquid(source, stack);
            }
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
    }

    public static class MapleSapBottle extends TreeTapDispenseBehavior
    {
        public MapleSapBottle()
        {
            super(DEFAULT);
        }

        @Override
        protected ItemStack execute(IBlockSource source, ItemStack stack, World level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
        {
            if (hasBucket && sap + 1 <= 3)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, sap + 1, true);
                this.setSuccess(true);
                return this.takeLiquid(source, stack, new ItemStack(Items.GLASS_BOTTLE));
            }
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
    }
}
