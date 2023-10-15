package net.npcinteractive.TranscendanceEngine.Misc;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Events.ToggleDebugMenuEvent;
import net.npcinteractive.TranscendanceEngine.Exceptions.NoInteractionForRoom;
import net.npcinteractive.TranscendanceEngine.Managers.EventManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Map.DebugCube;
import net.npcinteractive.TranscendanceEngine.Map.Tile;
import net.npcinteractive.TranscendanceEngine.TheGame;
import net.npcinteractive.TranscendanceEngine.Util.GlobalVariables;
import net.npcinteractive.TranscendanceEngine.Util.IInteractible;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import org.json.JSONException;
import org.json.JSONObject;

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



        AddActors(loadTiles());
    }

    public Tile[] loadTiles()
    {
        if(!Gdx.files.internal("rescources/maps/" + getName() + ".json").exists())
        {
            List<Tile> error = new ArrayList<>();
            error.add(new Tile("missing", new Vector2(0, 0), new Vector2(0, 0)));
            LogManager.print("The room.json file associated to " + getName() + " was not found, returning an empty array");
            return error.toArray(new Tile[0]);
        }

        FileHandle jsonFile = Gdx.files.internal("rescources/maps/" + getName() + ".json");

        String stringifiedJson = jsonFile.readString();

        try
        {
            JSONObject jsonObject = new JSONObject(stringifiedJson);
            JSONObject innerJsonObject = jsonObject.getJSONObject("tiles");

            List<Tile> stringList = new ArrayList<>();

            int i = 0;
            while (innerJsonObject.has(String.valueOf(i))) 
            {
                String texture = innerJsonObject.getJSONObject(String.valueOf(i)).getString("texture");
                boolean collider = innerJsonObject.getJSONObject(String.valueOf(i)).getBoolean("collider");
                boolean castLight = false;
                if(innerJsonObject.getJSONObject(String.valueOf(i)).has("castLight")){
                    castLight = innerJsonObject.getJSONObject(String.valueOf(i)).getBoolean("castLight");
                }
                int posX = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("posX");
                int posY = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("posY");
                int scaleX = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("scaleX");
                int scaleY = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("scaleY");
                int cScaleX = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("cScaleX");
                int cScaleY = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("cScaleY");
                int interactionID = innerJsonObject.getJSONObject(String.valueOf(i)).getInt("interactionID");


                Tile t = new Tile(texture,
                        new Vector2(posX, posY),
                        new Vector2(scaleX, scaleY)
                );
                
                if(collider)
                {
                    if(!castLight) {
                        t.AddCollider(new DebugCube(Color.RED,
                                new Vector2(posX, -posY),
                                new Vector2(cScaleX, cScaleY)
                        ));
                    }else{
                        t.AddCollider(new DebugCube(Color.RED,
                                new Vector2(posX, -posY),
                                new Vector2(cScaleX, cScaleY),
                                "wall"
                        ));
                    }

                    if(interactionID != 0)
                    {
                        t.cube.AddInteraction(interaction, interactionID);
                    }
                }
                stringList.add(t);
                i++;
            }
            i = 0;
            if(jsonObject.has("walls"))
            {
                JSONObject wall = jsonObject.getJSONObject("walls");

                while (wall.has(String.valueOf(i)))
                {
                    int posX = wall.getJSONObject(String.valueOf(i)).getInt("posX");
                    int posY = wall.getJSONObject(String.valueOf(i)).getInt("posY");
                    int scaleX = wall.getJSONObject(String.valueOf(i)).getInt("scaleX");
                    int scaleY = wall.getJSONObject(String.valueOf(i)).getInt("scaleY");

                    new DebugCube(Color.RED,
                            new Vector2(posX, -posY),
                            new Vector2(scaleX, scaleY),
                            "wall"
                    );
                    i++;
                }
            }

            return stringList.toArray(new Tile[0]);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            List<Tile> error = new ArrayList<>();
            error.add(new Tile("missing", new Vector2(0, 0), new Vector2(0, 0)));
            return error.toArray(new Tile[0]);
        }
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.END))
        {
            boolean test = false;
            EventManager.EVENT_BUS.post(ToggleDebugMenuEvent.get(test));
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
