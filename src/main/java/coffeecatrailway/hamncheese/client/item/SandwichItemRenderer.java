package coffeecatrailway.hamncheese.client.item;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import coffeecatrailway.hamncheese.common.item.PizzaItem;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class SandwichItemRenderer extends ItemStackTileEntityRenderer
{
    private static final Random RANDOM = new Random(42);
    private static final Map<String, ItemStack> INGREDIENT_CACHE = new HashMap<>();

    private static final ResourceLocation PIZZA_SAUCE = HNCMod.getLocation("textures/item/pizza_sauce.png");
    private static List<Pair<Float, Float>> PIZZA_INGREDIENT_POS = Lists.newArrayList(
            Pair.of(-.5f, .2f),
            Pair.of(.25f, -.58f),
            Pair.of(.75f, .75f)
    );

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int combinedLight, int combinedOverlay)
    {
        super.renderByItem(stack, transformType, matrixStack, typeBuffer, combinedLight, combinedOverlay);
        if (stack.getItem() instanceof AbstractSandwichItem)
        {
            AbstractSandwichItem sandwichItem = (AbstractSandwichItem) stack.getItem();
            matrixStack.pushPose();

            // Rotate & position
            matrixStack.translate(.5f, .5f, .5f);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(90));

            // NBT setup
            CompoundNBT nbt = stack.getOrCreateTag();
            List<CompoundNBT> ingredients = nbt.getList(AbstractSandwichItem.TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND).stream().map(inbt -> (CompoundNBT) inbt).collect(Collectors.toList());
            ItemStack bun = sandwichItem.sandwichProperties.getBunItem(nbt);
            if (bun == null || bun.isEmpty())
                bun = ItemStack.EMPTY;

            // Cache ingredient if not already
            ingredients.stream().filter(inbt -> !INGREDIENT_CACHE.containsKey(inbt.getString("id")))
                    .forEach(inbt -> INGREDIENT_CACHE.put(inbt.getString("id"), ItemStack.of(inbt)));

            // Render sandwich
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            RANDOM.setSeed(42);
            if (sandwichItem instanceof PizzaItem)
            {
                // Render bun
                matrixStack.translate(0f, 0f, -(.06f * (ingredients.size() / 3f)) / 2f);
                itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, typeBuffer);

                // Render sauce
                matrixStack.pushPose();
                matrixStack.translate(0f, 0f, .0321f);
                Matrix4f matrixLast = matrixStack.last().pose();
                IVertexBuilder buffer = typeBuffer.getBuffer(RenderType.entityCutout(PIZZA_SAUCE));

                buffer.vertex(matrixLast, .5f, .5f, 0f).color(1f, 1f, 1f, 1f).uv(0f, 0f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, -.5f, .5f, 0f).color(1f, 1f, 1f, 1f).uv(1f, 0f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, -.5f, -.5f, 0f).color(1f, 1f, 1f, 1f).uv(1f, 1f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, .5f, -.5f, 0f).color(1f, 1f, 1f, 1f).uv(0f, 1f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLight).normal(0f, 1f, 0f).endVertex();
                matrixStack.popPose();

                // Render ingredients
                matrixStack.pushPose();
                int counter = 0;
                matrixStack.scale(.3f, .3f, .3f);
                matrixStack.translate(0f, 0f, .06f);
                for (CompoundNBT ingredient : ingredients)
                {
                    if (counter % 3 == 0)
                        matrixStack.translate(0f, 0f, .06f);

                    Pair<Float, Float> pos = PIZZA_INGREDIENT_POS.get(counter % 3);
                    matrixStack.translate(pos.getFirst(), pos.getSecond(), 0f);

                    float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                    matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                    itemRenderer.renderStatic(INGREDIENT_CACHE.get(ingredient.getString("id")), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, typeBuffer);
                    matrixStack.mulPose(Vector3f.ZP.rotation(angle));

                    matrixStack.translate(-pos.getFirst(), -pos.getSecond(), 0f);

                    counter++;
                }
                matrixStack.popPose();
            } else
            {
                // Render bun
                matrixStack.translate(0f, 0f, -(.06f * ingredients.size()) / 2f);
                itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, typeBuffer);

                // Render ingredients
                for (CompoundNBT ingredient : ingredients)
                {
                    matrixStack.translate(0f, 0f, .06f);
                    float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                    matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                    itemRenderer.renderStatic(INGREDIENT_CACHE.get(ingredient.getString("id")), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, typeBuffer);
                    matrixStack.mulPose(Vector3f.ZP.rotation(angle));
                }

                // Render other bun
                if (sandwichItem.sandwichProperties.hasTwoBuns())
                {
                    matrixStack.translate(0f, 0f, .06f);
                    itemRenderer.renderStatic(bun, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, typeBuffer);
                }
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
