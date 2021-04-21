package coffeecatrailway.hamncheese.common.entity.ai.goal;

import coffeecatrailway.hamncheese.datagen.HNCBlockTags;
import coffeecatrailway.hamncheese.datagen.HNCItemTags;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
public class FindChestWithFoodGoal extends MoveToBlockGoal
{
    private int ticker;

    public FindChestWithFoodGoal(CreatureEntity entity, double speedModifier, int searchRange, int verticalSearchRange)
    {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    @Override
    public double acceptedDistance()
    {
        return 2d;
    }

    @Override
    public boolean shouldRecalculatePath()
    {
        return this.tryTicks % 20 == 0;
    }

    @Override
    public boolean canUse()
    {
        return !this.mob.isAggressive() && super.canUse();
    }

    @Override
    public void start()
    {
        this.ticker = 0;
        super.start();
    }

    @Override
    public void tick()
    {
        if (this.isReachedTarget())
        {
            if (this.ticker >= 40)
                this.eatFoodInChest(this.mob.level, this.mob);
            else
                this.ticker++;
        } else
        {
            if (this.mob.level.random.nextFloat() < .05f)
                this.mob.playSound(SoundEvents.FOX_SNIFF, 1f, 1f);
        }
        super.tick();
    }

    private void eatFoodInChest(World world, CreatureEntity entity)
    {
        if (ForgeEventFactory.getMobGriefingEvent(world, entity))
        {
            LazyOptional<IItemHandler> chestCap = this.getChest(world, this.blockPos);
            chestCap.ifPresent(handler -> {
                for (int i = 0; i < handler.getSlots(); i++)
                {
                    if (world.random.nextFloat() < .05f)
                    {
                        ItemStack stack = handler.getStackInSlot(i);
                        Item item = stack.getItem();
                        if (!HNCItemTags.MOUSE_BLACKLIST.contains(item))
                        {
                            if (!stack.isEmpty() && item.isEdible() && item.getFoodProperties() != null)
                            {
                                stack = entity.eat(world, stack);
                                stack.grow(1);
                                int count = stack.getCount();
                                handler.extractItem(i, count, false);
                                handler.insertItem(i, new ItemStack(HNCItems.FOOD_SCRAPS.get(), world.random.nextInt(count + 1)), false);

                                entity.heal(item.getFoodProperties().getNutrition() / 2f);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected boolean isValidTarget(IWorldReader world, BlockPos pos)
    {
        LazyOptional<IItemHandler> chestCap = this.getChest(world, pos);
        if (chestCap.isPresent())
        {
            IItemHandler handler = chestCap.orElseThrow(() -> new NullPointerException("Inventory Capability was null when present!"));
            for (int i = 0; i < handler.getSlots(); i++)
            {
                ItemStack stack = handler.getStackInSlot(i);
                if (!HNCItemTags.MOUSE_BLACKLIST.contains(stack.getItem()) && !stack.isEmpty() && stack.isEdible())
                    return true;
            }
        }
        return false;
    }

    private LazyOptional<IItemHandler> getChest(IWorldReader world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);
        if (state.is(HNCBlockTags.MOUSE_SEARCH) && !state.is(Tags.Blocks.CHESTS_ENDER))
        {
            if (state.hasTileEntity())
            {
                TileEntity tile = world.getBlockEntity(pos);
                if (tile instanceof LockableLootTileEntity && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent())
                    return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
            }
        }
        return LazyOptional.empty();
    }
}
