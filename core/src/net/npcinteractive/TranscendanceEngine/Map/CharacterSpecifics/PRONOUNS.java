package net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics;

public enum PRONOUNS {
    HEHIM("he/him"),
    SHEHER("she/her"),
    THEYTHEM("they/them"),
    HETHEY("he/they"),
    SHETHEY("she/they"),
    ANY("any"),
    NONE("unknown");

    public String token;

    PRONOUNS(String token) {
        this.token = token;
    }
}
