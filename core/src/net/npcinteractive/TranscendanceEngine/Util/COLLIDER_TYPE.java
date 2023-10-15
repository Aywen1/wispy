package net.npcinteractive.TranscendanceEngine.Util;

public enum COLLIDER_TYPE {
    NONE("none"),
    FULL("full"),
    HALF("half"),
    QUARTER("quarter");

    String name;

    COLLIDER_TYPE(String name) {
        this.name = name;
    }
}
