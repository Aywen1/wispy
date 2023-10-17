package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;
import net.npcinteractive.TranscendanceEngine.Util.SHADER;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

import java.util.Objects;

public class Tile extends Actor implements Cloneable
{
    public String texture;

    @Getter(AccessLevel.PUBLIC)
    Texture _texture;

    public Rectangle boundingBox;
    public boolean markForDelete;
    public DebugCube cube;

    boolean loadFromTextureOrString;
    public int id;

    Chunk chunk;

    public Tile(Texture texture, Vector2 position, Vector2 scale, int index, Chunk chunk)
    {
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        setZIndex(0);
        loadFromTextureOrString = true;
        this.chunk = chunk;
        this.id = index;
        _texture = texture;

    }

    public Tile(String texture, Vector2 position, Vector2 scale)
    {
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        setZIndex(0);

        loadFromTextureOrString = false;

        if(Thread.currentThread().getId() == 1)
            _texture = FileManager.getTexture(texture);

    }

    Shader _shader;

    public Tile(Texture texture, Vector2 position, Vector2 scale, SHADER shader)
    {
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        setZIndex(0);

        loadFromTextureOrString = true;

        //if(Thread.currentThread().getId() == 1)
            _texture = texture;

        if(Thread.currentThread().getId() == 1)
            _shader = new Shader(shader);
    }

    public Tile AddCollider(DebugCube debug)
    {
        cube = debug;
        cube.setName(texture);
        return this;
    }

    public void Dispose(){}

    private float time;

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    boolean invert = false;

    boolean init;

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(Thread.currentThread().getId() == 1 && _texture == null)
        {
            if(!loadFromTextureOrString)
            {
                _texture = FileManager.getTexture("tiles/" + texture);
            }else
            {
                _texture = BlockManager.registeredBlocks.get(id);
            }
        }

        if(cube != null)
        {
            if(cube.body != null)
            {
                boundingBox = new Rectangle(cube.body.getPosition().x, cube.body.getPosition().y, 32, 32);

                if (GameRoom.getMousePosition().overlaps(boundingBox) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && Objects.equals(RoomManager.getLoaded().getName(), "GameRoom".toLowerCase())) {
                                            // casse pas si le block est la dernière couche
                    if (cube.body != null && getY() != 0)
                    {
                        markForDelete = true;

                        System.out.println("Found a cube to delete");
                    }
                }
                cube.setPosition(boundingBox.getX(), boundingBox.getY());
                cube.setScale(getWidth(), getHeight());
            }
        }
        batch.setColor(Color.WHITE);
        if(_shader != null)
        {
            if(time <= 0.90F)
                time += 0.003F;
            else
                time = 0;

            _shader.DrawWithShader(batch, time, new Vector2(getX(), getY()), new Vector2(getScaleX(), getScaleY()));
        }
        else
        {
            batch.draw(_texture, getX(), getY(), getScaleX(), getScaleY());
        }
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
