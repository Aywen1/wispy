package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class TileManager
{
    public static List<Tile> tileList = new ArrayList<>();

    public TileManager()
    {
        tileList = new ArrayList<>();
    }

    public static Tile getTileByID(int id)
    {
        return tileList.get(id);
    }
}
