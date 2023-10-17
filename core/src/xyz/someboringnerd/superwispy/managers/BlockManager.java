package xyz.someboringnerd.superwispy.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.content.blocks.*;

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
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/bedrock")); // 6
    }

    public static Block getBlockFromID(Vector2 position, int id, Chunk chunk)
    {
        switch (id){
            case 1:
                return new GrassBlock(position, chunk);
            case 2:
                return new DirtBlock(position, chunk);
            case 3:
                return new StoneBlock(position, chunk);
            case 4:
                return new LogBlock(position, chunk);
            case 5:
                return new LeaveBlock(position, chunk);
            case 6:
                return new BedrockBlock(position, chunk);
            default:
                return new AirBlock(position, chunk);
        }
    }
}
