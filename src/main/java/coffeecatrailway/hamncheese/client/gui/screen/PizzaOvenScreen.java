package coffeecatrailway.hamncheese.client.gui.screen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.PizzaOvenContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author CoffeeCatRailway
 * Created: 22/05/2021
 */
public class PizzaOvenScreen extends ContainerScreen<PizzaOvenContainer>
{
    public static final ResourceLocation TEXTURE = HNCMod.getLocation("textures/gui/container/pizza_oven.png");

    public PizzaOvenScreen(PizzaOvenContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 171;
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = (this.imageWidth / 2 - this.font.width(this.title) / 2) + 9;
        this.inventoryLabelY = this.imageHeight - 89;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.drawShadow(matrixStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 0xd6d6d6);
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float) this.inventoryLabelX, (float) this.inventoryLabelY, 0x404040);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager._color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (this.getMenu().isBurning())
        {
            int burnTime = this.getMenu().getBurnLeftScaled();
            this.blit(matrixStack, leftPos + 88, topPos + 36 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1);
        }

        int cookProgress = this.getMenu().getCookProgressionScaled();
        this.blit(matrixStack, leftPos + 88, topPos + 18, 176, 14, cookProgress + 1, 16);
    }
}
