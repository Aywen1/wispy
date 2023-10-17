package net.npcinteractive.TranscendanceEngine.Misc;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.TheGame;
import net.npcinteractive.TranscendanceEngine.Util.GlobalVariables;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

import java.util.*;

public abstract class AbstractRoom
{
    public IInteractible interaction = new IInteractible() {
        @Override
        public void onInteract(int id) throws NoInteractionForRoom
        {
            throw new NoInteractionForRoom();
        }
    };
    public abstract String roomMusic();

    public abstract Vector2 roomSize();

    public abstract void initInteractions();

    /**
     * get the name of a scene
     * @return name of scene
     */
    public String getName()
    {
        return this.getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

    /**
     * Wrapper Actor List
     */

    public List<Actor> actorList = new ArrayList<>();
    public List<Actor> tiles = new ArrayList<>();
    public List<Light> lights = new ArrayList<>();

    public void Load()
    {
        rayHandler = new RayHandler(RoomManager.world);
        rayHandler.setShadows(true);

        GlobalVariables.RenderLights = !lights.isEmpty();
    }

    /**
     * Called when the room is loaded
     */
    public abstract void init();

    /**
     * Called on each game update
     */
    public abstract void render(Batch batch);

    public void AddActors(Actor... actors)
    {
        actorList.addAll(Arrays.asList(actors));
    }

    public RayHandler rayHandler;

    public void preRender(Batch batch)
    {
        GlobalVariables.RenderLights = !lights.isEmpty();
        rayHandler.setShadows(GlobalVariables.RenderLights);

        if(GlobalVariables.RenderLights)
        {
            rayHandler.setAmbientLight(0, 0, 0, .2f);
        }
        // render background tiles
        for(Actor actor : tiles)
        {
            actor.draw(batch, 1);
        }

        // render actors with depth sorting
        for (Actor actor : sortedActors())
        {
            if(!(actor instanceof GUI))
                actor.draw(batch, 1);
        }

        batch.end();
        if(GlobalVariables.RenderLights)
        {
            rayHandler.setCombinedMatrix(RenderUtil.camera);

            rayHandler.updateAndRender();

            // this is the worst hack possible.
            // for some godforsaken reason, rayHandler reset the scaling,
            // so I need to resize the viewscale to 720p, change the scaling
            // and then re-scale it to whatever it was before.

            // this make no fucking sense, and I want to commit alt+f4 IRL
            int x = Gdx.graphics.getWidth();
            int y = Gdx.graphics.getHeight();
            TheGame.getInstance().resize(1280, 720);
            RenderUtil.getViewport().setScaling(Scaling.fit);
            TheGame.getInstance().resize(x, y);

        }
        batch.begin();

        // render UI after everything else
        for (Actor actor : sortedActors())
        {
            if((actor instanceof GUI))
                actor.draw(batch, 1);
        }

        render(batch);
    }

    public Actor[] sortedActors()
    {
        Actor[] array = actorList.toArray(new Actor[0]);
        Arrays.sort(array, Comparator.comparingDouble(Actor::getY));
        Collections.reverse(Arrays.asList(array)); // Reverse the array
        return array;
    }
}
