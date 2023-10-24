package xyz.someboringnerd.superwispy.content.items;

import com.badlogic.gdx.graphics.Texture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;

public class Item
{
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PROTECTED)
    private String defaultName = getClass().getSimpleName();

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int ID;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PROTECTED)
    private Texture itemTexture = FileManager.getTexture(getClass().getCanonicalName().toLowerCase().replace("xyz.someboringnerd.superwispy.content", "").replace(".", "/"));
}
