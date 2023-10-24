package net.npcinteractive.TranscendanceEngine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;

public class InputSystem
{

    public static boolean controllerIsPluggedIn()
    {
        return Controllers.getCurrent() != null;
    }

    static boolean buttonA, buttonX, buttonB, start, up, down, left, right;

    public static boolean SafeIsButtonPressed(CONTROLLER_KEY input)
    {
        if(!controllerIsPluggedIn()) return false;

        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonX)) buttonX = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonA)) buttonA = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonB)) buttonB = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonStart)) start = false;

        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonA)) up = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadDown)) down = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadLeft)) left = false;
        if(!Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadRight)) right = false;

        switch (input){
            case ANY:
                return Controllers.getCurrent().getButton(-1);
            case UP: return  Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonA);
            case DOWN: return  Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadDown);
            case LEFT: return  Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadLeft);
            case RIGHT: return  Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadRight);
            case UUP:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonA))
                {
                    if(up) return false;
                    up = true;
                    return true;
                }

                return false;
            case UDOWN:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadDown))
                {
                    if(down) return false;
                    down = true;
                    return true;
                }

                return false;
            case ULEFT:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadLeft))
                {
                    if(left) return false;
                    left = true;
                    return true;
                }

                return false;
            case URIGHT:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonDpadRight))
                {
                    if(right) return false;
                    right = true;
                    return true;
                }

                return false;
            case START:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonStart))
                {
                    if(start) return false;
                    start = true;
                    return true;
                }

                return false;
            case NEXT:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonX))
                {
                    if(buttonX) return false;
                    buttonX = true;
                    return true;
                }
                return false;
            default:
                return false;
            case INTERACT:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonA))
                {
                    if(buttonA) return false;
                    buttonA = true;
                    return true;
                }

                return false;
            case RETURN:
                if(Controllers.getCurrent().getButton(Controllers.getCurrent().getMapping().buttonB))
                {
                    if(buttonB) return false;
                    buttonB = true;
                    return true;
                }

                return false;
        }
    }

    public static boolean interact()
    {
        return Gdx.input.isKeyJustPressed(Input.Keys.E) || SafeIsButtonPressed(CONTROLLER_KEY.INTERACT);
    }

    public static boolean Return()
    {
        return Gdx.input.isKeyJustPressed(Input.Keys.X) || SafeIsButtonPressed(CONTROLLER_KEY.RETURN);
    }

    public static boolean next()
    {
        return Gdx.input.isKeyJustPressed(Input.Keys.F) || SafeIsButtonPressed(CONTROLLER_KEY.NEXT);
    }

    public static boolean start()
    {
        return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || SafeIsButtonPressed(CONTROLLER_KEY.START);
    }

    // ---------------- isKeyPressed equivalent, for movement and stuff like that

    public static boolean moveUp(){
        return Gdx.input.isKeyPressed(Input.Keys.SPACE) || SafeIsButtonPressed(CONTROLLER_KEY.UP);
    }

    public static boolean moveDown(){
        return Gdx.input.isKeyPressed(Input.Keys.S) || SafeIsButtonPressed(CONTROLLER_KEY.DOWN);
    }

    public static boolean moveLeft()
    {
        return Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.A) || SafeIsButtonPressed(CONTROLLER_KEY.LEFT);
    }

    public static boolean moveRight(){
        return Gdx.input.isKeyPressed(Input.Keys.D) || SafeIsButtonPressed(CONTROLLER_KEY.RIGHT);
    }

    // ---------------- isKeyJustPressed equivalent, for UI and stuff

    public static boolean justMoveUp(){
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || SafeIsButtonPressed(CONTROLLER_KEY.UUP);
    }

    public static boolean justMoveDown(){
        return Gdx.input.isKeyJustPressed(Input.Keys.S) || SafeIsButtonPressed(CONTROLLER_KEY.UDOWN);
    }

    public static boolean justMoveLeft(){
        return Gdx.input.isKeyJustPressed(Input.Keys.Q) || Gdx.input.isKeyJustPressed(Input.Keys.A) || SafeIsButtonPressed(CONTROLLER_KEY.ULEFT);
    }

    public static boolean justMoveRight(){
        return Gdx.input.isKeyJustPressed(Input.Keys.D) || SafeIsButtonPressed(CONTROLLER_KEY.URIGHT);
    }

    public static boolean any(){
        return Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || SafeIsButtonPressed(CONTROLLER_KEY.ANY);
    }
}

enum CONTROLLER_KEY
{
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UUP,
    UDOWN,
    ULEFT,
    URIGHT,
    INTERACT,
    NEXT,
    START,
    ANY,
    RETURN
}