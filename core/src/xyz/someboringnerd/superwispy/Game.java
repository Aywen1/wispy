package xyz.someboringnerd.superwispy;

import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.MainClass;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import org.json.JSONObject;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;

public class Game extends MainClass
{

    @Override
    public void Init() {

    }

    @Override
    public void Render()
    {
        if(PlayerEntity.getInstance() != null)
        {
            RenderUtil.getViewport().getCamera().position.set(new Vector2((int) PlayerEntity.getInstance().getX(), (int) PlayerEntity.getInstance().getY()), 0);
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
