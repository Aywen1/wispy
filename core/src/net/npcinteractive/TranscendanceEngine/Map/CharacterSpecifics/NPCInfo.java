package net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics;

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

    public boolean isDiscovered;


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

    public NPCInfo setEnabled(boolean enabled){
        isDiscovered = enabled;
        return this;
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

