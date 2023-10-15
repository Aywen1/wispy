package xyz.someboringnerd.superwispy.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class GameRoom extends AbstractRoom
{

    List<Chunk> chunklist = new ArrayList<>();

    Tile sky = new Tile("Sky", new Vector2(0, 0), new Vector2(2560, 1440));

    @Getter(AccessLevel.PUBLIC)
    PlayerEntity player;

    @Getter(AccessLevel.PUBLIC)
    private static GameRoom instance;

    @Override
    public String roomMusic()
    {
        return null;
    }

    @Override
    public Vector2 roomSize()
    {
        return null;
    }

    @Override
    public void initInteractions()
    {

    }

    @Override
    public void init()
    {
        tiles.add(sky);

        instance = this;

        player = new PlayerEntity(new Vector2(800, 64 * 32 + 128));
        for(int i = -10; i < 10; i++)
        {
            Chunk newChunk = new Chunk((32 * 16) * i);
            chunklist.add(newChunk);
        }
    }

    @Override
    public void render(Batch batch)
    {
        sky.setPosition((int)RenderUtil.getXRelativeZero() - 1280 / 2, (int)RenderUtil.getYRelativeZero() - 720 - 720 / 2);

        for(Chunk chunk : LoadedChunks())
        {
            chunk.Draw(batch);
        }

        player.draw(batch, 1.0f);
    }

    private List<Chunk> LoadedChunks()
    {
        List<Chunk> chunks = new ArrayList<>();

        for(Chunk ch : chunklist)
        {

            if(Math.abs(PlayerEntity.getInstance().getX() - ch.getOffset()) <= 1280)
            {
                chunks.add(ch);


            }
        }

        return chunks;
    }
}
