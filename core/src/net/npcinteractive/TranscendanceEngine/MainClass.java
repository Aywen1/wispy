package net.npcinteractive.TranscendanceEngine;

import org.json.JSONObject;

public abstract class MainClass
{
    public abstract void Init();
    public abstract void Render();
    public abstract JSONObject Save(JSONObject add);
    public abstract JSONObject SaveOptions();
    public abstract void ParseSave(JSONObject saveData);
    public abstract void ParseOptions(JSONObject saveData);
}
