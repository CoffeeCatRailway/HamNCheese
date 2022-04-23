package io.github.coffeecatrailway.hamncheese.common.block.dispenser;

import io.github.coffeecatrailway.hamncheese.common.block.TreeTapBlock;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public abstract class TreeTapDispenseBehavior extends OptionalDispenseItemBehavior
{
    protected static final DispenseItemBehavior DEFAULT = new DefaultDispenseItemBehavior();
    protected final DispenseItemBehavior parent;

    public TreeTapDispenseBehavior(DispenseItemBehavior parent)
    {
        this.parent = parent;
    }

    @Override
    protected void playSound(BlockSource source)
    {
        if (this.isSuccess())
            super.playSound(source);
    }

    @Override
    protected void playAnimation(BlockSource source, Direction direction)
    {
        if (this.isSuccess())
            super.playAnimation(source, direction);
    }

    protected abstract ItemStack execute(BlockSource source, ItemStack stack, Level level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket);

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack)
    {
        Level level = source.getLevel();
        BlockPos relativePos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        BlockState relative = level.getBlockState(relativePos);
        if (!relative.is(HNCBlocks.TREE_TAP.get()))
        {
            this.setSuccess(false);
            return this.parent.dispense(source, stack);
        }
        return this.execute(source, stack, level, relativePos, relative, relative.getValue(TreeTapBlock.SAP_LEVEL), relative.getValue(TreeTapBlock.BUCKET));
    }

    protected ItemStack takeLiquid(BlockSource source, ItemStack stack)
    {
        return this.takeLiquid(source, stack, ItemStack.EMPTY);
    }

    /**
     * From: {@link DispenseItemBehavior}
     */
    protected ItemStack takeLiquid(BlockSource source, ItemStack stack, ItemStack newStack)
    {
        stack.shrink(1);
        if (stack.isEmpty())
            return newStack.copy();
        else
        {
            if (source.<DispenserBlockEntity>getEntity().addItem(newStack.copy()) < 0)
                DEFAULT.dispense(source, stack);
            return stack;
        }
    }

    public static class Bucket extends TreeTapDispenseBehavior
    {
        public Bucket(DispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack, Level level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
        {
            if (sap == 3)
            {
                ((TreeTapBlock) relative.getBlock()).setSapLevel(level, relativePos, relative, 0, true);
                this.setSuccess(true);
                return this.takeLiquid(source, stack, new ItemStack(HNCFluids.MAPLE_SAP_BUCKET.get()));
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
        public GlassBottle(DispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack, Level level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
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
        public MapleSapBucket(DispenseItemBehavior parent)
        {
            super(parent);
        }

        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack, Level level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
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
        protected ItemStack execute(BlockSource source, ItemStack stack, Level level, BlockPos relativePos, BlockState relative, int sap, boolean hasBucket)
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
