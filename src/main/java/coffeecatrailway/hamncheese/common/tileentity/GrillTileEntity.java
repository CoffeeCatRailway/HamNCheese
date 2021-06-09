package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2021
 */
public class GrillTileEntity extends HNCCookerTileEntity
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
        return null;
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
        return false;
    }

    @Override
    protected void smeltRecipe(@Nullable IRecipe<IInventory> iRecipe)
    {

    }
}
