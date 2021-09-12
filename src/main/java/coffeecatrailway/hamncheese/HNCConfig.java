package coffeecatrailway.hamncheese;

import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 21/10/2020
 */
public class HNCConfig
{
    public static Server SERVER;
    private static ForgeConfigSpec SERVER_SPEC;

    private static final String CONFIG = "config." + HNCMod.MOD_ID + ".";
    private static final Logger LOGGER = HNCMod.getLogger("Config");

    public static class Server
    {
        public ForgeConfigSpec.DoubleValue crackedEggSpawnChance;
        public ForgeConfigSpec.IntValue maxSandwichIngredientCraftCount;

        public ForgeConfigSpec.DoubleValue cookedFoodModifier;

        public ForgeConfigSpec.IntValue pizzaOvenCookTimeTotal;
        public ForgeConfigSpec.IntValue grillCookTimeTotal;

        public ForgeConfigSpec.BooleanValue mapleSapStuck;

        public ForgeConfigSpec.BooleanValue generateWildPineapples;
        public ForgeConfigSpec.IntValue chanceWildPineapples;

        public ForgeConfigSpec.BooleanValue generateWildTomatoes;
        public ForgeConfigSpec.IntValue chanceWildTomatoes;

        public ForgeConfigSpec.BooleanValue generateWildCorn;
        public ForgeConfigSpec.IntValue chanceWildCorn;

        public ForgeConfigSpec.IntValue mouseSpawnWeight;
        public ForgeConfigSpec.IntValue mouseMinCount;
        public ForgeConfigSpec.IntValue mouseMaxCount;
        public ForgeConfigSpec.ConfigValue<List<? extends String>> biomeCategoryWhitelist;

        public ForgeConfigSpec.BooleanValue generateVillageRestaurants;
        public ForgeConfigSpec.IntValue plainsRestaurantWeight;
        public ForgeConfigSpec.IntValue snowyRestaurantWeight;
        public ForgeConfigSpec.IntValue savannaRestaurantWeight;
        public ForgeConfigSpec.IntValue desertRestaurantWeight;
        public ForgeConfigSpec.IntValue taigaRestaurantWeight;

        public ForgeConfigSpec.BooleanValue allowButcherTrades;
        public ForgeConfigSpec.BooleanValue allowFarmerTrades;

        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Server Configurable Settings").push("item");
            this.crackedEggSpawnChance = builder.comment("The chance of a cracked egg dropping from a thrown egg").translation(CONFIG + "item.crackedEggSpawnChance")
                    .defineInRange("crackedEggSpawnChance", .25d, 0d, 1d);
            this.maxSandwichIngredientCraftCount = builder.comment("The amount of foods that can be crafted into a sandwich").translation(CONFIG + "item.maxSandwichIngredientCraftCount")
                    .defineInRange("maxSandwichIngredientCraftCount", 8, 1, 8);

            this.cookedFoodModifier = builder.comment("The amount of how much the saturation changes when grilled/cooked").translation(CONFIG + "item.cookedFoodModifier")
                    .defineInRange("cookedFoodModifier", 1.5d, 0.5d, 10d);
            builder.pop();

            builder.push("block");
            this.pizzaOvenCookTimeTotal = builder.comment("Total cook time for the Pizza Oven").translation(CONFIG + "block.pizzaOvenCookTimeTotal")
                    .defineInRange("pizzaOvenCookTimeTotal", 200, 1, Integer.MAX_VALUE);
            this.grillCookTimeTotal = builder.comment("Total cook time for the Grill").translation(CONFIG + "block.grillCookTimeTotal")
                    .defineInRange("grillCookTimeTotal", 200, 1, Integer.MAX_VALUE);

            this.mapleSapStuck = builder.comment("True if you want to be players stuck in maple sap").translation(CONFIG + "block.mapleSapStuck")
                    .define("mapleSapStuck", true);
            builder.pop();

