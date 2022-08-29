package io.github.coffeecatrailway.hamncheese.mixins;

import io.github.coffeecatrailway.hamncheese.common.item.HasInitialNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author CoffeeCatRailway
 * Created: 27/08/2022
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Shadow public abstract CompoundTag getOrCreateTag();

    @Shadow public abstract Item getItem();

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = {@At(value = "TAIL")})
    public void overrideConstructor(ItemLike itemLike, int count, CallbackInfo callback)
    {
        if (itemLike instanceof HasInitialNbt)
            ((HasInitialNbt) itemLike).saveInitialNbt((ItemStack) (Object) this, this.getOrCreateTag());
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = {@At(value = "TAIL")})
    public void overrideConstructor(CompoundTag compoundTag, CallbackInfo callback)
    {
        if (this.getItem() instanceof HasInitialNbt)
            ((HasInitialNbt) this.getItem()).saveInitialNbt((ItemStack) (Object) this, this.getOrCreateTag());
    }
}
