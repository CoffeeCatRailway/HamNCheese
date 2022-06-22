package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 14/06/2022
 */
public class ChoppingBoardRecipe implements Recipe<ChoppingBoardBlockEntity>
{
    private final ResourceLocation id;

    private final Ingredient ingredient;
    private final Ingredient tool;
    private final ItemStack result;

    public ChoppingBoardRecipe(ResourceLocation id, Ingredient ingredient, Ingredient tool, ItemStack result)
    {
        this.id = id;
        this.ingredient = ingredient;
        this.tool = tool;
        this.result = result;
    }

    @Override
    public boolean matches(ChoppingBoardBlockEntity entity, Level level)
    {
        return this.ingredient.test(entity.getItem(0));
    }

    @Override
    public ItemStack assemble(ChoppingBoardBlockEntity container)
    {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return this.result;
    }

    public Ingredient getIngredient()
    {
        return this.ingredient;
    }

    public Ingredient getTool()
    {
        return this.tool;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.of(this.ingredient, this.tool);
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.CHOPPING_BOARD_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return HNCRecipes.CHOPPING_BOARD_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ChoppingBoardRecipe>
    {
        @Override
        public ChoppingBoardRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            Ingredient tool = Ingredient.EMPTY;
            if (json.has("tool"))
                tool = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "tool"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new ChoppingBoardRecipe(id, ingredient, tool, result);
        }

        @Nullable
        @Override
        public ChoppingBoardRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient tool = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new ChoppingBoardRecipe(id, ingredient, tool, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChoppingBoardRecipe recipe)
        {
            recipe.ingredient.toNetwork(buffer);
            recipe.tool.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
