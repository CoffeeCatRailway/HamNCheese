package io.github.coffeecatrailway.hamncheese.mixins;

import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2022
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;updateEmptyCacheFlag()V")})
    public void overrideConstructor(ItemLike itemLike, int count, CallbackInfo callback)
    {
        this.updateSandwichTags((ItemStack) (Object) this);
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;updateEmptyCacheFlag()V")})
    public void overrideConstructor(CompoundTag compoundTag, CallbackInfo callback)
    {
        this.updateSandwichTags((ItemStack) (Object) this);
    }

    private void updateSandwichTags(ItemStack stack)
    {
        // This can be done in forge using `initCapabilities`
        if (stack.getItem() instanceof AbstractSandwichItem)
        {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains(AbstractSandwichItem.TAG_INGREDIENTS))
                tag.put(AbstractSandwichItem.TAG_INGREDIENTS, new ListTag());

            if (((AbstractSandwichItem) stack.getItem()).sandwichProperties.canBeToasted() && !tag.contains(AbstractSandwichItem.TAG_TOASTED, NbtConstants.BYTE))
                tag.putBoolean(AbstractSandwichItem.TAG_TOASTED, false);
        }
    }
}
