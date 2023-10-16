package xyz.someboringnerd.superwispy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.npcinteractive.TranscendanceEngine.MainClass;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import org.json.JSONObject;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;
import xyz.someboringnerd.superwispy.managers.BlockManager;

public class Game extends MainClass
{

    @Override
    public void Init()
    {
        new BlockManager();
    }

    @Override
    public void Render()
    {
        if(PlayerEntity.getInstance() != null)
        {
            RenderUtil.getViewport().getCamera().position.lerp(new Vector3((int) PlayerEntity.getInstance().getX(), (int) PlayerEntity.getInstance().getY(), 0), 0.075f);
        }
    }

    @Override
    public void ParseSave(JSONObject saveData) {

    }

    @Override
    public void ParseOptions(JSONObject saveData) {

    }

    @Override
    public JSONObject Save(JSONObject add)
    {
        add.put("test", "some values idk");
        return add;
    }

    @Override
    public JSONObject SaveOptions() {
        return null;
    }
}
