package xyz.someboringnerd.superwispy.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Entity.Entity;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.GlobalData;
import xyz.someboringnerd.superwispy.managers.ItemManager;
import xyz.someboringnerd.superwispy.util.ItemStack;
import xyz.someboringnerd.superwispy.util.MathUtil;

import java.util.Random;

import static net.npcinteractive.TranscendanceEngine.Managers.RoomManager.world;

public class DroppedItemEntity extends Entity
{

    @Getter(AccessLevel.PUBLIC)
    private ItemStack stack;

    @Getter(AccessLevel.PUBLIC)
    private int UUID = new Random().nextInt(0, Integer.MAX_VALUE - 1);

    public DroppedItemEntity(Vector2 initialPosition, ItemStack stack)
    {
        super(initialPosition, "");
        this.stack = stack;
        CreateBody();

        LogManager.print("Created new drop with UUID " + UUID);
    }

    void CreateBody()
    {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(getX(), getY());

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(.1f,.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        Fixture fixture = body.createFixture(fixtureDef);

        box.dispose();
        boundingBox = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(body == null) return;

        setPosition(body.getPosition().x, body.getPosition().y);
        batch.draw(stack.getItem().getItemTexture(), getX(), getY(), 16, 16);

        for(DroppedItemEntity entity : ItemManager.dropList)
        {
            if(entity.body != null && entity.stack.isEqual(this.stack) && UUID != entity.getUUID() && MathUtil.getDistance(body.getPosition(), entity.body.getPosition()) / 32 <= 2f)
            {
                this.stack.setQuantity(this.stack.getQuantity() + entity.stack.getQuantity());
                entity.DestroySelf();
                ItemManager.removeList.add(entity);
            }
        }



        if(GlobalData.displayDebugInformation)
            RenderUtil.DrawText(batch, stack.toObject().toString(5), new Vector2(getX(), getY() + 30), RenderUtil.DebugFont);
    }

    private void DestroySelf()
    {
        world.destroyBody(body);

        body = null;
    }
}
