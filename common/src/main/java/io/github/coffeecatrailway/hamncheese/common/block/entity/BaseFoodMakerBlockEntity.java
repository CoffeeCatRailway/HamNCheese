package io.github.coffeecatrailway.hamncheese.common.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gg.moonflower.pollen.api.util.NbtConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 5/08/2021
 */
public abstract class BaseFoodMakerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible
{
    protected final NonNullList<ItemStack> inventory;

    private final Map<ResourceLocation, Integer> recipeAmounts = Maps.newHashMap();
    protected final RecipeType<Recipe<Container>> recipeType;

    public BaseFoodMakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, RecipeType<? extends Recipe<?>> recipeType)
    {
        super(type, pos, state);
        this.inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
        this.recipeType = (RecipeType<Recipe<Container>>) recipeType;
    }

    protected void markUpdated()
    {
        super.setChanged();
        if (this.hasLevel())
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compound = new CompoundTag();
        this.saveAdditional(compound);
        return compound;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void giveExperience(Player player)
    {
        List<Recipe<?>> recipes = Lists.newArrayList();

        this.recipeAmounts.forEach((location, amount) -> player.level.getRecipeManager().byKey(location).ifPresent(iRecipe -> {
            recipes.add(iRecipe);
            this.giveExperience(player, amount, Mth.nextDouble(player.getRandom(), 0f, 1f));
        }));

        player.awardRecipes(recipes);
        this.recipeAmounts.clear();
    }

    protected void giveExperience(Player player, float amount, double experience)
    {
        if (experience <= 0f)
        {
            amount = 0;
        } else if (experience < 1f)
        {
            int i = Mth.floor(amount * experience);
            if (i < Mth.ceil(amount * experience) && Math.random() < amount * experience - (float) i)
                i++;

            amount = i;
        }

        while (amount > 0)
        {
            int j = ExperienceOrb.getExperienceValue((int) amount);
            amount -= j;
            player.level.addFreshEntity(new ExperienceOrb(player.level, player.position().x, player.position().y + .5d, player.position().z + .5d, j));
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        if (direction != null)
            return Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index); // This works if the world is reloaded. Why is direction nullable?!
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        Item item = stack.getItem();
        return item != Items.WATER_BUCKET && item != Items.BUCKET && Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index);
    }

    @Override
    public int getContainerSize()
    {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty()
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            if (!this.inventory.get(i).isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getItem(int index)
    {
        return this.inventory.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount)
    {
        this.markUpdated();
        return index >= 0 && index < this.getContainerSize() && !this.getItem(index).isEmpty() && amount > 0 ? this.getItem(index).split(amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        if (index >= 0 && index < this.getContainerSize())
        {
            ItemStack stack = this.getItem(index).copy();
            this.inventory.set(index, ItemStack.EMPTY);
            return stack;
        } else
            return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());
        this.markUpdated();
    }

    @Override
    public boolean stillValid(Player player)
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
    public void fillStackedContents(StackedContents contents)
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            contents.accountStack(this.getItem(i));
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe)
    {
        if (recipe != null)
            this.recipeAmounts.compute(recipe.getId(), (location, amount) -> 1 + ((amount == null) ? 0 : amount));
        this.markUpdated();
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed()
    {
        return null;
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        ContainerHelper.loadAllItems(compound, this.inventory);
        ListTag recipesUsedNbt = compound.getList("RecipesUsed", NbtConstants.COMPOUND);
        for (int i = 0; i < recipesUsedNbt.size(); i++)
        {
            CompoundTag obj = recipesUsedNbt.getCompound(i);
            this.recipeAmounts.put(new ResourceLocation(obj.getString("Location")), obj.getInt("Amount"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.inventory);
        ListTag recipesUsedNbt = new ListTag();
        this.recipeAmounts.forEach((location, amount) -> {
            CompoundTag obj = new CompoundTag();
            obj.putString("Location", location.toString());
            obj.putInt("Amount", amount);
            recipesUsedNbt.add(obj);
        });
        compound.put("RecipesUsed", recipesUsedNbt);
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
