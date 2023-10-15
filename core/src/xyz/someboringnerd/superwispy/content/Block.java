package xyz.someboringnerd.superwispy.content;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Map.DebugCube;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import org.w3c.dom.Text;

public class Block extends Tile
{
    @Getter(AccessLevel.PUBLIC)
    private Vector2 position;


    public Block(String texture, Vector2 position)
    {
        super(texture, position, new Vector2(32, 32));

        this.position = position;

        AddCollider(new DebugCube(Color.BLACK, this.position, new Vector2(32, 32)));
    }
}
