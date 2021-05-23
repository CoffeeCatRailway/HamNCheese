package coffeecatrailway.hamncheese.common.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import coffeecatrailway.hamncheese.common.inventory.PizzaOvenContainer;
import coffeecatrailway.hamncheese.common.item.crafting.PizzaOvenRecipe;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import coffeecatrailway.hamncheese.registry.HNCTileEntities;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
public class PizzaOvenTileEntity extends HNCLockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity
{
    private static final int[] SLOTS_UP = new int[]{
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };
    private static final int[] SLOTS_DOWN = new int[]{12};
    private static final int[] SLOTS_HORIZONTAL = new int[]{9, 10, 11};

    public ItemStackHandler inventory = new ItemStackHandler(13)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            PizzaOvenTileEntity.this.sendUpdates(PizzaOvenTileEntity.this);
        }
    };
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
                    return PizzaOvenTileEntity.this.burnTime;
                case 1:
                    return PizzaOvenTileEntity.this.burnTimeTotal;
                case 2:
                    return PizzaOvenTileEntity.this.cookTime;
                case 3:
                    return PizzaOvenTileEntity.this.cookTimeTotal;
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
                    PizzaOvenTileEntity.this.burnTime = value;
                    break;
                case 1:
                    PizzaOvenTileEntity.this.burnTimeTotal = value;
                    break;
                case 2:
                    PizzaOvenTileEntity.this.cookTime = value;
                    break;
                case 3:
                    PizzaOvenTileEntity.this.cookTimeTotal = value;
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
    private final IRecipeType<PizzaOvenRecipe> recipeType;

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public PizzaOvenTileEntity()
    {
        super(HNCTileEntities.PIZZA_OVEN.get());
        this.recipeType = HNCRecipes.PIZZA_OVEN_TYPE;
    }

    @Override
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

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        if (direction == Direction.DOWN)
            return SLOTS_DOWN;
        else
            return direction == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
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
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container." + HNCMod.MOD_ID + ".pizza_oven");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory)
    {
        return new PizzaOvenContainer(id, playerInventory, this, this.data);
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
            this.cookTimeTotal = 200; // TODO: make cookTimeTotal a config value
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
                    this.burnTime = PizzaOvenTileEntity.getBurnTime(fuelStack);
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
                        this.cookTimeTotal = 200; // TODO: make cookTimeTotal a config value
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

        this.burnTimeTotal = PizzaOvenTileEntity.getBurnTime(this.getNextFuelStack().getFirst());
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
        for (int i = 0; i < 9; i++)
            if (!this.getItem(i).isEmpty())
                return true;
        return false;
    }

    private Pair<ItemStack, Integer> getNextFuelStack()
    {
        for (int i = 9; i <= 11; i++)
            if (!this.getItem(i).isEmpty())
                return Pair.of(this.getItem(i), i);
        return Pair.of(ItemStack.EMPTY, 9);
    }

    private boolean canSmelt(@Nullable IRecipe<IInventory> iRecipe)
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

    private void smeltRecipe(@Nullable IRecipe<IInventory> iRecipe)
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
