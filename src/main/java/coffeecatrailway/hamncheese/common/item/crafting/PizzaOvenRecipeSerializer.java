package coffeecatrailway.hamncheese.common.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 20/05/2021
 */
public class PizzaOvenRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PizzaOvenRecipe>
{
    private final PizzaOvenRecipeSerializer.IFactory factory;

    public PizzaOvenRecipeSerializer()
    {
        this.factory = PizzaOvenRecipe::new;
    }

    @Override
    public PizzaOvenRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        if (!json.has("ingredients"))
            throw new JsonSyntaxException("Missing ingredients, expected to find a JsonArray");
        NonNullList<Ingredient> ingredients = NonNullList.create();
        JSONUtils.getAsJsonArray(json, "ingredients").forEach(element -> ingredients.add(Ingredient.fromJson(element)));

        if (!json.has("result"))
            throw new JsonSyntaxException("Missing result, expected to find a String or JsonObject");
        ItemStack result;
        if (json.get("result").isJsonObject())
            result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
        else
        {
            String resultId = JSONUtils.getAsString(json, "result");
            result = new ItemStack(Registry.ITEM.getOptional(new ResourceLocation(resultId)).orElseThrow(() -> new IllegalStateException("Item: " + resultId + " does not exist")));
        }

        float experience = JSONUtils.getAsFloat(json, "experience", 0f);
        int cookTime = JSONUtils.getAsInt(json, "cookTime", 200);
        return this.factory.create(id, ingredients, result, experience, cookTime);
    }

    @Nullable
    @Override
    public PizzaOvenRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
    {
        int size = buffer.readVarInt();
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (int i = 0; i < size; i++)
            ingredients.add(Ingredient.fromNetwork(buffer));
        ItemStack result = buffer.readItem();
        float experience = buffer.readFloat();
        int cookTime = buffer.readVarInt();
        return this.factory.create(id, ingredients, result, experience, cookTime);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, PizzaOvenRecipe recipe)
    {
        buffer.writeVarInt(recipe.getIngredients().size());
        for (int i = 0; i < recipe.getIngredients().size(); i++)
            recipe.getIngredients().get(i).toNetwork(buffer);
        buffer.writeItemStack(recipe.getResultItem(), false);
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookTime());
    }

    public interface IFactory {
        PizzaOvenRecipe create(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result, float experience, int cookTime);
    }
}
