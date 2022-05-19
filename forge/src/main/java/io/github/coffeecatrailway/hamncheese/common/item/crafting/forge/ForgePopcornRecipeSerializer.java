package io.github.coffeecatrailway.hamncheese.common.item.crafting.forge;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 19/05/2022
 */
public class ForgePopcornRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PopcornRecipe>
{
    private final PopcornRecipe.Serializer serializer;

    public ForgePopcornRecipeSerializer() {
        this.serializer = new PopcornRecipe.Serializer();
    }

    @Override
    public PopcornRecipe fromJson(ResourceLocation id, JsonObject jsonObject)
    {
        return this.serializer.fromJson(id, jsonObject);
    }

    @Nullable
    @Override
    public PopcornRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
    {
        return this.serializer.fromNetwork(id, buffer);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, PopcornRecipe recipe)
    {
        this.serializer.toNetwork(buffer, recipe);
    }
}
