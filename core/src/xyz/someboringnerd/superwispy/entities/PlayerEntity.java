package xyz.someboringnerd.superwispy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Entity.Entity;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;

import static net.npcinteractive.TranscendanceEngine.Entity.Entity.HEIGHT;
import static net.npcinteractive.TranscendanceEngine.Entity.Entity.WIDTH;
import static net.npcinteractive.TranscendanceEngine.Managers.RoomManager.world;

public class PlayerEntity extends Entity
{
    @Getter(AccessLevel.PUBLIC)
    private static PlayerEntity instance;

    private Texture player;

    public PlayerEntity(Vector2 position)
    {
        super(position, "");
        instance = this;
        player = FileManager.getTexture("p_stop");

        CreateBody();
    }

    void CreateBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Make sure to adjust the body type as needed
        bodyDef.position.set(getX(), getY());
        body = world.createBody(bodyDef);

        // Define the player's shape (assuming a rectangular shape)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15, 32); // Half width and half height
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.density = 0.5f;
        body.createFixture(fixtureDef);
        shape.dispose();

        boundingBox = new Rectangle();
    }

    Vector2 move;

    Vector2 moveSprite()
    {
        Vector2 movement = new Vector2(0, 0);

        if(InputSystem.moveLeft())
        {
            movement.x -= 64;
        }
        if(InputSystem.moveRight())
        {
            movement.x += 64;
        }
        if(InputSystem.justMoveUp() && (int)body.getLinearVelocity().y == 0)
        {
            float jumpForce = (float) (80 * 8 * 32 * 10) / 2;
            body.applyLinearImpulse(new Vector2(0, jumpForce), body.getPosition(), true);
        }

        return movement;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        move = moveSprite();

        body.setLinearVelocity(move.x, body.getLinearVelocity().y);
        body.setFixedRotation(true);

        setPosition(body.getPosition().x - 16, body.getPosition().y - 32);
        boundingBox.set(getX(), getY(), getWidth(), getHeight());

        batch.draw(player, getX(), getY(), 32, 64);
        RenderUtil.DrawText(batch, "X : " + getX(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 32), RenderUtil.Deter30px);
        RenderUtil.DrawText(batch, "Y : " + getY(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 64), RenderUtil.Deter30px);
        RenderUtil.DrawText(batch, "vY : " + body.getLinearVelocity().y, new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 96), RenderUtil.Deter30px);
    }
}
