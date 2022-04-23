package io.github.coffeecatrailway.hamncheese.common.block.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class MapleSapDispenseBehavior extends DefaultDispenseItemBehavior
{
    private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack)
    {
        BucketItem bucket = (BucketItem) stack.getItem();
        BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        Level level = source.getLevel();
        if (bucket.emptyContents(null, level, blockpos, null))
        {
            bucket.checkExtraContent(null, level, stack, blockpos);
            return new ItemStack(Items.BUCKET);
        } else
            return this.defaultBehavior.dispense(source, stack);
    }
}
