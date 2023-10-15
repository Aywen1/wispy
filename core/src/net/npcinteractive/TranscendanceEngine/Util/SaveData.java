package net.npcinteractive.TranscendanceEngine.Util;

import org.json.JSONObject;

import net.npcinteractive.TranscendanceEngine.Interfaces.Option;

import java.lang.reflect.Field;

public class SaveData
{
    public static SaveData instance;

    public static void Load(JSONObject saved)
    {
        if(!saved.has("SkipDisclaimer")) return;

        for (Field t: instance.getClass().getFields())
        {
            // if a field dont have a key in the save file, we just dont do anything
            if (saved.has(t.getName()))
            {
                try {
                    if (t.getType().equals(String.class))
                    {
                        t.set(String.class, saved.getString(t.getName()));
                    }
                    else if (t.getType().equals(Integer.class) ||
                            t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        t.set(t.getClass(), saved.getDouble(t.getName()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        t.set(t.getClass(), saved.getBoolean(t.getName()));
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static JSONObject getOptions()
    {
       JSONObject saveData = new JSONObject();

        for (Field t: instance.getClass().getFields())
        {
            if(t.getAnnotation(Option.class) != null)
            {
                try {
                    if (t.getType().equals(String.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                    else if (t.getType().equals(Integer.class) ||
                            t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            } 
        }

        return saveData;
    }

    public static JSONObject getSave()
    {
        JSONObject saveData = new JSONObject();

        for (Field t: instance.getClass().getFields())
        {
            if(t.getAnnotation(Option.class) == null)
            {
                try {
                    if (t.getType().equals(String.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                    else if (t.getType().equals(Integer.class) ||
                            t.getType().equals(Float.class) || t.getType().equals(Double.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                    else if(t.getType().equals(boolean.class))
                    {
                        saveData.put(t.getName(), t.get(instance.getClass()));
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            } 
        }

        return saveData;
    }
}
