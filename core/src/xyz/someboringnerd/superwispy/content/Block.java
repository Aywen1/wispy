package xyz.someboringnerd.superwispy.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Map.DebugCube;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Util.COLLIDER_TYPE;
import org.w3c.dom.Text;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

public class Block extends Actor
{

    public boolean markForDelete;
    private Texture texture;

    @Getter(AccessLevel.PUBLIC)
    private int id;
    private Chunk chunk;
    public boolean shouldInitLater = !(Thread.currentThread().getId() == 1);

    public Body body;

    @Getter(AccessLevel.PUBLIC)
    private Vector2 chunkPosition;

    public static final int BLOCK_SCALE = 32;

    public Rectangle getBoundingBox()
    {
        if(body != null)
            return new Rectangle(body.getPosition().x, body.getPosition().y, BLOCK_SCALE, BLOCK_SCALE);
        else
            return new Rectangle(getRealCoordinate().x + (getScaleX() / 2), getRealCoordinate().y + (getScaleY() / 2), BLOCK_SCALE, BLOCK_SCALE);
    }

    public Block(Vector2 position, int id, Chunk chunk)
    {
        chunkPosition = position;
        setScale(BLOCK_SCALE, BLOCK_SCALE);
        setZIndex(0);

        this.chunk = chunk;
        this.id = id;

        texture = BlockManager.registeredBlocks.get(id);

        if(!shouldInitLater)
            createBody(RoomManager.world);
    }

    public void createBody(World world)
    {
        if(getX() % 16 != 0 && getY() % 16 != 0) {
            LogManager.print("Refused to create a collider for a block not on the scale");
            return;
        }

        if(body != null) {
            body = null;
        }

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody; // Change to dynamic if needed

        bodyDef.position.set(getRealCoordinate().x + (getScaleX() / 2), getRealCoordinate().y + (getScaleY() / 2));

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(getScaleX() / 2, getScaleY() / 2);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
        shouldInitLater = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(GameRoom.getMousePosition().overlaps(getBoundingBox()))
        {
            GameRoom.selectorPos.set(getRealCoordinate().x, getRealCoordinate().y);
        }

        batch.draw(texture, getRealCoordinate().x, getRealCoordinate().y, BLOCK_SCALE, BLOCK_SCALE);


        if(GameRoom.getMousePosition().overlaps(getBoundingBox()) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && getChunkPosition().y != 0 && body != null)
        {
            markForDelete = true;
        }
    }

    private Vector2 getRealCoordinate()
    {
        return new Vector2(chunkPosition.x * 32 + chunk.getOffset(), chunkPosition.y * 32);
    }
}
