package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.common.tileentity.PopcornMachineTileEntity;
import coffeecatrailway.hamncheese.registry.HNCRecipes;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author CoffeeCatRailway
 * Created: 8/08/2021
 */
public class PopcornRecipe implements IRecipe<PopcornMachineTileEntity>
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
    public boolean matches(PopcornMachineTileEntity tile, World level)
    {
        return this.seasoning.test(tile.getItem(PopcornMachineTileEntity.SLOT_SEASONING)) && tile.getPopcorn() >= this.popcorn && this.flavouring.test(tile.getItem(PopcornMachineTileEntity.SLOT_FLAVOURING));
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
            Ingredient flavouring = Ingredient.EMPTY;
            if (json.has("flavouring"))
                flavouring = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "flavouring"));
            Ingredient seasoning = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "seasoning"));
            if (Arrays.stream(seasoning.getItems()).noneMatch(stack -> !stack.hasContainerItem() || stack.getMaxStackSize() >= 2))
                throw new JsonParseException("Popcorn recipe " + id + " seasoning has a container item or can't stack up to or above 2");
            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            return new PopcornRecipe(id, popcorn, flavouring, seasoning, result);
        }

        @Nullable
        @Override
        public PopcornRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
        {
            int popcorn = buffer.readVarInt();
            Ingredient flavouring = Ingredient.fromNetwork(buffer);
            Ingredient seasoning = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new PopcornRecipe(id, popcorn, flavouring, seasoning, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PopcornRecipe recipe)
        {
            buffer.writeVarInt(recipe.popcorn);
            recipe.flavouring.toNetwork(buffer);
            recipe.seasoning.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
