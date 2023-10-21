package net.npcinteractive.TranscendanceEngine.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Interfaces.description;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class RenderUtil
{
    static ShapeRenderer shape;
    public static OrthographicCamera camera;

    @Getter(AccessLevel.PUBLIC)
    static FitViewport viewport;

    public static float getXRelativeZero(){
        return  (viewport.getCamera().position.x - 640);
    }

    public static float getYRelativeZero(){
        return (viewport.getCamera().position.y + 360);
    }

    public RenderUtil()
    {
        DebugFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        DebugFont.getData().scale(.1f);

        TextBoxFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextBoxFont.getData().scale(1.33333333333f);

        camera = new OrthographicCamera();

        viewport = new FitViewport(1280, 720, camera);
        viewport.setScaling(Scaling.fit);
        viewport.setCamera(camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rescources/fonts/Determination.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        DebugFont = generator.generateFont(parameter);

        parameter.size = 30;
        Deter30px = generator.generateFont(parameter);

        parameter.size = 72;
        Deter72px = generator.generateFont(parameter);

        parameter.size = 40;
        TextBoxFont = generator.generateFont(parameter);

        parameter.size = 52;
        Deter52px = generator.generateFont(parameter);

        generator.dispose();
    }
    static GlyphLayout layout = new GlyphLayout();
    public static int getStringWidth(String input, BitmapFont font)
    {
        layout.setText(font, input);
        return (int) layout.width;
    }

    public static BitmapFont DebugFont = new BitmapFont();
    public static BitmapFont TextBoxFont = new BitmapFont();
    public static BitmapFont Deter30px = new BitmapFont();
    public static BitmapFont Deter52px = new BitmapFont();
    public static BitmapFont Deter72px = new BitmapFont();

    @description(desc = "Draw text at specified coordinates with specified font")
    public static void DrawText(Batch batch, String text, Vector2 position, BitmapFont font)
    {
        font.draw(batch, text, (int)position.x, (int)position.y);
    }

    @description(desc = "Draw text at specified coordinates with specified font with a max width for textbox and stuff")
    public static void DrawText(Batch batch, String text, Vector2 position, BitmapFont font, int MaxWidth)
    {
        font.draw(batch, text, position.x, position.y, 0, text.length(), MaxWidth, Align.topLeft, true);
    }

    static Texture empty = new Texture(Gdx.files.internal("rescources/textures/tiles/EmptyCube.png"));

    @description(desc = "Fuck with text, dont use for anything that is not debug cubes or stuff that is not seen by end user")
    public static void DrawCube(Color color, Vector2 scale, Vector2 position, Batch batch)
    {
        ShapeDrawer drawer = new ShapeDrawer(batch, new TextureRegion(empty));

        drawer.filledRectangle(position.x, position.y, scale.x, scale.y, color);
    }
}
