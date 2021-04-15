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
        public ForgeConfigSpec.BooleanValue generateWildPineapples;
        public ForgeConfigSpec.IntValue chanceWildPineapples;

        public ForgeConfigSpec.BooleanValue generateWildTomatoes;
        public ForgeConfigSpec.IntValue chanceWildTomatoes;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common Configurable Settings").push("generation");

            builder.push("wildPineapples");
            this.generateWildPineapples = builder.comment("Generate pineapples in biomes with a temperature of .5 to 1").translation(CONFIG + "generation.wildPineapples.generateWildPineapples")
                    .define("generateWildPineapples", true);
            this.chanceWildPineapples = builder.comment("Pineapple generation chance").translation(CONFIG + "generation.wildPineapples.chanceWildPineapples")
                    .defineInRange("chanceWildPineapples", 10, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wildTomatoes");
            this.generateWildTomatoes = builder.comment("Generate tomatoes in biomes with a temperature of .5 to 1").translation(CONFIG + "generation.wildTomatoes.generateWildTomatoes")
                    .define("generateWildTomatoes", true);
            this.chanceWildTomatoes = builder.comment("Tomato generation chance").translation(CONFIG + "generation.wildTomatoes.chanceWildTomatoes")
                    .defineInRange("chanceWildTomatoes", 10, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.pop();
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
