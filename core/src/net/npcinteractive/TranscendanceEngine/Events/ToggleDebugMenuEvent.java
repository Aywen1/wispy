package net.npcinteractive.TranscendanceEngine.Events;

import meteordevelopment.orbit.ICancellable;

public class ToggleDebugMenuEvent implements ICancellable
{

    public static final ToggleDebugMenuEvent tdme = new ToggleDebugMenuEvent();

    private boolean visible;

    public boolean isVisible()
    {
        return visible;
    }

    public static ToggleDebugMenuEvent get(boolean visible)
    {
        tdme.visible = visible;
        return tdme;
    }

    @Override
    public void setCancelled(boolean cancelled) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
