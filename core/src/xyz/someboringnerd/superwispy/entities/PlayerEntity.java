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
import net.npcinteractive.TranscendanceEngine.Entity.DIRECTION;
import net.npcinteractive.TranscendanceEngine.Entity.Entity;
import net.npcinteractive.TranscendanceEngine.Managers.AudioManager;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.InputSystem;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.GlobalData;
import xyz.someboringnerd.superwispy.gui.hud.Hotbar;
import xyz.someboringnerd.superwispy.gui.screens.Inventory;
import xyz.someboringnerd.superwispy.managers.InventoryManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

import static net.npcinteractive.TranscendanceEngine.Managers.RoomManager.world;

public class PlayerEntity extends Entity
{
    @Getter(AccessLevel.PUBLIC)
    private static PlayerEntity instance;

    final static float MAX_VELOCITY = 256f;
    final static float JUMP_VELOCITY = 128f;

    private final Texture player;

    public DIRECTION direction;

    @Getter(AccessLevel.PUBLIC)
    private final Hotbar hotbar = new Hotbar();

    @Getter(AccessLevel.PUBLIC)
    private final InventoryManager inventory = new InventoryManager();

    public PlayerEntity(Vector2 position)
    {
        super(position, "");
        instance = this;
        player = FileManager.getTexture("p_stop");

        CreateBody();
    }

    /**
     * Génère la boite de collision du joueur
     */
    void CreateBody()
    {
        // complètement copié collé de la documentation mdr
        // - SBN

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(getX(), getY());

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(15);

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
        // obtient la position et vélocité du joueur
        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();
        //    vitesse       |   movement en X
        float movement = 0.5f, moveX = 0;

        // empeche le joueur d'être trop rapide
        if (Math.abs(vel.x) > MAX_VELOCITY)
        {
            vel.x = Math.signum(vel.x) * MAX_VELOCITY;
            body.setLinearVelocity(vel.x, vel.y);
        }

        // si le joueur est autorisé a bouger
        if(GameRoom.getGui() == null || GameRoom.getGui().isCanPlayerMove())
        {
            // réduit la vélocité du joueur de 30% si il ne bouge pas
            // ça donne un petit effet fancy où il ne se stoppe pas brusquement
            if (!InputSystem.moveLeft() && !InputSystem.moveRight())
            {
                moveX = vel.x * 0.7f;
            }

            // enlève 128 * movement a la vélocité du joueur pour le faire aller a gauche
            // et change sa direction pour l'animation
            if (InputSystem.moveLeft() && vel.x > -MAX_VELOCITY)
            {
                direction = DIRECTION.LEFT;
                moveX = (-(128 * movement));
            }

            // pareil mais a droite
            if (InputSystem.moveRight() && vel.x < MAX_VELOCITY) {
                direction = DIRECTION.RIGHT;
                moveX = (128 * movement);
            }

            // si le joueur saute et que sa vélocité verticale est quasi égale a 0
            if (InputSystem.justMoveUp() && Math.abs(body.getLinearVelocity().y) <= 0.03f) {
                yJump = pos.y / 32;

                body.setLinearVelocity(vel.x, vel.y);
                body.setTransform(pos.x, pos.y, 0);

                AudioManager.getInstance().playAudio("sfx/jump");

                body.applyLinearImpulse(0, (JUMP_VELOCITY * JUMP_VELOCITY * JUMP_VELOCITY) * 1.5f, 0, pos.y, true);
            }
        }

        // accelère la vélocité du joueur de 10% par frame où il tombe
        if(body.getLinearVelocity().y < 0)
        {
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y * 1.1f);
        }

        // si le joueur est collé a un block et qu'il bouge dans la direction du block et que sa position Y convertie en int n'a pas changé depuis le début du saut
        // on nullifie sa vélocité en X, ça permet d'éviter qu'il se coince dedans
        // au prix que quand ça arrive, son saut est réduit de moitié.
        // enlever la condition body.getLinearVelocity().y < 0 fixe le problème mais rend les sauts assez akward.
        // plus de tests sont necessaires
        if(previousX / 32 == pos.x / 32 && body.getLinearVelocity().y < 0 && (int)(pos.y / 32) == (int)yJump)
        {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
        else
        {
            body.setLinearVelocity(moveX, body.getLinearVelocity().y);
        }

        // empêche la boite de collision d'avoir des rotations
        body.setFixedRotation(true);

        // change la position du sprite pour qu'elle soit en accord avec la boite de collision
        setPosition(body.getPosition().x - 16, body.getPosition().y - 16);

        // la bounding box est une deuxième boite de collision utilisée par l'engine.
        boundingBox.set(getX(), getY(), getWidth(), getHeight());

        previousX = pos.x;

        // dessine le joueur.
        // ajouter 32 a sa position X et mettre sa scale a -32 en x permet de flip le sprite
        batch.draw(player, direction == DIRECTION.LEFT ? getX() + 32 : getX(), getY(), direction == DIRECTION.LEFT ? -32 : 32, 64);

        // ouvre l'inventaire
        if(InputSystem.next() && GameRoom.getGui() == null)
        {
            GameRoom.setGui(new Inventory());
        }

        hotbar.Draw(batch);

        // informations de debug
        if(GlobalData.displayDebugInformation)
        {
            RenderUtil.DrawText(batch, "X : " + (getX() / 32), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 32), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "Y : " + (getY() / 32), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 64), RenderUtil.Deter30px);

            RenderUtil.DrawText(batch, "vY : " + (body.getLinearVelocity().y), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 96), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "vX : " + (body.getLinearVelocity().x), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - 128), RenderUtil.Deter30px);

            RenderUtil.DrawText(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 32)), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "Chunks loaded : " + GameRoom.getInstance().safeLoaded().size(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 64)), RenderUtil.Deter30px);
            RenderUtil.DrawText(batch, "total chunks : " + GameRoom.getInstance().chunklist.size(), new Vector2(RenderUtil.getXRelativeZero() + 32, RenderUtil.getYRelativeZero() - (128 + 96)), RenderUtil.Deter30px);
        }
    }
}
