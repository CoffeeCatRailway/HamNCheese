package coffeecatrailway.hamncheese.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 14/02/2021
 */
public class GreenFoodItem extends Item
{
    public GreenFoodItem(Properties prop, Food food, int stackSize)
    {
        super(prop.food(food).stacksTo(stackSize));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("Yay to Dr. Seuss for his green eggs and ham!"));
        if (this.isDate(Calendar.MARCH, 2))
        {
            tooltip.add(new StringTextComponent("And a happy birthday to the great Dr. Seuss!"));
            tooltip.add(new StringTextComponent("Born 2 March 1904, Died 24 September 1991"));
        }
    }

    private boolean isDate(int month, int day) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int curentMonth = cal.get(Calendar.MONTH);
        int curentDay = cal.get(Calendar.DAY_OF_MONTH);
        boolean inRange = curentDay == day;

        return (curentMonth == month && inRange);
    }
}
