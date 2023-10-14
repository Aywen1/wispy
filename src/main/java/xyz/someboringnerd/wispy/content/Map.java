package xyz.someboringnerd.wispy.content;

import xyz.someboringnerd.wispy.managers.BlockManager;

import java.util.ArrayList;
import java.util.List;

public class Map
{
    List<Block> blocks = new ArrayList<>();

    public Map()
    {
        for(int x = -100; x <= 100; x++)
        {
            blocks.add(BlockManager.getBlockById(1).clone().setPosition(x * 32, 0));
        }
    }
}
