package xyz.someboringnerd.superwispy.gui.screens;

import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import xyz.someboringnerd.superwispy.gui.GUI;

public class Inventory extends GUI
{
    public Inventory()
    {
        super(FileManager.getTexture("gui/inventory"), new Vector2(440, 415), false);

        LogManager.print("Inventory was opened");
    }
}
