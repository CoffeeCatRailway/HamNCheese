package io.github.coffeecatrailway.hamncheese.common.block.entity;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 5/08/2021
 */
public abstract class BaseFoodMakerBlockEntity<T extends BaseFoodMakerBlockEntity<?>> extends BaseContainerBlockEntity implements WorldlyRecipeContainer<T>
{
    protected final NonNullList<ItemStack> inventory;

    private final Map<ResourceLocation, Integer> recipeAmounts = Maps.newHashMap();

    public BaseFoodMakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize)
    {
        super(type, pos, state);
        this.inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
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
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed()
    {
        return null;
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        WorldlyRecipeContainer.super.load(compoundTag);
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        WorldlyRecipeContainer.super.save(compoundTag);
    }
}
