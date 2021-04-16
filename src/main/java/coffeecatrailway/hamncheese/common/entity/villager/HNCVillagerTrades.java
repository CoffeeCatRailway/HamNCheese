package coffeecatrailway.hamncheese.common.entity.villager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 16/04/2021
 */
public class HNCVillagerTrades
{
    public static class EmeraldForItemsTrade implements VillagerTrades.ITrade {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider item, int cost, int maxUses, int villagerXp) {
            this.item = item.asItem();
            this.cost = cost;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = .05f;
        }

        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade {
        private final Item item;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(IItemProvider item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this.item = item.asItem();
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = .05f;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.item, this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }
}
