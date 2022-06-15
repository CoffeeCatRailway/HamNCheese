package io.github.coffeecatrailway.hamncheese.data.gen.recipes;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCItemTags;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCRecipeProvider;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author CoffeeCatRailway
 * Created: 15/06/2022
 */
public class ChoppingBoardRecipeBuilder
{
    private final Item result;
    private final int count;
    private final Ingredient ingredient;
    private Ingredient tool = Ingredient.EMPTY;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ChoppingBoardRecipeBuilder(ItemLike result, int count, Ingredient ingredient) {
        this.result = result.asItem();
        this.count = count;
        this.ingredient = ingredient;
    }

    public static ChoppingBoardRecipeBuilder recipe(ItemLike result, Ingredient ingredient)
    {
        return recipe(result, 1, ingredient);
    }

    public static ChoppingBoardRecipeBuilder recipe(ItemLike result, int count, Ingredient ingredient)
    {
        return new ChoppingBoardRecipeBuilder(result, count, ingredient);
    }

    public ChoppingBoardRecipeBuilder tool(Ingredient tool)
    {
        this.tool = tool;
        return this;
    }

    public ChoppingBoardRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion)
    {
        this.advancement.addCriterion(name, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer)
    {
        ResourceLocation location = Registry.ITEM.getKey(this.result);
        this.ensureValid(location);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).addCriterion("has_board", HNCRecipeProvider.has(HNCItemTags.CHOPPING_BOARDS)).rewards(AdvancementRewards.Builder.recipe(location)).requirements(RequirementsStrategy.OR);
        consumer.accept(new ChoppingBoardRecipeBuilder.Result(location, this.result, this.count, this.ingredient, this.tool, this.advancement, new ResourceLocation(location.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + location.getPath())));
    }

    private void ensureValid(ResourceLocation location)
    {
        if (this.advancement.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + location);
    }

    public class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final Ingredient ingredient;
        private final Ingredient tool;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, Ingredient ingredient, Ingredient tool, Advancement.Builder advancement, ResourceLocation advancementId)
        {
            this.id = id;
            this.result = result;
            this.count = count;
            this.ingredient = ingredient;
            this.tool = tool;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json)
        {
            json.add("ingredient", this.ingredient.toJson());
            if (!this.tool.isEmpty())
                json.add("tool", this.tool.toJson());

            JsonObject result = new JsonObject();
            result.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1)
                result.addProperty("count", this.count);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return HNCRecipes.CHOPPING_BOARD_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
