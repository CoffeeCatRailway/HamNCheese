package coffeecatrailway.hamncheese.client.entity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 */
@OnlyIn(Dist.CLIENT)
public class HNCBoatEntityRenderer extends EntityRenderer<HNCBoatEntity>
{
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[]{HNCMod.getLocation("textures/entity/boat/maple.png")};
    protected final BoatModel model = new BoatModel();

    public HNCBoatEntityRenderer(EntityRendererManager renderManager)
    {
        super(renderManager);
        this.shadowRadius = .8f;
    }

    @Override
    public void render(HNCBoatEntity boat, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight)
    {
        matrixStack.pushPose();
        matrixStack.translate(0d, .375d, 0d);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180f - yaw));
        float f = (float) boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;
        if (f1 < 0f)
            f1 = 0f;

        if (f > 0f)
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10f * (float) boat.getHurtDir()));

        float f2 = boat.getBubbleAngle(partialTicks);
        if (!MathHelper.equal(f2, 0f))
            matrixStack.mulPose(new Quaternion(new Vector3f(1f, 0f, 1f), boat.getBubbleAngle(partialTicks), true));

        matrixStack.scale(-1f, -1f, 1f);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(90f));
        this.model.setupAnim(boat, partialTicks, 0f, -.1f, 0f, 0f);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(boat)));
        this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        if (!boat.isUnderWater())
        {
            IVertexBuilder ivertexbuilder1 = buffer.getBuffer(RenderType.waterMask());
            this.model.waterPatch().render(matrixStack, ivertexbuilder1, packedLight, OverlayTexture.NO_OVERLAY);
        }

        matrixStack.popPose();
        super.render(boat, yaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(HNCBoatEntity boat)
    {
        return BOAT_TEXTURES[boat.getModType().ordinal()];
    }
}
