package coffeecatrailway.hamncheese.client.block.tile;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.CurdlerBlock;
import coffeecatrailway.hamncheese.common.tileentity.CurdlerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
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

        matrixStack.pushPose();

        if (CRANK_MODEL == null)
            CRANK_MODEL = Minecraft.getInstance().getModelManager().getModel(CRANK_LOCATION);
        World level = tile.getLevel();
        matrixStack.translate(.5f, 0f, .5f);
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(tile.getCrankAngle()));
        matrixStack.translate(-.5f, 0f, -.5f);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(level, CRANK_MODEL, state, tile.getBlockPos().above(), matrixStack, typeBuffer.getBuffer(RenderType.solid()), true, level.random, state.getSeed(tile.getBlockPos().above()), combinedOverlay, EmptyModelData.INSTANCE);

        matrixStack.popPose();

        float milk = tile.getMilk() * 1f;
        if (milk > 0f)
        {
            matrixStack.pushPose();
            float height = milk / tile.getMilkCapacity() * (.95f - .01f) + .01f;
            this.drawMilk(tile.getLevel(), Fluids.WATER.getFluid(), tile.getBlockPos(), matrixStack, typeBuffer, new Vector3f(.5f, height, .5f), .9f, .9f, .8f, .8f, combinedLight);
            matrixStack.popPose();
        }
    }

    private void drawMilk(World level, Fluid fluid, BlockPos pos, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, Vector3f centerPos, float width, float height, float texUWidth, float texVHeight, int combinedLight)
    {

        Vector3f leftToRight = new Vector3f(-1f, 0f, 0f);
        leftToRight.mul(.5f * width);
        Vector3f bottomToTop = new Vector3f(0f, 0f, 1f);
        bottomToTop.mul(.5f * height);

        Vector3f bottomLeftPos = centerPos.copy();
        bottomLeftPos.sub(leftToRight);
        bottomLeftPos.sub(bottomToTop);

        Vector3f bottomRightPos = centerPos.copy();
        bottomRightPos.add(leftToRight);
        bottomRightPos.sub(bottomToTop);

        Vector3f topRightPos = centerPos.copy();
        topRightPos.add(leftToRight);
        topRightPos.add(bottomToTop);

        Vector3f topLeftPos = centerPos.copy();
        topLeftPos.sub(leftToRight);
        topLeftPos.add(bottomToTop);

        TextureAtlasSprite sprite = ForgeHooksClient.getFluidSprites(level, pos, fluid.defaultFluidState())[0];
        float minU = sprite.getU0();
        float maxU = Math.min(minU + (sprite.getU1() - minU), sprite.getU1());
        float minV = sprite.getV0();
        float maxV = Math.min(minV + (sprite.getV1() - minV), sprite.getV1());
        Vector2f bottomLeftUVpos = new Vector2f(minU, minV);
        Vector2f bottomRightUVpos = new Vector2f(maxU, minV);
        Vector2f topLeftUVpos = new Vector2f(maxU, maxV);
        Vector2f topRightUVpos = new Vector2f(minU, maxV);

        IVertexBuilder buffer = typeBuffer.getBuffer(RenderType.entityTranslucentCull(sprite.atlas().location()));
        Matrix4f matrixPos = matrixStack.last().pose();
        Matrix3f matrixNormal = matrixStack.last().normal();

        Vector3f normalVector = Direction.UP.step();
        int color = fluid.getAttributes().getColor(level, pos);
        this.addVertex(buffer, matrixPos, matrixNormal, bottomLeftPos, bottomLeftUVpos, normalVector, color, combinedLight);
        this.addVertex(buffer, matrixPos, matrixNormal, bottomRightPos, bottomRightUVpos, normalVector, color, combinedLight);
        this.addVertex(buffer, matrixPos, matrixNormal, topRightPos, topLeftUVpos, normalVector, color, combinedLight);
        this.addVertex(buffer, matrixPos, matrixNormal, topLeftPos, topRightUVpos, normalVector, color, combinedLight);
    }

    private void addVertex(IVertexBuilder builder, Matrix4f matrixLast, Matrix3f normal, Vector3f pos, Vector2f texUV, Vector3f normalVector, int color, int combinedLight)
    {
        builder.vertex(matrixLast, pos.x(), pos.y(), pos.z()).color((color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f, (color >> 24 & 255) / 255f)
                .uv(texUV.x, texUV.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLight).normal(normal, normalVector.x(), normalVector.y(), normalVector.z()).endVertex();
    }
}
