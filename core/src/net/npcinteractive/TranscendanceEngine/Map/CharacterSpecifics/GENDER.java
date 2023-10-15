package net.npcinteractive.TranscendanceEngine.Map.CharacterSpecifics;

public enum GENDER {
    MALE("Man"),
    FEMALE("Woman"),
    NON_BINARY("Non Binary"),
    UNKNOWN("Unknown"),
    GENDERFLUID("Genderfluid");
    public String token;

    GENDER(String token) {
        this.token = token;
    }
}
