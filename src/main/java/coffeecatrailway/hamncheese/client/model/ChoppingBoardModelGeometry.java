package coffeecatrailway.hamncheese.client.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.geometry.ISimpleModelGeometry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 20/09/2021
 */
@OnlyIn(Dist.CLIENT)
public class ChoppingBoardModelGeometry implements ISimpleModelGeometry<ChoppingBoardModelGeometry>
{
    private final ModelLoaderRegistry.VanillaProxy proxy;

    public ChoppingBoardModelGeometry(ModelLoaderRegistry.VanillaProxy proxy)
    {
        this.proxy = proxy;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation)
    {
        IBakedModel baseModel = this.proxy.bake(owner, bakery, spriteGetter, modelTransform, overrides, modelLocation);
        return new ChoppingBoardModel(baseModel);
    }

    @Override
    public void addQuads(IModelConfiguration owner, IModelBuilder<?> modelBuilder, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation)
    {
        this.proxy.addQuads(owner, modelBuilder, bakery, spriteGetter, modelTransform, modelLocation);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
    {
        return this.proxy.getTextures(owner, modelGetter, missingTextureErrors);
    }
}
