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

        public ForgeConfigSpec.BooleanValue allowButcherTrades;
        public ForgeConfigSpec.BooleanValue allowFarmerTrades;

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
            builder.pop(2);

            builder.push("villagers");
            this.allowButcherTrades = builder.comment("Allow butchers to trade emeralds for cooking tools").translation(CONFIG + "villagers.allowButcherTrades")
                    .define("allowButcherTrades", true);
            this.allowFarmerTrades = builder.comment("Allow farmers to trade emeralds for foods").translation(CONFIG + "villagers.allowFarmerTrades")
                    .define("allowFarmerTrades", true);
            builder.pop();
        }
    }

    public static class Server
    {
        public ForgeConfigSpec.DoubleValue crackedEggSpawnChance;
        public ForgeConfigSpec.IntValue maxSandwichIngredientCraftCount;

        public ForgeConfigSpec.DoubleValue cookedFoodModifier;

        public ForgeConfigSpec.IntValue mouseSpawnWeight;
        public ForgeConfigSpec.IntValue mouseMinCount;
        public ForgeConfigSpec.IntValue mouseMaxCount;

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

            builder.push("generation").push("mouse");
            this.mouseSpawnWeight = builder.comment("The weight of mice spawning").translation(CONFIG + "item.mouseSpawnWeight")
                    .defineInRange("mouseSpawnWeight", 10, 1, Integer.MAX_VALUE);
            this.mouseMinCount = builder.comment("The minimum amount of mice that can spawn").translation(CONFIG + "item.mouseMinCount")
                    .defineInRange("mouseMinCount", 2, 0, Integer.MAX_VALUE);
            this.mouseMaxCount = builder.comment("The maximum amount of mice that can spawn").translation(CONFIG + "item.mouseMaxCount")
                    .defineInRange("mouseMaxCount", 4, 0, Integer.MAX_VALUE);
            builder.pop(2);
        }

        public boolean canSpawnMouse()
        {
            return this.mouseMaxCount.get() != 0 && this.mouseMinCount.get() <= this.mouseMaxCount.get();
        }
    }
}
