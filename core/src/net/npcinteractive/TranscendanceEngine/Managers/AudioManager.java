package net.npcinteractive.TranscendanceEngine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import lombok.AccessLevel;
import lombok.Getter;
import meteordevelopment.orbit.EventHandler;
import net.npcinteractive.TranscendanceEngine.Events.GameRenderEvent;
import net.npcinteractive.TranscendanceEngine.Interfaces.description;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;

import java.util.ArrayList;
import java.util.List;

public class AudioManager {

    @Getter(AccessLevel.PUBLIC)
    private static AudioManager instance;

    public static boolean playFromFallback;

    @Getter(AccessLevel.PUBLIC)
    private static boolean playingLoop = false;

    public int activeSounds()
    {
        return activeSoundsList.size();
    }

    public AudioManager()
    {
        instance = this;
        playFromFallback = (Gdx.audio.getAvailableOutputDevices().length == 0);

        EventManager.EVENT_BUS.subscribe(this);
    }
    int prevSize;

    @EventHandler
    @description(desc = "Dispose of stopped sounds")
    void OnRenderScreen(GameRenderEvent event)
    {
        if(prevSize == activeSoundsList.size()) return;

        for (Music m: activeSoundsList)
        {
            if(!m.isPlaying())
            {
                m.stop();
                m.dispose();
            }
        }

        prevSize = activeSoundsList.size();
    }

    static List<Music> activeSoundsList = new ArrayList<>();

    public void playAudio(String path)
    {
        playAudio(path, false, 1.0f);
    }

    public void playAudio(String path, float volume)
    {
        playAudio(path, false, volume);
    }

    public void playAudio(String path, boolean loop)
    {
        playAudio(path, loop, 1.0f);
    }

    public void playAudio(String path, boolean loop, float volume)
    {
        if(path.contains("null")) return;
        if(playingLoop && loop) return;

        if(Gdx.files.local("/audio/" + MiscUtil.addIfAbsent(path, ".wav")).exists())
        {
            path = MiscUtil.addIfAbsent(path, ".wav");
        }
        else if(Gdx.files.local("/audio/" + MiscUtil.addIfAbsent(path, ".mp3")).exists())
        {
            path = MiscUtil.addIfAbsent(path, ".mp3");
        }

        Music music = Gdx.audio.newMusic(Gdx.files.local("/audio/" + path));
        
        music.setLooping(loop);
        music.setVolume(volume);
        music.play();

        if(loop) playingLoop = true;

        activeSoundsList.add(music);
    }

    public void StopAllSounds()
    {
        StopAllSounds(false);
    }

    public void StopAllSounds(boolean force)
    {
        for (Music m: activeSoundsList)
        {
            if(!m.isLooping() || force)
            {
                m.stop();
                m.dispose();
            }
        }

        if(force) playingLoop = false;
    }
}