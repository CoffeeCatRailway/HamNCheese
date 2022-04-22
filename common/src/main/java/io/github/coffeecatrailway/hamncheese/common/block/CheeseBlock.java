package io.github.coffeecatrailway.hamncheese.common.block;

import gg.moonflower.pollen.api.util.VoxelShapeHelper;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCFoods;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 1/09/2021
 */
public class CheeseBlock extends Block
{
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(1d, 0.d, 1d, 15d, 8d, 15d),
            new VoxelShapeHelper.Builder().append(Block.box(8d, 0.d, 1d, 15d, 8d, 15d),
                    Block.box(1d, 0.d, 8d, 8d, 8d, 15d)).build(),
            Block.box(8d, 0.d, 1d, 15d, 8d, 15d),
            Block.box(8d, 0.d, 1d, 15d, 8d, 8d)
    };

    public CheeseBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getBlock() == HNCBlocks.BLOCK_OF_CHEESE.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!this.isRandomlyTicking(state) && random.nextFloat() < HamNCheese.CONFIG_SERVER.blockOfCheeseChance.get().floatValue())
            return;
        level.setBlock(pos, HNCBlocks.BLOCK_OF_BLUE_CHEESE.get().defaultBlockState().setValue(BITES, state.getValue(BITES)), 3);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        ItemStack handStack = player.getItemInHand(hand);
        if (level.isClientSide)
        {
            if (this.eatCheese(level, pos, state, player).consumesAction())
                return InteractionResult.SUCCESS;

            if (handStack.isEmpty())
                return InteractionResult.CONSUME;
        } else if (handStack.is(HNCItemTags.SHEARS_COMMON) && state.getBlock() == HNCBlocks.BLOCK_OF_CHEESE.get())
        {
            level.setBlock(pos, HNCBlocks.BLOCK_OF_SWISS_CHEESE.get().defaultBlockState().setValue(BITES, state.getValue(BITES)), 3);
            Random random = level.getRandom();
            ItemEntity itemEntity = this.spawnAtLocation(new ItemStack(HNCItems.SWISS_CHEESE_BITS.get(), random.nextInt(2) + 1), level, pos);
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * .1f, random.nextFloat() * .05f, (random.nextFloat() - random.nextFloat()) * .1f));
            return InteractionResult.SUCCESS;
        }

        return this.eatCheese(level, pos, state, player);
    }

    /**
     * Based on {@link net.minecraft.world.entity.Entity#spawnAtLocation(net.minecraft.world.item.ItemStack, float)}
     */
    private ItemEntity spawnAtLocation(ItemStack itemStack, Level level, BlockPos pos)
    {
        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + .5f, pos.getZ(), itemStack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
        return itemEntity;
    }

    private InteractionResult eatCheese(Level level, BlockPos blockPos, BlockState state, Player player)
    {
        if (!player.canEat(false))
            return InteractionResult.PASS;
        else
        {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            FoodProperties food = HNCFoods.CHEESE_SLICE;
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            int i = state.getValue(BITES);
            if (i < 3)
                level.setBlock(blockPos, state.setValue(BITES, i + 1), 3);
            else
                level.removeBlock(blockPos, false);

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1)
    {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, level, pos, pos1);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BITES);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return (4 - state.getValue(BITES)) * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos pos, PathComputationType pathComputationType)
    {
        return false;
    }
}
