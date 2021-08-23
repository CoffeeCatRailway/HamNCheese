package coffeecatrailway.hamncheese.common.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 5/08/2021
 */
public abstract class TickableLockableTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity
{
    protected final ItemStackHandler inventory;

    private final Map<ResourceLocation, Integer> recipeAmounts = Maps.newHashMap();
    protected final IRecipeType<IRecipe<IInventory>> recipeType;

    public TickableLockableTileEntity(TileEntityType<?> type, int inventorySize, IRecipeType<? extends IRecipe<?>> recipeType)
    {
        super(type);
        this.inventory = new ItemStackHandler(inventorySize)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                TickableLockableTileEntity.this.sendUpdates(TickableLockableTileEntity.this);
            }
        };
        this.recipeType = (IRecipeType<IRecipe<IInventory>>) recipeType;
    }

    protected void sendUpdates(TileEntity tile)
    {
        super.setChanged();
        if (tile.hasLevel())
        {
            BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
            tile.getLevel().sendBlockUpdated(tile.getBlockPos(), state, state, 8);
        }
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.worldPosition, 9, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.load(this.getBlockState(), pkt.getTag());
    }

    public void giveExperience(PlayerEntity player)
    {
        List<IRecipe<?>> recipes = Lists.newArrayList();

        this.recipeAmounts.forEach((location, amount) -> player.level.getRecipeManager().byKey(location).ifPresent(iRecipe -> {
            recipes.add(iRecipe);
            this.giveExperience(player, amount, MathHelper.nextDouble(player.getRandom(), 0f, 1f));
        }));

        player.awardRecipes(recipes);
        this.recipeAmounts.clear();
    }

    protected void giveExperience(PlayerEntity player, float amount, double experience)
    {
        if (experience <= 0f)
        {
            amount = 0;
        } else if (experience < 1f)
        {
            int i = MathHelper.floor(amount * experience);
            if (i < MathHelper.ceil(amount * experience) && Math.random() < amount * experience - (float) i)
                i++;

            amount = i;
        }

        while (amount > 0)
        {
            int j = ExperienceOrbEntity.getExperienceValue((int) amount);
            amount -= j;
            player.level.addFreshEntity(new ExperienceOrbEntity(player.level, player.position().x, player.position().y + .5d, player.position().z + .5d, j));
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        if (direction == Direction.DOWN && index == 1)
        {
            Item item = stack.getItem();
            return item != Items.WATER_BUCKET && item != Items.BUCKET;
        }
        return true;
    }

    @Override
    public int getContainerSize()
    {
        return this.inventory.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            if (!this.inventory.getStackInSlot(i).isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getItem(int index)
    {
        return this.inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount)
    {
        return index >= 0 && index < this.getContainerSize() && !this.getItem(index).isEmpty() && amount > 0 ? this.getItem(index).split(amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        if (index >= 0 && index < this.getContainerSize())
        {
            ItemStack stack = this.getItem(index).copy();
            this.inventory.setStackInSlot(index, ItemStack.EMPTY);
            return stack;
        } else
            return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        ItemStack itemStack = this.getItem(index);
        this.inventory.setStackInSlot(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return this.hasLevel() && this.level.getBlockEntity(this.getBlockPos()) == this && player.distanceToSqr((double) this.getBlockPos().getX() + .5d, (double) this.getBlockPos().getY() + .5d, (double) this.getBlockPos().getZ() + .5d) <= 64d;
    }

    @Override
    public void clearContent()
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            this.setItem(i, ItemStack.EMPTY);
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper)
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            helper.accountStack(this.getItem(i));
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe)
    {
        if (recipe != null)
            this.recipeAmounts.compute(recipe.getId(), (location, amount) -> 1 + ((amount == null) ? 0 : amount));
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed()
    {
        return null;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        ListNBT recipesUsedNbt = compound.getList("RecipesUsed", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < recipesUsedNbt.size(); i++)
        {
            CompoundNBT obj = recipesUsedNbt.getCompound(i);
            this.recipeAmounts.put(new ResourceLocation(obj.getString("Location")), obj.getInt("Amount"));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.put("Inventory", this.inventory.serializeNBT());
        ListNBT recipesUsedNbt = new ListNBT();
        this.recipeAmounts.forEach((location, amount) -> {
            CompoundNBT obj = new CompoundNBT();
            obj.putString("Location", location.toString());
            obj.putInt("Amount", amount);
            recipesUsedNbt.add(obj);
        });
        compound.put("RecipesUsed", recipesUsedNbt);
        return compound;
    }

    protected boolean hasItems(int startSlot, int endSlot)
    {
        return this.hasItems(startSlot, endSlot, false);
    }

    protected boolean hasItems(int startSlot, int endSlot, boolean all)
    {
        if (startSlot >= endSlot)
            return false;
        if (all)
        {
            for (int i = startSlot; i < endSlot; i++)
                if (this.getItem(i).isEmpty())
                    return false;
            return true;
        } else
        {
            for (int i = startSlot; i < endSlot; i++)
                if (!this.getItem(i).isEmpty())
                    return true;
            return false;
        }
    }
}
