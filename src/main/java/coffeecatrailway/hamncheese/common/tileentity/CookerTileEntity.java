package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
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
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 11/05/2021
 */
@SuppressWarnings("unchecked")
public abstract class CookerTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity
{
    protected final ItemStackHandler inventory;

    private int burnTime;
    private int burnTimeTotal;
    private int cookTime;
    private int cookTimeTotal;
    public final IIntArray data = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return CookerTileEntity.this.burnTime;
                case 1:
                    return CookerTileEntity.this.burnTimeTotal;
                case 2:
                    return CookerTileEntity.this.cookTime;
                case 3:
                    return CookerTileEntity.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value)
        {
            switch (index)
            {
                case 0:
                    CookerTileEntity.this.burnTime = value;
                    break;
                case 1:
                    CookerTileEntity.this.burnTimeTotal = value;
                    break;
                case 2:
                    CookerTileEntity.this.cookTime = value;
                    break;
                case 3:
                    CookerTileEntity.this.cookTimeTotal = value;
                    break;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    };

    private final Map<ResourceLocation, Integer> recipeAmounts = Maps.newHashMap();
    private final IRecipeType<IRecipe<IInventory>> recipeType;

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public CookerTileEntity(TileEntityType<?> type, int inventorySize, IRecipeType<? extends IRecipe<?>> recipeType)
    {
        super(type);
        this.inventory = new ItemStackHandler(inventorySize)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                CookerTileEntity.this.sendUpdates(CookerTileEntity.this);
            }
        };
        this.recipeType = (IRecipeType<IRecipe<IInventory>>) recipeType;
    }

    protected abstract int[] getTableSlots();

    protected abstract int[] getOutputSlots();

    protected abstract int[] getFuelSlots();

    protected abstract int getCookTimeTotal();

    protected abstract boolean canSmelt(@Nullable IRecipe<IInventory> iRecipe);

    protected abstract void smeltRecipe(@Nullable IRecipe<IInventory> iRecipe);

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
    public int[] getSlotsForFace(Direction direction)
    {
        switch (direction)
        {
            case DOWN:
                return this.getOutputSlots();
            case UP:
                return this.getTableSlots();
            default:
                return this.getFuelSlots();
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
        boolean flag = !stack.isEmpty() && stack.sameItemStackIgnoreDurability(itemStack) && ItemStack.tagMatches(stack, itemStack);
        this.inventory.setStackInSlot(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());

        if (index == 0 && !flag)
        {
            this.cookTimeTotal = this.getCookTimeTotal();
            this.cookTime = 0;
            this.sendUpdates(this);
        }
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
    public void tick()
    {
        boolean isBurningFlag = this.isBurning();
        boolean flag = false;
        if (this.isBurning())
        {
            this.burnTime--;
            this.sendUpdates(this);
        }

        if (this.hasLevel() && !this.level.isClientSide())
        {
            ItemStack fuelStack = this.getNextFuelStack().getFirst();
            int fuelSlot = this.getNextFuelStack().getSecond();
            if (this.isBurning() || !fuelStack.isEmpty() && this.hasItems())
            {
                IRecipe<IInventory> iRecipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
                if (!this.isBurning() && this.canSmelt(iRecipe))
                {
                    this.burnTime = getBurnTime(fuelStack);
                    this.burnTimeTotal = this.burnTime;
                    if (this.isBurning())
                    {
                        flag = true;
                        if (fuelStack.hasContainerItem())
                        {
                            this.setItem(fuelSlot, fuelStack.getContainerItem());
                            this.sendUpdates(this);
                        } else
                        {
                            if (!fuelStack.isEmpty())
                            {
                                fuelStack.shrink(1);
                                if (fuelStack.isEmpty())
                                    this.setItem(fuelSlot, fuelStack.getContainerItem());
                                this.sendUpdates(this);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(iRecipe))
                {
                    this.cookTime++;
                    if (this.cookTime >= this.cookTimeTotal)
                    {
                        this.smeltRecipe(iRecipe);
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTimeTotal();
                        flag = true;
                        this.sendUpdates(this);
                    }
                } else
                {
                    this.cookTime = 0;
                    this.sendUpdates(this);
                }
            } else if (!this.isBurning() && this.cookTime > 0)
            {
                this.cookTime -= MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                this.sendUpdates(this);
            }

            if (isBurningFlag != this.isBurning())
            {
                flag = true;
                this.level.setBlock(this.getBlockPos(), this.level.getBlockState(this.getBlockPos()).setValue(PizzaOvenBlock.LIT, this.isBurning()), Constants.BlockFlags.DEFAULT);
            }
        }

        if (flag)
            this.sendUpdates(this);
    }

    private boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));

        this.burnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");

        this.burnTimeTotal = getBurnTime(this.getNextFuelStack().getFirst());
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
        compound.put("Inventory", this.inventory.serializeNBT());

        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);

        ListNBT recipesUsedNbt = new ListNBT();
        this.recipeAmounts.forEach((location, amount) -> {
            CompoundNBT obj = new CompoundNBT();
            obj.putString("Location", location.toString());
            obj.putInt("Amount", amount);
            recipesUsedNbt.add(obj);
        });
        compound.put("RecipesUsed", recipesUsedNbt);
        return super.save(compound);
    }

    public boolean hasItems()
    {
        for (int i = 0; i < this.getTableSlots().length; i++)
            if (!this.getItem(i).isEmpty())
                return true;
        return false;
    }

    private Pair<ItemStack, Integer> getNextFuelStack()
    {
        for (int i = this.getTableSlots().length; i < this.getTableSlots().length + this.getFuelSlots().length; i++)
            if (!this.getItem(i).isEmpty())
                return Pair.of(this.getItem(i), i);
        return Pair.of(ItemStack.EMPTY, this.getTableSlots().length);
    }

    public static int getBurnTime(ItemStack fuelStack)
    {
        if (fuelStack.isEmpty())
            return 0;
        else
            return ForgeEventFactory.getItemBurnTime(fuelStack, fuelStack.getBurnTime() == -1 ? ForgeHooks.getBurnTime(fuelStack) : fuelStack.getBurnTime());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (!this.isRemoved() && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            switch (side)
            {
                case UP:
                    return this.handlers[0].cast();
                case DOWN:
                    return this.handlers[1].cast();
                default:
                    return this.handlers[2].cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers) handler.invalidate();
    }
}
