package coffeecatrailway.hamncheese.client.entity;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.entity.MouseEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
@OnlyIn(Dist.CLIENT)
public class MouseRenderer extends MobRenderer<MouseEntity, MouseModel>
{
    public MouseRenderer(EntityRendererManager manager)
    {
        super(manager, new MouseModel(), .5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MouseEntity mouse)
    {
        return mouse.getTexture();
    }
}
