package io.github.coffeecatrailway.hamncheese.data.gen.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.block.CheeseBlock;
import io.github.coffeecatrailway.hamncheese.common.block.CornPlantBlock;
import io.github.coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import io.github.coffeecatrailway.hamncheese.common.block.TomatoPlantBlock;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2022
 * <p>
 * Based on {@link net.minecraft.data.loot.BlockLoot}
 */
public class HNCBlockLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();

    private static <T> T applyExplosionDecay(ItemLike itemLike, FunctionUserBuilder<T> functionUserBuilder)
    {
        return !EXPLOSION_RESISTANT.contains(itemLike.asItem()) ? functionUserBuilder.apply(ApplyExplosionDecay.explosionDecay()) : functionUserBuilder.unwrap();
    }

    private static <T> T applyExplosionCondition(ItemLike itemLike, ConditionUserBuilder<T> conditionUserBuilder)
    {
        return !EXPLOSION_RESISTANT.contains(itemLike.asItem()) ? conditionUserBuilder.when(ExplosionCondition.survivesExplosion()) : conditionUserBuilder.unwrap();
    }

    private static LootTable.Builder createSingleItemTable(ItemLike itemLike)
    {
        return LootTable.lootTable().withPool(applyExplosionCondition(itemLike, LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(itemLike))));
    }

    private static LootTable.Builder createSelfDropDispatchTable(Block block, LootItemCondition.Builder builder, LootPoolEntryContainer.Builder<?> builder2)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(block).when(builder).otherwise(builder2)));
    }

    private static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder)
    {
        return createSelfDropDispatchTable(block, HAS_SHEARS_OR_SILK_TOUCH, builder);
    }

    private static LootTable.Builder createSilkTouchOnlyTable(ItemLike itemLike)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(itemLike)));
    }

    private static LootTable.Builder createPotFlowerItemTable(ItemLike itemLike)
    {
        return LootTable.lootTable().withPool(applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(applyExplosionCondition(itemLike, LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(itemLike))));
    }

    private static <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block block, Property<T> property, T comparable)
    {
        return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, comparable))))));
    }

    private static LootTable.Builder createLeavesDrops(Block block, Block block2, float... fs)
    {
        return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(block2)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, fs))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    public static LootTable.Builder noDrop()
    {
        return LootTable.lootTable();
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        this.add(HNCBlocks.PINEAPPLE_PLANT.get(), block -> LootTable.lootTable()
                .withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(HNCItems.PINEAPPLE.get())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                                                .hasProperty(PineapplePlantBlock.AGE, 4)))
                                .otherwise(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get())))))
                .withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                        .hasProperty(PineapplePlantBlock.AGE, 4))))));

        this.add(HNCBlocks.TOMATO_PLANT.get(), block -> LootTable.lootTable()
                .withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
                        .add(LootItem.lootTableItem(HNCItems.TOMATO.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(TomatoPlantBlock.AGE, 9).hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .otherwise(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)))))))
                .withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
                        .add(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(TomatoPlantBlock.AGE, 9).hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER))))));

        this.add(HNCBlocks.CORN_PLANT.get(), block -> LootTable.lootTable()
                .withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(HNCItems.CORN_COB.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 2))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(CornPlantBlock.AGE, 6).hasProperty(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .otherwise(LootItem.lootTableItem(HNCItems.CORN_COB.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornPlantBlock.HALF, DoubleBlockHalf.LOWER))))))));

        this.dropSelf(HNCBlocks.OAK_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.BIRCH_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.SPRUCE_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.JUNGLE_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.ACACIA_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.CRIMSON_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.WARPED_CHOPPING_BOARD.get());

        this.dropSelf(HNCBlocks.STONE_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.GOLD_CHOPPING_BOARD.get());
        this.dropSelf(HNCBlocks.IRON_CHOPPING_BOARD.get());

        this.dropSelf(HNCBlocks.MAPLE_CHOPPING_BOARD.get());

//        this.add(HNCBlocks.PIZZA_OVEN.get(), block -> LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .add(LootItem.lootTableItem(HNCBlocks.PIZZA_OVEN.get())
//                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))
//                        .when(ExplosionCondition.survivesExplosion())));

//        this.add(HNCBlocks.GRILL.get(), block -> LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .add(LootItem.lootTableItem(HNCBlocks.GRILL.get())
//                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))
//                        .when(ExplosionCondition.survivesExplosion())));

