package xyz.someboringnerd.superwispy.content.blocks.simple;

import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class LeaveBlock extends Block
{
    public LeaveBlock(Vector2 position, Chunk chunk)
    {
        super(position, chunk);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
