package xyz.someboringnerd.superwispy.managers;

import com.badlogic.gdx.graphics.Texture;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import xyz.someboringnerd.superwispy.content.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockManager
{

    public static List<Texture> registeredBlocks = new ArrayList<>();

    public BlockManager()
    {
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/air"));    // 0
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/grass"));  // 1
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/dirt"));   // 2
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/stone"));  // 3
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/log"));    // 4
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/leaves")); // 5
    }
}
