package xyz.someboringnerd.wispy.content;

import xyz.someboringnerd.wispy.math.Vector2D;

import java.awt.*;

public abstract class Entity extends Rectangle
{
    public Vector2D<Integer> position = new Vector2D<>(0, 0);

    public abstract void Init();
    public abstract void Update();
    public abstract void Draw(Graphics g);
}
