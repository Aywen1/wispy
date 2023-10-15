package xyz.someboringnerd.superwispy.rooms;

import box2dLight.DirectionalLight;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom extends AbstractRoom
{

    List<Chunk> chunklist = new ArrayList<>();

    Tile sky = new Tile("Sky", new Vector2(0, 0), new Vector2(2560, 1440));

    public static AtomicInteger firstY = new AtomicInteger(65);

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

    private DirectionalLight Sun;

    @Override
    public void init()
    {
        tiles.add(sky);

        instance = this;

        player = new PlayerEntity(new Vector2(256, 64 * 32 + 256));
        chunklist.add(new Chunk(0));
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
            if((isChunkLoaded(ch)))
            {
                chunks.add(ch);
            }
        }

        return chunks;
    }

    public static boolean isChunkLoaded(Chunk chunk)
    {
        return Math.abs(PlayerEntity.getInstance().getX() - chunk.getOffset()) <= 1280;
    }

    public void Generate(int offset)
    {
        if(!doesChunkExistAtOffset(offset + 512))
        {
            chunklist.add(new Chunk(offset + 512));
        }
        else if(!doesChunkExistAtOffset(offset - 512))
        {
            chunklist.add(new Chunk(offset - 512));
        }

    }

    public Chunk getChunkAtOffset(int i)
    {
        for (Chunk chunk : chunklist)
        {
            if(chunk.getOffset() == i){
                return chunk;
            }
        }

        return null;
    }

    public boolean doesChunkExistAtOffset(int i)
    {
        for (Chunk chunk : chunklist)
        {
            if(chunk.getOffset() == i){
                return true;
            }
        }

        return false;
    }
}
