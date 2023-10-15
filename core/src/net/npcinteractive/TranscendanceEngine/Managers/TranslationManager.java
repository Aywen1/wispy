package net.npcinteractive.TranscendanceEngine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import lombok.AccessLevel;
import lombok.Getter;
import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.Starscript;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.Error;

import org.json.JSONException;
import org.json.JSONObject;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;

import java.util.ArrayList;
import java.util.List;

public class TranslationManager
{
    @Getter(AccessLevel.PUBLIC)
    private static Starscript starScriptParser = new Starscript();

    public static String parsedString(String input)
    {
        Parser.Result result = Parser.parse(input);

        if (result.hasErrors()) {
            for (Error error : result.errors) System.out.println(error);
        }
        Script script = Compiler.compile(result);

        return starScriptParser.run(script).toString();
    }

    public static String[] getDialogue(String path, int index)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/langs/english/Dialogues/" + MiscUtil.addIfAbsent(path, ".json"));
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);

        try
        {
            JSONObject innerJsonObject = jsonObject.getJSONObject(String.valueOf(index));

            List<String> stringList = new ArrayList<>();

            int i = 0;
            while (innerJsonObject.has(String.valueOf(i))) 
            {
                stringList.add(parsedString(innerJsonObject.getString(String.valueOf(i))));
                i++;
            }

            return stringList.toArray(new String[0]);
        }
        catch (JSONException e)
        {
            String[] error = new String[]{
                "Error : Text at index " + index + " wasnt found in file english/Dialogues/" + path + ".json" + "."
            };
            return error;
        }
    }

    public static String[] getRoom(String path, String index)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/langs/english/Rooms/" + MiscUtil.addIfAbsent(path, ".json"));
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);
        try
        {
            JSONObject innerJsonObject = jsonObject.getJSONObject(String.valueOf(index));

            List<String> stringList = new ArrayList<>();

            int i = 0;
            while (innerJsonObject.has(String.valueOf(i))) {
                stringList.add(parsedString(innerJsonObject.getString(String.valueOf(i))));
                i++;
            }

            return stringList.toArray(new String[0]);
        }
        catch (JSONException e)
        {
            String[] error = new String[]{
                    "Error : Text at index " + index + " wasnt found in file english/Dialogues/" + path + ".json" + "."
            };
            return error;
        }
    }

    public static int getNumberOfObjects(String path)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/langs/english/Dialogues/" + MiscUtil.addIfAbsent(path, ".json"));
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);

        return jsonObject.length();
    }

    public static String getString(String path, String key)
    {
        FileHandle jsonFile = Gdx.files.internal("rescources/langs/english/" + MiscUtil.addIfAbsent(path, ".json"));
        String stringifiedJson = jsonFile.readString();

        JSONObject jsonObject = new JSONObject(stringifiedJson);

        return parsedString(jsonObject.getString(key));
    }
}
