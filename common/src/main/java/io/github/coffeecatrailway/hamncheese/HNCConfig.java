package io.github.coffeecatrailway.hamncheese;

import com.google.common.collect.Lists;
import gg.moonflower.pollen.api.config.PollinatedConfigBuilder;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 22/03/2022
 */
public class HNCConfig
{
    public static class Server
    {
        // Items
        public final PollinatedConfigBuilder.ConfigValue<Double> crackedEggSpawnChance;
        public final PollinatedConfigBuilder.ConfigValue<Integer> maxSandwichIngredientCraftCount;

        public final PollinatedConfigBuilder.ConfigValue<Double> cookedFoodModifier;

        // Blocks
        public final PollinatedConfigBuilder.ConfigValue<Integer> pizzaOvenCookTimeTotal;
        public final PollinatedConfigBuilder.ConfigValue<Integer> grillCookTimeTotal;

        public final PollinatedConfigBuilder.ConfigValue<Boolean> mapleSapStuck;

        // Blocks - Dispenser
        public final PollinatedConfigBuilder.ConfigValue<Boolean> dispenseTomatoSauce;

        // Generation
        public final PollinatedConfigBuilder.ConfigValue<Double> mapleTreeWeight;

        // Generation - Crops - Pineapples
        public final PollinatedConfigBuilder.ConfigValue<Boolean> generateWildPineapples;
        public final PollinatedConfigBuilder.ConfigValue<Integer> chanceWildPineapples;

        // Crops - Tomatoes
        public final PollinatedConfigBuilder.ConfigValue<Boolean> generateWildTomatoes;
        public final PollinatedConfigBuilder.ConfigValue<Integer> chanceWildTomatoes;

        // Crops - Corn
        public final PollinatedConfigBuilder.ConfigValue<Boolean> generateWildCorn;
        public final PollinatedConfigBuilder.ConfigValue<Integer> chanceWildCorn;

        // Generation - Mouse
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseSpawnWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseMinCount;
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseMaxCount;
        public final PollinatedConfigBuilder.ConfigValue<List<? extends String>> biomeCategoryWhitelist;

        // Generation - Villagers
        public final PollinatedConfigBuilder.ConfigValue<Boolean> generateVillageRestaurants;
        public final PollinatedConfigBuilder.ConfigValue<Integer> plainsRestaurantWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> snowyRestaurantWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> savannaRestaurantWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> desertRestaurantWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> taigaRestaurantWeight;

        // Villagers - Trading
        public final PollinatedConfigBuilder.ConfigValue<Boolean> allowButcherTrades;
        public final PollinatedConfigBuilder.ConfigValue<Boolean> allowFarmerTrades;

