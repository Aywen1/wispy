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

        tileList.add(new Tile("ArtThingy", new Vector2(0, 0), new Vector2(120, 160)));
        tileList.add(new Tile("DebugTile", new Vector2(0, 0), new Vector2(64, 64)));
        tileList.add(new Tile("Bed", new Vector2(0, 0), new Vector2(124, 254)));
        tileList.add(new Tile("Poster1", new Vector2(0, 0), new Vector2(78, 90)));
        tileList.add(new Tile("Poster2", new Vector2(0, 0), new Vector2(32, 64)));
        tileList.add(new Tile("Poster3", new Vector2(0, 0), new Vector2(96, 59)));
        tileList.add(new Tile("Desktop", new Vector2(0, 0), new Vector2(256, 152)));
        tileList.add(new Tile("Door", new Vector2(0, 0), new Vector2(80, 130)));
        tileList.add(new Tile("Plushie", new Vector2(0, 0), new Vector2(48, 86)));

    }

    public static Tile getTileByID(int id)
    {
        return tileList.get(id);
    }
}
