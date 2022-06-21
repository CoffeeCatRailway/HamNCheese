package io.github.coffeecatrailway.hamncheese.common.material;

import com.mojang.blaze3d.systems.RenderSystem;
import gg.moonflower.pollen.api.fluid.PollinatedFluid;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCFluidTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 22/04/2022
 */
public abstract class MilkFluid extends FlowingFluid implements PollinatedFluid
{
    private static final ResourceLocation STILL = new ResourceLocation(HamNCheese.MOD_ID, "block/milk_still");
    private static final ResourceLocation FLOW = new ResourceLocation(HamNCheese.MOD_ID, "block/milk_flow");
    private static final ResourceLocation OVERLAY = new ResourceLocation(HamNCheese.MOD_ID, "block/milk_overlay");

    @Override
    public ResourceLocation getStillTextureName()
    {
        return STILL;
    }

    @Override
    public ResourceLocation getFlowingTextureName()
    {
        return FLOW;
    }

    @Override
    public @Nullable ResourceLocation getOverlayTextureName()
    {
        return OVERLAY;
    }

    @Override
    public int getFogColor(Camera camera, ClientLevel level, Holder<Biome> biome, float partialTicks)
    {
        return 0xf9f9f9;
    }

    @Override
    public void applyFog(GameRenderer renderer, Camera camera, float distance, float partialTicks)
    {
        RenderSystem.setShaderFogEnd(1.25f);
        RenderSystem.setShaderFogStart(.2f);
    }

    @Override
    public Fluid getFlowing()
    {
        return HNCFluids.MILK_FLOWING.get();
    }

    @Override
    public Fluid getSource()
    {
        return HNCFluids.MILK.get();
    }

    @Override
    public Item getBucket()
    {
        return Items.MILK_BUCKET;
    }

    @Override
    public Optional<SoundEvent> getPickupSound()
    {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    @Override
    public Optional<SoundEvent> getEmptySound()
    {
        return Optional.of(SoundEvents.BUCKET_EMPTY);
    }

    @Override
    protected boolean canConvertToSource()
    {
        return true;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state)
    {
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }

    @Override
    protected int getSlopeFindDistance(LevelReader level)
    {
        return 4;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state)
    {
        return HNCFluids.MILK_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluid)
    {
        return fluid == this.getSource() || fluid == this.getFlowing();
    }

    @Override
    public int getDropOff(LevelReader level)
    {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader level)
    {
        return 5;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction)
    {
        return direction == Direction.DOWN && !fluid.is(HNCFluidTags.MILK);
    }

    @Override
    protected float getExplosionResistance()
    {
        return 100f;
    }

    @Nullable
    @Override
    public BlockState getInteractionState(Level level, FluidState fluidState, BlockPos pos, BlockPos neighborPos)
    {
        return null;
    }

    public static class Flowing extends MilkFluid
    {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder)
        {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state)
        {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state)
        {
            return false;
        }
    }

    public static class Source extends MilkFluid
    {
        @Override
        public int getAmount(FluidState state)
        {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state)
        {
            return true;
        }
    }
}
