package net.npcinteractive.TranscendanceEngine.Util;

import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;

public interface IInteractible
{
    public void onInteract(int id) throws NoInteractionForRoom;
}
