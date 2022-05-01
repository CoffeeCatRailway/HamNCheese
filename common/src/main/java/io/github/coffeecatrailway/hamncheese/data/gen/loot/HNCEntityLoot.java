package io.github.coffeecatrailway.hamncheese.data.gen.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCEntities;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2022
 * <p>
 * Based on {@link net.minecraft.data.loot.EntityLoot}
 */
public class HNCEntityLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
    private static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());

    private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        this.add(HNCEntities.MOUSE.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(HNCItems.MOUSE.get())
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))));

        Set<ResourceLocation> set = Sets.newHashSet();
        Set<ResourceLocation> entityTypeKeys = Registry.ENTITY_TYPE.keySet().stream().filter(entityType -> HamNCheese.MOD_ID.equals(entityType.getNamespace())).collect(Collectors.toSet());
        for (ResourceLocation entityTypeKey : entityTypeKeys)
        {
            EntityType<?> entityType = Registry.ENTITY_TYPE.get(entityTypeKey);
            ResourceLocation resourcelocation = entityType.getDefaultLootTable();
            if (this.isNonLiving(entityType))
            {
                if (resourcelocation != BuiltInLootTables.EMPTY && this.map.remove(resourcelocation) != null)
                    throw new IllegalStateException(String.format("Weird loottable '%s' for '%s', not a LivingEntity so should not have loot", resourcelocation, entityTypeKey));
            } else if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation))
            {
                LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
                if (loottable$builder == null)
                    throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, entityTypeKey));

                consumer.accept(resourcelocation, loottable$builder);
            }
        }

        this.map.forEach(consumer);
    }

    protected boolean isNonLiving(EntityType<?> entityType)
    {
        return !SPECIAL_LOOT_TABLE_TYPES.contains(entityType) && entityType.getCategory() == MobCategory.MISC;
    }

    private void add(EntityType<?> entityType, LootTable.Builder builder)
    {
        this.add(entityType.getDefaultLootTable(), builder);
    }

    private void add(ResourceLocation resourceLocation, LootTable.Builder builder)
    {
        this.map.put(resourceLocation, builder);
    }
}
