package io.github.coffeecatrailway.hamncheese;

import com.google.common.collect.Lists;
import gg.moonflower.pollen.api.config.PollinatedConfigBuilder;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 22/03/2022
 */
public class HNCConfig
{
    public static class Server
    {
        private static final Set<String> CATEGORY_NAMES = Arrays.stream(Biome.BiomeCategory.values()).map(Biome.BiomeCategory::getName).collect(Collectors.toSet());
        private static final String ALLOWED_CATEGORIES_COMMENT;

        static {
            int i = 0;
            StringBuilder builder = new StringBuilder().append("Allowed categories: [");
            for (String name : CATEGORY_NAMES)
            {
                builder.append(name);
                if (i < CATEGORY_NAMES.size())
                    builder.append(", ");
                i++;
            }
            ALLOWED_CATEGORIES_COMMENT = builder.append("]").toString();
        }

        // Items
        public final PollinatedConfigBuilder.ConfigValue<Double> crackedEggSpawnChance;
        public final PollinatedConfigBuilder.ConfigValue<Integer> maxSandwichIngredientCraftCount;

        public final PollinatedConfigBuilder.ConfigValue<Double> cookedFoodModifier;

        // Blocks
        public final PollinatedConfigBuilder.ConfigValue<Integer> pizzaOvenCookTimeTotal;
        public final PollinatedConfigBuilder.ConfigValue<Integer> grillCookTimeTotal;

        public final PollinatedConfigBuilder.ConfigValue<Boolean> mapleSapStuck;

        public final PollinatedConfigBuilder.ConfigValue<Double> blockOfCheeseChance;

        // Blocks - Dispenser
        public final PollinatedConfigBuilder.ConfigValue<Boolean> dispenseTomatoSauce;

        // Generation
        public final PollinatedConfigBuilder.ConfigValue<Double> mapleTreeWeight;

        // Generation - Crops - Pineapples
        public final PollinatedConfigBuilder.ConfigValue<Boolean> wildPineapplesGenerate;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildPineapplesChance;
        public final PollinatedConfigBuilder.ConfigValue<Double> wildPineapplesProbability;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildPineapplesSpread;
        public final PollinatedConfigBuilder.ConfigValue<List<? extends Biome.BiomeCategory>> wildPineapplesCategoryWhitelist;

        // Crops - Tomatoes
        public final PollinatedConfigBuilder.ConfigValue<Boolean> wildTomatoesGenerate;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildTomatoesChance;
        public final PollinatedConfigBuilder.ConfigValue<Double> wildTomatoesProbability;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildTomatoesSpread;
        public final PollinatedConfigBuilder.ConfigValue<List<? extends Biome.BiomeCategory>> wildTomatoesCategoryWhitelist;

        // Crops - Corn
        public final PollinatedConfigBuilder.ConfigValue<Boolean> wildCornGenerate;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildCornChance;
        public final PollinatedConfigBuilder.ConfigValue<Double> wildCornProbability;
        public final PollinatedConfigBuilder.ConfigValue<Integer> wildCornSpread;
        public final PollinatedConfigBuilder.ConfigValue<List<? extends Biome.BiomeCategory>> wildCornCategoryWhitelist;

        // Generation - Mouse
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseSpawnWeight;
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseMinCount;
        public final PollinatedConfigBuilder.ConfigValue<Integer> mouseMaxCount;
        public final PollinatedConfigBuilder.ConfigValue<List<? extends Biome.BiomeCategory>> mouseCategoryWhitelist;

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

            this.blockOfCheeseChance = builder.comment("Chance that a \"Block Of Cheese\" will turn into blue cheese", "Note: Larger the value less the chance")
                    .defineInRange("blockOfCheeseChance", .9f, 0f, 1f);

            builder.push("dispenser");
            this.dispenseTomatoSauce = builder.comment("Whether or not tomato sauce is dispensed along with the pizza")
                    .define("dispenseTomatoSauce", false);
            builder.pop(2);

            builder.push("generation");
            this.mapleTreeWeight = builder.comment("Changes require a server restart!", "Maple tree spawn weight. Note: Maple trees won't generate if set to '0'")
                    .defineInRange("mapleTreeWeight", .02d, 0, 1f);

