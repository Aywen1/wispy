package net.npcinteractive.TranscendanceEngine.Map;

import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.TranslationManager;

public class NPCInfo
{
    @Getter(AccessLevel.PUBLIC)
    private String name;

    @Getter(AccessLevel.PUBLIC)
    private GENDER gender;

    @Getter(AccessLevel.PUBLIC)
    private PRONOUNS pronouns;

    @Getter(AccessLevel.PUBLIC)
    private int age;

    public String getDisplayName() {
        return TranslationManager.getString("characters/" + getName() + ".json", "name");
    }

    @Getter(AccessLevel.PUBLIC)
    private boolean DebugCharacter;

    public NPCInfo(String name, GENDER gender, PRONOUNS pronouns, int age)
    {
        this.name = name;
        this.gender = gender;
        this.pronouns = pronouns;
        this.age = age;
    }

    public NPCInfo(String name, GENDER gender, PRONOUNS pronouns, int age, boolean DebugCharacter)
    {
        this.name = name;
        this.gender = gender;
        this.pronouns = pronouns;
        this.age = age;
        this.DebugCharacter = DebugCharacter;
    }
}
enum GENDER
{
    MALE("Man"),
    FEMALE("Woman"),
    NON_BINARY("Non Binary"),
    UNKNOWN("Unknown"),
    GENDERFLUID("Genderfluid");
    public String name;
    GENDER(String name)
    {
        this.name = name;
    }
}

enum PRONOUNS
{
    HEHIM("he/him"),
    SHEHER("she/her"),
    THEYTHEM("they/them"),
    HETHEY("he/they"),
    SHETHEY("she/they"),
    NONE("unknown");

    public String name;

    PRONOUNS(String name)
    {
        this.name = name;
    }
}
