package io.github.coffeecatrailway.hamncheese.common.entity.villager;

import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 16/04/2021
 */
public class HNCVillagerTrades
{
    public static class EmeraldForItemsTrade implements VillagerTrades.ItemListing
    {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(ItemLike item, int cost, int maxUses, int villagerXp)
        {
            this.item = item.asItem();
            this.cost = cost;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = .05f;
        }

        public MerchantOffer getOffer(Entity entity, Random random)
        {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class ItemsForEmeraldsTrade implements VillagerTrades.ItemListing
    {
        private final Item item;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(ItemLike item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
        {
            this.item = item.asItem();
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = .05f;
        }

        public MerchantOffer getOffer(Entity entity, Random random)
        {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.item, this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class SandwichForEmeralds implements VillagerTrades.ItemListing
    {
        private final Supplier<? extends AbstractSandwichItem> sandwich;
        private final List<Item> ingredients;
        private final boolean toasted;
        @Nullable
        private final String name;

        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;

        public SandwichForEmeralds(Supplier<? extends AbstractSandwichItem> sandwich, List<Item> ingredients, boolean toasted, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
        {
            this(sandwich, ingredients, toasted, null, emeraldCost, numberOfItems, maxUses, villagerXp);
        }

        public SandwichForEmeralds(Supplier<? extends AbstractSandwichItem> sandwich, List<Item> ingredients, boolean toasted, @Nullable String name, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
        {
            this.sandwich = sandwich;
            this.ingredients = ingredients;
            this.toasted = toasted;
            this.name = name;
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random)
        {
            ItemStack sandwichStack = new ItemStack(this.sandwich.get(), this.numberOfItems);
            if (this.name != null)
                sandwichStack.setHoverName(new TextComponent(this.name));
            this.ingredients.forEach(item -> AbstractSandwichItem.addIngredient(sandwichStack, new ItemStack(item)).copy());
            sandwichStack.getOrCreateTag().putBoolean(AbstractSandwichItem.TAG_TOASTED, this.toasted);
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), sandwichStack.copy(), this.maxUses, this.villagerXp, .05f);
        }
    }
}
