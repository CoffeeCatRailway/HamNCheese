package io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.displays;

import dev.architectury.utils.NbtType;
import io.github.coffeecatrailway.hamncheese.common.item.crafting.ChoppingBoardRecipe;
import io.github.coffeecatrailway.hamncheese.compat.fabric.rei.common.HNCREIPlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 19/06/2022
 */
public class ChoppingBoardDisplay implements Display
{
    private final List<EntryIngredient> input;
    private final List<EntryIngredient> result;
    private final Optional<ResourceLocation> location;

    public ChoppingBoardDisplay(ChoppingBoardRecipe recipe)
    {
        this(List.of(EntryIngredients.ofIngredient(recipe.getIngredient()), EntryIngredients.ofIngredient(recipe.getTool())), Collections.singletonList(EntryIngredients.of(recipe.getResultItem())), Optional.ofNullable(recipe.getId()));
    }

    public ChoppingBoardDisplay(List<EntryIngredient> input, List<EntryIngredient> result, Optional<ResourceLocation> location) {
        this.input = input;
        this.result = result;
        this.location = location;
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
        return HNCREIPlugin.CHOPPING_BOARD;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation()
    {
        return this.location;
    }

    public static class Serializer implements DisplaySerializer<ChoppingBoardDisplay>
    {
        protected final Constructor constructor;

        private Serializer(Constructor constructor)
        {
            this.constructor = constructor;
        }

        public static Serializer of()
        {
            return new Serializer(ChoppingBoardDisplay::new);
        }

        @Override
        public CompoundTag save(CompoundTag tag, ChoppingBoardDisplay display)
        {
            tag.put("input", EntryIngredients.save(display.getInputEntries()));
            tag.put("result", EntryIngredients.save(display.getOutputEntries()));
            display.getDisplayLocation().ifPresent(location -> tag.putString("location", location.toString()));
            return tag;
        }

        @Override
        public ChoppingBoardDisplay read(CompoundTag tag)
        {
            List<EntryIngredient> input = EntryIngredients.read(tag.getList("input", NbtType.LIST));
            List<EntryIngredient> result = EntryIngredients.read(tag.getList("result", NbtType.LIST));
            ResourceLocation location = null;
            if (tag.contains("location", NbtType.STRING))
                location = new ResourceLocation(tag.getString("location"));
            return constructor.construct(input, result, Optional.ofNullable(location));
        }

        @FunctionalInterface
        public interface Constructor
        {
            ChoppingBoardDisplay construct(List<EntryIngredient> input, List<EntryIngredient> result, Optional<ResourceLocation> location);
        }
    }
}
