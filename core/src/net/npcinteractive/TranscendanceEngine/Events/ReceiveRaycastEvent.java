package net.npcinteractive.TranscendanceEngine.Events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import meteordevelopment.orbit.ICancellable;

public class ReceiveRaycastEvent implements ICancellable
{

    private static final ReceiveRaycastEvent rre = new ReceiveRaycastEvent();

    private Rectangle raycast;
    private Body body;

    public Rectangle getRaycast()
    {
        return raycast;
    }

    public Body getBody() {
        return body;
    }

    public static ReceiveRaycastEvent get(Body body, Rectangle raycast)
    {
        rre.raycast = raycast;
        rre.body = body;
        return rre;
    }


    @Override
    public void setCancelled(boolean cancelled) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}