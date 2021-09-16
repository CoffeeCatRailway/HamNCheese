package coffeecatrailway.hamncheese.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 12/09/2021
 */
@Mixin(DispenserBlock.class)
public interface AccessorDispenserBlock
{
    @Accessor("DISPENSER_REGISTRY")
    static Map<Item, IDispenseItemBehavior> getDispenseBehaviorRegistry()
    {
        throw new IllegalStateException();
    }
}
