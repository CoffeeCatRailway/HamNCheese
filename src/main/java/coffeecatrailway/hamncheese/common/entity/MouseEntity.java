package coffeecatrailway.hamncheese.common.entity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.entity.ai.goal.FindChestWithFoodGoal;
import coffeecatrailway.hamncheese.registry.HNCEntities;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
public class MouseEntity extends AnimalEntity
{
    private static final DataParameter<Integer> COAT_TYPE_ID = EntityDataManager.defineId(MouseEntity.class, DataSerializers.INT);
    public static final List<ResourceLocation> TEXTURE_BY_TYPE = Lists.newArrayList(
            HNCMod.getLocation("textures/entity/mouse/black.png"),
            HNCMod.getLocation("textures/entity/mouse/brown.png"),
            HNCMod.getLocation("textures/entity/mouse/patch_black_white.png"),
            HNCMod.getLocation("textures/entity/mouse/patch_brown_white.png"),
            HNCMod.getLocation("textures/entity/mouse/pump_white.png"),
            HNCMod.getLocation("textures/entity/mouse/tan_black.png"),
            HNCMod.getLocation("textures/entity/mouse/tricolor.png"),
            HNCMod.getLocation("textures/entity/mouse/white.png")
    );

    public MouseEntity(EntityType<? extends AnimalEntity> type, World world)
    {
        super(type, world);
    }

    public int getCoatType()
    {
        return this.entityData.get(COAT_TYPE_ID);
    }

    public void setCoatType(int type)
    {
        if (type < 0 || type >= TEXTURE_BY_TYPE.size())
            type = this.random.nextInt(TEXTURE_BY_TYPE.size());
        this.entityData.set(COAT_TYPE_ID, type);
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTexture()
    {
        return TEXTURE_BY_TYPE.get(this.getCoatType());
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(COAT_TYPE_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt)
    {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("CoatType", this.getCoatType());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt)
    {
        super.readAdditionalSaveData(nbt);
        this.setCoatType(nbt.getInt("CoatType"));
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1d, true));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, CatEntity.class, 12f, .8d, 1d));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, OcelotEntity.class, 12f, .8d, 1d));
        this.goalSelector.addGoal(2, new FindChestWithFoodGoal(this, 1.2f, 12, 2));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, .6d));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 2f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new BreedGoal(this, .8d));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    public static AttributeModifierMap.MutableAttribute registerAttributeMap()
    {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10f).add(Attributes.MOVEMENT_SPEED, .3f).add(Attributes.ATTACK_DAMAGE, 1f);
    }

    @Override
    protected PathNavigator createNavigation(World world)
    {
        GroundPathNavigator navigator = new GroundPathNavigator(this, world);
        navigator.getNodeEvaluator().setCanPassDoors(true);
        return navigator;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block)
    {
        this.playSound(SoundEvents.CHICKEN_STEP, .15f, 1f);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity)
    {
        MouseEntity mouse = HNCEntities.MOUSE.get().create(world);
        if (entity instanceof MouseEntity && mouse != null)
        {
            if (this.random.nextBoolean())
                mouse.setCoatType(this.getCoatType());
            else
                mouse.setCoatType(((MouseEntity) entity).getCoatType());
        }
        return mouse;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt)
    {
        entityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData, nbt);
        this.setCoatType(TEXTURE_BY_TYPE.size());
        return entityData;
    }

    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
