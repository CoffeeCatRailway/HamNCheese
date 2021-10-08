package coffeecatrailway.hamncheese.common.block.dispenser;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

/**
 * @author CoffeeCatRailway
 * Created: 8/10/2021
 */
public class SandwichExplodeBehavior extends DefaultDispenseItemBehavior
{
    private final Behavior defaultBehavior = new Behavior();

    private final Item bread;
    private final Item toast;
    private final boolean twoSides;
    private final Item[] extras;

    public SandwichExplodeBehavior(Item bread, Item toast, boolean twoSides)
    {
        this(bread, toast, twoSides, new Item[]{});
    }

    public SandwichExplodeBehavior(Item bread, Item toast, boolean twoSides, Item... extras)
        {
        this.bread = bread;
        this.toast = toast;
        this.twoSides = twoSides;
        this.extras = extras;
    }

    @Override
    protected ItemStack execute(IBlockSource source, ItemStack stack)
    {
        ItemStack breadStack = new ItemStack(this.bread);
        if (stack.getOrCreateTag().getBoolean(AbstractSandwichItem.TAG_TOASTED))
            breadStack = new ItemStack(this.toast);

        Set<ItemStack> ingredients = AbstractSandwichItem.getIngredients(stack);
        ingredients.add(breadStack.copy());
        if (this.twoSides)
            ingredients.add(breadStack.copy());

        for (Item extra : this.extras)
            ingredients.add(new ItemStack(extra));

        ingredients.forEach(ingredient -> this.defaultBehavior.dispenseNoSound(source, ingredient));
        stack.shrink(1);
        if (stack.getCount() <= 0)
            return ItemStack.EMPTY;
        return stack;
    }

    private static class Behavior extends DefaultDispenseItemBehavior
    {
        public void dispenseNoSound(IBlockSource source, ItemStack stack)
        {
            this.execute(source, stack);
            this.playAnimation(source, source.getBlockState().getValue(DispenserBlock.FACING));
        }
    }
}
