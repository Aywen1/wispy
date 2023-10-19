package xyz.someboringnerd.superwispy.content.blocks.simple;

import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class GrassBlock extends Block
{
    public GrassBlock(Vector2 position, Chunk chunk)
    {
        super(position, chunk);
    }

    @Override
    public void Update()
    {
        if(getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)) != null && getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)).hasCollision())
        {
            replaceSelf(new DirtBlock(new Vector2((int) getChunkPosition().x, (int) getChunkPosition().y), chunk));
        }
    }
}
