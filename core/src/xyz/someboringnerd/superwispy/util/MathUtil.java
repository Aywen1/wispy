package xyz.someboringnerd.superwispy.util;

import com.badlogic.gdx.math.Vector2;

public class MathUtil
{
    public static float getDistance(Vector2 pointA, Vector2 pointB)
    {
        double a = (pointA.x - pointB.x);
        double b = (pointA.y - pointB.y);

        return (float) Math.abs(Math.sqrt((a * a) + (b * b)));
    }
}
