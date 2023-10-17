package xyz.someboringnerd.superwispy;

import net.npcinteractive.TranscendanceEngine.Interfaces.Save;
import net.npcinteractive.TranscendanceEngine.Util.SaveData;

public class GlobalData extends SaveData
{
    @Save
    public static boolean displayDebugInformation = false;

    @Save
    public static boolean showChunkPlayerIsIn = false;
}
