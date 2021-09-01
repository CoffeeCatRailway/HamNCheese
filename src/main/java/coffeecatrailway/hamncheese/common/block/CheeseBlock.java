package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCFoods;
import io.github.ocelot.sonar.common.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

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
        this.registerDefaultState(this.getStateDefinition().any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos blockPos, ISelectionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult result)
    {
        if (level.isClientSide)
        {
            ItemStack itemstack = player.getItemInHand(hand);
            if (this.eatCheese(level, blockPos, state, player).consumesAction())
                return ActionResultType.SUCCESS;

            if (itemstack.isEmpty())
                return ActionResultType.CONSUME;
        }

        return this.eatCheese(level, blockPos, state, player);
    }

    private ActionResultType eatCheese(World level, BlockPos blockPos, BlockState state, PlayerEntity player)
    {
        if (!player.canEat(false))
            return ActionResultType.PASS;
        else
        {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            Food food = HNCFoods.CHEESE_SLICE;
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            int i = state.getValue(BITES);
            if (i < 3)
                level.setBlock(blockPos, state.setValue(BITES, Integer.valueOf(i + 1)), 3);
            else
                level.removeBlock(blockPos, false);

            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld level, BlockPos pos, BlockPos pos1)
    {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, level, pos, pos1);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> container)
    {
        container.add(BITES);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World level, BlockPos blockPos)
    {
        return (4 - state.getValue(BITES)) * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader level, BlockPos blockPos, PathType pathType)
    {
        return false;
    }
}
