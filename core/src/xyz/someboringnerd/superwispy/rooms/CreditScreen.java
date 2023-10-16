package xyz.someboringnerd.superwispy.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Managers.TranslationManager;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.gui.content.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreditScreen extends AbstractRoom
{
    private Button BackToMain;

    @Override
    public String roomMusic() {
        return "mainmenu";
    }
    private List<String> peopleList = new ArrayList<>();
    private List<String> toolList = new ArrayList<>();
    private List<String> libList = new ArrayList<>();

    private String title;

    GlyphLayout layout = new GlyphLayout();

    @Override
    public void init()
    {
        peopleList.clear();

        peopleList.addAll(Arrays.asList(TranslationManager.getRoom("CreditScreen", "peoples")));

        title = TranslationManager.getString("Rooms/CreditScreen.json", "Title");

        BackToMain = new Button(new IInteractible()
        {
            @Override
            public void onInteract(int id) throws NoInteractionForRoom
            {
                RoomManager.LoadRoom("MainMenu");
            }
        }, "Retour",
                new Vector2(RenderUtil.getXRelativeZero() + 50, RenderUtil.getYRelativeZero() - 680),
                new Vector2(300, 100)
        );
    }
    @Override
    public Vector2 roomSize() {
        return new Vector2(1280, 720);
    }

    @Override
    public void initInteractions() {

    }

    @Override
    public void render(Batch batch)
    {
        layout.setText(RenderUtil.TextBoxFont, title);

        if(InputSystem.interact()) RoomManager.LoadRoom("MainMenu");

        RenderUtil.DrawText(batch, title, new Vector2(RenderUtil.getXRelativeZero() + (1280 / 2) - (layout.width / 2), RenderUtil.getYRelativeZero() - 20), RenderUtil.TextBoxFont);
        int y = 80;
        for (String str: peopleList)
        {
            layout.setText(RenderUtil.Deter30px, str);
            RenderUtil.DrawText(batch, str, new Vector2(RenderUtil.getXRelativeZero() + (50), RenderUtil.getYRelativeZero() - y), RenderUtil.Deter30px, 1230);
            y += 80;
        }

        BackToMain.Draw(batch, GameRoom.getMousePosition());
    }
}
