package xyz.someboringnerd.superwispy.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

/**
 * Les enfants de cette classe sont l'équivalent des Screen de Minecraft, un seul peut être affiché en même temps.
 * Ne pas confondre avec le HUD !
 */
public class GUI
{
    private Texture image;

    private Vector2 scale;

    @Getter(AccessLevel.PUBLIC)
    private boolean canPlayerMove;

    public GUI(Texture texture, Vector2 scale, boolean PlayerAllowedToMove)
    {
        this.image = texture;
        this.scale = scale;
        this.canPlayerMove = PlayerAllowedToMove;
    }

    public GUI(Texture texture, boolean PlayerAllowedToMove)
    {
        this.image = texture;
        this.canPlayerMove = PlayerAllowedToMove;
    }

    public void Draw(Batch batch)
    {
        batch.draw(image, RenderUtil.getXRelativeZero() + 640 - getScale().x / 2, RenderUtil.getYRelativeZero() - 360 - getScale().x / 2, getScale().x, getScale().y);

        if(InputSystem.Return())
        {
            GameRoom.setGui(null);
        }
    }

    public Vector2 getScale()
    {
        return scale == null ? new Vector2(image.getWidth(), image.getHeight()) : scale;
    }
}
