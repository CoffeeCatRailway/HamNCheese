package io.github.coffeecatrailway.hamncheese.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import io.github.coffeecatrailway.hamncheese.common.block.entity.ChoppingBoardBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 15/06/2022
 */
@Environment(EnvType.CLIENT)
public class ChoppingBoardRenderer implements BlockEntityRenderer<ChoppingBoardBlockEntity>
{
    private static final Random RANDOM = new Random(BlockPos.ZERO.asLong());

    public ChoppingBoardRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    @Override
    public void render(ChoppingBoardBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int combinedOverlay)
    {
        poseStack.pushPose();

        // Rotate & position
        poseStack.translate(.5f, .075f, .5f);
        poseStack.scale(.5f, .5f, .5f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90f));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(90f * blockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING).get2DDataValue()));
        RANDOM.setSeed(blockEntity.getBlockPos().asLong());
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(360f * RANDOM.nextFloat()));

        // Render sandwich
        if (!blockEntity.getIngredient().isEmpty())
            Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getIngredient(), ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, poseStack, multiBufferSource, (int) blockEntity.getBlockPos().asLong());

        poseStack.popPose();
    }
}
