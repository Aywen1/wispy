package net.npcinteractive.TranscendanceEngine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.VisUI;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Events.GameRenderEvent;
import net.npcinteractive.TranscendanceEngine.Events.WindowResizeEvent;
import net.npcinteractive.TranscendanceEngine.Managers.*;
import net.npcinteractive.TranscendanceEngine.Map.CharacterManager;
import net.npcinteractive.TranscendanceEngine.Misc.EVENT_SIDE;
import net.npcinteractive.TranscendanceEngine.Util.GlobalVariables;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class TheGame extends ApplicationAdapter
{
	SpriteBatch batch;

	public MainClass mainApp;

	@Getter(AccessLevel.PUBLIC)
	private static TheGame instance;

	@Override
	public void create ()
	{
		instance = this;

		Gdx.graphics.setTitle(FileManager.getStringFromConfig("GameTitle"));

		batch = new SpriteBatch();
		VisUI.load(VisUI.SkinScale.X1);

	

		new CharacterManager();

		try {
			try {
				mainApp = (MainClass) Class.forName(FileManager.getStringFromConfig("mainClass")).getDeclaredConstructor().newInstance();
			} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}

			mainApp.Init();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		new EventManager();

		File saveFile = new File("./SaveData");

        if(!saveFile.exists())
		{
			saveFile.mkdir();
		}
		else
		{
			GlobalVariables.Load();
			GlobalVariables.LoadOptions();
			LogManager.print("The room that will load after the disclaimer is : " + GlobalVariables.lastLoadedScene);
		}

		new LogManager();
		new AudioManager();
		new RenderUtil();

		new RoomManager();

		boolean PrintAudioDebugInfo = false;
		if(PrintAudioDebugInfo)
		{
			if (Gdx.audio.getAvailableOutputDevices().length == 0) {
				LogManager.error("There's no audio device detected, I can go fuck myself I guess...");
				LogManager.error("If you can read this, too bad ! That's an issue on your end, not mine.");

				Mixer.Info[] sourceInfos = AudioSystem.getMixerInfo();
				LogManager.print("Available output devices through javax.sound.AudioSystem");
				for (Mixer.Info sourceInfo : sourceInfos) {
					LogManager.print(sourceInfo.getName());
				}

				LogManager.print("Compatible file format : ");

				for (AudioFileFormat.Type reader : AudioSystem.getAudioFileTypes()) {
					LogManager.print(reader.getExtension());
				}

				if (sourceInfos.length == 0) {
					LogManager.error("No audio device was found, cant help this time");
				}
			} else {
				LogManager.print("Let's see if I have any output devices");
				for (String d : Gdx.audio.getAvailableOutputDevices()) {
					LogManager.print("Audio device : " + d);
				}
			}
		}

		tFade = FileManager.getTexture("black");
	}

	public int fade = 0;

	public static boolean shouldFade;

	boolean reverse;

	Texture tFade;

	@Override
	public void resize (int width, int height) {
		if (width == 0 && height == 0) return;
		if(RoomManager.getLoaded() == null) return;

		RenderUtil.getViewport().update(width, height, true);

		EventManager.getInstance().FireEvent(WindowResizeEvent.post(new Vector2(width, height)), EVENT_SIDE.BOTH);
	}

	@Override
	public void render()
	{
		batch.setProjectionMatrix(RenderUtil.camera.combined);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
				
		EventManager.getInstance().FireEvent(GameRenderEvent.get(batch), EVENT_SIDE.BOTH);

		mainApp.Render();
		
		batch.setColor(1, 1, 1, 1);

		if(shouldFade){
			batch.setColor(1, 1, 1, (float) fade / 255);
			batch.draw(tFade, -100000, -100000, 200000, 200000);
		}
		batch.end();

		if(shouldFade)
		{
			if(!reverse)
				fade+=32;
			else
				fade-=32;

			if(fade >= 320)
			{
				reverse = true;
				RoomManager.LoadRoom(RoomManager.roomToLoad, true);
			}
			else if(fade <= 0)
			{
				reverse = false;
				shouldFade = false;
			}
		}

		RoomManager.world.step(1 / 60f, 6, 2);
		RenderUtil.getViewport().getCamera().update();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
}
