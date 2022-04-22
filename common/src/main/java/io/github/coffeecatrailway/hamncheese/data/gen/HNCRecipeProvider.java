package io.github.coffeecatrailway.hamncheese.data.gen;

import gg.moonflower.pollen.api.datagen.provider.PollinatedRecipeProvider;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCBlocks;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

/**
 * @author CoffeeCatRailway
 * Created: 24/03/2022
 */
public class HNCRecipeProvider extends PollinatedRecipeProvider
{
    public HNCRecipeProvider(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        /*
         * Crafting Ingredients
         */
        ShapedRecipeBuilder.shaped(HNCItems.WOODEN_GEAR.get(), 4).define('s', HNCItemTags.WOOD_STICKS).define('p', net.minecraft.tags.ItemTags.PLANKS)
                .pattern(" s ").pattern("sps").pattern(" s ").unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS))
                .unlockedBy("has_planks", has(ItemTags.PLANKS)).save(consumer);

        /*
         * Tools
         */
        ShapedRecipeBuilder.shaped(HNCItems.CURDLER.get()).define('s', HNCItemTags.WOOD_STICKS).define('g', HNCItemTags.GEARS_WOODEN_COMMON)
                .pattern("s").pattern("s").pattern("g").unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS))
                .unlockedBy("has_gears", has(HNCItemTags.GEARS_WOODEN_COMMON)).save(consumer, HamNCheese.getLocation("curdler_alternate"));
        ShapedRecipeBuilder.shaped(HNCItems.CURDLER.get()).define('s', HNCItemTags.WOOD_STICKS).define('g', HNCItemTags.GEARS_WOODEN_COMMON)
                .pattern("g").pattern("s").pattern("s").unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS))
                .unlockedBy("has_gears", has(HNCItemTags.GEARS_WOODEN_COMMON)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.ROLLING_PIN.get()).define('s', HNCItemTags.WOOD_STICKS).define('p', net.minecraft.tags.ItemTags.PLANKS).pattern("sps")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_planks", has(net.minecraft.tags.ItemTags.PLANKS)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.GRIND_STONES.get(), 2)
                .requires(HNCItemTags.STONE_COMMON).unlockedBy("has_cobble", has(HNCItemTags.STONE_COMMON)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.WOODEN_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', ItemTags.PLANKS).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_planks", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.STONE_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', HNCItemTags.STONE_COMMON).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_stone", has(HNCItemTags.STONE_COMMON)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.COPPER_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', HNCItemTags.COPPER_INGOTS_COMMON).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_copper", has(HNCItemTags.COPPER_INGOTS_COMMON)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.GOLDEN_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', HNCItemTags.GOLD_INGOTS_COMMON).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_gold", has(HNCItemTags.GOLD_INGOTS_COMMON)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.IRON_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', HNCItemTags.IRON_INGOTS_COMMON).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_iron", has(HNCItemTags.IRON_INGOTS_COMMON)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.DIAMOND_KNIFE.get()).group("knife").define('s', HNCItemTags.WOOD_STICKS).define('i', HNCItemTags.DIAMONDS_COMMON).pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(HNCItemTags.WOOD_STICKS)).unlockedBy("has_diamond", has(HNCItemTags.DIAMONDS_COMMON)).save(consumer);
        netheriteSmithing(consumer, HNCItems.DIAMOND_KNIFE.get(), HNCItems.NETHERITE_KNIFE.get());

        /*
         * Foods
         */
        ShapelessRecipeBuilder.shapeless(HNCBlocks.BLOCK_OF_CHEESE.get(), 2).group("block_of_cheese").requires(Items.MILK_BUCKET).requires(HNCItems.CURDLER.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .unlockedBy("has_curdler", has(HNCItems.CURDLER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.CHEESE_SLICE.get(), 4).group("cheese_slice").requires(HNCBlocks.BLOCK_OF_CHEESE.get()).requires(HNCItemTags.KNIVES_COMMON).unlockedBy("has_cheese", has(HNCBlocks.BLOCK_OF_CHEESE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.BLUE_CHEESE_SLICE.get(), 4).group("cheese_slice").requires(HNCBlocks.BLOCK_OF_BLUE_CHEESE.get()).requires(HNCItemTags.KNIVES_COMMON).unlockedBy("has_cheese", has(HNCBlocks.BLOCK_OF_BLUE_CHEESE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get()).group("block_of_cheese").define('r', HNCItemTags.RED_DYES_COMMON).define('c', HNCBlocks.BLOCK_OF_CHEESE.get()).pattern(" r ").pattern("rcr").pattern(" r ")
                .unlockedBy("has_dye", has(HNCItemTags.RED_DYES_COMMON)).unlockedBy("has_cheese", has(HNCBlocks.BLOCK_OF_CHEESE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.GOUDA_CHEESE_SLICE.get(), 4).group("cheese_slice").requires(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get()).requires(HNCItemTags.KNIVES_COMMON).unlockedBy("has_cheese", has(HNCBlocks.BLOCK_OF_GOUDA_CHEESE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.SWISS_CHEESE_SLICE.get(), 4).group("cheese_slice").requires(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get()).requires(HNCItemTags.KNIVES_COMMON).unlockedBy("has_cheese", has(HNCBlocks.BLOCK_OF_SWISS_CHEESE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.ROCK_SALT.get(), 2).requires(HNCItems.GRIND_STONES.get()).requires(HNCItemTags.STONE_COMMON).unlockedBy("has_stone", has(HNCItemTags.STONE_COMMON))
                .unlockedBy("has_grind_stones", has(HNCItems.GRIND_STONES.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.FLOUR.get(), 3).requires(HNCItems.GRIND_STONES.get()).requires(HNCItemTags.GRAIN_COMMON).unlockedBy("has_wheat", has(HNCItemTags.GRAIN_COMMON))
                .unlockedBy("has_grind_stones", has(HNCItems.GRIND_STONES.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.DOUGH.get(), 3).requires(HNCItemTags.GRAIN_COMMON).requires(HNCItemTags.SUGAR_COMMON).requires(HNCItemTags.SALT_COMMON).requires(HNCItemTags.FLOUR_COMMON).unlockedBy("has_wheat", has(HNCItemTags.GRAIN_COMMON))
                .unlockedBy("has_sugar", has(HNCItemTags.SUGAR_COMMON)).unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).unlockedBy("has_flour", has(HNCItemTags.FLOUR_COMMON)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.UNBAKED_PIZZA_BASE.get()).requires(HNCItems.ROLLING_PIN.get()).requires(HNCItems.DOUGH.get()).unlockedBy("has_dough", has(HNCItems.DOUGH.get()))
                .unlockedBy("has_rolling_pin", has(HNCItems.ROLLING_PIN.get())).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.UNBAKED_BREAD.get(), 2).define('d', HNCItemTags.DOUGH_COMMON).pattern("ddd").unlockedBy("has_dough", has(HNCItemTags.DOUGH_COMMON)).save(consumer);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_BREAD.get()), Items.BREAD, .35f, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_unbaked_bread", has(HNCItems.UNBAKED_BREAD.get())).save(consumer, HamNCheese.getLocation("bread_smoking"));
        ShapelessRecipeBuilder.shapeless(HNCItems.BREAD_SLICE.get(), 3).requires(HNCItemTags.KNIVES_COMMON).requires(Items.BREAD).unlockedBy("has_bread", has(Items.BREAD)).unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BREAD_SLICE.get()), HNCItems.TOAST.get(), .35f, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_bread_slice", has(HNCItems.BREAD_SLICE.get())).save(consumer, HamNCheese.getLocation("toast_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BREAD_SLICE.get()), HNCItems.TOAST.get(), .35f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_bread_slice", has(HNCItems.BREAD_SLICE.get())).save(consumer, HamNCheese.getLocation("toast_campfire"));

        ShapelessRecipeBuilder.shapeless(HNCItems.UNBAKED_CRACKER.get(), 2).requires(HNCItemTags.GRAIN_COMMON).requires(HNCItemTags.SALT_COMMON).requires(HNCItemTags.FLOUR_COMMON).unlockedBy("has_wheat", has(HNCItemTags.GRAIN_COMMON))
                .unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).unlockedBy("has_flour", has(HNCItemTags.FLOUR_COMMON)).save(consumer);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_CRACKER.get()), HNCItems.CRACKER.get(), .35f, 50, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_unbaked_cracker", has(HNCItems.UNBAKED_CRACKER.get())).save(consumer, HamNCheese.getLocation("cracker_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_CRACKER.get()), HNCItems.CRACKER.get(), .35f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_unbaked_cracker", has(HNCItems.UNBAKED_CRACKER.get())).save(consumer, HamNCheese.getLocation("cracker_campfire"));

        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.CRACKED_EGG.get()), HNCItems.COOKED_EGG.get(), .15f, 50, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_cracked_egg", has(HNCItems.CRACKED_EGG.get())).save(consumer, HamNCheese.getLocation("cooked_egg_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.CRACKED_EGG.get()), HNCItems.COOKED_EGG.get(), .15f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_cracked_egg", has(HNCItems.CRACKED_EGG.get())).save(consumer, HamNCheese.getLocation("cooked_egg_campfire"));
        ShapelessRecipeBuilder.shapeless(HNCItems.GREEN_EGG.get()).requires(HNCItemTags.GREEN_DYES_COMMON).requires(HNCItems.CRACKED_EGG.get()).unlockedBy("has_egg", has(HNCItems.CRACKED_EGG.get()))
                .unlockedBy("has_dye", has(HNCItemTags.GREEN_DYES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.HAM_SLICE.get(), 3).requires(HNCItemTags.KNIVES_COMMON).requires(Items.PORKCHOP).unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON))
                .unlockedBy("has_porkchop", has(Items.PORKCHOP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.COOKED_HAM_SLICE.get(), 3).requires(HNCItemTags.KNIVES_COMMON).requires(Items.COOKED_PORKCHOP).unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON))
                .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP)).save(consumer);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.HAM_SLICE.get()), HNCItems.COOKED_HAM_SLICE.get(), .35f, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ham_slice", has(HNCItems.HAM_SLICE.get())).save(consumer, HamNCheese.getLocation("cooked_ham_slice_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.HAM_SLICE.get()), HNCItems.COOKED_HAM_SLICE.get(), .35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ham_slice", has(HNCItems.HAM_SLICE.get())).save(consumer, HamNCheese.getLocation("cooked_ham_slice_campfire"));
        ShapelessRecipeBuilder.shapeless(HNCItems.GREEN_HAM_SLICE.get()).requires(HNCItemTags.GREEN_DYES_COMMON).requires(HNCItems.HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.HAM_SLICE.get()))
                .unlockedBy("has_dye", has(HNCItemTags.GREEN_DYES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.BACON.get(), 2).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItems.HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.HAM_SLICE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.COOKED_BACON.get(), 2).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItems.COOKED_HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.COOKED_HAM_SLICE.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer, HamNCheese.getLocation("cooked_bacon_knife"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BACON.get()), HNCItems.COOKED_BACON.get(), .2f, 50, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_bacon", has(HNCItems.BACON.get())).save(consumer, HamNCheese.getLocation("cooked_bacon_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BACON.get()), HNCItems.COOKED_BACON.get(), .2f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_bacon", has(HNCItems.BACON.get())).save(consumer, HamNCheese.getLocation("cooked_bacon_campfire"));

        ShapelessRecipeBuilder.shapeless(HNCItems.PINEAPPLE_RING.get(), 4).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItemTags.PINEAPPLE_COMMON).unlockedBy("has_pineapple", has(HNCItemTags.PINEAPPLE_COMMON))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.PINEAPPLE_BIT.get(), 3).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItems.PINEAPPLE_RING.get()).unlockedBy("has_pineapple", has(HNCItems.PINEAPPLE_RING.get()))
                .unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SAUCE.get()).requires(Items.BOWL).requires(Items.PUMPKIN_SEEDS).requires(HNCItemTags.TOMATO_COMMON).unlockedBy("has_bowl", has(Items.BOWL))
                .unlockedBy("has_pumpkin_seeds", has(Items.PUMPKIN_SEEDS)).unlockedBy("has_tomato", has(HNCItemTags.TOMATO_COMMON)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SLICE.get(), 4).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItemTags.TOMATO_COMMON).unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON))
                .unlockedBy("has_tomato", has(HNCItemTags.TOMATO_COMMON)).save(consumer);
