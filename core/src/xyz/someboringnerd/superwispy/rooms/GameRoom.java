package xyz.someboringnerd.superwispy.rooms;

import box2dLight.DirectionalLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.GlobalData;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom extends AbstractRoom
{

    public List<Chunk> chunklist = new ArrayList<>();

    Tile sky = new Tile("tiles/Sky", new Vector2(0, 0), new Vector2(2560, 1440));

    public static AtomicInteger firstY = new AtomicInteger(65);

    Texture cursor = FileManager.getTexture("cursor");
    Texture selector = FileManager.getTexture("selector");

    public static Vector2 selectorPos = new Vector2();

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

        player = new PlayerEntity(new Vector2(256, 68 * 32));
        chunklist.add(new Chunk(0));

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("rescources/textures/hidden.png")), 0, 0));
    }

    @Override
    public void render(Batch batch)
    {
        sky.setPosition((int)RenderUtil.getXRelativeZero() - 1280 / 2, (int)RenderUtil.getYRelativeZero() - 720 - 720 / 2);

        if(Gdx.input.isKeyJustPressed(Input.Keys.F3))
        {
            GlobalData.displayDebugInformation = !GlobalData.displayDebugInformation;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F4))
        {
            GlobalData.showChunkPlayerIsIn = !GlobalData.showChunkPlayerIsIn;
        }

        for(Chunk chunk : LoadedChunks())
        {
            chunk.Draw(batch);
        }

        player.draw(batch, 1.0f);

        batch.draw(cursor, getMousePosition().x - 24, getMousePosition().y - 24);
        batch.draw(selector, selectorPos.x, selectorPos.y, Block.BLOCK_SCALE, Block.BLOCK_SCALE);
    }

    public List<Chunk> safeLoaded()
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

    public List<Chunk> LoadedChunks()
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

    public static Rectangle getMousePosition()
    {
        return new Rectangle( RenderUtil.getXRelativeZero() +
            ((float) Gdx.input.getX() * ((float) 1280 / (Gdx.graphics.getWidth() != 0 ? Gdx.graphics.getWidth() : 1280))) ,
            RenderUtil.getYRelativeZero() - ((float) Gdx.input.getY() * ((float) 720 /  (Gdx.graphics.getHeight() != 0 ? Gdx.graphics.getHeight() : 720))) ,
            1, 1);
    }
}
