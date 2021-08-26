package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.registry.HNCItems;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
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

    public PopcornRecipeBuilder(IItemProvider result, int count, int popcorn, Ingredient seasoning)
    {
        this.result = result.asItem();
        this.count = count;
        this.popcorn = popcorn;
        this.seasoning = seasoning;
    }

    public static PopcornRecipeBuilder popcorn(IItemProvider result, int popcorn, Ingredient seasoning)
    {
        return popcorn(result, 1, popcorn, seasoning);
    }

    public static PopcornRecipeBuilder popcorn(IItemProvider result, int count, int popcorn, Ingredient seasoning)
    {
        return new PopcornRecipeBuilder(result, count, popcorn, seasoning);
    }

    public PopcornRecipeBuilder flavouring(Ingredient flavouring)
    {
        this.flavouring = flavouring;
        return this;
    }

    public PopcornRecipeBuilder unlockedBy(String name, ICriterionInstance criterion)
    {
        this.advancement.addCriterion(name, criterion);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> consumer)
    {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.result);
        this.ensureValid(location);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).addCriterion("has_bag", HNCRecipeGen.hasPublic(HNCItems.POPCORN_BAG.get())).rewards(AdvancementRewards.Builder.recipe(location)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new PopcornRecipeBuilder.Result(location, this.result, this.count, this.popcorn, this.flavouring, this.seasoning, this.advancement, new ResourceLocation(location.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + location.getPath())));
    }

    private void ensureValid(ResourceLocation location)
    {
        if (this.advancement.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + location);
    }

    public class Result implements IFinishedRecipe
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
        public IRecipeSerializer<?> getType()
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
