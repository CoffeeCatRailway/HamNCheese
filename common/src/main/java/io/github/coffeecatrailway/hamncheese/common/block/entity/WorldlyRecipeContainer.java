package io.github.coffeecatrailway.hamncheese.common.block.entity;

import com.google.common.collect.Lists;
import gg.moonflower.pollen.api.util.NbtConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 14/06/2022
 */
public interface WorldlyRecipeContainer<T extends BlockEntity> extends Container, WorldlyContainer, RecipeHolder, StackedContentsCompatible
{
    Map<ResourceLocation, Integer> getRecipeAmounts();

    NonNullList<ItemStack> getInventory();

    T getThis();

    default void markUpdated()
    {
        this.markUpdated(this.getThis());
    }

    default void markUpdated(T entity)
    {
        entity.setChanged();
        if (entity.hasLevel())
            entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
    }

    default void giveExperience(Player player)
    {
        List<Recipe<?>> recipes = Lists.newArrayList();

        this.getRecipeAmounts().forEach((location, amount) -> player.level.getRecipeManager().byKey(location).ifPresent(iRecipe -> {
            recipes.add(iRecipe);
            this.giveExperience(player, amount, Mth.nextDouble(player.getRandom(), 0f, 1f));
        }));

        player.awardRecipes(recipes);
        this.getRecipeAmounts().clear();
    }

    default void giveExperience(Player player, float amount, double experience)
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
    default boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        if (direction != null)
            return Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index); // This works if the world is reloaded. Why is direction nullable?!
        return this.canPlaceItem(index, stack);
    }

    @Override
    default boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        Item item = stack.getItem();
        return item != Items.WATER_BUCKET && item != Items.BUCKET && Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index);
    }

    @Override
    default int getContainerSize()
    {
        return this.getInventory().size();
    }

    @Override
    default boolean isEmpty()
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            if (!this.getInventory().get(i).isEmpty())
                return false;
        return true;
    }

    @Override
    default ItemStack getItem(int index)
    {
        return this.getInventory().get(index);
    }

    @Override
    default ItemStack removeItem(int index, int amount)
    {
        this.markUpdated();
        return index >= 0 && index < this.getContainerSize() && !this.getItem(index).isEmpty() && amount > 0 ? this.getItem(index).split(amount) : ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeItemNoUpdate(int index)
    {
        if (index >= 0 && index < this.getContainerSize())
        {
            ItemStack stack = this.getItem(index).copy();
            this.getInventory().set(index, ItemStack.EMPTY);
            return stack;
        } else
            return ItemStack.EMPTY;
    }

    @Override
    default void setItem(int index, ItemStack stack)
    {
        this.getInventory().set(index, stack);
        if (stack.getCount() > this.getMaxStackSize())
            stack.setCount(this.getMaxStackSize());
        this.markUpdated();
    }

    @Override
    default boolean stillValid(Player player)
    {
        return this.getThis().hasLevel() && this.getThis().getLevel().getBlockEntity(this.getThis().getBlockPos()) == this && player.distanceToSqr((double) this.getThis().getBlockPos().getX() + .5d, (double) this.getThis().getBlockPos().getY() + .5d, (double) this.getThis().getBlockPos().getZ() + .5d) <= 64d;
    }

    @Override
    default void clearContent()
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            this.setItem(i, ItemStack.EMPTY);
    }

    @Override
    default void fillStackedContents(StackedContents contents)
    {
        for (int i = 0; i < this.getContainerSize(); i++)
            contents.accountStack(this.getItem(i));
    }

    @Override
    default void setRecipeUsed(@Nullable Recipe<?> recipe)
    {
        if (recipe != null)
            this.getRecipeAmounts().compute(recipe.getId(), (location, amount) -> 1 + ((amount == null) ? 0 : amount));
        this.markUpdated();
    }

    default void load(CompoundTag compoundTag)
    {
        ContainerHelper.loadAllItems(compoundTag, this.getInventory());
        ListTag recipesUsedNbt = compoundTag.getList("RecipesUsed", NbtConstants.COMPOUND);
        for (int i = 0; i < recipesUsedNbt.size(); i++)
        {
            CompoundTag obj = recipesUsedNbt.getCompound(i);
            this.getRecipeAmounts().put(new ResourceLocation(obj.getString("Location")), obj.getInt("Amount"));
        }
    }

    default void save(CompoundTag compoundTag)
    {
        saveEveryItem(compoundTag, this.getInventory());
        ListTag recipesUsedTag = new ListTag();
        this.getRecipeAmounts().forEach((location, amount) -> {
            CompoundTag obj = new CompoundTag();
            obj.putString("Location", location.toString());
            obj.putInt("Amount", amount);
            recipesUsedTag.add(obj);
        });
        compoundTag.put("RecipesUsed", recipesUsedTag);
    }

    static CompoundTag saveEveryItem(CompoundTag compoundTag, NonNullList<ItemStack> nonNullList)
    {
        return saveEveryItem(compoundTag, nonNullList, false);
    }

    static CompoundTag saveEveryItem(CompoundTag compoundTag, NonNullList<ItemStack> nonNullList, boolean includeEmpty)
    {
        ListTag itemsTag = new ListTag();
        for (int i = 0; i < nonNullList.size(); ++i)
        {
            ItemStack itemStack = nonNullList.get(i);
            if (!itemStack.isEmpty() || includeEmpty)
            {
                CompoundTag slotTag = new CompoundTag();
                slotTag.putByte("Slot", (byte) i);
                itemStack.save(slotTag);
                itemsTag.add(slotTag);
            }
        }

        compoundTag.put("Items", itemsTag);
        return compoundTag;
    }

    default boolean hasItems(int startSlot, int endSlot)
    {
        return this.hasItems(startSlot, endSlot, false);
    }

    default boolean hasItems(int startSlot, int endSlot, boolean all)
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
