package xyz.someboringnerd.superwispy.content.structures;

import xyz.someboringnerd.superwispy.content.Block;
import xyz.someboringnerd.superwispy.content.Structure;

public class Tree extends Structure
{
    public Tree()
    {
        super("LogTree_Basic");

        this.content = new int[][]
        {
                {0, 0, 5, 5, 0},
                {0, 0, 5, 5, 5},
                {4, 4, 5, 5, 5},
                {0, 0, 5, 5, 5},
                {0, 0, 5, 5, 0},
        };
    }
}
