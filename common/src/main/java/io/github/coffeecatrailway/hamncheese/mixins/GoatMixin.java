package io.github.coffeecatrailway.hamncheese.mixins;

import io.github.coffeecatrailway.hamncheese.registry.HNCFluids;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * @author CoffeeCatRailway
 * Created: 30/05/2022
 */
@Mixin(Goat.class)
public class GoatMixin
{
    @ModifyArg(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemUtils;createFilledResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"), index = 2)
    private ItemStack getMilkBucket(ItemStack stack)
    {
        return HNCFluids.GOAT_MILK_BUCKET.get().getDefaultInstance();
    }
}
