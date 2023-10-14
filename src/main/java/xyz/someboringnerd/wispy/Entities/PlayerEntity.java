package xyz.someboringnerd.wispy.Entities;

import xyz.someboringnerd.wispy.content.Entity;
import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;

public class PlayerEntity extends Entity
{

    public PlayerEntity(Vector2D<Integer> initialPosition)
    {
        this.position = initialPosition;
    }



    @Override
    public void Init()
    {

    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(null, position.x, position.y, 32, 64, null);
    }
}
