package io.github.coffeecatrailway.hamncheese.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.GrillContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author CoffeeCatRailway
 * Created: 10/06/2021
 */
@Environment(EnvType.CLIENT)
public class GrillScreen extends AbstractContainerScreen<GrillContainer>
{
    public static final ResourceLocation TEXTURE = HamNCheese.getLocation("textures/gui/container/grill.png");

    public GrillScreen(GrillContainer container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = (this.imageWidth / 2 - this.font.width(this.title) / 2);
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        this.blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isBurning())
        {
            int burnTime = this.menu.getBurnLeftScaled();
            this.blit(poseStack, leftPos + 80, topPos + 45 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1);
        }

        int cookProgress = this.menu.getCookProgressionScaled();
        this.blit(poseStack, leftPos + 76, topPos + 26, 176, 14, cookProgress + 1, 16);
    }
}
