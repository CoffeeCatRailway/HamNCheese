package io.github.coffeecatrailway.hamncheese.forge;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.registry.HNCFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author CoffeeCatRailway
 * Created: 19/04/2022
 */
@Mod.EventBusSubscriber(modid = HamNCheese.MOD_ID)
public class CommonEvents
{
    public static void init(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (event.getCategory() == Biome.BiomeCategory.PLAINS || event.getCategory() == Biome.BiomeCategory.RIVER)
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.MAPLE_TREE_PLACEMENT.get());

        if (HamNCheese.CONFIG_SERVER.wildPineapplesGenerate.get() && HamNCheese.CONFIG_SERVER.wildPineapplesCategoryWhitelist.get().contains(event.getCategory()))
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HNCFeatures.Configured.WILD_PINEAPPLE_PLACEMENT.get());
    }
}
