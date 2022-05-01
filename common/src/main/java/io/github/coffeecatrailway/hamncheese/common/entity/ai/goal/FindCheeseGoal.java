package io.github.coffeecatrailway.hamncheese.common.entity.ai.goal;

import io.github.coffeecatrailway.hamncheese.common.block.CheeseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

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

    public FindCheeseGoal(PathfinderMob entity, double speedModifier, int searchRange, int verticalSearchRange)
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
    protected void interact(Level level, PathfinderMob mob)
    {
        if (this.tryTicks % 10 != 0)
            return;
        BlockState state = level.getBlockState(this.blockPos);
        int bites = state.getValue(CheeseBlock.BITES) + 1;
        if (bites <= 3)
            level.setBlock(this.blockPos, state.setValue(CheeseBlock.BITES, bites), 2);
        else
        {
            level.removeBlock(blockPos, false);
            this.beingEaten.remove(this.blockPos, this.mob.getUUID());
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader reader, BlockPos pos)
    {
        return reader.getBlockState(pos).getBlock() instanceof CheeseBlock && (!this.beingEaten.containsKey(pos) || (this.beingEaten.containsKey(pos) && this.beingEaten.get(pos).equals(this.mob.getUUID())));
    }
}
