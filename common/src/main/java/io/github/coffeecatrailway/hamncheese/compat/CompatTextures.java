package io.github.coffeecatrailway.hamncheese.compat;

import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 5/06/2022
 */
@Environment(EnvType.CLIENT)
public class CompatTextures // TODO: Use for jei
{
    public static final ResourceLocation POPCORN_MACHINE = HamNCheese.getLocation("textures/gui/jei/popcorn.png");
}
