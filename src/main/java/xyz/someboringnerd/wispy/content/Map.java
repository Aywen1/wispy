package xyz.someboringnerd.wispy.content;

import xyz.someboringnerd.wispy.managers.BlockManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map
{
    List<Block> blocks = new ArrayList<>();

    public Map()
    {
        instance = this;
        for(int x = -1000; x <= 1000; x+=32)
        {
            Block b = new Block("grass", 1);
            b.setPosition(x, 720/2);
            blocks.add(b);

            System.out.println(x + ", " + b.position.x);
        }
        for(int x = -1000; x <= 2048; x+=32)
        {
            Block b = new Block("grass", 1);
            b.setPosition(x, 720/2 + 32);
            blocks.add(b);

            System.out.println(x + ", " + b.position.x);
        }

        System.out.println("Current map has " + blocks.size() + " blocks");
    }
    private static Map instance;
    public static Map getInstance()
    {
        return instance;
    }

    public int getSafeSpawn(int x)
    {
        for(Block block : blocks)
        {
            if(block.position.x <= x + 32 && block.position.x >= x)
            {
                return block.position.y + 32;
            }
        }

        return 0;
    }

    public void draw(Graphics g, Point mouseLocation)
    {
        for(Block block : blocks)
        {
            g.drawImage(block.texture, block.position.x, block.position.y, 32, 32, null);
        }
    }
}
