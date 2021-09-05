package coffeecatrailway.hamncheese.common.entity.ai.goal;

import coffeecatrailway.hamncheese.common.block.CheeseBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author CoffeeCatRailway
 * Created: 5/09/2021
 */
public class FindCheeseGoal extends MouseInteractGoal
{
    private final Map<BlockPos, UUID> beingEaten = new HashMap<>();

    public FindCheeseGoal(CreatureEntity entity, double speedModifier, int searchRange, int verticalSearchRange)
    {
        super(entity, speedModifier, searchRange, verticalSearchRange);
    }

    @Override
    public void start()
    {
        super.start();
        this.beingEaten.clear();
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.isReachedTarget())
            this.beingEaten.put(this.blockPos, this.mob.getUUID());
    }

    @Override
    protected void interact(World level, CreatureEntity mob)
    {
        if (this.tryTicks % 10 != 0)
            return;
        BlockState state = level.getBlockState(this.blockPos);
        int bites = state.getValue(CheeseBlock.BITES) + 1;
        if (bites <= 3)
            level.setBlock(this.blockPos, state.setValue(CheeseBlock.BITES, bites), Constants.BlockFlags.DEFAULT);
        else
        {
            level.removeBlock(blockPos, false);
            this.beingEaten.remove(this.blockPos, this.mob.getUUID());
        }
    }

    @Override
    protected boolean isValidTarget(IWorldReader reader, BlockPos pos)
    {
        return reader.getBlockState(pos).is(HNCBlocks.BLOCK_OF_CHEESE.get()) && (!this.beingEaten.containsKey(pos) || (this.beingEaten.containsKey(pos) && this.beingEaten.get(pos).equals(this.mob.getUUID())));
    }
}
