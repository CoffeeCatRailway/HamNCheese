package coffeecatrailway.hamncheese.client.tileentity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.tileentity.ChoppingBoardTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.ocelot.sonar.client.render.BakedModelRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 18/09/2021
 */
@OnlyIn(Dist.CLIENT)
public class ChoppingBoardTileEntityRenderer extends TileEntityRenderer<ChoppingBoardTileEntity>
{
    public ChoppingBoardTileEntityRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(ChoppingBoardTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
    {
        matrixStack.pushPose();
//        matrixStack.mulPose(tile.getBlockState().getValue(ChoppingBoardBlock.HORIZONTAL_FACING).getRotation());
        IBakedModel model = Minecraft.getInstance().getModelManager().getModel(HNCMod.getLocation("block/chopping_board_visual"));
//        this.renderer.textureManager.bind(HNCMod.getLocation("textures/block/maple_planks.png"));
        BakedModelRenderer.renderModel(model, buffer.getBuffer(RenderType.entityCutout(PlayerContainer.BLOCK_ATLAS)), matrixStack, 1f, 1f, 1f, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }
}
