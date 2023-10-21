package xyz.someboringnerd.superwispy.content.blocks.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.items.debug.BlockItem;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.managers.InventoryManager;
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
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void Update()
    {
        if(getBoundingBox().overlaps(GameRoom.getMousePosition()) && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
        {
            if((InventoryManager.getInstance().getItemInSlot(PlayerEntity.getInstance().getHotbar().getSelected()).getItem() instanceof BlockItem))
            {
                InventoryManager.getInstance().getItemInSlot(PlayerEntity.getInstance().getHotbar().getSelected()).setQuantity(InventoryManager.getInstance().getItemInSlot(PlayerEntity.getInstance().getHotbar().getSelected()).getQuantity() - 1);
                replaceSelf(BlockManager.getBlockByID(new Vector2((int) getChunkPosition().x, (int) getChunkPosition().y), InventoryManager.getInstance().getItemInSlot(PlayerEntity.getInstance().getHotbar().getSelected()).getItem().getID(), chunk));
            }
        }
    }
}
