package net.npcinteractive.TranscendanceEngine.Events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class WindowResizeListener  implements EventListener {
    @Override
    public boolean handle (Event event)
    {
        resize();
        return false;
    }

    public abstract void resize ();
}

