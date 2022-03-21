//package io.github.coffeecatrailway.plus;
//
//import gg.moonflower.pollen.api.config.PollinatedConfigBuilder;
//
///**
// * @author CoffeeCatRailway
// * Created: 2/01/2022
// */
//public class PlusConfig
//{
//    public static class Server
//    {
//        /// Items ///
//        public final PollinatedConfigBuilder.ConfigValue<Double> warmthAmuletSize;
//
//        // Items - Enchantments
//        public final PollinatedConfigBuilder.ConfigValue<Double> heatWalkerLevel;
//        public final PollinatedConfigBuilder.ConfigValue<Double> frostWalkerLevel;
//
//        /// Blocks ///
//        public final PollinatedConfigBuilder.ConfigValue<Double> sawBladeDamage;
//
//        /// Entities ///
//        public final PollinatedConfigBuilder.ConfigValue<Integer> turtleScuteDrop;
//
//        public Server(PollinatedConfigBuilder builder)
//        {
//            builder.push("items");
//            this.warmthAmuletSize = builder.comment("Default area effect size for \"Warmth Amulet\".").defineInRange("warmthAmuletSize", 2d, 1d, 16d);
//
//            builder.push("enchantments");
//            this.heatWalkerLevel = builder.comment("Default heat walker enchant level. min(16, level + modifier)").defineInRange("heatWalkerLevel", 2d, 1d, 16d);
//            this.frostWalkerLevel = builder.comment("Default frost walker enchant level. min(16, level + modifier)").defineInRange("frostWalkerLevel", 2d, 1d, 16d);
//            builder.pop(2);
//
//            builder.push("blocks");
//            this.sawBladeDamage = builder.comment("How much damage is dealt to entities standing on a stonecutter/saw bench").defineInRange("sawBladeDamage", 2d, 1d, Double.MAX_VALUE);
//            builder.pop();
//
//            builder.push("entities");
//            this.turtleScuteDrop = builder.comment("How many scute(s) are dropped from turtles becoming adults, Vanilla value: 1").defineInRange("turtleScuteDrop", 2, 1, Integer.MAX_VALUE);
//            builder.pop();
//        }
//    }
//}
