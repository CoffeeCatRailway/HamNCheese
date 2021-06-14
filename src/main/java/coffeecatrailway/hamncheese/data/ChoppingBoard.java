package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ToolType;

/**
 * @author CoffeeCatRailway
 * Created: 3/05/2021
 */
public class ChoppingBoard
{
    public static final Codec<ChoppingBoard> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.fieldOf("pressurePlate").forGetter(board -> board.pressurePlate),
            Registry.BLOCK.fieldOf("board").forGetter(board -> board.board),
            SoundEvent.CODEC.fieldOf("sound").forGetter(board -> board.sound),
            Codec.STRING.fieldOf("toolType").forGetter(board -> board.toolType)
    ).apply(instance, (stripBlock, result, soundEvent, toolType) -> {
        if (result instanceof ChoppingBoardBlock)
            return new ChoppingBoard(stripBlock, (ChoppingBoardBlock) result, soundEvent, toolType);
        else
            throw new IllegalStateException("Result must be of type 'Chopping Board'");
    }));

    private final Block pressurePlate;
    private final ChoppingBoardBlock board;
    private final SoundEvent sound;
    private final String toolType;

    public ChoppingBoard(Block pressurePlate, ChoppingBoardBlock board, SoundEvent sound, String toolType)
    {
        this.pressurePlate = pressurePlate;
        this.board = board;
        this.sound = sound;
        this.toolType = toolType;
    }

    public Block getPressurePlate()
    {
        return this.pressurePlate;
    }

    public ChoppingBoardBlock getBoard()
    {
        return this.board;
    }

    public SoundEvent getSound()
    {
        return this.sound;
    }

    public ToolType getToolType()
    {
        return ToolType.get(this.toolType);
    }
}
