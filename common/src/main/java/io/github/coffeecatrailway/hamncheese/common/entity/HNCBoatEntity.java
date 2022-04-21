package io.github.coffeecatrailway.hamncheese.common.entity;

import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class HNCBoatEntity extends Boat
{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(HNCBoatEntity.class, EntityDataSerializers.INT);

    public HNCBoatEntity(EntityType<? extends Boat> type, Level level)
    {
        super(type, level);
    }

    public HNCBoatEntity(Level level, double x, double y, double z)
    {
        this(HNCEntities.MAPLE_BOAT.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(TYPE, ModType.MAPLE.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt)
    {
        if (nbt.contains("Type", NbtConstants.STRING))
            this.setModType(ModType.byName(nbt.getString("Type")));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt)
    {
        nbt.putString("Type", this.getModType().getName());
    }

    public void setModType(ModType type)
    {
        this.entityData.set(TYPE, type.ordinal());
    }

    public ModType getModType()
    {
        return ModType.byOrdinal(this.entityData.get(TYPE));
    }

    @Override
    public Item getDropItem()
    {
        switch (this.getModType())
        {
            default:
            case MAPLE:
                return HNCItems.MAPLE_BOAT.get();
        }
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos)
    {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger())
        {
            if (onGround)
            {
                if (this.fallDistance > 3.0F)
                {
                    if (this.status != Boat.Status.ON_LAND)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1f, DamageSource.FALL);
                    if (!this.level.isClientSide && !this.isRemoved())
                    {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
                        {
                            for (int i = 0; i < 3; ++i)
                                this.spawnAtLocation(this.getModType().getBlock());

                            for (int j = 0; j < 2; ++j)
                                this.spawnAtLocation(Items.STICK);
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D)
                this.fallDistance = (float) ((double) this.fallDistance - y);
        }
    }

    public enum ModType
    {
        MAPLE(HNCBlocks.MAPLE_PLANKS, "maple");

        private final Supplier<Block> block;
        private final String name;

        ModType(Supplier<Block> block, String name)
        {
            this.block = block;
            this.name = name;
        }

        public Block getBlock()
        {
            return this.block.get();
        }

        public String getName()
        {
            return this.name;
        }

        @Override
        public String toString()
        {
            return this.name;
        }

        /*
         * Setup in case of future types
         */
        public static ModType byOrdinal(int ordinal)
        {
            ModType[] types = values();
            if (ordinal < 0 || ordinal >= types.length)
                ordinal = 0;
            return types[ordinal];
        }

        public static ModType byName(String name)
        {
            ModType[] types = values();
            for (ModType type : types)
                if (type.name.equals(name))
                    return type;
            return types[0];
        }
    }
}
