package net.npcinteractive.TranscendanceEngine.Events;

import com.badlogic.gdx.math.Vector2;
import meteordevelopment.orbit.ICancellable;

public class WindowResizeEvent implements ICancellable
{
    private static final WindowResizeEvent WSR = new WindowResizeEvent();

    public Vector2 newSize;

    public static WindowResizeEvent post(Vector2 newSize)
    {
        WSR.newSize = newSize;
        return WSR;
    }

    @Override
    public void setCancelled(boolean cancelled) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