            builder.push(Lists.newArrayList("generation", "crops", "wildPineapples"));
            this.generateWildPineapples = builder.comment("Generate pineapples in biomes with a temperature of .5 to 1").translation(CONFIG + "generation.crops.wildPineapples.generateWildPineapples")
                    .define("generateWildPineapples", true);
            this.chanceWildPineapples = builder.comment("Pineapple generation chance").translation(CONFIG + "generation.wildPineapples.chanceWildPineapples")
                    .defineInRange("chanceWildPineapples", 4, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wildTomatoes");
            this.generateWildTomatoes = builder.comment("Generate tomatoes in biomes with a temperature of .5 to 1").translation(CONFIG + "generation.crops.wildTomatoes.generateWildTomatoes")
                    .define("generateWildTomatoes", true);
            this.chanceWildTomatoes = builder.comment("Tomato generation chance").translation(CONFIG + "generation.wildTomatoes.chanceWildTomatoes")
                    .defineInRange("chanceWildTomatoes", 8, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wildCorn");
            this.generateWildCorn = builder.comment("Generate corn plants in biomes with a temperature of .5 to 1").translation(CONFIG + "generation.crops.wildCorn.generateWildCorn")
                    .define("generateWildCorn", true);
            this.chanceWildCorn = builder.comment("Corn plant generation chance").translation(CONFIG + "generation.wildCorn.chanceWildCorn")
                    .defineInRange("chanceWildCorn", 8, 0, Integer.MAX_VALUE);
            builder.pop(2);

            builder.push("mouse");
            this.mouseSpawnWeight = builder.comment("The weight of mice spawning").translation(CONFIG + "generation.mouse.mouseSpawnWeight")
                    .defineInRange("mouseSpawnWeight", 10, 1, Integer.MAX_VALUE);
            this.mouseMinCount = builder.comment("The minimum amount of mice that can spawn").translation(CONFIG + "generation.mouse.mouseMinCount")
                    .defineInRange("mouseMinCount", 2, 0, Integer.MAX_VALUE);
            this.mouseMaxCount = builder.comment("The maximum amount of mice that can spawn").translation(CONFIG + "generation.mouse.mouseMaxCount")
                    .defineInRange("mouseMaxCount", 4, 0, Integer.MAX_VALUE);
            List<String> categories = Lists.newArrayList(Biome.Category.EXTREME_HILLS, Biome.Category.FOREST, Biome.Category.MUSHROOM, Biome.Category.JUNGLE, Biome.Category.PLAINS).stream().map(Biome.Category::getName).collect(Collectors.toList());
            this.biomeCategoryWhitelist = builder.comment("What biome categories mise can spawn in").translation(CONFIG + "generation.mouse.biomeCategoryWhitelist")
                    .defineList("biomeCategoryWhitelist", categories, obj -> Arrays.stream(Biome.Category.values()).map(Biome.Category::getName).anyMatch(cat -> cat.equals(obj)));
            builder.pop();

            builder.push("village");
            this.generateVillageRestaurants = builder.comment("Whether or not the restaurant can generate in villages").translation(CONFIG + "generation.village.generateVillageRestaurants")
                    .define("generateVillageRestaurants", true);
            this.plainsRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a plains village").translation(CONFIG + "generation.village.plainsRestaurantWeight")
                    .defineInRange("plainsRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.snowyRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a snowy village").translation(CONFIG + "generation.village.snowyRestaurantWeight")
                    .defineInRange("snowyRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.savannaRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a savanna village").translation(CONFIG + "generation.village.savannaRestaurantWeight")
                    .defineInRange("savannaRestaurantWeight", 100, 1, Integer.MAX_VALUE);
            this.desertRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a desert village").translation(CONFIG + "generation.village.desertRestaurantWeight")
                    .defineInRange("desertRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.taigaRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a taiga village").translation(CONFIG + "generation.village.taigaRestaurantWeight")
                    .defineInRange("taigaRestaurantWeight", 8, 1, Integer.MAX_VALUE);

            builder.push("trading");
            this.allowButcherTrades = builder.comment("Allow butchers to trade emeralds for cooking tools").translation(CONFIG + "generation.villagers.trading.allowButcherTrades")
                    .define("allowButcherTrades", true);
            this.allowFarmerTrades = builder.comment("Allow farmers to trade emeralds for foods").translation(CONFIG + "generation.villagers.trading.allowFarmerTrades")
                    .define("allowFarmerTrades", true);
            builder.pop(3);
        }

        public boolean canSpawnMouse()
        {
            return this.mouseMaxCount.get() != 0 && this.mouseMinCount.get() <= this.mouseMaxCount.get();
        }

        public static void init(ModLoadingContext context)
        {
            if (SERVER_SPEC == null)
            {
                Pair<Server, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(HNCConfig.Server::new);
                SERVER_SPEC = server.getRight();
                SERVER = server.getLeft();
                LOGGER.info("Register config(s)");
            }
            context.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        }
    }
}
