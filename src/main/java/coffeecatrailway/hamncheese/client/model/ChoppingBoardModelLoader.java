package coffeecatrailway.hamncheese.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

/**
 * @author CoffeeCatRailway
 * Created: 20/09/2021
 */
@OnlyIn(Dist.CLIENT)
public class ChoppingBoardModelLoader implements IModelLoader<ChoppingBoardModelGeometry>
{
    public static final ChoppingBoardModelLoader INSTANCE = new ChoppingBoardModelLoader();

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
    }

    @Override
    public ChoppingBoardModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
    {
        ModelLoaderRegistry.VanillaProxy proxy = ModelLoaderRegistry.VanillaProxy.Loader.INSTANCE.read(deserializationContext, modelContents);
        return new ChoppingBoardModelGeometry(proxy);
    }
}
