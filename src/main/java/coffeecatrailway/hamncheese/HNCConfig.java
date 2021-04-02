package coffeecatrailway.hamncheese;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 21/10/2020
 */
public class HNCConfig
{
    private static final String CONFIG = "config." + HNCMod.MOD_ID + ".";

    public static class Client
    {
        public Client(ForgeConfigSpec.Builder builder)
        {
//            builder.comment("Client Configurable Settings");
//            builder.pop();
        }
    }

    public static class Common
    {
        public Common(ForgeConfigSpec.Builder builder)
        {
//            builder.comment("Common Configurable Settings");
//            builder.pop();
        }
    }

    public static class Server
    {
        public ForgeConfigSpec.DoubleValue crackedEggSpawnChance;
        public ForgeConfigSpec.IntValue maxSandwichIngredientCraftCount;

        public ForgeConfigSpec.DoubleValue cookedFoodModifier;

        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Server Configurable Settings").push("item");
            this.crackedEggSpawnChance = builder.comment("The chance of a cracked egg dropping from a thrown egg").translation(CONFIG + "item.crackedEggSpawnChance")
                    .defineInRange("crackedEggSpawnChance", .25d, 0d, 1d);
            this.maxSandwichIngredientCraftCount = builder.comment("The amount of foods that can be crafted into a sandwich").translation(CONFIG + "item.maxSandwichIngredientCraftCount")
                    .defineInRange("maxSandwichIngredientCraftCount", 8, 1, 8);

            builder.push("modifier");
            this.cookedFoodModifier = builder.comment("The amount of how much the saturation changes when grilled/cooked").translation(CONFIG + "item.cookedFoodModifier")
                    .defineInRange("cookedFoodModifier", 1.5d, 0.5d, 10d);

            builder.pop(2);
        }
    }
}
