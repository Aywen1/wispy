package net.npcinteractive.TranscendanceEngine.Entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;

import static net.npcinteractive.TranscendanceEngine.Managers.RoomManager.world;

public abstract class Entity extends Actor
{
    public static Rectangle boundingBox;

    BodyDef bodyDef;
    public Body body;
    TextureRegion region;
    public DIRECTION direction;

    public static final float WIDTH = 64;
    public static final float HEIGHT = 32;

    public Entity(Vector2 initialPosition, String path)
    {
        if(world == null) return;

        setPosition(initialPosition.x, initialPosition.y);
    }

    public void Init(AbstractRoom room, Vector2 position, DIRECTION direction)
    {

    }
}