//        this.add(HNCBlocks.POPCORN_MACHINE.get(), block -> LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .add(LootItem.lootTableItem(HNCBlocks.POPCORN_MACHINE.get())
//                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))
//                        .when(ExplosionCondition.survivesExplosion())));

        this.blockOfCheese(HNCBlocks.BLOCK_OF_CHEESE.get());
        this.blockOfCheese(HNCBlocks.BLOCK_OF_BLUE_CHEESE.get());
        this.blockOfCheese(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get());
        this.blockOfCheese(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get());

        this.dropSelf(HNCBlocks.MAPLE_LOG.get());
        this.dropSelf(HNCBlocks.MAPLE_WOOD.get());
        this.dropSelf(HNCBlocks.STRIPPED_MAPLE_LOG.get());
        this.dropSelf(HNCBlocks.STRIPPED_MAPLE_WOOD.get());
        this.add(HNCBlocks.MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, HNCBlocks.MAPLE_SAPLING.get(), .05f, .0625f, .083333336f, .1f));
        this.dropSelf(HNCBlocks.MAPLE_SAPLING.get());
        this.dropPottedContents(HNCBlocks.POTTED_MAPLE_SAPLING.get());
        this.dropSelf(HNCBlocks.MAPLE_PLANKS.get());
        this.dropSelf(HNCBlocks.MAPLE_STAIRS.get());
        this.dropSelf(HNCBlocks.MAPLE_SLAB.get());
        this.dropSelf(HNCBlocks.MAPLE_SIGN.getFirst().get());
        this.add(HNCBlocks.MAPLE_SIGN.getSecond().get(), noDrop());
        this.dropSelf(HNCBlocks.MAPLE_PRESSURE_PLATE.get());
        this.dropSelf(HNCBlocks.MAPLE_BUTTON.get());
        this.dropSelf(HNCBlocks.MAPLE_FENCE.get());
        this.dropSelf(HNCBlocks.MAPLE_FENCE_GATE.get());
        this.dropSelf(HNCBlocks.MAPLE_TRAPDOOR.get());
        this.add(HNCBlocks.MAPLE_DOOR.get(), HNCBlockLoot::createDoorTable);

        this.dropSelf(HNCBlocks.TREE_TAP.get());

        Set<ResourceLocation> set = Sets.newHashSet();
        Set<ResourceLocation> blockKeys = Registry.BLOCK.keySet().stream().filter(entityType -> HamNCheese.MOD_ID.equals(entityType.getNamespace())).collect(Collectors.toSet());
        for (ResourceLocation blockKey : blockKeys)
        {
            Block block = Registry.BLOCK.get(blockKey);
            ResourceLocation resourcelocation = block.getLootTable();
            if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation))
            {
                LootTable.Builder builder = this.map.remove(resourcelocation);
                if (builder == null)
                    throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, blockKey));

                consumer.accept(resourcelocation, builder);
            }
        }

        if (!this.map.isEmpty())
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
    }

    private void blockOfCheese(CheeseBlock block)
    {
        this.add(block, LootTable.lootTable()
                .withPool(applyExplosionCondition(block,
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                                .add(LootItem.lootTableItem(block))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(CheeseBlock.BITES, 0))))));
    }

    public static LootTable.Builder createDoorTable(Block block)
    {
        return createSinglePropConditionTable(block, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    public void dropPottedContents(Block block)
    {
        this.add(block, blockx -> createPotFlowerItemTable(((FlowerPotBlock) blockx).getContent()));
    }

    public void otherWhenSilkTouch(Block block, Block block2)
    {
        this.add(block, createSilkTouchOnlyTable(block2));
    }

    public void dropOther(Block block, ItemLike itemLike)
    {
        this.add(block, createSingleItemTable(itemLike));
    }

    public void dropWhenSilkTouch(Block block)
    {
        this.otherWhenSilkTouch(block, block);
    }

    public void dropSelf(Block block)
    {
        this.dropOther(block, block);
    }

    private void add(Block block, Function<Block, LootTable.Builder> function)
    {
        this.add(block, function.apply(block));
    }

    private void add(Block block, LootTable.Builder builder)
    {
        this.map.put(block.getLootTable(), builder);
    }
}
