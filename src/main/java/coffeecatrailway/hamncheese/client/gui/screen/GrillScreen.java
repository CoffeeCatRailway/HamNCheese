package coffeecatrailway.hamncheese.client.gui.screen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.inventory.GrillContainer;
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
 * Created: 10/06/2021
 */
@OnlyIn(Dist.CLIENT)
public class GrillScreen extends ContainerScreen<GrillContainer>
{
    public static final ResourceLocation TEXTURE = HNCMod.getLocation("textures/gui/container/grill.png");

    public GrillScreen(GrillContainer container, PlayerInventory playerInventory, ITextComponent title)
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager._color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isBurning())
        {
            int burnTime = this.menu.getBurnLeftScaled();
            this.blit(matrixStack, leftPos + 80, topPos + 45 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1);
        }

        int cookProgress = this.menu.getCookProgressionScaled();
        this.blit(matrixStack, leftPos + 76, topPos + 26, 176, 14, cookProgress + 1, 16);
    }
}
