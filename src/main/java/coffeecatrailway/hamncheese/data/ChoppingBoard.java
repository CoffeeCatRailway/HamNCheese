package coffeecatrailway.hamncheese.data;

import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
            Registry.BLOCK.fieldOf("stripBlock").forGetter(board -> board.stripBlock),
            Registry.BLOCK.fieldOf("result").forGetter(board -> board.result),
            SoundEvent.CODEC.fieldOf("stripSound").forGetter(board -> board.stripSound),
            Codec.STRING.fieldOf("toolType").forGetter(board -> board.toolType)
    ).apply(instance, (stripBlock, result, soundEvent, toolType) -> {
        if (result instanceof ChoppingBoardBlock)
            return new ChoppingBoard(stripBlock, (ChoppingBoardBlock) result, soundEvent, toolType);
        else
            throw new IllegalStateException("Result must be of type 'Chopping Board'");
    }));

    private final Block stripBlock;
    private final ChoppingBoardBlock result;
    private final SoundEvent stripSound;
    private final String toolType;

    public ChoppingBoard(Block stripBlock, ChoppingBoardBlock result, SoundEvent stripSound, String toolType)
    {
        this.stripBlock = stripBlock;
        this.result = result;
        this.stripSound = stripSound;
        this.toolType = toolType;
    }

    public Block getStripBlock()
    {
        return this.stripBlock;
    }

    public ChoppingBoardBlock getResult()
    {
        return this.result;
    }

    public SoundEvent getStripSound()
    {
        return this.stripSound;
    }

    public ToolType getToolType()
    {
        return ToolType.get(this.toolType);
    }
}
