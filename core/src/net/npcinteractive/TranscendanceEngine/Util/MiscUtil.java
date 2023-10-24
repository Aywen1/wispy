package net.npcinteractive.TranscendanceEngine.Util;

import net.npcinteractive.TranscendanceEngine.Interfaces.description;

public class MiscUtil
{
    @description(desc = "I'm inconsistent with adding file extension to imported assets")
    public static String addIfAbsent(String input, String stuffToAdd)
    {
        if(!input.endsWith(stuffToAdd)){
            input += stuffToAdd;
        }

        return input;
    }
}
