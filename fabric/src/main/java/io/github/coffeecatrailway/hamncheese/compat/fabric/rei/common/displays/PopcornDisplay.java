package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays;

import dev.architectury.utils.NbtType;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.PopcornRecipe;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2022
 */
public class PopcornDisplay implements Display
{
    private final int popcorn;
    private final List<EntryIngredient> input;
    private final List<EntryIngredient> result;
    private final Optional<ResourceLocation> location;

    public PopcornDisplay(PopcornRecipe recipe)
    {
        this(recipe.getPopcorn(), List.of(EntryIngredients.ofIngredient(recipe.getFlavouring()), EntryIngredients.ofIngredient(recipe.getSeasoning()), EntryIngredients.of(new ItemStack(HNCItems.POPCORN_BAG.get(), recipe.getResultItem().getCount()))),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem())), Optional.ofNullable(recipe.getId()));
    }

    public PopcornDisplay(int popcorn, List<EntryIngredient> input, List<EntryIngredient> result, Optional<ResourceLocation> location)
    {
        this.popcorn = popcorn;
        this.input = input;
        this.result = result;
        this.location = location;

//        if (this.input.get(0).isEmpty())
//            this.input.remove(0);
    }

    public int getPopcorn()
    {
        return popcorn;
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return this.input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return this.result;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return HNCREIPlugin.POPCORN;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation()
    {
        return this.location;
    }

    public static class Serializer implements DisplaySerializer<PopcornDisplay>
    {
        protected final Constructor<PopcornDisplay> constructor;

        private Serializer(Constructor<PopcornDisplay> constructor)
        {
            this.constructor = constructor;
        }

        public static Serializer of()
        {
            return new Serializer(PopcornDisplay::new);
        }

        @Override
        public CompoundTag save(CompoundTag tag, PopcornDisplay display)
        {
            tag.putInt("popcorn", display.popcorn);
            tag.put("input", EntryIngredients.save(display.getInputEntries()));
            tag.put("result", EntryIngredients.save(display.getOutputEntries()));
            display.getDisplayLocation().ifPresent(location -> tag.putString("location", location.toString()));
            return tag;
        }

        @Override
        public PopcornDisplay read(CompoundTag tag)
        {
            int popcorn = tag.getInt("popcorn");
            List<EntryIngredient> input = EntryIngredients.read(tag.getList("input", NbtType.LIST));
            List<EntryIngredient> result = EntryIngredients.read(tag.getList("result", NbtType.LIST));
            ResourceLocation location = null;
            if (tag.contains("location", NbtType.STRING))
                location = new ResourceLocation(tag.getString("location"));
            return constructor.construct(popcorn, input, result, Optional.ofNullable(location));
        }

        @FunctionalInterface
        public interface Constructor<D>
        {
            D construct(int popcorn, List<EntryIngredient> input, List<EntryIngredient> result, Optional<ResourceLocation> location);
        }
    }
}
