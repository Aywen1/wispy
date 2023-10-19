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
import lombok.Setter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.GlobalData;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;
import xyz.someboringnerd.superwispy.gui.GUI;
import xyz.someboringnerd.superwispy.managers.BlockManager;

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

    public static boolean isInSelector(Block block)
    {
        return selectorPos.x == block.getRealCoordinate().x && selectorPos.y == block.getRealCoordinate().y;
    }

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

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private static GUI gui;

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

        BlockManager.Update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F3))
        {
            GlobalData.displayDebugInformation = !GlobalData.displayDebugInformation;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F4))
        {
            GlobalData.showChunkPlayerIsIn = !GlobalData.showChunkPlayerIsIn;
        }

        for(Chunk chunk : safeLoaded())
        {
            chunk.Draw(batch);
        }

        player.draw(batch, 1.0f);

        if(GameRoom.getGui() == null)
            batch.draw(selector, selectorPos.x, selectorPos.y, Block.BLOCK_SCALE, Block.BLOCK_SCALE);
        else
            GameRoom.getGui().Draw(batch);

        batch.draw(cursor, getMousePosition().x - 24, getMousePosition().y - 24);
    }

    /**
     * @return la liste des chunks chargés pour les dessiner
     */
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

    /**
     * Determine si le chunk est assez proche du joueur pour être dessiné
     * @param chunk instance du chunk
     * @return vrai si le chunk est a moins de 1280 pixels du joueur en partant de son block le plus a gauche
     */
    public static boolean isChunkLoaded(Chunk chunk)
    {
        return Math.abs(PlayerEntity.getInstance().getX() - chunk.getOffset()) <= 1280;
    }

    /**
     * Genère un chunk basé sur la position du précédent
     * @param offset : position réelle du chunk sur la map (multiple positif ou négatif de 512)
     */
    public void Generate(int offset)
    {
        // genère un chunk a droite de celui existant
        if(!doesChunkExistAtOffset(offset + 512))
        {
            chunklist.add(new Chunk(offset + 512));
        }
        // genère un chunk a gauche de celui existant
        else if(!doesChunkExistAtOffset(offset - 512))
        {
            chunklist.add(new Chunk(offset - 512));
        }

    }

    /**
     * Obtient le chunk a un offset donné
     * @param offset : offset du chunk cherché
     * @return : l'instance du chunk, si elle existe
     */
    public Chunk getChunkAtOffset(int offset)
    {
        for (Chunk chunk : chunklist)
        {
            if(chunk.getOffset() == offset){
                return chunk;
            }
        }

        return null;
    }

    /**
     * cherche si un chunk a un offset donné existe ou non
     * @param offset : offset du chunk cherché
     * @return vrai si le chunk existe
     */
    public boolean doesChunkExistAtOffset(int offset)
    {
        return getChunkAtOffset(offset) != null;
    }

    /**
     * @return la position approximative du curseur
     */
    public static Rectangle getMousePosition()
    {
        return new Rectangle( RenderUtil.getXRelativeZero() +
            ((float) Gdx.input.getX() * ((float) 1280 / (Gdx.graphics.getWidth() != 0 ? Gdx.graphics.getWidth() : 1280))) ,
            RenderUtil.getYRelativeZero() - ((float) Gdx.input.getY() * ((float) 720 /  (Gdx.graphics.getHeight() != 0 ? Gdx.graphics.getHeight() : 720))) ,
            1, 1);
    }
}
