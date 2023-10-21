package xyz.someboringnerd.superwispy.content.items.debug;

import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.items.Item;

public class BlockItem extends Item
{
    public BlockItem(Block block)
    {
        super();
        setItemTexture(block.getTexture());
        setID(block.getId());
        setDefaultName(block.getClass().getSimpleName());
    }
}
