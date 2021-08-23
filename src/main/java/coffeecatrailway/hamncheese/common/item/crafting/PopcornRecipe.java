package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.PopcornMachineTileEntity;
import coffeecatrailway.hamncheese.data.PopcornFlavourManager;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 8/08/2021
 */
public class PopcornRecipe implements IRecipe<PopcornMachineTileEntity>
{
    private static final JsonObject FLAVOUR_NONE = new JsonObject();

    static
    {
        FLAVOUR_NONE.addProperty("amount", 0);
        FLAVOUR_NONE.addProperty("id", PopcornFlavourManager.NONE_ID.toString());
    }

    private final ResourceLocation id;

    private final int popcorn;
    private final int flavour;
    private final ResourceLocation flavourId;
    private final Ingredient seasoning;
    private final ItemStack result;

    public PopcornRecipe(ResourceLocation id, int popcorn, int flavour, ResourceLocation flavourId, Ingredient seasoning, ItemStack result)
    {
        this.id = id;
        this.popcorn = popcorn;
        this.flavour = flavour;
        this.flavourId = flavourId;
        this.seasoning = seasoning;
        this.result = result;
    }

    @Override
    public boolean matches(PopcornMachineTileEntity tile, World level)
    {
        return this.seasoning.test(tile.getItem(2)) && tile.getPopcorn() >= this.popcorn && tile.hasFlavour(this.flavourId, this.flavour);
    }

    @Override
    public ItemStack assemble(PopcornMachineTileEntity tile)
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

    public int getFlavour()
    {
        return this.flavour;
    }

    public ResourceLocation getFlavourId()
    {
        return this.flavourId;
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
    public IRecipeSerializer<?> getSerializer()
    {
        return HNCRecipes.POPCORN_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return HNCRecipes.POPCORN_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PopcornRecipe>
    {
        @Override
        public PopcornRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            int popcorn = JSONUtils.getAsInt(json, "popcorn");
            JsonObject flavourObj = JSONUtils.getAsJsonObject(json, "flavour", FLAVOUR_NONE);
            int flavour = JSONUtils.getAsInt(flavourObj, "amount", 0);
            ResourceLocation flavourId = new ResourceLocation(JSONUtils.getAsString(flavourObj, "id", PopcornFlavourManager.NONE_ID.toString()));
            Ingredient seasoning = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "seasoning"));
            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            return new PopcornRecipe(id, popcorn, flavour, flavourId, seasoning, result);
        }

        @Nullable
        @Override
        public PopcornRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
        {
            int popcorn = buffer.readVarInt();
            int flavour = buffer.readVarInt();
            ResourceLocation flavourId = buffer.readResourceLocation();
            Ingredient seasoning = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new PopcornRecipe(id, popcorn, flavour, flavourId, seasoning, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PopcornRecipe recipe)
        {
            buffer.writeVarInt(recipe.popcorn);
            buffer.writeVarInt(recipe.flavour);
            buffer.writeResourceLocation(recipe.flavourId);
            recipe.seasoning.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
