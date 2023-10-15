package net.npcinteractive.TranscendanceEngine.Events;

import com.badlogic.gdx.graphics.g2d.Batch;
import meteordevelopment.orbit.ICancellable;

public class GameRenderEvent implements ICancellable
{

    private static final GameRenderEvent gre = new GameRenderEvent();

    private Batch batch;

    public Batch getBatch()
    {
        return batch;
    }

    public static GameRenderEvent get(Batch batch)
    {
        gre.batch = batch;
        return gre;
    }


    @Override
    public void setCancelled(boolean cancelled) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
