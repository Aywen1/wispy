package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class BedrockBlock extends Block
{
    public BedrockBlock(Vector2 position, Chunk chunk)
    {
        super(position, 6, chunk);
    }
}
