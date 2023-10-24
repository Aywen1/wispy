package xyz.someboringnerd.superwispy.content.structures;

import xyz.someboringnerd.superwispy.content.Structure;
import xyz.someboringnerd.superwispy.content.blocks.simple.LeaveBlock;
import xyz.someboringnerd.superwispy.content.blocks.simple.LogBlock;
import xyz.someboringnerd.superwispy.managers.BlockManager;

public class Tree extends Structure
{
    public Tree()
    {
        super("LogTree_Basic");

        int t = BlockManager.getIDFromBlock(LogBlock.class);
        int l = BlockManager.getIDFromBlock(LeaveBlock.class);

        this.content = new int[][]
        {
                {0, 0, l, l, 0},
                {0, 0, l, l, l},
                {t, t, l, l, l},
                {0, 0, l, l, l},
                {0, 0, l, l, 0},
        };
    }
}
