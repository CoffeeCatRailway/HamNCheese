package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCLootTables extends ForgeLootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = ImmutableList.of(
            Pair.of(ChestProvider::new, LootParameterSets.CHEST),
            Pair.of(EntityProvider::new, LootParameterSets.ENTITY),
            Pair.of(BlockProvider::new, LootParameterSets.BLOCK));

    public HNCLootTables(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return this.tables;
    }

    private static class ChestProvider extends ChestLootTables
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
        }
    }

    private static class EntityProvider extends EntityLootTables
    {
        @Override
        protected void addTables()
        {
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities()
        {
            return ForgeRegistries.ENTITIES.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HNCMod.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }

    private static class BlockProvider extends BlockLootTables
    {
        @Override
        protected void addTables()
        {
            this.add(HNCBlocks.PINEAPPLE_PLANT.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE.get())
                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                                                    .hasProperty(PineapplePlantBlock.AGE, 4)))
                                    .otherwise(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()))))
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE_PLANT.get())
                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                            .hasProperty(PineapplePlantBlock.AGE, 4))))
                    .apply(ExplosionDecay.explosionDecay()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HNCMod.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }
}
