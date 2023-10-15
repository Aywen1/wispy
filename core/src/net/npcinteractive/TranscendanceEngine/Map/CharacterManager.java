package net.npcinteractive.TranscendanceEngine.Map;

import net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics.GENDER;
import net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics.NPCInfo;
import net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics.PRONOUNS;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager
{
    public static List<NPCInfo> npcInfoList = new ArrayList<>();

    public CharacterManager()
    {
        npcInfoList = new ArrayList<>();

        npcInfoList.add(new NPCInfo("Narrator", GENDER.GENDERFLUID, PRONOUNS.ANY, 21).setEnabled(true));
        npcInfoList.add(new NPCInfo("Error_Test", GENDER.UNKNOWN, PRONOUNS.NONE, Integer.MAX_VALUE, true));
        npcInfoList.add(new NPCInfo("Mizuniji", GENDER.FEMALE, PRONOUNS.SHEHER, 21, true));
        npcInfoList.add(new NPCInfo("Sammy", GENDER.NON_BINARY, PRONOUNS.THEYTHEM, -1275, true));
        npcInfoList.add(new NPCInfo("System", GENDER.UNKNOWN, PRONOUNS.NONE, 0, true));

        npcInfoList.add(new NPCInfo("Classmate1", GENDER.NON_BINARY, PRONOUNS.SHETHEY, 21));
        npcInfoList.add(new NPCInfo("Classmate2", GENDER.GENDERFLUID, PRONOUNS.ANY, 20));
    }

    public static List<NPCInfo> getRealNPCs(){
        List<NPCInfo> npcs = new ArrayList<>();

        for(NPCInfo npc : npcInfoList){
            if(!npc.isDebugCharacter()){
                npcs.add(npc);
            }
        }

        return npcs;
    }

    public static NPCInfo getCharacterByInternalName(String input)
    {
        for (NPCInfo info : npcInfoList)
        {
            if(info.getName().equals(input)){
                return info;
            }
        }

        return npcInfoList.get(0);
    }
}