            builder.push(Lists.newArrayList("crops", "wildPineapples"));
            this.wildPineapplesGenerate = builder.comment("Whether pineapples are enabled or not")
                    .define("wildPineapplesGenerate", true);
            this.wildPineapplesChance = builder.comment("Patch spawn once every #")
                    .defineInRange("wildPineapplesChance", 24, 1, Integer.MAX_VALUE);
            this.wildPineapplesProbability = builder.comment("Probability of a plant spawning in a patch")
                    .defineInRange("wildPineapplesProbability", .25f, 0f, 1f);
            this.wildPineapplesSpread = builder.comment("Max spread of a patch")
                    .defineInRange("wildPineapplesSpread", 4, 1, 10);
            this.wildPineapplesCategoryWhitelist = this.defineCategoryWhitelist(builder, "wildPineapplesCategoryWhitelist", "What types of biomes pineapples will generate in", Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.JUNGLE);
            builder.pop();

            builder.push("wildTomatoes");
            this.wildTomatoesGenerate = builder.comment("Whether tomatoes are enabled or not")
                    .define("wildTomatoesGenerate", true);
            this.wildTomatoesChance = builder.comment("Patch spawn once every #")
                    .defineInRange("wildTomatoesChance", 24, 1, Integer.MAX_VALUE);
            this.wildTomatoesProbability = builder.comment("Probability of a plant spawning in a patch")
                    .defineInRange("wildTomatoesProbability", .225f, 0f, 1f);
            this.wildTomatoesSpread = builder.comment("Max spread of a patch")
                    .defineInRange("wildTomatoesSpread", 3, 1, 10);
            this.wildTomatoesCategoryWhitelist = this.defineCategoryWhitelist(builder, "wildTomatoesCategoryWhitelist", "What types of biomes tomatoes will generate in", Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.FOREST);
            builder.pop();

            builder.push("wildCorn");
            this.wildCornGenerate = builder.comment("Whether corn are enabled or not")
                    .define("wildCornGenerate", true);
            this.wildCornChance = builder.comment("Patch spawn once every #")
                    .defineInRange("wildCornChance", 24, 1, Integer.MAX_VALUE);
            this.wildCornProbability = builder.comment("Probability of a plant spawning in a patch")
                    .defineInRange("wildCornProbability", .225f, 0f, 1f);
            this.wildCornSpread = builder.comment("Max spread of a patch")
                    .defineInRange("wildCornSpread", 3, 1, 10);
            this.wildCornCategoryWhitelist = this.defineCategoryWhitelist(builder, "wildCornCategoryWhitelist", "What types of biomes corn will generate in", Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.FOREST);
            builder.pop(2);

            builder.push("mouse");
            this.mouseSpawnWeight = builder.comment("The weight of mice spawning")
                    .defineInRange("mouseSpawnWeight", 10, 1, Integer.MAX_VALUE);
            this.mouseMinCount = builder.comment("NOTE: If either of the 'count' values are 0 or lower mice will not spawn!", "The minimum amount of mice that can spawn")
                    .defineInRange("mouseMinCount", 2, 0, Integer.MAX_VALUE);
            this.mouseMaxCount = builder.comment("The maximum amount of mice that can spawn")
                    .defineInRange("mouseMaxCount", 4, 0, Integer.MAX_VALUE);
            this.mouseCategoryWhitelist = this.defineCategoryWhitelist(builder, "mouseCategoryWhitelist", "What types of biomes mise can spawn in", Biome.BiomeCategory.EXTREME_HILLS, Biome.BiomeCategory.FOREST, Biome.BiomeCategory.MUSHROOM, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.PLAINS);
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

        private PollinatedConfigBuilder.ConfigValue<List<? extends Biome.BiomeCategory>> defineCategoryWhitelist(PollinatedConfigBuilder builder, String path, String comment, Biome.BiomeCategory... categories)
        {
            List<Biome.BiomeCategory> categoryList = Arrays.stream(categories).toList();
            return builder.comment("Changes require a server restart!", ALLOWED_CATEGORIES_COMMENT, comment).defineList(path, categoryList, category -> CATEGORY_NAMES.contains(category.toString().toLowerCase(Locale.ROOT)));
        }
    }
}
