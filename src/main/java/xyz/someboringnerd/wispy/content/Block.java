package xyz.someboringnerd.wispy.content;

import xyz.someboringnerd.wispy.managers.TextureManager;
import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block implements Cloneable
{
    String texturePath;
    int id;

    BufferedImage texture;
    public Block(String texturePath, int id)
    {
        this.id = id;
        this.texturePath = "blocks/" + texturePath + ".png";

        texture = TextureManager.getTexture(this.texturePath);
    }

    public void paint(Graphics g, int newX, int newY, int newBlockWidth, int newBlockHeight)
    {
        g.drawImage(texture, position.x, position.y, 32, 32, null);
    }

    @Override
    public Block clone()
    {
        try
        {
            return (Block) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }

    Vector2D<Integer> position = new Vector2D<>(0, 0);

    public Block setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        return this;
    }

    public Block setPosition(Vector2D<Integer> newPos)
    {
        position = newPos;
        return this;
    }
}
