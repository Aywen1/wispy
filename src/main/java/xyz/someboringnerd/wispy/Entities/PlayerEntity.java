package xyz.someboringnerd.wispy.Entities;

import xyz.someboringnerd.wispy.content.Entity;
import xyz.someboringnerd.wispy.content.KEYS;
import xyz.someboringnerd.wispy.content.Map;
import xyz.someboringnerd.wispy.managers.InputManager;
import xyz.someboringnerd.wispy.managers.TextureManager;
import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerEntity extends Entity
{

    public FACING facing;
    public BufferedImage idle = TextureManager.getTexture("player/p_stop.png");
    public BufferedImage walk = TextureManager.getTexture("player/p_walk1.png");
    public BufferedImage walk_2 = TextureManager.getTexture("player/p_walk2.png");
    public BufferedImage activeFrame = walk;

    public PlayerEntity(Vector2D<Integer> initialPosition)
    {
        this.position = initialPosition;
    }

    @Override
    public void Init()
    {
        facing = FACING.RIGHT;
    }

    int frame = 0;

    @Override
    public void Update()
    {
        Vector2D<Integer> move = new Vector2D<>(0, 32);

        if(InputManager.isKeyDown(KEYS.LEFT))
        {
            move.x -= 2;
            facing = FACING.LEFT;
        }
        else if(InputManager.isKeyDown(KEYS.RIGHT))
        {
            move.x += 2;
            facing = FACING.RIGHT;
        }
        move.y = Map.getInstance().getSafeSpawn(position.x - 32) - 96;

        if(move.x != 0 || move.y != 0)
        {
            position.x += move.x;
            position.y = move.y;

            if(move.x != 0)
            {
                if (frame <= 15)
                {
                    activeFrame = walk;
                }
                else
                {
                    activeFrame = walk_2;
                }
            }
            else
            {
                activeFrame = idle;
                frame = 0;
            }
        }

        if(frame >= 30)
        {
            frame = 0;
        }
        frame++;
    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(activeFrame, position.x + (facing == FACING.LEFT ? 32 : 0), position.y, facing == FACING.LEFT ? -32 : 32, 64, null);
    }
}

enum FACING{
    LEFT,
    RIGHT
}