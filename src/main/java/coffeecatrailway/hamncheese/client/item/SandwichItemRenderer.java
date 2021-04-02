package coffeecatrailway.hamncheese.client.item;

import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class SandwichItemRenderer extends ItemStackTileEntityRenderer
{
    private static final Random RANDOM = new Random(42);

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
    {
        super.renderByItem(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
        if (stack.getItem() instanceof AbstractSandwichItem)
        {
            AbstractSandwichItem item = (AbstractSandwichItem) stack.getItem();
            matrixStack.pushPose();

            // Rotate & position
            matrixStack.translate(.5f, .5f, .5f);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(90));

            // NBT setup
            CompoundNBT nbt = stack.getOrCreateTag();
            ListNBT ingredients = nbt.getList(AbstractSandwichItem.TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
            ItemStack bun = new ItemStack(nbt.getBoolean(AbstractSandwichItem.TAG_TOASTED) ? item.foodProperties.getToastedBunItem().get() : item.foodProperties.getBunItem().get());

            // Move back if ingredients are present
            float offset;
            if (ingredients.size() > 0)
            {
                offset = -(.06f * ingredients.size()) / 2f;
                matrixStack.translate(0f, 0f, offset);
            }

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);

            // Render ingredients
            if (ingredients.size() > 0)
            {
                RANDOM.setSeed(42);
                for (INBT ingredient : ingredients)
                {
                    matrixStack.translate(0f, 0f, .06f);
                    float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                    matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                    itemRenderer.renderStatic(ItemStack.of((CompoundNBT) ingredient), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
                    matrixStack.mulPose(Vector3f.ZP.rotation(angle));
                }
            }

            if (item.foodProperties.hasTwoBuns())
            {
                matrixStack.translate(0f, 0f, .06f);
                itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
            }

            matrixStack.popPose();
        }
    }
}
