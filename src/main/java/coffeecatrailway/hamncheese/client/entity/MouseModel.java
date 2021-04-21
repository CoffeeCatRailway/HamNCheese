package coffeecatrailway.hamncheese.client.entity;

import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author ManWithoutTaste, CoffeeCatRailway
 */
@OnlyIn(Dist.CLIENT)
public class MouseModel extends EntityModel<MouseEntity>
{
    private final ModelRenderer body;
    private final ModelRenderer upperbody;
    private final ModelRenderer head;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer tail;
    private final ModelRenderer tailLower;

    public MouseModel()
    {
        this.texWidth = 48;
        this.texHeight = 16;

        this.body = new ModelRenderer(this);
        this.body.setPos(0f, 22f, 4f);
        this.body.texOffs(24, 0).addBox(-3f, -3f, -3f, 6f, 4f, 5f, 0f, false);

        this.upperbody = new ModelRenderer(this);
        this.upperbody.setPos(0f, 0f, -3f);
        this.body.addChild(this.upperbody);
        this.upperbody.texOffs(9, 4).addBox(-2.5f, -2f, -5f, 5f, 3f, 5f, 0f, false);

        this.head = new ModelRenderer(this);
        this.head.setPos(0f, 0f, -5f);
        this.upperbody.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-2f, -2f, -3f, 4f, 3f, 3f, 0f, false);
        this.head.texOffs(0, 6).addBox(-3f, -3f, -1f, 2f, 2f, 1f, 0f, false);
        this.head.texOffs(6, 6).addBox(1f, -3f, -1f, 2f, 2f, 1f, 0f, false);

        this.legBackRight = new ModelRenderer(this);
        this.legBackRight.setPos(-2f, 1f, -.5f);
        this.body.addChild(this.legBackRight);
        this.legBackRight.texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, 0f, false);

        this.legBackLeft = new ModelRenderer(this);
        this.legBackLeft.setPos(2f, 1f, -.5f);
        this.body.addChild(this.legBackLeft);
        this.legBackLeft.texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, 0f, false);

        this.legFrontRight = new ModelRenderer(this);
        this.legFrontRight.setPos(-1.5f, 1f, -5.5f);
        this.body.addChild(this.legFrontRight);
        this.legFrontRight.texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, 0f, false);

        this.legFrontLeft = new ModelRenderer(this);
        this.legFrontLeft.setPos(1.5f, 1f, -5.5f);
        this.body.addChild(this.legFrontLeft);
        this.legFrontLeft.texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, 0f, false);

        this.tail = new ModelRenderer(this);
        this.tail.setPos(0f, 0f, 2f);
        this.body.addChild(this.tail);
        this.tail.xRot = -.4363f;
        this.tail.texOffs(14, 0).addBox(-.5f, 0f, 0f, 1f, 1f, 3f, 0f, false);

        this.tailLower = new ModelRenderer(this);
        this.tailLower.setPos(0f, .5f, 3f);
        this.tail.addChild(this.tailLower);
        this.tailLower.xRot = .4363f;
        this.tailLower.texOffs(14, 0).addBox(-.5f, -.5468f, -.2113f, 1f, 1f, 3f, 0f, false);
        this.tailLower.texOffs(19, 0).addBox(-.5f, -.5468f, 2.7887f, 1f, 1f, 2f, 0f, false);
    }

    @Override
    public void setupAnim(MouseEntity mouse, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float aggressiveAngle = mouse.isAggressive() ? -((float) Math.PI / 5f) : 0f;
        body.xRot = aggressiveAngle;

        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount - aggressiveAngle;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount - aggressiveAngle;

        this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;

        this.tail.xRot = -aggressiveAngle;
        this.tail.yRot = MathHelper.sin(ageInTicks * .4f) * .5f;
        this.tailLower.yRot = -MathHelper.cos(ageInTicks * .4f) * .25f;

        this.head.xRot = headPitch * ((float) Math.PI / 180f) - aggressiveAngle;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body.render(matrixStack, vertexBuilder, packedLight, packedOverlay, red, green, blue, alpha);
    }
}