package coffeecatrailway.hamncheese.datagen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCRecipeGen extends RecipeProvider
{
    public HNCRecipeGen(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
    {
        /*
         * Crafting Ingredients
         */
        ShapedRecipeBuilder.shaped(HNCItems.WOODEN_GEAR.get(), 4).define('s', Tags.Items.RODS_WOODEN).define('p', net.minecraft.tags.ItemTags.PLANKS)
                .pattern(" s ").pattern("sps").pattern(" s ").unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .unlockedBy("has_planks", has(ItemTags.PLANKS)).save(consumer);

        /*
         * Tools
         */
        ShapedRecipeBuilder.shaped(HNCItems.CURDLER.get()).define('s', Tags.Items.RODS_WOODEN).define('g', HNCItemTags.GEARS_WOODEN)
                .pattern("s").pattern("s").pattern("g").unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .unlockedBy("has_gears", has(HNCItemTags.GEARS_WOODEN)).save(consumer, HNCMod.getLocation("curdler_alternate"));
        ShapedRecipeBuilder.shaped(HNCItems.CURDLER.get()).define('s', Tags.Items.RODS_WOODEN).define('g', HNCItemTags.GEARS_WOODEN)
                .pattern("g").pattern("s").pattern("s").unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .unlockedBy("has_gears", has(HNCItemTags.GEARS_WOODEN)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.ROLLING_PIN.get()).define('s', Tags.Items.RODS_WOODEN).define('p', net.minecraft.tags.ItemTags.PLANKS).pattern("sps")
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN)).unlockedBy("has_planks", has(net.minecraft.tags.ItemTags.PLANKS)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.GRIND_STONES.get(), 2)
                .requires(Tags.Items.STONE).unlockedBy("has_stone", has(Tags.Items.STONE)).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.KNIFE.get()).define('s', Tags.Items.RODS_WOODEN).define('i', Tags.Items.INGOTS_IRON).pattern("  i").pattern(" i ").pattern("s  ")
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN)).unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer);

        /*
         * Foods
         */
        ShapelessRecipeBuilder.shapeless(HNCItems.BLOCK_OF_CHEESE.get(), 2).requires(Items.MILK_BUCKET).requires(HNCItems.CURDLER.get()).unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .unlockedBy("has_curdler", has(HNCItems.CURDLER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.CHEESE_SLICE.get(), 3).requires(HNCItems.BLOCK_OF_CHEESE.get()).requires(HNCItems.KNIFE.get()).unlockedBy("has_cheese", has(HNCItems.BLOCK_OF_CHEESE.get()))
                .unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.ROCK_SALT.get(), 2).requires(HNCItems.GRIND_STONES.get()).requires(Tags.Items.STONE).unlockedBy("has_stone", has(Tags.Items.STONE))
                .unlockedBy("has_gind_stones", has(HNCItems.GRIND_STONES.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.FLOUR.get(), 3).requires(HNCItems.GRIND_STONES.get()).requires(HNCItemTags.WHEAT).unlockedBy("has_wheat", has(HNCItemTags.WHEAT))
                .unlockedBy("has_gind_stones", has(HNCItems.GRIND_STONES.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.DOUGH.get(), 2).requires(HNCItemTags.WHEAT).requires(HNCItemTags.SUGAR).requires(HNCItemTags.SALT).requires(HNCItemTags.FLOUR).unlockedBy("has_wheat", has(HNCItemTags.WHEAT))
                .unlockedBy("has_sugar", has(HNCItemTags.SUGAR)).unlockedBy("has_salt", has(HNCItemTags.SALT)).unlockedBy("has_flour", has(HNCItemTags.FLOUR)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.UNBAKED_PIZZA_BASE.get()).requires(HNCItems.ROLLING_PIN.get()).requires(HNCItems.DOUGH.get()).unlockedBy("has_dough", has(HNCItems.DOUGH.get()))
                .unlockedBy("has_rolling_pin", has(HNCItems.ROLLING_PIN.get())).save(consumer);

        ShapedRecipeBuilder.shaped(HNCItems.UNBAKED_BREAD.get(), 2).define('d', HNCItemTags.DOUGH).pattern("ddd").unlockedBy("has_dough", has(HNCItemTags.DOUGH)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_BREAD.get()), Items.BREAD, .35f, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_unbaked_bread", has(HNCItems.UNBAKED_BREAD.get())).save(consumer, HNCMod.getLocation("bread_smoking"));
        ShapelessRecipeBuilder.shapeless(HNCItems.BREAD_SLICE.get()).requires(HNCItems.KNIFE.get()).requires(Items.BREAD).unlockedBy("has_bread", has(Items.BREAD)).unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BREAD_SLICE.get()), HNCItems.TOAST.get(), .35f, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_bread_slice", has(HNCItems.BREAD_SLICE.get())).save(consumer, HNCMod.getLocation("toast_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BREAD_SLICE.get()), HNCItems.TOAST.get(), .35f, 300, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_bread_slice", has(HNCItems.BREAD_SLICE.get())).save(consumer, HNCMod.getLocation("toast_campfire"));

        ShapelessRecipeBuilder.shapeless(HNCItems.UNBAKED_CRACKER.get(), 2).requires(HNCItemTags.WHEAT).requires(HNCItemTags.SALT).requires(HNCItemTags.FLOUR).unlockedBy("has_wheat", has(HNCItemTags.WHEAT))
                .unlockedBy("has_salt", has(HNCItemTags.SALT)).unlockedBy("has_flour", has(HNCItemTags.FLOUR)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_CRACKER.get()), HNCItems.CRACKER.get(), .35f, 50, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_unbaked_cracker", has(HNCItems.UNBAKED_CRACKER.get())).save(consumer, HNCMod.getLocation("cracker_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.UNBAKED_CRACKER.get()), HNCItems.CRACKER.get(), .35f, 300, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_unbaked_cracker", has(HNCItems.UNBAKED_CRACKER.get())).save(consumer, HNCMod.getLocation("cracker_campfire"));

        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.CRACKED_EGG.get()), HNCItems.COOKED_EGG.get(), .15f, 50, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_cracked_egg", has(HNCItems.CRACKED_EGG.get())).save(consumer, HNCMod.getLocation("cooked_egg_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.CRACKED_EGG.get()), HNCItems.COOKED_EGG.get(), .15f, 300, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_cracked_egg", has(HNCItems.CRACKED_EGG.get())).save(consumer, HNCMod.getLocation("cooked_egg_campfire"));
        ShapelessRecipeBuilder.shapeless(HNCItems.GREEN_EGG.get()).requires(Tags.Items.DYES_GREEN).requires(HNCItems.CRACKED_EGG.get()).unlockedBy("has_egg", has(HNCItems.CRACKED_EGG.get()))
                .unlockedBy("has_dye", has(Tags.Items.DYES_GREEN)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.HAM_SLICE.get(), 3).requires(HNCItems.KNIFE.get()).requires(Items.PORKCHOP).unlockedBy("has_knife", has(HNCItems.KNIFE.get()))
                .unlockedBy("has_porkchop", has(Items.PORKCHOP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.COOKED_HAM_SLICE.get(), 3).requires(HNCItems.KNIFE.get()).requires(Items.COOKED_PORKCHOP).unlockedBy("has_knife", has(HNCItems.KNIFE.get()))
                .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.HAM_SLICE.get()), HNCItems.COOKED_HAM_SLICE.get(), .35f, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ham_slice", has(HNCItems.HAM_SLICE.get())).save(consumer, HNCMod.getLocation("cooked_ham_slice_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.HAM_SLICE.get()), HNCItems.COOKED_HAM_SLICE.get(), .35f, 600, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ham_slice", has(HNCItems.HAM_SLICE.get())).save(consumer, HNCMod.getLocation("cooked_ham_slice_campfire"));
        ShapelessRecipeBuilder.shapeless(HNCItems.GREEN_HAM_SLICE.get()).requires(Tags.Items.DYES_GREEN).requires(HNCItems.HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.HAM_SLICE.get()))
                .unlockedBy("has_dye", has(Tags.Items.DYES_GREEN)).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.BACON.get(), 2).requires(HNCItems.KNIFE.get()).requires(HNCItems.HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.HAM_SLICE.get()))
                .unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.COOKED_BACON.get(), 2).requires(HNCItems.KNIFE.get()).requires(HNCItems.COOKED_HAM_SLICE.get()).unlockedBy("has_ham", has(HNCItems.COOKED_HAM_SLICE.get()))
                .unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer, HNCMod.getLocation("cooked_bacon_kinfe"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BACON.get()), HNCItems.COOKED_BACON.get(), .2f, 50, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_bacon", has(HNCItems.BACON.get())).save(consumer, HNCMod.getLocation("cooked_bacon_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.BACON.get()), HNCItems.COOKED_BACON.get(), .2f, 300, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_bacon", has(HNCItems.BACON.get())).save(consumer, HNCMod.getLocation("cooked_bacon_campfire"));

        ShapelessRecipeBuilder.shapeless(HNCItems.PINEAPPLE_RING.get(), 4).requires(HNCItems.KNIFE.get()).requires(HNCItems.PINEAPPLE.get()).unlockedBy("has_pineapple", has(HNCItems.PINEAPPLE.get()))
                .unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.PINEAPPLE_BIT.get(), 3).requires(HNCItems.KNIFE.get()).requires(HNCItems.PINEAPPLE_RING.get()).unlockedBy("has_pineapple", has(HNCItems.PINEAPPLE_RING.get()))
                .unlockedBy("has_knife", has(HNCItems.KNIFE.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SAUCE.get()).requires(Items.BOWL).requires(Items.PUMPKIN_SEEDS).requires(HNCItems.TOMATO.get()).unlockedBy("has_bowl", has(Items.BOWL))
                .unlockedBy("has_pumpkin_seeds", has(Items.PUMPKIN_SEEDS)).unlockedBy("has_tomato", has(HNCItems.TOMATO.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SLICE.get(), 4).requires(HNCItems.KNIFE.get()).requires(HNCItems.TOMATO.get()).unlockedBy("has_knife", has(HNCItems.KNIFE.get()))
                .unlockedBy("has_tomato", has(HNCItems.TOMATO.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(HNCItems.TOMATO_SEEDS.get(), 4).requires(HNCItems.TOMATO_SLICE.get()).unlockedBy("has_tomato_slice", has(HNCItems.TOMATO_SLICE.get())).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 100).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HNCMod.getLocation("cooked_mouse"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 50, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HNCMod.getLocation("cooked_mouse_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(HNCItems.MOUSE.get()), HNCItems.COOKED_MOUSE.get(), .2f, 300, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_mouse", has(HNCItems.MOUSE.get())).save(consumer, HNCMod.getLocation("cooked_mouse_campfire"));
    }
}
