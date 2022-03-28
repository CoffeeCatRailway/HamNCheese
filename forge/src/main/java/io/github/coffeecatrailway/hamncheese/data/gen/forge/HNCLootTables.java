//package io.github.coffeecatrailway.hamncheese.data.gen.forge;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.datafixers.util.Pair;
//import io.github.coffeecatrailway.hamncheese.HamNCheese;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.loot.BlockLoot;
//import net.minecraft.data.loot.ChestLoot;
//import net.minecraft.data.loot.EntityLoot;
//import net.minecraft.data.loot.LootTableProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
//import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import java.util.List;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
///**
// * @author CoffeeCatRailway
// * Created: 17/03/2021
// */
//public class HNCLootTables extends LootTableProvider
//{
//    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(
//            Pair.of(ChestProvider::new, LootContextParamSets.CHEST),
//            Pair.of(EntityProvider::new, LootContextParamSets.ENTITY),
//            Pair.of(BlockProvider::new, LootContextParamSets.BLOCK));
//
//    public static final ResourceLocation PLAINS_CHEF_RESTAURANT = HamNCheese.getLocation("chests/village/village_chef_restaurant");
//
//    public HNCLootTables(DataGenerator generator)
//    {
//        super(generator);
//    }
//
//    @Override
//    public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
//    {
//        return this.tables;
//    }
//
//    private static class ChestProvider extends ChestLoot
//    {
//        @Override
//        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
//        {
//            registry.accept(PLAINS_CHEF_RESTAURANT, LootTable.lootTable()
//                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 8))
////                            .add(LootItem.lootTableItem(HNCItems.KNIFE.get()).setWeight(2))
////                            .add(LootItem.lootTableItem(HNCItems.CURDLER.get()).setWeight(2))
////                            .add(LootItem.lootTableItem(HNCItems.GRIND_STONES.get()).setWeight(2))
////                            .add(LootItem.lootTableItem(HNCItems.PINEAPPLE.get()).setWeight(5)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
////                            .add(LootItem.lootTableItem(HNCItems.TOMATO.get()).setWeight(5)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
////                            .add(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.CORN_COB.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
//                                    .add(LootItem.lootTableItem(Items.BREAD).setWeight(8)
//                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
////                            .add(LootItem.lootTableItem(HNCItems.BREAD_SLICE.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
////                            .add(LootItem.lootTableItem(HNCBlocks.BLOCK_OF_CHEESE.get()).setWeight(8)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.CHEESE_SLICE.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
//                                    .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2)
//                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
////                            .add(LootItem.lootTableItem(HNCItems.ROCK_SALT.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.FLOUR.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.FOOD_SCRAPS.get()).setWeight(8)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
////                            .add(LootItem.lootTableItem(HNCItems.CRACKED_EGG.get()).setWeight(10)
////                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
//                                    .add(LootItem.lootTableItem(Items.EGG).setWeight(10)
//                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 10))))
//                    ));
//        }
//    }
//
//    private static class EntityProvider extends EntityLoot
//    {
//        @Override
//        protected void addTables()
//        {
////            this.add(HNCEntities.MOUSE.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCItems.MOUSE.get())
////                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))));
//        }
//
//        @Override
//        protected Iterable<EntityType<?>> getKnownEntities()
//        {
//            return ForgeRegistries.ENTITIES.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HamNCheese.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
//        }
//    }
//
//    private static class BlockProvider extends BlockLoot
//    {
//        @Override
//        protected void addTables()
//        {
////            this.add(HNCBlocks.PINEAPPLE_PLANT.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCItems.PINEAPPLE.get())
////                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
////                                            .setProperties(StatePropertiesPredicate.Builder.properties()
////                                                    .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
////                                                    .hasProperty(PineapplePlantBlock.AGE, 4)))
////                                    .otherwise(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()))))
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get())
////                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
////                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
////                                    .setProperties(StatePropertiesPredicate.Builder.properties()
////                                            .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
////                                            .hasProperty(PineapplePlantBlock.AGE, 4))))
////                    .apply(ExplosionDecay.explosionDecay()));
////
////            this.add(HNCBlocks.TOMATO_PLANT.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
////                            .add(LootItem.lootTableItem(HNCItems.TOMATO.get())
////                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3))
////                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.TOMATO_PLANT.get())
////                                            .setProperties(StatePropertiesPredicate.Builder.properties()
////                                                    .hasProperty(TomatoPlantBlock.AGE, 9).hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)))
////                                    .otherwise(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get())
////                                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.TOMATO_PLANT.get())
////                                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER))))))
////                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 3))
////                            .add(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get())
////                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
////                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.TOMATO_PLANT.get())
////                                    .setProperties(StatePropertiesPredicate.Builder.properties()
////                                            .hasProperty(TomatoPlantBlock.AGE, 9).hasProperty(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER))))
////                    .apply(ExplosionDecay.explosionDecay()));
////
////            this.add(HNCBlocks.CORN_PLANT.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCItems.CORN_COB.get())
////                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 2))
////                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.CORN_PLANT.get())
////                                            .setProperties(StatePropertiesPredicate.Builder.properties()
////                                                    .hasProperty(CornPlantBlock.AGE, 6).hasProperty(CornPlantBlock.HALF, DoubleBlockHalf.LOWER)))
////                                    .otherwise(LootItem.lootTableItem(HNCItems.CORN_COB.get())
////                                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.CORN_PLANT.get())
////                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornPlantBlock.HALF, DoubleBlockHalf.LOWER))))))
////                    .apply(ExplosionDecay.explosionDecay()));
////
////            this.dropSelf(HNCBlocks.OAK_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.BIRCH_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.SPRUCE_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.JUNGLE_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.ACACIA_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.CRIMSON_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.WARPED_CHOPPING_BOARD.get());
////
////            this.dropSelf(HNCBlocks.STONE_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.GOLD_CHOPPING_BOARD.get());
////            this.dropSelf(HNCBlocks.IRON_CHOPPING_BOARD.get());
////
////            this.dropSelf(HNCBlocks.MAPLE_CHOPPING_BOARD.get());
////
////            this.add(HNCBlocks.PIZZA_OVEN.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCBlocks.PIZZA_OVEN.get())
////                                    .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))
////                            .when(SurvivesExplosion.survivesExplosion())));
////
////            this.add(HNCBlocks.GRILL.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCBlocks.GRILL.get())
////                                    .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))
////                            .when(SurvivesExplosion.survivesExplosion())));
////
////            this.add(HNCBlocks.POPCORN_MACHINE.get(), LootTable.lootTable()
////                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
////                            .add(LootItem.lootTableItem(HNCBlocks.POPCORN_MACHINE.get())
////                                    .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))
////                            .when(SurvivesExplosion.survivesExplosion())));
////
////            this.add(HNCBlocks.BLOCK_OF_CHEESE.get(), noDrop());
////
////            this.dropSelf(HNCBlocks.MAPLE_LOG.get());
////            this.dropSelf(HNCBlocks.MAPLE_WOOD.get());
////            this.dropSelf(HNCBlocks.STRIPPED_MAPLE_LOG.get());
////            this.dropSelf(HNCBlocks.STRIPPED_MAPLE_WOOD.get());
////            this.add(HNCBlocks.MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, HNCBlocks.MAPLE_SAPLING.get(), .05f, .0625f, .083333336f, .1f));
////            this.dropSelf(HNCBlocks.MAPLE_SAPLING.get());
////            this.dropPottedContents(HNCBlocks.POTTED_MAPLE_SAPLING.get());
////            this.dropSelf(HNCBlocks.MAPLE_PLANKS.get());
////            this.dropSelf(HNCBlocks.MAPLE_STAIRS.get());
////            this.dropSelf(HNCBlocks.MAPLE_SLAB.get());
////            this.dropSelf(HNCBlocks.MAPLE_SIGN.get());
////            this.add(HNCBlocks.MAPLE_WALL_SIGN.get(), noDrop());
////            this.dropSelf(HNCBlocks.MAPLE_PRESSURE_PLATE.get());
////            this.dropSelf(HNCBlocks.MAPLE_BUTTON.get());
////            this.dropSelf(HNCBlocks.MAPLE_FENCE.get());
////            this.dropSelf(HNCBlocks.MAPLE_FENCE_GATE.get());
////            this.dropSelf(HNCBlocks.MAPLE_TRAPDOOR.get());
////            this.add(HNCBlocks.MAPLE_DOOR.get(), BlockLootTables::createDoorTable);
////
////            this.dropSelf(HNCBlocks.TREE_TAP.get());
////            this.add(HNCBlocks.MAPLE_SAP.get(), noDrop());
//        }
//
//        @Override
//        protected Iterable<Block> getKnownBlocks()
//        {
//            return ForgeRegistries.BLOCKS.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HamNCheese.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
//        }
//    }
//}
