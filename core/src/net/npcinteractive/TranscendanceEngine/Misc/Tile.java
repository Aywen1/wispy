package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;
import net.npcinteractive.TranscendanceEngine.Util.SHADER;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

import java.util.Objects;

public class Tile extends Actor
{
    public String texture;

    @Getter(AccessLevel.PUBLIC)
    Texture _texture;

    public Tile(String texture, Vector2 position, Vector2 scale) {
        setPosition(position.x, position.y);
        setScale(scale.x, scale.y);
        _texture = FileManager.getTexture(texture);

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        batch.draw(_texture, getX(), getY(), getScaleX(), getScaleY());
    }
}
