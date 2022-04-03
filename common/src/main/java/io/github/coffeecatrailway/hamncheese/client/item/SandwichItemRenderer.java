package io.github.coffeecatrailway.hamncheese.client.item;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import gg.moonflower.pollen.api.client.render.DynamicItemRenderer;
import gg.moonflower.pollen.api.event.events.network.ClientNetworkEvents;
import gg.moonflower.pollen.api.util.NbtConstants;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import io.github.coffeecatrailway.hamncheese.common.item.PizzaItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 1/04/2021
 */
public class SandwichItemRenderer implements DynamicItemRenderer
{
    public static final SandwichItemRenderer INSTANCE = new SandwichItemRenderer();
    private static final Random RANDOM = new Random(42);

    private static final ResourceLocation PIZZA_SAUCE = HamNCheese.getLocation("textures/item/pizza_sauce.png");
    private static final List<Pair<Float, Float>> PIZZA_INGREDIENT_POS = Lists.newArrayList(
            Pair.of(-.5f, .2f),
            Pair.of(.25f, -.58f),
            Pair.of(.75f, .75f)
    );

    private final Map<String, ItemStack> ingredientCache;

    public SandwichItemRenderer()
    {
        this.ingredientCache = new HashMap<>();
    }

    public static void init()
    {
        ClientNetworkEvents.LOGOUT.register((controller, player, connection) -> INSTANCE.ingredientCache.clear());
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource multiBufferSource, int packedLight, int combinedOverlay)
    {
        if (stack.getItem() instanceof AbstractSandwichItem)
        {
            AbstractSandwichItem sandwichItem = (AbstractSandwichItem) stack.getItem();
            matrixStack.pushPose();

            // Rotate & position
            matrixStack.translate(.5f, .5f, .5f);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(90));

            // NBT setup
            CompoundTag nbt = stack.getOrCreateTag();
            List<CompoundTag> ingredients = nbt.getList(AbstractSandwichItem.TAG_INGREDIENTS, NbtConstants.COMPOUND).stream().map(inbt -> (CompoundTag) inbt).toList();
            ItemStack bun = sandwichItem.sandwichProperties.getBunItem(nbt);
            if (bun == null || bun.isEmpty())
                bun = ItemStack.EMPTY;

            // Cache ingredient if not already
            ingredients.stream().filter(inbt -> !this.ingredientCache.containsKey(inbt.getString("id")))
                    .forEach(inbt -> this.ingredientCache.put(inbt.getString("id"), ItemStack.of(inbt)));

            // Render sandwich
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            RANDOM.setSeed(42);
            if (sandwichItem instanceof PizzaItem)
            {
                // Render bun
                matrixStack.translate(0f, 0f, -(.06f * ingredients.size() / 12f) / 2f);
                itemRenderer.renderStatic(bun, ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, matrixStack, multiBufferSource, 0);

                // Render sauce
                matrixStack.pushPose();
                matrixStack.translate(0f, 0f, .0321f);
                Matrix4f matrixLast = matrixStack.last().pose();
                VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.entityCutout(PIZZA_SAUCE));

                buffer.vertex(matrixLast, .5f, .5f, 0f).color(1f, 1f, 1f, 1f).uv(0f, 0f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, -.5f, .5f, 0f).color(1f, 1f, 1f, 1f).uv(1f, 0f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, -.5f, -.5f, 0f).color(1f, 1f, 1f, 1f).uv(1f, 1f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrixLast, .5f, -.5f, 0f).color(1f, 1f, 1f, 1f).uv(0f, 1f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0f, 1f, 0f).endVertex();
                matrixStack.popPose();

                // Render ingredients
                matrixStack.pushPose();
                int counter = 0;
                matrixStack.scale(.3f, .3f, .3f);
                matrixStack.translate(0f, 0f, .06f);
                for (CompoundTag ingredient : ingredients)
                {
                    if (counter % 3 == 0)
                        matrixStack.translate(0f, 0f, .06f);

                    Pair<Float, Float> pos = PIZZA_INGREDIENT_POS.get(counter % 3);
                    matrixStack.translate(pos.getFirst(), pos.getSecond(), 0f);

                    float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                    matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                    itemRenderer.renderStatic(this.ingredientCache.get(ingredient.getString("id")), ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, matrixStack, multiBufferSource, 0);
                    matrixStack.mulPose(Vector3f.ZP.rotation(angle));

                    matrixStack.translate(-pos.getFirst(), -pos.getSecond(), 0f);

                    counter++;
                }
                matrixStack.popPose();
            } else
            {
                // Render bun
                matrixStack.translate(0f, 0f, -(.06f * ingredients.size()) / 2f);
                itemRenderer.renderStatic(bun, ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, matrixStack, multiBufferSource, 0);

                // Render ingredients
                for (CompoundTag ingredient : ingredients)
                {
                    matrixStack.translate(0f, 0f, .06f);
                    float angle = (float) (RANDOM.nextFloat() * Math.PI * 2f);
                    matrixStack.mulPose(Vector3f.ZN.rotation(angle));
                    itemRenderer.renderStatic(this.ingredientCache.get(ingredient.getString("id")), ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, matrixStack, multiBufferSource, 0);
                    matrixStack.mulPose(Vector3f.ZP.rotation(angle));
                }

                // Render other bun
                if (sandwichItem.sandwichProperties.hasTwoBuns())
                {
                    matrixStack.translate(0f, 0f, .06f);
                    itemRenderer.renderStatic(bun, ItemTransforms.TransformType.FIXED, packedLight, combinedOverlay, matrixStack, multiBufferSource, 0);
                }
            }

            matrixStack.popPose();
        }
    }
}
