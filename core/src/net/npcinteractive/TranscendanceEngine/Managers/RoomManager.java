package net.npcinteractive.TranscendanceEngine.Managers;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import lombok.AccessLevel;
import lombok.Getter;
import meteordevelopment.orbit.EventHandler;
import net.npcinteractive.TranscendanceEngine.TheGame;
import net.npcinteractive.TranscendanceEngine.Events.GameRenderEvent;
import net.npcinteractive.TranscendanceEngine.Interfaces.DontSave;
import net.npcinteractive.TranscendanceEngine.Misc.AbstractRoom;
import net.npcinteractive.TranscendanceEngine.Misc.ErrorRoom;
import net.npcinteractive.TranscendanceEngine.Misc.LogoRoom;
import net.npcinteractive.TranscendanceEngine.Util.GlobalVariables;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

public class RoomManager
{
    public static ArrayList<AbstractRoom> rooms = new ArrayList<>();

    @Getter(AccessLevel.PUBLIC)
    private static AbstractRoom loaded;
    public static World world;

    @Getter(AccessLevel.PUBLIC)
    RayHandler handler;

    @DontSave
    public static String previousRoom;

    public RoomManager()
    {
        world = new World(new Vector2(0, -(96*2)), true);

        rooms.add(new ErrorRoom());
        rooms.add(new LogoRoom());

        new Reflections(FileManager.getStringFromConfig("roomPackage")).getSubTypesOf(AbstractRoom.class).stream().forEach(room -> {
            try
            {
                AbstractRoom e = room.getDeclaredConstructor().newInstance();
                rooms.add(e);
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        EventManager.EVENT_BUS.subscribe(this);

        debugRenderer = new Box2DDebugRenderer();

        LoadRoom("LogoRoom", true);
    }

    Box2DDebugRenderer debugRenderer;

    @EventHandler
    public void RenderGame(GameRenderEvent event)
    {
        if (loaded == null) return;
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        loaded.preRender(event.getBatch());

        if(Gdx.input.isKeyJustPressed(Input.Keys.F5))
        {
            GlobalVariables.ReadDebugInfos = !GlobalVariables.ReadDebugInfos;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F6))
        {
            GlobalVariables.DrawDebugCollisions = !GlobalVariables.DrawDebugCollisions;
        }

        if(GlobalVariables.DrawDebugCollisions)
        {
            event.getBatch().end();
            debugRenderer.render(world, event.getBatch().getProjectionMatrix());
            event.getBatch().begin();
        }

        if(GlobalVariables.ReadDebugInfos)
        {
            RenderUtil.DrawText(event.getBatch(), "Active sounds : " + AudioManager.getInstance().activeSounds(), new Vector2(RenderUtil.getXRelativeZero() + 10, RenderUtil.getYRelativeZero() - 10), RenderUtil.DebugFont);
            RenderUtil.DrawText(event.getBatch(), "Playing from  : " + (AudioManager.playFromFallback ? "Fallback javax.sound" : "Libgdx's OpenAL"), new Vector2(RenderUtil.getXRelativeZero() + 10, RenderUtil.getYRelativeZero() - 30), RenderUtil.DebugFont);
        }
    }

    public static String roomToLoad;

    public static void LoadRoom(String name, boolean bypassTransition)
    {
        // allowing a room to be reloaded cause a memory leak, the room is loaded once,
        // if you reload it it get reloaded twice
        // then 4 time
        // then 16, you got it.
        // each "over reloaded" room never get disposed, so i'm fucked.
        // @todo : allow a scene to be reloaded only once.
        if(loaded != null)
            if(name.toLowerCase().trim().equals(loaded.getName().toLowerCase().trim())) return;

        if(!bypassTransition)
        {
            if(TheGame.shouldFade != true)
            {
                TheGame.shouldFade = true;
                roomToLoad = name;
            }

            return;
        }

        for (AbstractRoom room: rooms)
        {
            if(name.toLowerCase().trim().equals(room.getName().toLowerCase().trim()))
            {
                final Table root = new Table();
                root.setFillParent(true);

                if(loaded != null)
                {
                    previousRoom = loaded.getName();
                    Array<Body> bodies = new Array<>();

                    world.getBodies(bodies);

                    for (Body body : bodies)
                    {
                        world.destroyBody(body);
                    }

                    loaded.actorList.clear();

                    loaded.lights.clear();

                    AudioManager.getInstance().StopAllSounds(!Objects.equals(loaded.roomMusic(), room.roomMusic()));

                    loaded = null;
                }

                loaded = room;

                loaded.initInteractions();

                loaded.Load();

                loaded.init();

                GlobalVariables.RenderLights = loaded.lights.isEmpty();

                AudioManager.getInstance().playAudio("music/" + loaded.roomMusic(), true, .5f);

                return;
            }
        }

        LogManager.error("Room " + name + " dont exist, falling back to ErrorRoom");
        LoadRoom("ErrorRoom");
    }

    public static void LoadRoom(String name)
    {
        LoadRoom(name, false);
    }
}
