package net.npcinteractive.TranscendanceEngine.Misc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

public class ErrorRoom extends AbstractRoom {
    @Override
    public String roomMusic()
    {
        return "null";
    }

    @Override
    public Vector2 roomSize() {
        return new Vector2(1280, 720);
    }

    @Override
    public void initInteractions() {

    }

    @Override
    public void init()
    {
    }

    @Override
    public void render(Batch batch)
    {
        RenderUtil.DrawText(batch, "You are trying to load a room that don't exist.\n\nif this is not intentional, please contact the game developer to fix it.\n\nPress E to return to the first screen", new Vector2(RenderUtil.getXRelativeZero() + 25, RenderUtil.getYRelativeZero() - 20), RenderUtil.TextBoxFont, 1260);

        if(InputSystem.interact())
        {
            RoomManager.LoadRoom(FileManager.getStringFromConfig("FirstRoom"), true);
        }
    }
}
