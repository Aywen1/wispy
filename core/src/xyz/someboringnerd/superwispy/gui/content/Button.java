package xyz.someboringnerd.superwispy.gui.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

public class Button
{
    private IInteractible interactible;

    private Vector2 position, scale;

    private String displayName;

    private final Texture blank = FileManager.getTexture("tiles/BlankTile");

    public Button(IInteractible interactible, String displayName, Vector2 position, Vector2 scale)
    {
        this.interactible = interactible;
        this.displayName = displayName;
        this.position = position;
        this.scale = scale;
    }

    public void Draw(Batch batch, Rectangle mousePosition)
    {
        Rectangle rect = new Rectangle(position.x, position.y, scale.x, scale.y);

        if(!rect.overlaps(mousePosition))
            batch.setColor(0, 0, 0, 1);
        else
            batch.setColor(1, 1, 1, 1);

        batch.draw(blank, position.x, position.y, scale.x, scale.y);

        if(!rect.overlaps(mousePosition))
            batch.setColor(1, 1, 1, 1);
        else
            batch.setColor(0, 0, 0, 1);

        batch.draw(blank, position.x + 10, position.y + 10, scale.x - 20, scale.y - 20);

        if(!rect.overlaps(mousePosition))
            RenderUtil.Deter52px.setColor(0, 0, 0, 1);
        else
            RenderUtil.Deter52px.setColor(1, 1, 1, 1);

        RenderUtil.DrawText(batch, displayName,
                new Vector2(position.x + scale.x / 2 - RenderUtil.getStringWidth(displayName, RenderUtil.Deter52px) / 2,
                        position.y + scale.y / 2 + 20), RenderUtil.Deter52px);

        RenderUtil.Deter52px.setColor(1, 1, 1, 1);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && rect.overlaps(mousePosition))
        {
            try {
                interactible.onInteract(0);
            } catch (NoInteractionForRoom e) {
                throw new RuntimeException(e);
            }
        }
    }
}
