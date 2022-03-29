package io.github.coffeecatrailway.hamncheese.mixins;

import io.github.coffeecatrailway.hamncheese.common.item.SelfRemainder;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2022
 */
@Mixin(Item.class)
public abstract class ItemMixin
{
    @Mutable
    @Shadow @Final private Item craftingRemainingItem;

    @Inject(method = "<init>", at = {@At("TAIL")})
    public void overrideConstructor(Item.Properties properties, CallbackInfo callback)
    {
        Item instance = (Item) (Object) this;
        if (instance instanceof SelfRemainder)
            this.craftingRemainingItem = instance; // TODO: Make item lose durability
    }
}
