package io.github.coffeecatrailway.hamncheese.common.item.forge;

import io.github.coffeecatrailway.hamncheese.common.item.CraftingToolItem;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

/**
 * @author CoffeeCatRailway
 * Created: 30/03/2022
 */
public class CraftingToolItemForge extends CraftingToolItem
{
    public CraftingToolItemForge(float attackModifier, double attackSpeed, Tier tier, Tag<Block> mineableBlocks, Properties properties)
    {
        super(attackModifier, attackSpeed, tier, mineableBlocks, properties);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack stack = itemStack.copy();
        stack.setDamageValue(stack.getDamageValue() + 1);
        if (stack.getDamageValue() > stack.getMaxDamage())
            return ItemStack.EMPTY;
        return stack;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}
