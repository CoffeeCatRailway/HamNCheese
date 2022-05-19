package io.github.coffeecatrailway.hamncheese.common.item.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.coffeecatrailway.hamncheese.common.block.entity.PopcornMachineBlockEntity;
import io.github.coffeecatrailway.hamncheese.registry.HNCRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author CoffeeCatRailway
 * Created: 8/08/2021
 */
public class PopcornRecipe implements Recipe<PopcornMachineBlockEntity>
{
    private final ResourceLocation id;

    private final int popcorn;
    private final Ingredient flavouring;
    private final Ingredient seasoning;
    private final ItemStack result;

    public PopcornRecipe(ResourceLocation id, int popcorn, Ingredient flavouring, Ingredient seasoning, ItemStack result)
    {
        this.id = id;
        this.popcorn = popcorn;
        this.flavouring = flavouring;
        this.seasoning = seasoning;
        this.result = result;
    }

    @Override
    public boolean matches(PopcornMachineBlockEntity tile, Level level)
    {
        return this.seasoning.test(tile.getItem(PopcornMachineBlockEntity.SLOT_SEASONING)) && tile.getPopcorn() >= this.popcorn && this.flavouring.test(tile.getItem(PopcornMachineBlockEntity.SLOT_FLAVOURING));
    }

    @Override
    public ItemStack assemble(PopcornMachineBlockEntity tile)
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

    public int getPopcorn()
    {
        return this.popcorn;
    }

    public Ingredient getFlavouring()
    {
        return this.flavouring;
    }

    public Ingredient getSeasoning()
    {
        return this.seasoning;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.POPCORN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return HNCRecipes.POPCORN_TYPE;
    }

    public static class Serializer implements RecipeSerializer<PopcornRecipe>
    {
        @Override
        public PopcornRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            int popcorn = GsonHelper.getAsInt(json, "popcorn");
            Ingredient flavouring = Ingredient.EMPTY;
            if (json.has("flavouring"))
                flavouring = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "flavouring"));
            Ingredient seasoning = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "seasoning"));
            if (Arrays.stream(seasoning.getItems()).noneMatch(stack -> !stack.getItem().hasCraftingRemainingItem() || stack.getMaxStackSize() >= 2))
                throw new JsonParseException("Popcorn recipe " + id + " seasoning has a container item or can't stack up to or above 2");
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new PopcornRecipe(id, popcorn, flavouring, seasoning, result);
        }

        @Nullable
        @Override
        public PopcornRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            int popcorn = buffer.readVarInt();
            Ingredient flavouring = Ingredient.fromNetwork(buffer);
            Ingredient seasoning = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new PopcornRecipe(id, popcorn, flavouring, seasoning, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PopcornRecipe recipe)
        {
            buffer.writeVarInt(recipe.popcorn);
            recipe.flavouring.toNetwork(buffer);
            recipe.seasoning.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
