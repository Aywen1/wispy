package net.npcinteractive.TranscendanceEngine.Exceptions;

import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;

public class NoInteractionForRoom extends Exception
{
    public NoInteractionForRoom(){
        super("No interactions were found for the room " + RoomManager.getLoaded().getName());
    }
}
