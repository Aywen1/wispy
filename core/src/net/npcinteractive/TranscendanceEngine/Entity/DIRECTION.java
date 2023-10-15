package net.npcinteractive.TranscendanceEngine.Entity;

public enum DIRECTION {
    FRONT(0),
    BACK(1),
    RIGHT(2),
    LEFT(3);

    public int order;
    DIRECTION(int order)
    {
        this.order = (order);
    }
}
