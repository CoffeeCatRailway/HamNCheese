package coffeecatrailway.hamncheese.common.entity.villager;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 16/04/2021
 */
public class HNCVillagerTrades
{
    public static class EmeraldForItemsTrade implements VillagerTrades.ITrade
    {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider item, int cost, int maxUses, int villagerXp)
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

    public static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade
    {
        private final Item item;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(IItemProvider item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
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

    public static class SandwichForEmeralds implements VillagerTrades.ITrade
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
                sandwichStack.setHoverName(new StringTextComponent(this.name));
            this.ingredients.forEach(item -> AbstractSandwichItem.addIngredient(sandwichStack, new ItemStack(item)));
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), sandwichStack, this.maxUses, this.villagerXp, .05f);
        }
    }
}
