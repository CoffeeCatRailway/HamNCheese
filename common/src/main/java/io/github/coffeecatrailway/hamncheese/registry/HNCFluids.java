package io.github.coffeecatrailway.hamncheese.registry;

import gg.moonflower.pollen.api.block.PollinatedLiquidBlock;
import gg.moonflower.pollen.api.item.BucketItemBase;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedFluidRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.material.MapleSapFluid;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class HNCFluids
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PollinatedFluidRegistry FLUIDS = PollinatedRegistry.createFluid(HamNCheese.MOD_ID);

    public static final Supplier<FlowingFluid> MAPLE_SAP = FLUIDS.register("maple_sap", MapleSapFluid.Source::new); // TODO: Ask jackson/ocelot about swim crash
    public static final Supplier<FlowingFluid> MAPLE_SAP_FLOWING = FLUIDS.register("maple_sap_flowing", MapleSapFluid.Flowing::new);
    public static final Supplier<PollinatedLiquidBlock> MAPLE_SAP_BLOCK = HNCBlocks.registerWithItem("maple_sap", () -> new PollinatedLiquidBlock(MAPLE_SAP, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()), null);
    public static final Supplier<BucketItem> MAPLE_SAP_BUCKET = HNCItems.registerIdAsName("maple_sap_bucket", prop -> new BucketItemBase(MAPLE_SAP, prop.craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void load(Platform platform)
    {
        LOGGER.debug("Loaded");
        FLUIDS.register(platform);
    }
}