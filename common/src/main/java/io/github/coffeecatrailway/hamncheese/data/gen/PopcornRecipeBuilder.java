package io.github.coffeecatrailway.hamncheese.data.gen;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author CoffeeCatRailway
 * Created: 24/08/2021
 */
public class PopcornRecipeBuilder
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final int popcorn;
    private Ingredient flavouring = Ingredient.EMPTY;
    private final Ingredient seasoning;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public PopcornRecipeBuilder(ItemLike result, int count, int popcorn, Ingredient seasoning)
    {
        this.result = result.asItem();
        this.count = count;
        this.popcorn = popcorn;
        this.seasoning = seasoning;
    }

    public static PopcornRecipeBuilder popcorn(ItemLike result, int popcorn, Ingredient seasoning)
    {
        return popcorn(result, 1, popcorn, seasoning);
    }

    public static PopcornRecipeBuilder popcorn(ItemLike result, int count, int popcorn, Ingredient seasoning)
    {
        return new PopcornRecipeBuilder(result, count, popcorn, seasoning);
    }

    public PopcornRecipeBuilder flavouring(Ingredient flavouring)
    {
        this.flavouring = flavouring;
        return this;
    }

    public PopcornRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion)
    {
        this.advancement.addCriterion(name, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer)
    {
        ResourceLocation location = Registry.ITEM.getKey(this.result);
        this.ensureValid(location);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).addCriterion("has_bag", HNCRecipeProvider.has(HNCItems.POPCORN_BAG.get())).rewards(AdvancementRewards.Builder.recipe(location)).requirements(RequirementsStrategy.OR);
        consumer.accept(new PopcornRecipeBuilder.Result(location, this.result, this.count, this.popcorn, this.flavouring, this.seasoning, this.advancement, new ResourceLocation(location.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + location.getPath())));
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
        private final int popcorn;
        private final Ingredient flavouring;
        private final Ingredient seasoning;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, int popcorn, Ingredient flavouring, Ingredient seasoning, Advancement.Builder advancement, ResourceLocation advancementId)
        {
            this.id = id;
            this.result = result;
            this.count = count;
            this.popcorn = popcorn;
            this.flavouring = flavouring;
            this.seasoning = seasoning;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json)
        {
            json.addProperty("popcorn", this.popcorn);
            if (!this.flavouring.isEmpty())
                json.add("flavouring", this.flavouring.toJson());
            json.add("seasoning", this.seasoning.toJson());

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
            return HNCRecipes.POPCORN_SERIALIZER.get();
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
