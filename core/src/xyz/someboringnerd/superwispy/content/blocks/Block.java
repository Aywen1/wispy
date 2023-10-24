package xyz.someboringnerd.superwispy.content.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.content.items.debug.BlockItem;
import xyz.someboringnerd.superwispy.entities.DroppedItemEntity;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.managers.ItemManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;
import xyz.someboringnerd.superwispy.util.ItemStack;

public class Block extends Actor
{

    public boolean markForDelete;

    @Getter(AccessLevel.PUBLIC)
    private Texture texture;

    @Getter(AccessLevel.PUBLIC)
    private int id;
    public Chunk chunk;
    public Body body;

    @Getter(AccessLevel.PUBLIC)
    private Vector2 chunkPosition;

    public static final int BLOCK_SCALE = 32;

    public Block(Vector2 position, Chunk chunk)
    {
        chunkPosition = position;
        setScale(BLOCK_SCALE, BLOCK_SCALE);
        setZIndex(0);

        this.chunk = chunk;
        this.id = BlockManager.getIDFromBlock(this);

        texture = BlockManager.registeredBlocks.get(id);
    }

    /**
     * Obtient la bounding box du block.
     * @return rectangle
     */
    public Rectangle getBoundingBox()
    {
        if(body != null)
            return new Rectangle(body.getPosition().x, body.getPosition().y, BLOCK_SCALE, BLOCK_SCALE);
        else
            return new Rectangle(getRealCoordinate().x + (getScaleX() / 2), getRealCoordinate().y + (getScaleY() / 2), BLOCK_SCALE, BLOCK_SCALE);
    }

    /**
     * génère la boite de collision du block
     * @param world : monde physique de Box2D
     */
    public void createBody(World world)
    {
        if(getX() % 16 != 0 && getY() % 16 != 0 || !hasCollision())
        {
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
    }

    /**
     * Remplace le block par une instance d'un autre block sans le dropper
     * @param instance instance d'un block a remplacer
     */
    public void replaceSelf(Block instance)
    {
        replaceSelf(instance, false);
    }

    /**
     * Remplace le block par une instance d'un autre block
     * @param instance instance d'un block a remplacer
     * @param drop si le block doit se dropper
     */
    public void replaceSelf(Block instance, boolean drop)
    {
        if(body != null)
            RoomManager.world.destroyBody(body);

        chunk.blockList[(int) getChunkPosition().x][(int) getChunkPosition().y] = instance;

        if(drop)
        {
            ItemManager.dropSomething(new DroppedItemEntity(new Vector2(getRealCoordinate().x, getRealCoordinate().y + 16), new ItemStack(new BlockItem(this))));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(GameRoom.getMousePosition().overlaps(getBoundingBox()))
        {
            GameRoom.selectorPos.set(getRealCoordinate().x, getRealCoordinate().y);
        }

        batch.draw(texture, getRealCoordinate().x, getRealCoordinate().y, BLOCK_SCALE, BLOCK_SCALE);

        if(GameRoom.isInSelector(this) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && getChunkPosition().y != 0 && body != null && GameRoom.getGui() == null)
        {
            chunk.setToEdit(this);
        }
    }

    public Block getBlockAtCoordinates(Vector2 chunkPosition)
    {
        return Block.getBlockAtCoordinates(chunkPosition, chunk);
    }

    /**
     * Obtient un block dans le chunk.
     * @todo : faire en sorte que le block puisse récuperer un block dans un autre chunk.
     * @return vecteur
     */
    public static Block getBlockAtCoordinates(Vector2 chunkPosition, Chunk chunk)
    {
        if(chunkPosition.y < 0 || chunkPosition.y > 255)
        {
            return null;
        }

        if(chunkPosition.x < 0)
        {
            if(GameRoom.getInstance().doesChunkExistAtOffset(chunk.getOffset() - 512))
            {
                return GameRoom.getInstance().getChunkAtOffset(chunk.getOffset() - 512).getBlockAtCoordinates(new Vector2((16 + chunkPosition.x), chunkPosition.y));
            }
            return null;
        }
        else if (chunkPosition.x > 15)
        {

            if(GameRoom.getInstance().doesChunkExistAtOffset(chunk.getOffset() + 512))
            {
                return GameRoom.getInstance().getChunkAtOffset(chunk.getOffset() + 512).getBlockAtCoordinates(new Vector2(16 - (16 + chunkPosition.x), chunkPosition.y));
            }

            return null;
        }

        return chunk.blockList[(int) chunkPosition.x][(int) chunkPosition.y];
    }

    /**
     * Coordonnées dans le monde du block
     * @return vecteur
     */
    public Vector2 getRealCoordinate()
    {
        return new Vector2(chunkPosition.x * 32 + chunk.getOffset(), chunkPosition.y * 32);
    }

    /**
     * Overrided par les blocks aillant besoin d'executer du code
     */
    public void Update(){}

    /**
     * Overrided par les blocks n'aillant pas de collision comme le block d'air
     */
    public boolean hasCollision()
    {
        return true;
    }

    /**
     * Overrided par les blocks n'aillant pas de collision comme le block d'air
     */
    public boolean isTransparent()
    {
        return false;
    }
}
