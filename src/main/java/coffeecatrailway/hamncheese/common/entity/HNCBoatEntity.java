package coffeecatrailway.hamncheese.common.entity;

import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCEntities;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
public class HNCBoatEntity extends BoatEntity
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(HNCBoatEntity.class, DataSerializers.INT);

    public HNCBoatEntity(EntityType<? extends BoatEntity> type, World level)
    {
        super(type, level);
    }

    public HNCBoatEntity(World level, double x, double y, double z)
    {
        this(HNCEntities.MAPLE_BOAT.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vector3d.ZERO);
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
    protected void addAdditionalSaveData(CompoundNBT nbt)
    {
        if (nbt.contains("Type", Constants.NBT.TAG_STRING))
            this.setModType(ModType.byName(nbt.getString("Type")));
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt)
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
                    if (this.status != BoatEntity.Status.ON_LAND)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F);
                    if (!this.level.isClientSide && !this.removed)
                    {
                        this.remove();
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
        MAPLE(HNCBlocks.MAPLE_PLANKS.get(), "maple");

        private final Block block;
        private final String name;

        ModType(Block block, String name)
        {
            this.block = block;
            this.name = name;
        }

        public Block getBlock()
        {
            return this.block;
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

    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
