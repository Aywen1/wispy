package xyz.someboringnerd.superwispy.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.PerformanceCounter;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
import net.npcinteractive.TranscendanceEngine.Map.DebugCube;
import xyz.someboringnerd.superwispy.content.structures.Tree;
import xyz.someboringnerd.superwispy.managers.BlockManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Chunk extends Thread
{
    public List<Block> blockList = new ArrayList<>();

    @Getter(AccessLevel.PUBLIC)
    private int offset;

    int prevY = 0;

    int[] topBlock;

    private int leftY, rightY;

    boolean generateRight = false;

    public Chunk instance;

    public Chunk(int offset)
    {
        instance = this;
        this.offset = offset;
        counter = new PerformanceCounter("chunk " + (offset / 512));

        counter.start();
        // chunk a gauche de celui qu'on genère
        if(GameRoom.getInstance().doesChunkExistAtOffset(offset - 512))
        {
            LogManager.print("Generated chunk on the right of an existing chunk");
            LogManager.print("Top left of previous chunk : " + GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY);
            generateRight = true;
            Generate(GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY - 3, GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY + 3);
        }
        // chunk a droite de celui qu'on genère
        else if(GameRoom.getInstance().doesChunkExistAtOffset(offset + 512))
        {
            LogManager.print("Generated chunk on the left of an existing chunk");
            generateRight = false;
            LogManager.print("Top right of previous chunk : " + GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY);
            Generate(GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY - 3, GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY + 3);
        }else
        {
            if(offset != 0)
            {
                LogManager.error("A chunk was generated without being connected to another chunk, THIS SHOULD NEVER HAPPEN !!");
            }
            Generate(57, 63);
        }
    }

    boolean isPlayerInChunk()
    {
        return GameRoom.getInstance().getPlayer().getX() >= offset && offset + 16 * 32 >= GameRoom.getInstance().getPlayer().getX();
    }

    int y = 0;

    long RenderTime, GenerateTime;

    void Generate(int Lower, int Upper)
    {
        GeneratingDone.set(false);
        Thread thread = new Thread()
        {
            public void run()
            {
                topBlock = new int[16];
                if(offset == 0)
                {
                    y = 63;
                }
                for(int i = 0; i < 16; i++)
                {
                    if(i == 0)
                    {
                        y = Upper - 3;
                        if(generateRight)
                            leftY = y;
                        else
                            rightY = y;
                    }
                    if(i == 15)
                    {
                        if(generateRight)
                            rightY = y;
                        else
                            leftY = y;
                    }
                    int rng = new Random().nextInt(0, 100);
                    if(rng <= 20)
                    {
                        y--;
                    }
                    else if(rng >= 80)
                    {
                        y++;
                    }

                    if(y <= Lower)
                    {
                        y = Lower;
                    }

                    if(y >= Upper)
                    {
                        y = Upper;
                    }

                    if(y <= 54)
                    {
                        y = 54;
                    }

                    if(y >= 70)
                    {
                        y = 70;
                    }


                    topBlock[(generateRight ? i : 15 - i)] = y;

                    int realX = (generateRight ? i : 15 - i);


                    blockList.add(new Block(BlockManager.registeredBlocks.get(1), new Vector2(offset + realX * 32, y * 32), 1, instance));

                    for(int j = y - 1; j > 0; j--)
                    {
                        if(j >= y - 3)
                        {
                            blockList.add(new Block(BlockManager.registeredBlocks.get(2), new Vector2(offset + realX * 32, j * 32), 2, instance));
                        }else{
                            blockList.add(new Block(BlockManager.registeredBlocks.get(3), new Vector2(offset + realX * 32, j * 32), 3, instance));
                        }
                    }

                    int tempY = y + 1;

                    if(rng < 60 && rng > 50 && i < 13 && i > 3 && offset != 0)
                    {
                        Tree tree = new Tree();

                        for(int x = 0; x < tree.content.length; x++)
                        {
                            for(int _y = 0; _y < tree.content[x].length; _y++)
                            {
                                if(tree.content[x][_y] != 0)
                                    blockList.add(new Block(BlockManager.registeredBlocks.get(tree.content[x][_y]), new Vector2(offset + realX * 32 + (x * 32), tempY * 32 + (_y * 32)), 1, instance));
                            }
                        }
                    }
                }


                GeneratingDone.set(true);
            }
        };

        thread.start();
    }

    public boolean bodiesGenerated = false;

    public boolean regeneratingBodies;

    @Deprecated(since = "16/10/23 : Les chunks n'ont plus a supprimer leurs colliders.")
    private void DestroyBodies()
    {

    }

    // @fix : Une fois créé, un body ne peut être supprimé sinon ça marche pas les trucs
    public void GenerateBodies()
    {
        for(Block block : blockList)
        {
            if(block.cube.body == null)
            {
                block.cube.createBody(RoomManager.world);
            }

            if(getBlockAtCoordinates(new Vector2(block.getX(), block.getY() + 32)) == null ||
                    getBlockAtCoordinates(new Vector2(block.getX(), block.getY() - 32)) == null ||
                    getBlockAtCoordinates(new Vector2(block.getX() + 32, block.getY())) == null ||
                    getBlockAtCoordinates(new Vector2(block.getX() - 32, block.getY())) == null)
            {
                if(block.id != 0)
                {
                    block.cube.body.setActive(true);
                }
            }else{
                block.cube.body.setActive(false);
            }
        }

        bodiesGenerated = true;
    }

    public Block getBlockAtCoordinates(Vector2 blockCoordinate)
    {
        for(Block block : blockList)
        {
            // comment monter le temps de génération de quelques secondes a une heure et demi :)
            // - SBN
            // LogManager.print(block.getPosition() + " | " + blockCoordinate + " | " + (block.getPosition() == blockCoordinate));
            if(block.getPosition().x == blockCoordinate.x && block.getPosition().y == blockCoordinate.y)
            {
                return block;
            }
        }

        return null;
    }

    AtomicBoolean GeneratingDone = new AtomicBoolean(false);
    PerformanceCounter counter;
    public void Draw(Batch batch)
    {
        if(!GeneratingDone.get()) return;

        if(!bodiesGenerated)
        {
            GenerateBodies(); // PUTAIN
        }

        if(isPlayerInChunk())
        {
            GameRoom.getInstance().Generate(offset);
            GenerateBodies();
            batch.setColor(0, 1, 0, 1);
        }
        List<Block> toDelete = new ArrayList<>();
        for (Block block : blockList)
        {
            if(block.markForDelete)
            {
                toDelete.add(block);
            }else {
                block.draw(batch, 1.0f);
            }
        }

        if(!toDelete.isEmpty())
        {
            for (Block block : toDelete)
                RoomManager.world.destroyBody(block.cube.body);

            blockList.removeAll(toDelete);

            bodiesGenerated = false;
        }

        batch.setColor(1, 1, 1, 1);
    }
}