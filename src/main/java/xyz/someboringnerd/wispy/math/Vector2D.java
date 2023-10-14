package xyz.someboringnerd.wispy.math;

public class Vector2D<Number>
{
    public Number x, y;

    public Vector2D(Number defaultX, Number defaultY){
        x = defaultX;
        y = defaultY;
    }

    public void setX(Number x) {
        this.x = x;
    }

    public void setY(Number y) {
        this.y = y;
    }

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }
}