        public Server(PollinatedConfigBuilder builder)
        {
            builder.comment("Server Configurable Settings").push("item");
            this.crackedEggSpawnChance = builder.comment("The chance of a cracked egg dropping from a thrown egg")
                    .defineInRange("crackedEggSpawnChance", .25d, 0d, 1d);
            this.maxSandwichIngredientCraftCount = builder.comment("The amount of foods that can be crafted into a sandwich")
                    .defineInRange("maxSandwichIngredientCraftCount", 8, 1, 8);

            this.cookedFoodModifier = builder.comment("The amount of how much the saturation changes when grilled/cooked")
                    .defineInRange("cookedFoodModifier", 1.5d, 0.5d, 10d);
            builder.pop();

            builder.push("block");
            this.pizzaOvenCookTimeTotal = builder.comment("Total cook time for the Pizza Oven")
                    .defineInRange("pizzaOvenCookTimeTotal", 200, 1, Integer.MAX_VALUE);
            this.grillCookTimeTotal = builder.comment("Total cook time for the Grill")
                    .defineInRange("grillCookTimeTotal", 200, 1, Integer.MAX_VALUE);

            this.mapleSapStuck = builder.comment("True if you want to be players stuck in maple sap")
                    .define("mapleSapStuck", true);

            builder.push("dispenser");
            this.dispenseTomatoSauce = builder.comment("Whether or not tomato sauce is dispensed along with the pizza")
                    .define("dispenseTomatoSauce", false);
            builder.pop(2);

            builder.push("generation");
            this.mapleTreeWeight = builder.comment("Maple tree spawn weight (SERVER RESTART REQUIRED)")
                    .defineInRange("mapleTreeWeight", .02d, 0, Float.MAX_VALUE);

            builder.push(Lists.newArrayList("crops", "wildPineapples"));
            this.generateWildPineapples = builder.comment("Generate pineapples in biomes with a temperature of .5 to 1")
                    .define("generateWildPineapples", true);
            this.chanceWildPineapples = builder.comment("Pineapple generation chance")
                    .defineInRange("chanceWildPineapples", 4, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wildTomatoes");
            this.generateWildTomatoes = builder.comment("Generate tomatoes in biomes with a temperature of .5 to 1")
                    .define("generateWildTomatoes", true);
            this.chanceWildTomatoes = builder.comment("Tomato generation chance")
                    .defineInRange("chanceWildTomatoes", 8, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wildCorn");
            this.generateWildCorn = builder.comment("Generate corn plants in biomes with a temperature of .5 to 1")
                    .define("generateWildCorn", true);
            this.chanceWildCorn = builder.comment("Corn plant generation chance")
                    .defineInRange("chanceWildCorn", 8, 0, Integer.MAX_VALUE);
            builder.pop(2);

            builder.push("mouse");
            this.mouseSpawnWeight = builder.comment("The weight of mice spawning")
                    .defineInRange("mouseSpawnWeight", 10, 1, Integer.MAX_VALUE);
            this.mouseMinCount = builder.comment("NOTE: If either of the 'count' values are 0 or lower mice will not spawn!", "The minimum amount of mice that can spawn")
                    .defineInRange("mouseMinCount", 2, 0, Integer.MAX_VALUE);
            this.mouseMaxCount = builder.comment("The maximum amount of mice that can spawn")
                    .defineInRange("mouseMaxCount", 4, 0, Integer.MAX_VALUE);
            List<String> categories = Lists.newArrayList(Biome.BiomeCategory.EXTREME_HILLS, Biome.BiomeCategory.FOREST, Biome.BiomeCategory.MUSHROOM, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.PLAINS).stream().map(Biome.BiomeCategory::getName).collect(Collectors.toList());
            this.biomeCategoryWhitelist = builder.comment("What biome categories mise can spawn in")
                    .defineList("biomeCategoryWhitelist", categories, obj -> Arrays.stream(Biome.BiomeCategory.values()).map(Biome.BiomeCategory::getName).anyMatch(cat -> cat.equals(obj)));
            builder.pop();

            builder.push("village");
            this.generateVillageRestaurants = builder.comment("Whether or not the restaurant can generate in villages")
                    .define("generateVillageRestaurants", true);
            this.plainsRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a plains village")
                    .defineInRange("plainsRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.snowyRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a snowy village")
                    .defineInRange("snowyRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.savannaRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a savanna village")
                    .defineInRange("savannaRestaurantWeight", 100, 1, Integer.MAX_VALUE);
            this.desertRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a desert village")
                    .defineInRange("desertRestaurantWeight", 8, 1, Integer.MAX_VALUE);
            this.taigaRestaurantWeight = builder.comment("The weight for a restaurant to spawn in a taiga village")
                    .defineInRange("taigaRestaurantWeight", 8, 1, Integer.MAX_VALUE);

            builder.push("trading");
            this.allowButcherTrades = builder.comment("Allow butchers to trade emeralds for cooking tools")
                    .define("allowButcherTrades", true);
            this.allowFarmerTrades = builder.comment("Allow farmers to trade emeralds for foods")
                    .define("allowFarmerTrades", true);
            builder.pop(3);
        }

        public boolean canSpawnMouse()
        {
            return this.mouseMinCount.get() > 0 && this.mouseMaxCount.get() > 0 && this.mouseMaxCount.get() >= this.mouseMinCount.get();
        }
    }
}
