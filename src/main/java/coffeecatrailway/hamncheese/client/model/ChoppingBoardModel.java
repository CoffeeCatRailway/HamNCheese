package coffeecatrailway.hamncheese.client.model;

import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.tileentity.ChoppingBoardTileEntity;
import coffeecatrailway.hamncheese.data.ChoppingBoard;
import coffeecatrailway.hamncheese.data.ChoppingBoardManager;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.QuadTransformer;
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
    private static final Object2ObjectMap<Pair<Float, ResourceLocation>, List<BakedQuad>> BAKED_QUADS = new Object2ObjectArrayMap<>();

    private final IBakedModel baseModel;

    public ChoppingBoardModel(IBakedModel baseModel)
    {
        this.baseModel = baseModel;
        BAKED_QUADS.clear();
    }

    private static QuadTransformer angleToTransformer(float angle)
    {
        return new QuadTransformer(new TransformationMatrix(null, new Quaternion(0, angle, 0, true), null, null).blockCenterToCorner());
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData)
    {
        float angle = 0f;
        if (state != null)
        {
            Direction direction = state.getValue(ChoppingBoardBlock.HORIZONTAL_FACING);
            if (direction == Direction.EAST || direction == Direction.WEST)
                direction = direction.getOpposite();
            angle = direction.toYRot();
        }
        Pair<Float, ResourceLocation> identifier = Pair.of(angle, this.getBoard(extraData).getModel());
        BAKED_QUADS.computeIfAbsent(identifier, identifierIn -> angleToTransformer(identifierIn.getFirst()).processMany(this.getModel(identifierIn.getSecond()).getQuads(state, side, rand, extraData)));
        if (BAKED_QUADS.get(identifier).isEmpty())
            BAKED_QUADS.replace(identifier, angleToTransformer(identifier.getFirst()).processMany(this.getModel(identifier.getSecond()).getQuads(state, side, rand, extraData)));
        return BAKED_QUADS.get(identifier); // TODO: Fix item models
    }

    private ChoppingBoard getBoard(@Nonnull IModelData data)
    {
        return ChoppingBoardManager.INSTANCE.getById(data.getData(ChoppingBoardTileEntity.BOARD_ID));
    }

    private IBakedModel getModel(ResourceLocation modelLocation)
    {
        return Minecraft.getInstance().getModelManager().getModel(modelLocation);
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
        return this.getModel(this.getBoard(data).getModel()).getParticleTexture(data);
    }
}
