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
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class SandwichItemRenderer extends ItemStackTileEntityRenderer
{
    private static final Random RANDOM = new Random(42);
    private static final Map<INBT, ItemStack> INGREDIENT_CACHE = new HashMap<>();

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
            ItemStack bun = item.sandwichProperties.getBunItem(nbt);

            // Move back if ingredients are present
            if (ingredients.size() > 0)
                matrixStack.translate(0f, 0f, -(.06f * ingredients.size()) / 2f);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);

            // Render ingredients
            if (ingredients.size() > 0)
            {
                RANDOM.setSeed(42);
                for (INBT ingredient : ingredients)
                {
                    if (INGREDIENT_CACHE.containsKey(ingredient))
                    {
                        matrixStack.translate(0f, 0f, .06f);
                        float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                        matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                        itemRenderer.renderStatic(INGREDIENT_CACHE.get(ingredient), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
                        matrixStack.mulPose(Vector3f.ZP.rotation(angle));
                    } else
                        INGREDIENT_CACHE.put(ingredient, ItemStack.of((CompoundNBT) ingredient));
                }
            }

            if (item.sandwichProperties.hasTwoBuns())
            {
                matrixStack.translate(0f, 0f, .06f);
                itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
            }

            matrixStack.popPose();
        }
    }

    @SubscribeEvent
    public void onReload(TagsUpdatedEvent event)
    {
        if (!INGREDIENT_CACHE.isEmpty())
            INGREDIENT_CACHE.clear();
    }
}
