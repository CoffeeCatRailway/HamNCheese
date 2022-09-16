package io.github.coffeecatrailway.hamncheese.client.entity;

import io.github.coffeecatrailway.hamncheese.client.HamNCheeseClient;
import io.github.coffeecatrailway.hamncheese.common.entity.MouseEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2021
 */
@Environment(EnvType.CLIENT)
public class MouseRenderer extends MobRenderer<MouseEntity, MouseModel>
{
    public MouseRenderer(EntityRendererProvider.Context context)
    {
        super(context, new MouseModel(context.bakeLayer(HamNCheeseClient.MOUSE)), .5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MouseEntity mouse)
    {
        return mouse.getTexture();
    }
}
