package coffeecatrailway.hamncheese.client.item;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.client.model.ChoppingBoardModel;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.tileentity.ChoppingBoardTileEntity;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 10/10/2021
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = HNCMod.MOD_ID, value = Dist.CLIENT)
public class ChoppingBoardItemRenderer extends ItemStackTileEntityRenderer
{
    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int combinedLight, int combinedOverlay)
    {
        super.renderByItem(stack, transformType, matrixStack, typeBuffer, combinedLight, combinedOverlay);
        if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof ChoppingBoardBlock)
        {
            matrixStack.pushPose();
            BlockState state = HNCBlocks.CHOPPING_BOARD.get().defaultBlockState();
            IModelData data = ChoppingBoardTileEntity.getModelData(new ResourceLocation(stack.getOrCreateTagElement("BlockEntityTag").getString("BoardId")));
            List<BakedQuad> quads = Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, Direction.NORTH, ChoppingBoardModel.RANDOM, data);
            Minecraft.getInstance().getItemRenderer().renderQuadList(matrixStack, ItemRenderer.getFoilBufferDirect(typeBuffer, RenderTypeLookup.getRenderType(stack, true), true, stack.hasFoil()), quads, stack, combinedLight, combinedOverlay);
            matrixStack.popPose();
        }
    }
}
