package net.npcinteractive.TranscendanceEngine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;
import org.json.JSONObject;

public class FileManager
{
    public static Texture getTexture(String path)
    {
        if(Gdx.files.internal("rescources/textures/" + MiscUtil.addIfAbsent(path, ".png")).exists())
        {
            return new Texture(Gdx.files.internal("rescources/textures/" + MiscUtil.addIfAbsent(path, ".png")));
        }else
        {
            if(RoomManager.getLoaded() == null) return new Texture(Gdx.files.internal("rescources/textures/missing.png"));
            
            if(!(path == "tiles/missing" || path == "tiles/missing.png"))
                LogManager.error("The tile " + path + " couldnt be found !!!!" + " Room : " + RoomManager.getLoaded().getName());

            return new Texture(Gdx.files.internal("rescources/textures/missing.png"));
        }
    }

    public static String getStringFromConfig(String key)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/transcendance_config.json");
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);

        return jsonObject.getString(key);
    }

    public static boolean getBooleanFromConfig(String key)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/transcendance_config.json");
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);

        return jsonObject.getBoolean(key);
    }
}
