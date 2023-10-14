package xyz.someboringnerd.wispy.content;

public enum KEYS
{
    UP,
    DOWN,
    LEFT,
    RIGHT,
    ESCAPE,
    INVENTORY;

    boolean isPressed;

    public void setPressed(boolean input){isPressed = input;}

    public boolean isPressed()
    {
        return isPressed;
    }
}
