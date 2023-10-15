package xyz.someboringnerd.superwispy.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.gui.content.Button;

public class MainMenu extends AbstractRoom
{
    @Override
    public String roomMusic() {
        return "null";
    }

    @Override
    public Vector2 roomSize() {
        return new Vector2(1280, 720);
    }

    public Button QuitButton, PlayButton;

    @Override
    public void initInteractions() {

    }

    @Override
    public void init()
    {
        QuitButton = new Button(new IInteractible()
        {
            @Override
            public void onInteract(int id) throws NoInteractionForRoom
            {
                Gdx.app.exit();
            }
        }, "Quitter",
                new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - 150, RenderUtil.getYRelativeZero() - 650),
                new Vector2(300, 100)
        );

        PlayButton = new Button(new IInteractible()
        {
            @Override
            public void onInteract(int id) throws NoInteractionForRoom
            {
                RoomManager.LoadRoom("GameRoom");
            }
        }, "Jouer",
                new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - 150, RenderUtil.getYRelativeZero() - 300),
                new Vector2(300, 100)
        );

        tiles.add(new Tile("bg", new Vector2(RenderUtil.getXRelativeZero(), RenderUtil.getYRelativeZero() - 720), new Vector2(1280, 720)));
    }

    @Override
    public void render(Batch batch)
    {
        Rectangle mousePosition = new Rectangle( RenderUtil.getXRelativeZero() +
                ((float) Gdx.input.getX() * ((float) 1280 / (Gdx.graphics.getWidth() != 0 ? Gdx.graphics.getWidth() : 1280))) ,
                RenderUtil.getYRelativeZero() - ((float) Gdx.input.getY() * ((float) 720 /  (Gdx.graphics.getHeight() != 0 ? Gdx.graphics.getHeight() : 720))) ,
                1, 1);

        QuitButton.Draw(batch, mousePosition);
        PlayButton.Draw(batch, mousePosition);
        RenderUtil.DrawText(batch, "SuperWispy", new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - RenderUtil.getStringWidth("SuperWispy", RenderUtil.Deter72px) / 2, RenderUtil.getYRelativeZero() - 80), RenderUtil.Deter72px);
    }
}
