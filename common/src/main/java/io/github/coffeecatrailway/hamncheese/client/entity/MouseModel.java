package io.github.coffeecatrailway.hamncheese.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.coffeecatrailway.hamncheese.common.entity.MouseEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

/**
 * @author ManWithoutTaste, CoffeeCatRailway
 */
@Environment(EnvType.CLIENT)
public class MouseModel extends EntityModel<MouseEntity>
{
    private final ModelPart body;
    private final ModelPart head;

    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    private final ModelPart tail;
    private final ModelPart lowerTail;

    public MouseModel(ModelPart modelPart)
    {
        this.body = modelPart.getChild("body");
        ModelPart upperBody = this.body.getChild("upperBody");
        this.head = upperBody.getChild("head");

        this.rightHindLeg = this.body.getChild("rightHindLeg");
        this.leftHindLeg = this.body.getChild("leftHindLeg");
        this.rightFrontLeg = this.body.getChild("rightFrontLeg");
        this.leftFrontLeg = this.body.getChild("leftFrontLeg");
        this.tail = this.body.getChild("tail");
        this.lowerTail = this.tail.getChild("lowerTail");
    }

    public static LayerDefinition createLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 0).addBox(-3f, -3f, -3f, 6f, 4f, 5f, CubeDeformation.NONE), PartPose.offset(0f, 22f, 4f));
        PartDefinition upperBody = body.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(9, 4).addBox(-2.5f, -2f, -5f, 5f, 3f, 5f, CubeDeformation.NONE), PartPose.offset(0f, 0f, -3f));
        upperBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2f, -2f, -3f, 4f, 3f, 3f, CubeDeformation.NONE)
                .texOffs(0, 6).addBox(-3f, -3f, -1f, 2f, 2f, 1f, CubeDeformation.NONE)
                .texOffs(6, 6).addBox(1f, -3f, -1f, 2f, 2f, 1f, CubeDeformation.NONE), PartPose.offset(0f, 0f, -5f));

        body.addOrReplaceChild("rightHindLeg", CubeListBuilder.create().texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, CubeDeformation.NONE), PartPose.offset(-2f, 1f, -.5f));
        body.addOrReplaceChild("leftHindLeg", CubeListBuilder.create().texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, CubeDeformation.NONE), PartPose.offset(2f, 1f, -.5f));

        body.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, CubeDeformation.NONE), PartPose.offset(-1.5f, 1f, -5.5f));
        body.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(11, 0).addBox(-.5f, 0f, -1.5f, 1f, 1f, 2f, CubeDeformation.NONE), PartPose.offset(1.5f, 1f, -5.5f));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(14, 0).addBox(-.5f, 0f, 0f, 1f, 1f, 3f, CubeDeformation.NONE), PartPose.offsetAndRotation(0f, 0f, 2f, -.4363f, 0f, 0f));
        tail.addOrReplaceChild("lowerTail", CubeListBuilder.create().texOffs(14, 0).addBox(-.5f, -.5468f, -.2113f, 1f, 1f, 3f, CubeDeformation.NONE)
                .texOffs(19, 0).addBox(-.5f, -.5468f, 2.7887f, 1f, 1f, 2f, CubeDeformation.NONE), PartPose.offsetAndRotation(0f, .5f, 3f, .4363f, 0f, 0f));

        return LayerDefinition.create(mesh, 48, 16);
    }

    @Override
    public void setupAnim(MouseEntity mouse, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float aggressiveAngle = mouse.isAggressive() ? -((float) Math.PI / 5f) : 0f;
        this.body.xRot = aggressiveAngle;

        this.leftFrontLeg.xRot = Mth.cos(limbSwing * .6662f) * 1.4f * limbSwingAmount - aggressiveAngle;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * .6662f + (float) Math.PI) * 1.4f * limbSwingAmount - aggressiveAngle;

        this.leftHindLeg.xRot = Mth.cos(limbSwing * .6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * .6662f) * 1.4f * limbSwingAmount;

        this.tail.xRot = -aggressiveAngle;
        this.tail.yRot = Mth.sin(ageInTicks * .4f) * .5f;
        this.lowerTail.yRot = -Mth.cos(ageInTicks * .4f) * .25f;

        this.head.xRot = headPitch * ((float) Math.PI / 180f) - aggressiveAngle;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        if (this.young)
        {
            poseStack.pushPose();
            poseStack.scale(.5f, .5f, .5f);
            poseStack.translate(0f, 16f/11f, 0f);
            this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else
            this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}