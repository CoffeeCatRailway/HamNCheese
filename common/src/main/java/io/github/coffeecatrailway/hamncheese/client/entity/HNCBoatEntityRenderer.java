package io.github.coffeecatrailway.hamncheese.client.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.entity.HNCBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author CoffeeCatRailway
 * Created: 8/09/2021
 *
 * Based on {@link net.minecraft.client.renderer.entity.BoatRenderer}
 */
@Environment(EnvType.CLIENT)
public class HNCBoatEntityRenderer extends EntityRenderer<HNCBoatEntity>
{
    private final Map<HNCBoatEntity.ModType, Pair<ResourceLocation, BoatModel>> boatResources;

    public HNCBoatEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(HNCBoatEntity.ModType.values()).collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(HamNCheese.getLocation("textures/entity/boat/" + type.getName() + ".png"), new BoatModel(context.bakeLayer(createModelLayer(type))))));
    }

    public static ModelLayerLocation createModelLayer(HNCBoatEntity.ModType type)
    {
        return new ModelLayerLocation(HamNCheese.getLocation("boat/" + type.getName()), "main");
    }

    public void render(HNCBoatEntity boat, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i)
    {
        poseStack.pushPose();
        poseStack.translate(0f, .375f, 0f);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f - f));
        float h = (float) boat.getHurtTime() - g;
        float j = boat.getDamage() - g;
        if (j < 0f)
            j = 0f;

        if (h > 0f)
            poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(h) * h * j / 10f * (float) boat.getHurtDir()));

        float k = boat.getBubbleAngle(g);
        if (!Mth.equal(k, 0f))
            poseStack.mulPose(new Quaternion(new Vector3f(1f, 0f, 1f), boat.getBubbleAngle(g), true));

        Pair<ResourceLocation, BoatModel> pair = this.boatResources.get(boat.getModType());
        ResourceLocation resourceLocation = pair.getFirst();
        BoatModel boatModel = pair.getSecond();
        poseStack.scale(-1f, -1f, 1f);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90f));
        boatModel.setupAnim(boat, g, 0f, -0.1F, 0f, 0f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(boatModel.renderType(resourceLocation));
        boatModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        if (!boat.isUnderWater())
        {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask());
            boatModel.waterPatch().render(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(boat, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(HNCBoatEntity boat)
    {
        return this.boatResources.get(boat.getModType()).getFirst();
    }
}
