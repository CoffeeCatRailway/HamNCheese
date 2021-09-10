package coffeecatrailway.hamncheese.registry;

import coffeecatrailway.hamncheese.HNCMod;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

/**
 * @author CoffeeCatRailway
 * Created: 10/09/2021
 */
public class HNCFluids
{
    private static final Logger LOGGER = HNCMod.getLogger("Fluids");
    protected static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, HNCMod.MOD_ID);

    public static final RegistryObject<Fluid> MAPLE_SAP = FLUIDS.register("maple_sap", () -> new ForgeFlowingFluid.Source(HNCFluids.SAP_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MAPLE_SAP_FLOWING = FLUIDS.register("maple_sap_flowing", () -> new ForgeFlowingFluid.Flowing(HNCFluids.SAP_PROPERTIES));

    private static final ForgeFlowingFluid.Properties SAP_PROPERTIES = new ForgeFlowingFluid.Properties(MAPLE_SAP, MAPLE_SAP_FLOWING,
            FluidAttributes.builder(HNCMod.getLocation("block/maple_sap_still"), HNCMod.getLocation("block/maple_sap_flow")).luminosity(15).density(500).viscosity(1000).overlay(HNCMod.getLocation("block/maple_sap_overlay")).translationKey("fluid." + HNCMod.MOD_ID + ".maple_sap"))
            .block(HNCBlocks.MAPLE_SAP).bucket(HNCItems.MAPLE_SAP_BUCKET).tickRate(15).explosionResistance(100f).levelDecreasePerBlock(2);

    public static void load(IEventBus bus)
    {
        LOGGER.debug("Loaded");
        FLUIDS.register(bus);
    }
}
