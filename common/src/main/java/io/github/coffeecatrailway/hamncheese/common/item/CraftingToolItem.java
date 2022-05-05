package io.github.coffeecatrailway.hamncheese.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author CoffeeCatRailway
 * Created: 9/02/2021
 */
public class CraftingToolItem extends TieredItem implements Vanishable, SelfRemainder
{
    @Nullable
    private final TagKey<Block> mineableBlocks;
    protected final float mineSpeed;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public CraftingToolItem(float attackModifier, double attackSpeed, Tier tier, @Nullable TagKey<Block> mineableBlocks, Properties properties)
    {
        super(tier, properties);
        this.mineableBlocks = mineableBlocks;
        this.mineSpeed = tier.getSpeed();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackModifier + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        return this.mineableBlocks != null && state.is(this.mineableBlocks) ? this.mineSpeed : 1f;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity entity1)
    {
        stack.hurtAndBreak(1, entity1, entity2 -> entity2.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity)
    {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0f)
            stack.hurtAndBreak(1, entity, entity1 -> entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state)
    {
        int tierLevel = this.getTier().getLevel();
        if (tierLevel < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL))
            return false;
        else if (tierLevel < 2 && state.is(BlockTags.NEEDS_IRON_TOOL))
            return false;
        return (tierLevel >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL)) && this.mineableBlocks != null && state.is(this.mineableBlocks);
    }
}