//        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SEEDS.get(), 4).requires(HNCItems.TOMATO_SLICE.get()).unlockedBy("has_tomato_slice", has(HNCItems.TOMATO_SLICE.get())).save(consumer);

//        ShapelessRecipeBuilder.shapeless(HNCItems.CORN_KERNELS.get(), 8).requires(HNCItemTags.KNIVES_COMMON).requires(HNCItems.CORN_COB.get()).unlockedBy("has_knife", has(HNCItemTags.KNIVES_COMMON))
//                .unlockedBy("has_corn_cob", has(HNCItemTags.CORN_COMMON)).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(HNCItems.CORN_KERNELS.get()), HNCItems.DRIED_CORN_KERNELS.get(), .2f, 50).unlockedBy("has_corn_kernels", has(HNCItems.CORN_KERNELS.get())).save(consumer, HamNCheese.getLocation("dried_corn_kernals"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.CORN_KERNELS.get()), HNCItems.DRIED_CORN_KERNELS.get(), .2f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_corn_kernels", has(HNCItems.CORN_KERNELS.get())).save(consumer, HamNCheese.getLocation("dried_corn_kernals_campfire"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 100).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HamNCheese.getLocation("cooked_mouse"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 50, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HamNCheese.getLocation("cooked_mouse_smoking"));
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 300, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HamNCheese.getLocation("cooked_mouse_campfire"));

