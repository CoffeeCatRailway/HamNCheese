package io.github.coffeecatrailway.hamncheese.common.entity.ai.goal;

import io.github.coffeecatrailway.hamncheese.data.gen.HNCBlockTags;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
public class FindChestWithFoodGoal extends MouseInteractGoal
{
    public FindChestWithFoodGoal(PathfinderMob entity, double speedModifier, int searchRange, int verticalSearchRange)
    {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    protected void interact(Level level, PathfinderMob entity)
    {
//        LazyOptional<IItemHandler> handler = this.getLootContainer(level, this.blockPos).orElseThrow(() -> new IllegalStateException("Item handler isn't present for entity " + entity.getUUID()));
//        for (int i = 0; i < level.random.nextInt(handler.getSlots() / 2 + 1) + handler.getSlots() / 2; i++)
//        {
//            if (level.random.nextFloat() < .05f)
//            {
//                ItemStack stack = handler.getStackInSlot(i);
//                Item item = stack.getItem();
//                if (!HNCItemTags.MOUSE_BLACKLIST.contains(item))
//                {
//                    if (!stack.isEmpty() && item.isEdible() && item.getFoodProperties() != null)
//                    {
//                        stack = entity.eat(level, stack);
//                        stack.grow(1);
//                        int count = stack.getCount();
//                        handler.extractItem(i, count, false);
//                        handler.insertItem(i, new ItemStack(HNCItems.FOOD_SCRAPS.get(), level.random.nextInt(count + 1)), false);
//
//                        entity.heal(item.getFoodProperties().getNutrition() / 2f);
//                    }
//                }
//            }
//        }
        BaseContainerBlockEntity tile = this.getContainerBlock(level, this.blockPos);
        if (tile != null)
        {
            int start = level.random.nextInt(tile.getContainerSize() / 2);
            int end = Math.min(level.random.nextInt((tile.getContainerSize() - start) + 1) + start, tile.getContainerSize());
            for (int i = start; i < end; i++)
            {
                if (level.random.nextFloat() < .05f)
                {
                    ItemStack stack = tile.getItem(i);
                    Item item = stack.getItem();
                    if (!stack.is(HNCItemTags.MOUSE_BLACKLIST))
                    {
                        if (!stack.isEmpty() && item.isEdible() && item.getFoodProperties() != null)
                        {
                            stack = entity.eat(level, stack);
                            stack.grow(1);
                            int count = stack.getCount();
                            tile.setItem(i, new ItemStack(HNCItems.FOOD_SCRAPS.get(), level.random.nextInt(count) + 1));

                            entity.heal(item.getFoodProperties().getNutrition() / 2f);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader reader, BlockPos pos)
    {
//        LazyOptional<IItemHandler> lootContainer = this.getLootContainer(reader, pos);
//        if (lootContainer.isPresent())
//        {
//            IItemHandler handler = lootContainer.orElseThrow(() -> new NullPointerException("Inventory Capability was null when present!"));
//            for (int i = 0; i < handler.getSlots(); i++)
//            {
//                ItemStack stack = handler.getStackInSlot(i);
//                if (!HNCItemTags.MOUSE_BLACKLIST.contains(stack.getItem()) && !stack.isEmpty() && stack.isEdible())
//                    return true;
//            }
//        }
        BaseContainerBlockEntity tile = this.getContainerBlock(reader, pos);
        if (tile != null)
        {
            for (int i = 0; i < tile.getContainerSize(); i++)
            {
                ItemStack stack = tile.getItem(i);
                if (!stack.is(HNCItemTags.MOUSE_BLACKLIST) && !stack.isEmpty() && stack.isEdible())
                    return true;
            }
        }
        return false;
    }

    @Nullable
    private RandomizableContainerBlockEntity getContainerBlock(LevelReader reader, BlockPos pos)
    {
//        BlockState state = reader.getBlockState(pos);
//        if (state.is(HNCBlockTags.MOUSE_SEARCH) && !state.is(Tags.Blocks.CHESTS_ENDER))
//        {
//            if (state.hasTileEntity())
//            {
//                BlockEntity tile = reader.getBlockEntity(pos);
//                if (tile instanceof BaseContainerBlockEntity && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent())
//                    return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
//            }
//        }
//        return LazyOptional.empty();
        BlockState state = reader.getBlockState(pos);
        if (state.is(HNCBlockTags.MOUSE_SEARCHABLE) && !state.is(HNCBlockTags.ENDER_STORAGE))
        {
            if (state.hasBlockEntity())
            {
                BlockEntity tile = reader.getBlockEntity(pos);
                if (tile instanceof BaseContainerBlockEntity)
                    return (RandomizableContainerBlockEntity) tile;
            }
        }
        return null;
    }
}
