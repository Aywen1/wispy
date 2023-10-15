package net.npcinteractive.TranscendanceEngine.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Util.SHADER;

public class Shader
{
    String shaderStr, vertex;
    private ShaderProgram shaderProgram;
    private FrameBuffer fbo;

    SHADER shader;

    public Shader(SHADER shader)
    {
        this.shader = shader;
        this.shaderStr = Gdx.files.internal("rescources/shaders/" + shader.shaderName).readString();
        this.vertex = Gdx.files.internal("rescources/shaders/vertexShader.glsl").readString();
        shaderProgram = new ShaderProgram(vertex,shaderStr);
        ShaderProgram.pedantic = false;

        if (!shaderProgram.isCompiled())
        {
            System.err.println(shaderProgram.getLog());
        }

        if (!shaderProgram.getLog().isEmpty()){
            System.out.println(shaderProgram.getLog());
        }
    }

    public void DrawWithShader(Batch batch, float time, Vector2 pos, Vector2 scale)
    {
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, 1280, 720, true);
        batch.end();
        batch.flush();

        // We want to post to the framebuffer and draw our images as normal
        fbo.begin();
        batch.begin();

        batch.end();
        batch.flush();
        fbo.end();

        // now run our shader over the framebuffer image and draw
        batch.begin();
        batch.setShader(shaderProgram);

        shaderProgram.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        shaderProgram.setUniformf("time", time);

        // Get the texture from the framebuffer and display on screen
        Texture texture = fbo.getColorBufferTexture();
        TextureRegion textureRegion = new TextureRegion(texture);

        batch.draw(textureRegion, pos.x, pos.y, scale.x, scale.y);
        batch.setShader(null);
    }
}
