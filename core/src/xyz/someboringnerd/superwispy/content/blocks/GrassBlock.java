package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class GrassBlock extends Block
{
    public GrassBlock(Vector2 position, Chunk chunk)
    {
        super(position, 1, chunk);
    }

    @Override
    public void RandomTick()
    {
        if(getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)) != null && getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)).hasCollision())
        {
            chunk.blockList[(int) getChunkPosition().x][(int) getChunkPosition().y] = new DirtBlock(new Vector2((int) getChunkPosition().x, (int) getChunkPosition().y), chunk);
        }
    }
}
