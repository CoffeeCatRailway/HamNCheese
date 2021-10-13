package coffeecatrailway.hamncheese.client.block.tile;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.CurdlerBlock;
import coffeecatrailway.hamncheese.common.tileentity.CurdlerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

/**
 * @author CoffeeCatRailway
 * Created: 13/10/2021
 */
public class CurdlerTileEntityRenderer extends TileEntityRenderer<CurdlerTileEntity>
{
    public static final ResourceLocation CRANK_LOCATION = HNCMod.getLocation("block/cheese_curdler_crank");
    private static IBakedModel CRANK_MODEL = null;

    public CurdlerTileEntityRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(CurdlerTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int combinedLight, int combinedOverlay)
    {
        BlockState state = tile.getBlockState();
        if (!tile.hasLevel() || state.getValue(CurdlerBlock.HALF) != DoubleBlockHalf.LOWER)
            return;
        if (CRANK_MODEL == null)
            CRANK_MODEL = Minecraft.getInstance().getModelManager().getModel(CRANK_LOCATION);
        World level = tile.getLevel();
        matrixStack.pushPose();
        matrixStack.translate(.5f, 0f, .5f);
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(tile.getCrankAngle()));
        matrixStack.translate(-.5f, 0f, -.5f);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(level, CRANK_MODEL, state, tile.getBlockPos(), matrixStack, typeBuffer.getBuffer(RenderType.solid()), true, level.random, state.getSeed(tile.getBlockPos()), combinedOverlay, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }
}