//        ShapedRecipeBuilder.shaped(HNCBlocks.PIZZA_OVEN.get()).define('b', Items.BRICK).define('t', Blocks.WHITE_TERRACOTTA).define('c', Ingredient.of(HNCItemTags.CAMPFIRES))
//                .pattern(" t ").pattern("tct").pattern("bbb").unlockedBy("has_bricks", has(Items.BRICK)).unlockedBy("has_terracotta", has(Blocks.WHITE_TERRACOTTA))
//                .unlockedBy("has_campfire", has(HNCItemTags.CAMPFIRES)).save(consumer);

//        ShapedRecipeBuilder.shaped(HNCBlocks.GRILL.get()).define('i', HNCItemTags.IRON_INGOTS_COMMON).define('r', HNCItemTags.REDSTONE_DUSTS_COMMON).define('s', Blocks.SMOKER).define('b', Blocks.IRON_BARS)
//                .pattern("i i").pattern("rsi").pattern("b b").unlockedBy("has_iron", has(HNCItemTags.IRON_INGOTS_COMMON)).unlockedBy("has_redstone", has(HNCItemTags.REDSTONE_DUSTS_COMMON))
//                .unlockedBy("has_smoker", has(Blocks.SMOKER)).unlockedBy("has_bars", has(Blocks.IRON_BARS)).save(consumer);

