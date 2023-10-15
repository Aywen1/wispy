package xyz.someboringnerd.superwispy.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseEntity extends Actor
{
    Vector2 scale = new Vector2(32, 64);
    public BaseEntity(Vector2 position)
    {
        setPosition(position.x, position.y + 32);
    }
}
