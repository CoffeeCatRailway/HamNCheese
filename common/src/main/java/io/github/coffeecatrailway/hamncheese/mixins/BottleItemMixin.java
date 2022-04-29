package io.github.coffeecatrailway.hamncheese.mixins;

import io.github.coffeecatrailway.hamncheese.data.gen.HNCFluidTags;
import io.github.coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author CoffeeCatRailway
 * Created: 29/04/2022
 */
@Mixin(BottleItem.class)
public abstract class BottleItemMixin extends Item
{
    public BottleItemMixin(Properties properties)
    {
        super(properties);
    }

    @Shadow
    protected abstract ItemStack turnBottleIntoItem(ItemStack itemStack, Player player, ItemStack itemStack2);

    @Inject(method = "use", at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/item/BottleItem;getPlayerPOVHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/ClipContext$Fluid;)Lnet/minecraft/world/phys/BlockHitResult;")}, cancellable = true)
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callback)
    {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() == HitResult.Type.BLOCK)
        {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            if (!level.mayInteract(player, blockPos))
                callback.setReturnValue(InteractionResultHolder.pass(itemStack));

            if (level.getFluidState(blockPos).is(HNCFluidTags.MAPLE_SAP))
            {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1f, 1f);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                callback.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(itemStack, player, new ItemStack(HNCItems.MAPLE_SAP_BOTTLE.get())), level.isClientSide()));
            }
        }
    }
}
