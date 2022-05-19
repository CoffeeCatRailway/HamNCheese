package io.github.coffeecatrailway.hamncheese.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import io.github.coffeecatrailway.hamncheese.common.world.inventory.PopcornMachineContainer;
import io.github.coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
@Environment(EnvType.CLIENT)
public class PopcornMachineScreen extends AbstractContainerScreen<PopcornMachineContainer>
{
    public static final ResourceLocation TEXTURE = HamNCheese.getLocation("textures/gui/container/popcorn_machine.png");

    public PopcornMachineScreen(PopcornMachineContainer container, Inventory inventory, Component title)
    {
        super(container, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 176;
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = ((this.imageWidth / 2 + 52) - this.font.width(this.title) / 2);
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
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        super.renderTooltip(poseStack, mouseX, mouseY);
        if (this.isHovering(74, 23, 28, 26, mouseX, mouseY))
            this.renderTooltip(poseStack, HNCLanguage.getPopcorn(this.menu.getPopcorn()), mouseX, mouseY);
        if (this.isHovering(19, 28, 8, 22, mouseX, mouseY))
            this.renderTooltip(poseStack, HNCLanguage.getFlavour(this.menu.getFlavour()), mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int popcorn = this.menu.getPopcornScaled();
        this.blit(poseStack, this.leftPos + 74, this.topPos + 23 + 25 - popcorn, 178, 59 - popcorn, 28, popcorn + 1);
        this.blit(poseStack, this.leftPos + 74, this.topPos + 23, 178, 2, 28, 26);

        int flavour = this.menu.getFlavourScaled();
        this.blit(poseStack, this.leftPos + 20, this.topPos + 29 + 21 - flavour, 184, 85 - flavour, 8, flavour + 1);
        this.blit(poseStack, this.leftPos + 20, this.topPos + 29, 176, 64, 8, 22);
    }
}
