package xyz.someboringnerd.superwispy.content;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk
{
    public List<Block> blockList = new ArrayList<>();

    @Getter(AccessLevel.PUBLIC)
    private int offset;

    public Chunk(int offset)
    {
        this.offset = offset;
        for(int i = 0; i < 16; i++)
        {
            Random rng = new Random();

            int y = rng.nextInt(63, 66);

            blockList.add(new Block("blocks/grass", new Vector2(offset + i * 32, y * 32)));

            for(int j = y - 1; j > 0; j--)
            {
                if(j >= 60)
                {
                    blockList.add(new Block(("blocks/dirt"), new Vector2(offset + i * 32, j * 32)));
                }else{
                    blockList.add(new Block(("blocks/stone"), new Vector2(offset + i * 32, j * 32)));
                }
            }
        }
    }

    boolean isPlayerInChunk()
    {
        return GameRoom.getInstance().getPlayer().getX() >= offset && offset + 16 * 32 >= GameRoom.getInstance().getPlayer().getX();
    }

    public void Draw(Batch batch)
    {
        if(isPlayerInChunk())
            batch.setColor(0, 1, 0, 1);
        for (Block block : blockList)
        {
            block.draw(batch, 1.0f);
        }

        batch.setColor(1, 1, 1, 1);
    }
}
