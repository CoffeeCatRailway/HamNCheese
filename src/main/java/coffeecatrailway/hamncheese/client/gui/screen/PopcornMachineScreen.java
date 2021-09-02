package coffeecatrailway.hamncheese.client.gui.screen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.PopcornMachineContainer;
import coffeecatrailway.hamncheese.data.gen.HNCLanguage;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 4/08/2021
 */
@OnlyIn(Dist.CLIENT)
public class PopcornMachineScreen extends ContainerScreen<PopcornMachineContainer>
{
    public static final ResourceLocation TEXTURE = HNCMod.getLocation("textures/gui/container/popcorn_machine.png");

    public PopcornMachineScreen(PopcornMachineContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        super.renderTooltip(matrixStack, mouseX, mouseY);
        if (this.isHovering(74, 23, 28, 26, mouseX, mouseY))
            this.renderTooltip(matrixStack, HNCLanguage.getPopcorn(this.menu.getPopcorn()), mouseX, mouseY);
        if (this.isHovering(19, 28, 8, 22, mouseX, mouseY))
            this.renderTooltip(matrixStack, HNCLanguage.getFlavour(this.menu.getFlavour()), mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager._color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int popcorn = this.menu.getPopcornScaled();
        this.blit(matrixStack, this.leftPos + 74, this.topPos + 23 + 25 - popcorn, 178, 59 - popcorn, 28, popcorn + 1);
        this.blit(matrixStack, this.leftPos + 74, this.topPos + 23, 178, 2, 28, 26);

        int flavour = this.menu.getFlavourScaled();
        this.blit(matrixStack, this.leftPos + 20, this.topPos + 29 + 21 - flavour, 184, 85 - flavour, 8, flavour + 1);
        this.blit(matrixStack, this.leftPos + 20, this.topPos + 29, 176, 64, 8, 22);
    }
}
