package xyz.someboringnerd.superwispy.content.blocks.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

public class AirBlock extends Block {
    public AirBlock(Vector2 position, Chunk chunk)
    {
        super(position, chunk);
    }

    @Override
    public boolean hasCollision()
    {
        return false;
    }

    @Override
    public void Update()
    {
        if(getBoundingBox().overlaps(GameRoom.getMousePosition()) && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
        {
            replaceSelf(BlockManager.getBlockByID(new Vector2((int) getChunkPosition().x, (int) getChunkPosition().y), BlockManager.selected, chunk));
        }
    }
}
