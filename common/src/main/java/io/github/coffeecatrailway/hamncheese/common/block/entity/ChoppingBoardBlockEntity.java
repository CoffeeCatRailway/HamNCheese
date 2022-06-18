package io.github.coffeecatrailway.hamncheese.common.block.entity;

import com.google.common.collect.Maps;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 14/06/2022
 */
public class ChoppingBoardBlockEntity extends BlockEntity implements WorldlyRecipeContainer<ChoppingBoardBlockEntity>
{
    protected final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private final Map<ResourceLocation, Integer> recipeAmounts = Maps.newHashMap();

    private ChoppingBoardRecipe recipe = null;

    public ChoppingBoardBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(HNCBlockEntities.CHOPPING_BOARD.get(), blockPos, blockState);
    }

    public boolean placeIngredient(ItemStack stack, boolean replace, Player player)
    {
        if (this.getItem(0).isEmpty() || replace)
        {
            this.setItem(0, (player.getAbilities().instabuild ? stack.copy() : stack).split(1));
            this.markUpdated();
            return true;
        }
        return false;
    }

    public ItemStack getIngredient()
    {
        return this.getItem(0);
    }

    public void dropIngredient(Player player)
    {
        ItemStack stack = this.getIngredient().copy();
        Containers.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), stack);
        this.placeIngredient(ItemStack.EMPTY, true, player);
    }

    @Override
    public Map<ResourceLocation, Integer> getRecipeAmounts()
    {
        return this.recipeAmounts;
    }

    @Override
    public NonNullList<ItemStack> getInventory()
    {
        return this.inventory;
    }

    @Override
    public ChoppingBoardBlockEntity getThis()
    {
        return this;
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return new int[]{0};
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe)
    {
        WorldlyRecipeContainer.super.setRecipeUsed(recipe);
        if (recipe instanceof ChoppingBoardRecipe)
            this.recipe = (ChoppingBoardRecipe) recipe;
        this.markUpdated();
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed()
    {
        return this.recipe;
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        WorldlyRecipeContainer.super.load(compoundTag);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        WorldlyRecipeContainer.super.save(compoundTag);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return WorldlyRecipeContainer.saveEveryItem(new CompoundTag(), this.inventory, true);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
