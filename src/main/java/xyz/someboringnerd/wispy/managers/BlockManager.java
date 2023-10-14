package xyz.someboringnerd.wispy.managers;

import xyz.someboringnerd.wispy.content.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockManager
{
    static List<Block> blockList = new ArrayList<>();

    public BlockManager()
    {
        blockList.add(new Block("dirt", 0));
        blockList.add(new Block("grass", 1));
        blockList.add(new Block("leaves", 2));
        blockList.add(new Block("log", 3));
        blockList.add(new Block("sand", 4));
        blockList.add(new Block("stone", 5));
    }

    public static Block getBlockById(int id)
    {
        return blockList.get(id);
    }
}
