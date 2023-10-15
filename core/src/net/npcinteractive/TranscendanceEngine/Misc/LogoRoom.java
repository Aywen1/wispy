package net.npcinteractive.TranscendanceEngine.Misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

public class LogoRoom extends AbstractRoom
{

    Texture logo = FileManager.getTexture("transcendance.png");

    @Override
    public Vector2 roomSize() {
        return new Vector2(1280, 720);
    }

    @Override
    public void initInteractions() {

    }

    @Override
    public String roomMusic() {
        return null;
    }

    @Override
    public void init()
    {
        time = 1;
        reverse = false;

        if(FileManager.getBooleanFromConfig("skipLogo"))
        {
            RoomManager.LoadRoom(FileManager.getStringFromConfig("FirstRoom"), true);
        }
    }

    int time = 1;
    boolean reverse;
    @Override
    public void render(Batch batch)
    {
        batch.setColor(1, 1, 1, (float) time / 255);
        RenderUtil.Deter72px.setColor(1, 1, 1, (float) time / 255);
        RenderUtil.Deter30px.setColor(1, 1, 1, (float) time / 255);
        RenderUtil.DrawText(batch, "Powered by",
                new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - RenderUtil.getStringWidth("Powered by", RenderUtil.Deter72px
                ) / 2, RenderUtil.getYRelativeZero() - 50),
                RenderUtil.Deter72px);

        batch.draw(logo, RenderUtil.getXRelativeZero() + 1280 / 2 - 336, RenderUtil.getYRelativeZero() - 720 / 2 - 224, 336 * 2, 224 * 2);

        RenderUtil.DrawText(batch, "Transcendance Engine",
                new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - RenderUtil.getStringWidth("Transcendance Engine", RenderUtil.Deter72px) / 2,
                        RenderUtil.getYRelativeZero() - 625), RenderUtil.Deter72px);
        RenderUtil.DrawText(batch, "Built on top of Libgdx !",
                new Vector2(RenderUtil.getXRelativeZero() + 1280 / 2 - RenderUtil.getStringWidth("Built on top of Libgdx !", RenderUtil.Deter30px) / 2,
                        RenderUtil.getYRelativeZero() - 680), RenderUtil.Deter30px);
        batch.setColor(1, 1, 1, 1);



        if(reverse)
            time-=2;
        else
            time+=2;


        if(time >= 255)
            reverse = true;

        if(time <= - 10)
        {
            RenderUtil.Deter72px.setColor(1, 1, 1, 1);
            RenderUtil.Deter30px.setColor(1, 1, 1, 1);
            RoomManager.LoadRoom(FileManager.getStringFromConfig("FirstRoom"), true);
        }
    }
}
