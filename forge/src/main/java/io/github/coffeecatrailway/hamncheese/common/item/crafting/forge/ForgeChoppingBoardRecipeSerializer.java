package io.github.coffeecatrailway.hamncheese.common.item.crafting.forge;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 19/05/2022
 */
public class ForgeChoppingBoardRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ChoppingBoardRecipe>
{
    private final ChoppingBoardRecipe.Serializer serializer;

    public ForgeChoppingBoardRecipeSerializer() {
        this.serializer = new ChoppingBoardRecipe.Serializer();
    }

    @Override
    public ChoppingBoardRecipe fromJson(ResourceLocation id, JsonObject jsonObject)
    {
        return this.serializer.fromJson(id, jsonObject);
    }

    @Nullable
    @Override
    public ChoppingBoardRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
    {
        return this.serializer.fromNetwork(id, buffer);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ChoppingBoardRecipe recipe)
    {
        this.serializer.toNetwork(buffer, recipe);
    }
}
