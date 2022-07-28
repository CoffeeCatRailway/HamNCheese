package io.github.coffeecatrailway.hamncheese.common.entity;

import com.google.common.collect.Lists;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.advancements.critereon.PestControlTrigger;
import io.github.coffeecatrailway.hamncheese.common.entity.ai.goal.FindCheeseGoal;
import io.github.coffeecatrailway.hamncheese.common.entity.ai.goal.FindChestWithFoodGoal;
import io.github.coffeecatrailway.hamncheese.common.entity.ai.goal.MouseAvoidCatGoal;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCCriterionTriggers;
import io.github.coffeecatrailway.hamncheese.registry.HNCEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
public class MouseEntity extends Animal
{
    private static final Ingredient TEMPT = Ingredient.of(HNCItemTags.CHEESE_COMMON);
    private static final EntityDataAccessor<Integer> COAT_TYPE_ID = SynchedEntityData.defineId(MouseEntity.class, EntityDataSerializers.INT);
    public static final List<ResourceLocation> TEXTURE_BY_TYPE = Lists.newArrayList(
            HamNCheese.getLocation("textures/entity/mouse/black.png"),
            HamNCheese.getLocation("textures/entity/mouse/brown.png"),
            HamNCheese.getLocation("textures/entity/mouse/patch_black_white.png"),
            HamNCheese.getLocation("textures/entity/mouse/patch_brown_white.png"),
            HamNCheese.getLocation("textures/entity/mouse/pump_white.png"),
            HamNCheese.getLocation("textures/entity/mouse/tan_black.png"),
            HamNCheese.getLocation("textures/entity/mouse/tricolor.png"),
            HamNCheese.getLocation("textures/entity/mouse/white.png")
    );

    public MouseEntity(EntityType<? extends Animal> type, Level world)
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

    @Environment(EnvType.CLIENT)
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
    public void addAdditionalSaveData(CompoundTag nbt)
    {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("CoatType", this.getCoatType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt)
    {
        super.readAdditionalSaveData(nbt);
        this.setCoatType(nbt.getInt("CoatType"));
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1d, true));
        this.goalSelector.addGoal(1, new MouseAvoidCatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Ocelot.class, 12f, .8d, 1d));
        this.goalSelector.addGoal(2, new BreedGoal(this, .8d));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25, TEMPT, false));
        this.goalSelector.addGoal(2, new FindChestWithFoodGoal(this, 1.2f, 12, 2));
        this.goalSelector.addGoal(2, new FindCheeseGoal(this, 1.2f, 15, 3));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, .6d));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 2f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Cat.class, Ocelot.class).setAlertOthers());
    }

    @Override
    public boolean isFood(ItemStack stack)
    {
        return TEMPT.test(stack);
    }

    public static AttributeSupplier.Builder registerAttributeMap()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10f).add(Attributes.MOVEMENT_SPEED, .3f).add(Attributes.ATTACK_DAMAGE, 1f);
    }

    @Override
    protected PathNavigation createNavigation(Level world)
    {
        GroundPathNavigation navigator = new GroundPathNavigation(this, world);
        navigator.getNodeEvaluator().setCanPassDoors(true);
        return navigator;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block)
    {
        this.playSound(SoundEvents.CHICKEN_STEP, .15f, 1f);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal animal)
    {
        super.spawnChildFromBreeding(level, animal);
        ServerPlayer player = this.getLoveCause();
        if (player == null && animal.getLoveCause() != null)
            player = animal.getLoveCause();

        if (player != null)
            HNCCriterionTriggers.PEST_CONTROL_TRIGGER.trigger(player, PestControlTrigger.Type.ANTI);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity)
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag)
    {
        this.setCoatType(this.random.nextInt(TEXTURE_BY_TYPE.size()));
        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }

    @Override
    public void die(DamageSource source)
    {
        super.die(source);
        if (!this.dead)
            return;
        if (source.getEntity() instanceof ServerPlayer player)
            HNCCriterionTriggers.PEST_CONTROL_TRIGGER.trigger(player, PestControlTrigger.Type.NORMAL);
        if (source.getEntity() instanceof Cat cat && cat.isTame() && cat.getOwner() instanceof ServerPlayer player)
            HNCCriterionTriggers.GOOD_KITTY_TRIGGER.trigger(player);
    }
}
