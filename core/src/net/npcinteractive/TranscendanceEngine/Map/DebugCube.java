package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import meteordevelopment.orbit.EventHandler;
import net.npcinteractive.TranscendanceEngine.Events.GameRenderEvent;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.EventManager;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Events.ReceiveRaycastEvent;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Util.COLLIDER_TYPE;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.managers.BlockManager;

public class DebugCube extends Tile
{
    Color color;
    public COLLIDER_TYPE type;
    public Body body;

    private AbstractRoom loaded;

    public DebugCube(Color color, Vector2 position, Vector2 scale, COLLIDER_TYPE type, Chunk chunk)
    {
        super(BlockManager.registeredBlocks.get(0), position, scale, 0, chunk);

        this.color = color;
        this.type = type;

        setName("DebugCube-" + color);
        boundingBox = new Rectangle(getX(), getY(), getScaleX(), getScaleY());
        if(!shouldInitLater)
            createBody(RoomManager.world);
    }

    public DebugCube setObjectName(String name)
    {
        setName(name);
        return this;
    }

    public boolean shouldInitLater = !(Thread.currentThread().getId() == 1);

    IInteractible iInteractible;
    public int interactionID;
    public DebugCube AddInteraction(IInteractible iInteractible, int interactionID)
    {
        this.iInteractible = iInteractible;
        this.interactionID = interactionID;
        EventManager.EVENT_BUS.subscribe(this);
        loaded = RoomManager.getLoaded();
        return this;
    }

    @EventHandler
    public void onInteract(ReceiveRaycastEvent event)
    {
        if(loaded.getName() != RoomManager.getLoaded().getName()) return;

        if(iInteractible == null)
        {
            LogManager.error("But the event has no Interactible to trigger...");
        }
        if(boundingBox.overlaps(event.getRaycast()) && iInteractible != null)
        {
            try {
                iInteractible.onInteract(interactionID);
            } catch (NoInteractionForRoom e) {
                throw new RuntimeException(e);
            }
        }
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

        bodyDef.position.set(getX() + (getScaleX() / 2), (getScaleY() / 2) + getY());

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
    public void Dispose()
    {
        LogManager.print("Destroying self");
        RoomManager.world.destroyBody(body);
    }
}
