package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;

public class StoneBlock extends Block {
    public StoneBlock(Vector2 position, Chunk chunk) {
        super(position, 3, chunk);
    }
}
