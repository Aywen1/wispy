package xyz.someboringnerd.superwispy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Entity.Entity;
import net.npcinteractive.TranscendanceEngine.Managers.AudioManager;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.GlobalData;
import xyz.someboringnerd.superwispy.rooms.GameRoom;
import xyz.someboringnerd.superwispy.util.DIRECTION;

import static net.npcinteractive.TranscendanceEngine.Managers.RoomManager.world;

public class PlayerEntity extends Entity
{
    @Getter(AccessLevel.PUBLIC)
    private static PlayerEntity instance;

    final static float MAX_VELOCITY = 256f;
    final static float JUMP_VELOCITY = 128f;

    private Texture player;

    public DIRECTION direction;

    public PlayerEntity(Vector2 position)
    {
        super(position, "");
        instance = this;
        player = FileManager.getTexture("p_stop");

        CreateBody();
    }

    void CreateBody()
    {
        // complètement copié collé de la documentation mdr
        // - SBN

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(getX(), getY());

// Create our body in the world using our body definition
        body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(15);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();
        boundingBox = new Rectangle();
    }

    int frameStill;
    float previousX;

    float yJump;

    boolean previouslyInput;

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();

        float movement = 0.5f, moveX = 0;

        if (Math.abs(vel.x) > MAX_VELOCITY) {
            vel.x = Math.signum(vel.x) * MAX_VELOCITY;
            body.setLinearVelocity(vel.x, vel.y);
        }

        if (!InputSystem.moveLeft() && !InputSystem.moveRight())
        {
            moveX = vel.x * 0.7f;
        }

        if (InputSystem.moveLeft() && vel.x > -MAX_VELOCITY)
        {
            direction = DIRECTION.LEFT;
            moveX = (-(128 * movement));
        }

        if (InputSystem.justMoveUp() && body.getLinearVelocity().y == 0)
        {
            yJump = (int)pos.y;
            LogManager.print("Jumped at y = "+ yJump);
            body.setLinearVelocity(vel.x, vel.y);
            body.setTransform(pos.x, pos.y, 0);
            AudioManager.getInstance().playAudio("sfx/jump");
            body.applyLinearImpulse(0, (JUMP_VELOCITY*JUMP_VELOCITY*JUMP_VELOCITY) * 1.5f, 0, pos.y, true);
        }

        if (InputSystem.moveRight() && vel.x < MAX_VELOCITY)
        {
            direction = DIRECTION.RIGHT;
            moveX = (128 * movement);
        }

        if(body.getLinearVelocity().y < 0)
        {
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y * 1.1f);
        }

        if(previousX == pos.x && body.getLinearVelocity().y < 0)
        {
            body.setLinearVelocity(0, body.getLinearVelocity().y * 2);
        }
        else
        {
            body.setLinearVelocity(moveX, body.getLinearVelocity().y);
        }

        body.setFixedRotation(true);

        setPosition(body.getPosition().x - 16, body.getPosition().y - 16);
        boundingBox.set(getX(), getY(), getWidth(), getHeight());

        previousX = pos.x;

        batch.draw(player, direction == DIRECTION.LEFT ? getX() + 32 : getX(), getY(), direction == DIRECTION.LEFT ? -32 : 32, 64);

        if(GlobalData.displayDebugInformation)
        {
            RenderUtil.DrawText(batch, "X : " + (getX()), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 32), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "Y : " + (getY()), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 64), RenderUtil.Deter30px);

            RenderUtil.DrawText(batch, "vY : " + (body.getLinearVelocity().y), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 96), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "vX : " + (body.getLinearVelocity().x), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 128), RenderUtil.Deter30px);

            RenderUtil.DrawText(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 32)), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "Chunks loaded : " + GameRoom.getInstance().safeLoaded().size(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 64)), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "total chunks : " + GameRoom.getInstance().chunklist.size(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 96)), RenderUtil.Deter30px);
        }
    }
}
