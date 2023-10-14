package xyz.someboringnerd.wispy.Entities;

import xyz.someboringnerd.wispy.content.Entity;
import xyz.someboringnerd.wispy.content.KEYS;
import xyz.someboringnerd.wispy.managers.InputManager;
import xyz.someboringnerd.wispy.managers.TextureManager;
import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerEntity extends Entity
{

    public PlayerEntity(Vector2D<Integer> initialPosition)
    {
        this.position = initialPosition;
    }

    public BufferedImage idle = TextureManager.getTexture("player/p_stop.png");
    public BufferedImage walk = TextureManager.getTexture("player/p_walk1.png");
    public BufferedImage walk_2 = TextureManager.getTexture("player/p_walk2.png");

    @Override
    public void Init()
    {

    }

    @Override
    public void Update()
    {
        Vector2D<Integer> move = new Vector2D<>(0, 0);

        if(InputManager.isKeyDown(KEYS.LEFT))
        {
            move.x -= 1;
        }
        else if(InputManager.isKeyDown(KEYS.RIGHT))
        {
            move.x += 1;
        }

        if(move.x != 0)
            position.x += move.x;
    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(idle, position.x, position.y, 32, 64, null);
    }
}
