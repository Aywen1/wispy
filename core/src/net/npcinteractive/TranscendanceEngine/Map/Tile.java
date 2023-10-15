package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;
import net.npcinteractive.TranscendanceEngine.Util.SHADER;

public class Tile extends Actor implements Cloneable
{
    public String texture;

    @Getter(AccessLevel.PUBLIC)
    Texture _texture;

    public Rectangle boundingBox;

    public DebugCube cube;


    public Tile(String texture, Vector2 position, Vector2 scale)
    {

        this.texture = MiscUtil.addIfAbsent(texture, ".png");
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        setZIndex(0);
        if(Thread.currentThread().getId() == 1)
            _texture = FileManager.getTexture("tiles/" + texture);
        setName(texture);
    }

    Shader _shader;

    public Tile(String texture, Vector2 position, Vector2 scale, SHADER shader)
    {
        this.texture = MiscUtil.addIfAbsent(texture, ".png");
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        setZIndex(0);
        if(Thread.currentThread().getId() == 1)
            _texture = FileManager.getTexture("tiles/" + texture);

        setName(texture);
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
            _texture = FileManager.getTexture("tiles/" + texture);
        }

        boundingBox = new Rectangle(getX(), 720 + getY(), getWidth(), getHeight());
        if(cube != null)
        {
            cube.setPosition(boundingBox.getX(), boundingBox.getY());
            cube.setScale(getWidth(), getHeight());
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
