package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class AirBlock extends Block {
    public AirBlock(Vector2 position, Chunk chunk) {
        super(position, 0, chunk);
    }

    @Override
    public boolean hasCollision()
    {
        return false;
    }
}
