package io.github.coffeecatrailway.hamncheese.data.gen.loot;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2022
 * <p>
 * Based on {@link ChestLoot}
 */
public class HNCChestLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
    public static final ResourceLocation PLAINS_CHEF_RESTAURANT = HamNCheese.getLocation("chests/village/village_chef_restaurant");

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
    {
        registry.accept(PLAINS_CHEF_RESTAURANT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 8))
                                .add(LootItem.lootTableItem(HNCItems.STONE_KNIFE.get()).setWeight(3))
                                .add(LootItem.lootTableItem(HNCItems.COPPER_KNIFE.get()).setWeight(8))
                                .add(LootItem.lootTableItem(HNCItems.IRON_KNIFE.get()).setWeight(2))

                                .add(LootItem.lootTableItem(HNCItems.CURDLER.get()).setWeight(2))
                                .add(LootItem.lootTableItem(HNCItems.GRIND_STONES.get()).setWeight(2))

                                .add(LootItem.lootTableItem(HNCItems.PINEAPPLE.get()).setWeight(5)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                                .add(LootItem.lootTableItem(HNCItems.TOMATO.get()).setWeight(5)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))

//                        .add(LootItem.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
//                        .add(LootItem.lootTableItem(HNCItems.CORN_COB.get()).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
//                        .add(LootItem.lootTableItem(HNCItems.TOMATO_SEEDS.get()).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))

                                .add(LootItem.lootTableItem(Items.BREAD).setWeight(8)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                                .add(LootItem.lootTableItem(HNCItems.BREAD_SLICE.get()).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))

//                        .add(LootItem.lootTableItem(HNCBlocks.BLOCK_OF_CHEESE.get()).setWeight(8)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
                                .add(LootItem.lootTableItem(HNCItems.CHEESE_SLICE.get()).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))

                                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))

                                .add(LootItem.lootTableItem(HNCItems.ROCK_SALT.get()).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
                                .add(LootItem.lootTableItem(HNCItems.FLOUR.get()).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))

                                .add(LootItem.lootTableItem(HNCItems.FOOD_SCRAPS.get()).setWeight(8)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))))
                                .add(LootItem.lootTableItem(HNCItems.CRACKED_EGG.get()).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                                .add(LootItem.lootTableItem(Items.EGG).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 10))))
                ));
    }
}