//        ShapedRecipeBuilder.shaped(HNCBlocks.POPCORN_MACHINE.get()).define('i', HNCItemTags.IRON_INGOTS_COMMON).define('r', Blocks.RED_CONCRETE).define('w', Blocks.WHITE_CONCRETE).define('b', Items.BUCKET).define('g', Blocks.GLASS_PANE)
//                .pattern("rwr").pattern("gbg").pattern("i i").unlockedBy("has_iron", has(HNCItemTags.IRON_INGOTS_COMMON)).unlockedBy("has_red_concrete", has(Blocks.RED_CONCRETE))
//                .unlockedBy("has_white_concrete", has(Blocks.WHITE_CONCRETE)).unlockedBy("has_bucket", has(Items.BUCKET)).unlockedBy("has_glass_pane", has(Blocks.GLASS_PANE)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.POPCORN_BAG.get()).define('p', Items.PAPER).define('r', HNCItemTags.RED_DYES_COMMON)
                .pattern("p p").pattern("rpr").unlockedBy("has_paper", has(Items.PAPER)).unlockedBy("has_red_dye", has(HNCItemTags.RED_DYES_COMMON)).save(consumer);

//        PopcornRecipeBuilder.popcorn(HNCItems.POPCORN.get(), 2, 50, Ingredient.of(HNCItemTags.SALT_COMMON))
//                .unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).save(consumer);
//        PopcornRecipeBuilder.popcorn(HNCItems.CHEESY_POPCORN.get(), 75, Ingredient.of(HNCItemTags.SALT_COMMON)).flavouring(Ingredient.of(HNCItems.CHEESE_SLICE.get()))
//                .unlockedBy("has_cheese_slice", has(HNCItems.CHEESE_SLICE.get())).unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).save(consumer);
//        PopcornRecipeBuilder.popcorn(HNCItems.CARAMEL_POPCORN.get(), 75, Ingredient.of(HNCItemTags.SALT_COMMON)).flavouring(Ingredient.of(HNCItemTags.SUGAR_COMMON))
//                .unlockedBy("has_sugar", has(HNCItemTags.SUGAR_COMMON)).unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).save(consumer);
//        PopcornRecipeBuilder.popcorn(HNCItems.MAPLE_POPCORN.get(), 75, Ingredient.of(HNCItemTags.SALT_COMMON)).flavouring(Ingredient.of(HNCItems.MAPLE_SYRUP.get()))
//                .unlockedBy("has_syrup", has(HNCItems.MAPLE_SYRUP.get())).unlockedBy("has_salt", has(HNCItemTags.SALT_COMMON)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_WOOD.get(), 3).define('l', HNCBlocks.MAPLE_LOG.get()).pattern("ll").pattern("ll").group("bark").unlockedBy("has_log", has(HNCBlocks.MAPLE_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.STRIPPED_MAPLE_WOOD.get(), 3).define('l', HNCBlocks.STRIPPED_MAPLE_LOG.get()).pattern("ll").pattern("ll").group("bark").unlockedBy("has_log", has(HNCBlocks.STRIPPED_MAPLE_LOG.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.MAPLE_PLANKS.get(), 4).requires(Ingredient.of(HNCItemTags.MAPLE_LOGS)).group("planks").unlockedBy("has_log", has(HNCItemTags.MAPLE_LOGS)).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_STAIRS.get(), 4).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("p  ").pattern("pp ").pattern("ppp").group("wooden_stairs").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_SLAB.get(), 6).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("ppp").group("wooden_slab").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_SIGN.get(), 3).group("sign").define('p', HNCBlocks.MAPLE_PLANKS.get()).define('s', Items.STICK).pattern("ppp").pattern("ppp").pattern(" s ").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_PRESSURE_PLATE.get()).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("pp").group("wooden_pressure_plate").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.MAPLE_BUTTON.get()).requires(HNCBlocks.MAPLE_PLANKS.get()).group("wooden_button").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_FENCE.get(), 3).define('s', Items.STICK).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("psp").pattern("psp").group("wooden_fence").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_FENCE_GATE.get()).define('s', Items.STICK).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("sps").pattern("sps").group("wooden_fence_gate").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_TRAPDOOR.get(), 2).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("ppp").pattern("ppp").group("wooden_trapdoor").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCBlocks.MAPLE_DOOR.get(), 3).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("pp").pattern("pp").pattern("pp").group("wooden_door").unlockedBy("has_planks", has(HNCBlocks.MAPLE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HNCItems.MAPLE_BOAT.get()).define('p', HNCBlocks.MAPLE_PLANKS.get()).pattern("p p").pattern("ppp").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCBlocks.TREE_TAP.get()).define('i', HNCItemTags.IRON_INGOTS_COMMON).define('n', HNCItemTags.IRON_NUGGETS_COMMON).pattern(" n").pattern("ii").pattern(" n").unlockedBy("has_iron", has(HNCItemTags.IRON_INGOTS_COMMON)).unlockedBy("has_iron_nugget", has(HNCItemTags.IRON_NUGGETS_COMMON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCBlocks.OAK_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.OAK_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.OAK_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.BIRCH_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.BIRCH_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.BIRCH_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.SPRUCE_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.SPRUCE_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.SPRUCE_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.JUNGLE_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.JUNGLE_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.JUNGLE_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.ACACIA_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.ACACIA_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.ACACIA_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.DARK_OAK_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.DARK_OAK_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.CRIMSON_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.CRIMSON_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.CRIMSON_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.WARPED_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.WARPED_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.WARPED_PRESSURE_PLATE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCBlocks.STONE_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.STONE_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.STONE_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.GOLD_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCBlocks.IRON_CHOPPING_BOARD.get()).group("chopping_board").requires(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).unlockedBy("has_pressure_plate", has(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCBlocks.MAPLE_CHOPPING_BOARD.get()).group("chopping_board").requires(HNCBlocks.MAPLE_PRESSURE_PLATE.get()).unlockedBy("has_pressure_plate", has(HNCBlocks.MAPLE_PRESSURE_PLATE.get())).save(consumer);
    }
}
