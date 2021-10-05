package coffeecatrailway.hamncheese.client.model;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * @author CoffeeCatRailway
 * Created: 4/10/2021
 */
public class BakedQuadRetextured extends BakedQuad
{
    public BakedQuadRetextured(BakedQuad parent, TextureAtlasSprite texture)
    {
        super(parent.getVertices(), parent.getTintIndex(), parent.getDirection(), texture, parent.isShade());
        this.remap();
    }

    private void remap()
    {
        for (int i = 0; i < 4; i++)
        {
            int j = 7 * i;
            this.vertices[j + 4] = Float.floatToRawIntBits(this.getU(Float.intBitsToFloat(this.vertices[j + 4])));
            this.vertices[j + 4 + 1] = Float.floatToRawIntBits(this.getV(Float.intBitsToFloat(this.vertices[j + 4 + 1])));
        }
    }

    private float getU(float vertex)
    {
        float unInterpolated = (vertex - this.sprite.getU0()) / (this.sprite.getU1() - this.sprite.getU0()) * 16f;
        return this.sprite.getU(unInterpolated);
    }

    private float getV(float vertex)
    {
        float unInterpolated = (vertex - this.sprite.getV0()) / (this.sprite.getV1() - this.sprite.getV0()) * 16f;
        return this.sprite.getV(unInterpolated);
    }
}
