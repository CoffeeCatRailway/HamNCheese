package coffeecatrailway.hamncheese.common.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class MapleSapDispenseBehavior extends DefaultDispenseItemBehavior
{
    @Override
    protected ItemStack execute(IBlockSource source, ItemStack stack)
    {
        BucketItem bucket = (BucketItem) stack.getItem();
        BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        World level = source.getLevel();
        if (bucket.emptyBucket(null, level, blockpos, null)) {
            bucket.checkExtraContent(level, stack, blockpos);
            return new ItemStack(Items.BUCKET);
        } else
            return super.dispense(source, stack);
    }
}
