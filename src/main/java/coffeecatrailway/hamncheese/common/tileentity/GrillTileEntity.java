package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.GrillContainer;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2021
 */
public class GrillTileEntity extends CookerTileEntity
{
    private static final int[] SLOTS_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_DOWN = new int[]{6, 7, 8, 9};
    private static final int[] SLOTS_HORIZONTAL = new int[]{4, 5};

    public GrillTileEntity()
    {
        super(HNCTileEntities.GRILL.get(), 10, HNCRecipes.GRILL_TYPE);
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container." + HNCMod.MOD_ID + ".grill");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory)
    {
        return new GrillContainer(id, playerInventory, this, this.data);
    }

    @Override
    protected int[] getTableSlots()
    {
        return SLOTS_UP;
    }

    @Override
    protected int[] getOutputSlots()
    {
        return SLOTS_DOWN;
    }

    @Override
    protected int[] getFuelSlots()
    {
        return SLOTS_HORIZONTAL;
    }

    @Override
    protected int getCookTimeTotal()
    {
        return HNCMod.SERVER_CONFIG.grillCookTimeTotal.get();
    }

    @Override
    protected boolean canSmelt(@Nullable IRecipe<IInventory> iRecipe)
    {
        if (iRecipe != null && this.hasItems())
        {
            ItemStack result = iRecipe.assemble(this);
            if (result.isEmpty())
                return false;
            else
            {
                ItemStack output = this.getItem(12);
                if (output.isEmpty() || (output.getCount() + result.getCount() <= this.getMaxStackSize() && output.getCount() + result.getCount() <= output.getMaxStackSize()))
                    return true;
                else if (!output.areShareTagsEqual(result))
                    return false;
                else
                    return output.getCount() + result.getCount() <= output.getMaxStackSize();
            }
        } else
            return false;
    }

    @Override
    protected void smeltRecipe(@Nullable IRecipe<IInventory> iRecipe)
    {
        if (this.canSmelt(iRecipe))
        {
            ItemStack[] ingredients = new ItemStack[9];
            for (int i = 0; i < 9; i++)
                ingredients[i] = this.getItem(i);

            ItemStack result = iRecipe.assemble(this);
            ItemStack output = this.getItem(12);
            if (output.isEmpty())
                this.setItem(12, result.copy());
            else if (output.areShareTagsEqual(result))
                output.grow(result.getCount());

            if (this.hasLevel() && !this.level.isClientSide())
                this.setRecipeUsed(iRecipe);

            for (ItemStack ingredient : ingredients)
                if (!ingredient.isEmpty())
                    ingredient.shrink(1);
        }
    }
}
