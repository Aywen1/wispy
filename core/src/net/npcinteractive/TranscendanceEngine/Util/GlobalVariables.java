package net.npcinteractive.TranscendanceEngine.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import net.npcinteractive.TranscendanceEngine.TheGame;
import net.npcinteractive.TranscendanceEngine.Interfaces.DontSave;
import net.npcinteractive.TranscendanceEngine.Interfaces.Option;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;

public class GlobalVariables
{
    @DontSave
    public static boolean DrawDebugCollisions = false;
    @DontSave
    public static boolean ReadDebugInfos = false;
    public static String lastLoadedScene = "Disclaimer";

    @DontSave
    public static boolean RenderLights = true;

    public static void SaveOptions()
    {
        JSONObject options = new JSONObject();

        for(Field t: GlobalVariables.class.getFields())
        {
            if(t.getAnnotation(Option.class) != null)
            {
                try
                {
                    if (t.getType().equals(String.class))
                    {
                        options.put(t.getName(), t.get(GlobalVariables.class).toString());
                    }
                    else if (t.getType().equals(Integer.class) || t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        options.put(t.getName(), Double.parseDouble(t.get(GlobalVariables.class).toString()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        options.put(t.getName(), t.get(GlobalVariables.class));
                    }
                    else
                    {
                        LogManager.error("Cant figure out what type is " + t.getName());
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException("Couldn't save field " + t.getName() + " for the following reason : " + e);
                }
            }
        }

        options.put("GameSave", TheGame.getInstance().mainApp.SaveOptions());

        try
        {
            FileWriter writer = new FileWriter("./SaveData/options.json");
            writer.write(options.toString(5));
            writer.close();
        }
        catch (IOException ignored){}
    }

    // @todo: add serialization for special types like [REDACTED FOR THE VIDEO]
    public static void Save()
    {
        JSONObject SaveData = new JSONObject();
        
        GlobalVariables.lastLoadedScene = RoomManager.getLoaded().getName();

        for (Field t: GlobalVariables.class.getFields())
        {
            if(t.getAnnotation(DontSave.class) == null)
            {
                try
                {
                    if (t.getType().equals(String.class))
                    {
                        SaveData.put(t.getName(), t.get(GlobalVariables.class).toString());
                    }
                    else if (t.getType().equals(Integer.class) || t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        SaveData.put(t.getName(), Double.parseDouble(t.get(GlobalVariables.class).toString()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        SaveData.put(t.getName(), t.get(GlobalVariables.class));
                    }
                    else
                    {
                        LogManager.error("Cant figure out what type is " + t.getName());
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException("Couldn't save field " + t.getName() + " for the following reason : " + e);
                }
            }
        }

        SaveData.put("GameSave", TheGame.getInstance().mainApp.Save(new JSONObject()));

        try
        {
            FileWriter writer = new FileWriter("./SaveData/save.json");
            writer.write(SaveData.toString(5));
            writer.close();
        }
        catch (IOException ignored){}
    }

    public static void LoadOptions()
    {
        File saveFile = new File("./SaveData/options.json");

        if(!saveFile.exists()) return;

        FileHandle jsonFile = Gdx.files.internal("./SaveData/options.json");

        String stringifiedJson = jsonFile.readString();

        JSONObject saveFileParsed = new JSONObject(stringifiedJson);

        for (Field t: GlobalVariables.class.getFields())
        {
            // if a field dont have a key in the save file, we just dont do anything
            if (saveFileParsed.has(t.getName()))
            {
                try {
                    if (t.getType().equals(String.class))
                    {
                        t.set(String.class, saveFileParsed.getString(t.getName()));
                    }
                    else if (t.getType().equals(Integer.class) ||
                            t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        t.set(t.getClass(), saveFileParsed.getDouble(t.getName()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        t.set(t.getClass(), saveFileParsed.getBoolean(t.getName()));
                    }
                    else
                    {
                        LogManager.error("Cant figure out what type is " + t.getName());
                    }

                    LogManager.print("Set " + t.getName() + " of type "
                            + t.getType().getSimpleName() + " to " + t.get(GlobalVariables.class));
                } catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        TheGame.getInstance().mainApp.ParseOptions(saveFileParsed.getJSONObject("GameSave"));
    }
    
    public static void Load()
    {
        File saveFile = new File("./SaveData/save.json");

        if(!saveFile.exists()) return;

        FileHandle jsonFile = Gdx.files.internal("./SaveData/save.json");

        String stringifiedJson = jsonFile.readString();

        JSONObject saveFileParsed = new JSONObject(stringifiedJson);

        for (Field t: GlobalVariables.class.getFields())
        {
            // if a field dont have a key in the save file, we just dont do anything
            if (saveFileParsed.has(t.getName()))
            {
                try {
                    if (t.getType().equals(String.class))
                    {
                        t.set(String.class, saveFileParsed.getString(t.getName()));
                    }
                    else if (t.getType().equals(Integer.class) ||
                            t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        t.set(t.getClass(), saveFileParsed.getDouble(t.getName()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        t.set(t.getClass(), saveFileParsed.getBoolean(t.getName()));
                    }
                    else
                    {
                        LogManager.error("Cant figure out what type is " + t.getName());
                    }

                    LogManager.print("Set " + t.getName() + " of type "
                            + t.getType().getSimpleName() + " to " + t.get(GlobalVariables.class));
                } catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        TheGame.getInstance().mainApp.ParseSave(saveFileParsed.getJSONObject("GameSave"));
    }

    public static boolean DoesSaveFileExist()
    {
        return new File("SaveData/save.json").exists();
    }
}
