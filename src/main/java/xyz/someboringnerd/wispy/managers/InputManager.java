package xyz.someboringnerd.wispy.managers;

import xyz.someboringnerd.wispy.content.KEYS;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager
{
    public static boolean isKeyDown(KEYS key)
    {
        return key.isPressed();
    }



    public static void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_SPACE -> {
                KEYS.UP.setPressed(true);
            }
            case KeyEvent.VK_S -> {
                KEYS.DOWN.setPressed(true);
            }
            case KeyEvent.VK_Q -> {
                KEYS.LEFT.setPressed(true);
            }
            case KeyEvent.VK_D -> {
                KEYS.RIGHT.setPressed(true);
            }
            case KeyEvent.VK_ESCAPE -> {
                KEYS.ESCAPE.setPressed(true);
            }
            case KeyEvent.VK_E -> {
                KEYS.INVENTORY.setPressed(true);
            }
        }
    }

    public static void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_SPACE -> {
                KEYS.UP.setPressed(false);
            }
            case KeyEvent.VK_S -> {
                KEYS.DOWN.setPressed(false);
            }
            case KeyEvent.VK_Q -> {
                KEYS.LEFT.setPressed(false);
            }
            case KeyEvent.VK_D -> {
                KEYS.RIGHT.setPressed(false);
            }
            case KeyEvent.VK_ESCAPE -> {
                KEYS.ESCAPE.setPressed(false);
            }
            case KeyEvent.VK_E -> {
                KEYS.INVENTORY.setPressed(false);
            }
        }
    }
}
