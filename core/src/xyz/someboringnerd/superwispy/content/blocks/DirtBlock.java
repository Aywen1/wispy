package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class DirtBlock extends Block
{
    public DirtBlock(Vector2 position, Chunk chunk)
    {
        super(position, 2, chunk);
    }

    @Override
    public void RandomTick()
    {
        // check si le block au dessus n'a pas de collision ou n'existe pas
        if((getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)) == null || !getBlockAtCoordinates(new Vector2(getChunkPosition().x, getChunkPosition().y + 1)).hasCollision()) &&
            // check si le block a gauche est un block de grass
            ((getBlockAtCoordinates(new Vector2(getChunkPosition().x - 1, getChunkPosition().y)) != null && getBlockAtCoordinates(new Vector2(getChunkPosition().x - 1, getChunkPosition().y)) instanceof GrassBlock) ||
            // check si le block a droite est un block de grass
            (getBlockAtCoordinates(new Vector2(getChunkPosition().x + 1, getChunkPosition().y)) != null && getBlockAtCoordinates(new Vector2(getChunkPosition().x + 1, getChunkPosition().y)) instanceof GrassBlock)))
        {
            chunk.blockList[(int) getChunkPosition().x][(int) getChunkPosition().y] = new GrassBlock(new Vector2((int) getChunkPosition().x, (int) getChunkPosition().y), chunk);
        }
    }
}
