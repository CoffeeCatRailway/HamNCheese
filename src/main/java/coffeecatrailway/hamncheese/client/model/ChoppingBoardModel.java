package coffeecatrailway.hamncheese.client.model;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.tileentity.ChoppingBoardTileEntity;
import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.data.ChoppingBoardManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 20/09/2021
 */
public class ChoppingBoardModel implements IDynamicBakedModel
{
    private final IBakedModel baseModel;

    public ChoppingBoardModel(IBakedModel baseModel)
    {
        this.baseModel = baseModel;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData)
    {
        IBakedModel model = this.getBoardModel(extraData);
        return model.getQuads(state, side, rand, extraData);
    }

    private IBakedModel getBoardModel(@Nonnull IModelData data)
    {
        return Minecraft.getInstance().getModelManager().getModel(ChoppingBoardManager.INSTANCE.getById(data.getData(ChoppingBoardTileEntity.BOARD_ID)).getModel());
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IBlockDisplayReader level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData)
    {
        ResourceLocation boardId = ChoppingBoard.DEFAULT.getId();
        TileEntity tile = level.getBlockEntity(pos);
        if (tile instanceof ChoppingBoardTileEntity)
            boardId = ((ChoppingBoardTileEntity) tile).getBoardId();
        tileData.setData(ChoppingBoardTileEntity.BOARD_ID, boardId);
        return tileData;
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return this.baseModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return this.baseModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight()
    {
        return this.baseModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return this.baseModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon()
    {
        return this.baseModel.getParticleIcon();
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return this.baseModel.getOverrides();
    }

    @Override
    public ItemCameraTransforms getTransforms()
    {
        return this.baseModel.getTransforms();
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data)
    {
        return this.getBoardModel(data).getParticleTexture(data);
    }
}
