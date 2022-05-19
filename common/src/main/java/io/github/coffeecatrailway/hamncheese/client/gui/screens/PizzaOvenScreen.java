package io.github.coffeecatrailway.hamncheese.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PizzaOvenContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author CoffeeCatRailway
 * Created: 22/05/2021
 */
@Environment(EnvType.CLIENT)
public class PizzaOvenScreen extends AbstractContainerScreen<PizzaOvenContainer>
{
    public static final ResourceLocation TEXTURE = HamNCheese.getLocation("textures/gui/container/pizza_oven.png");

    public PizzaOvenScreen(PizzaOvenContainer container, Inventory inventory, Component title)
    {
        super(container, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 171;
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = (this.imageWidth / 2 - this.font.width(this.title) / 2) + 9;
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
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        this.font.drawShadow(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 0xd6d6d6);
        this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 0x404040);
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
            this.blit(poseStack, leftPos + 88, topPos + 36 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1);
        }

        int cookProgress = this.menu.getCookProgressionScaled();
        this.blit(poseStack, leftPos + 88, topPos + 18, 176, 14, cookProgress + 1, 16);
    }
}
