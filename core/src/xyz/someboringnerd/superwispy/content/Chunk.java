package xyz.someboringnerd.superwispy.content;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Managers.RoomManager;
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

    private int leftY, rightY;

    boolean generateRight = false;

    public Chunk(int offset)
    {
        this.offset = offset;

        if(GameRoom.getInstance().doesChunkExistAtOffset(offset - 512))
        {
            LogManager.print("Generated chunk on the left of an existing chunk");
            LogManager.print("Top left of previous chunk : " + GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY);
            generateRight = true;
            Generate(GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY - 3, GameRoom.getInstance().getChunkAtOffset(offset - 512).rightY + 3);
        }
        else if(GameRoom.getInstance().doesChunkExistAtOffset(offset + 512))
        {
            LogManager.print("Generated chunk on the right of an existing chunk");
            generateRight = false;
            LogManager.print("Top right of previous chunk : " + GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY);
            Generate(GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY - 3, GameRoom.getInstance().getChunkAtOffset(offset + 512).leftY + 3);
        }else
        {
            LogManager.print("Generated chunk as is with no clue of where to do");
            Generate(63, 60);
        }
    }

    boolean isPlayerInChunk()
    {
        return GameRoom.getInstance().getPlayer().getX() >= offset && offset + 16 * 32 >= GameRoom.getInstance().getPlayer().getX();
    }

    int y = 0;

    void Generate(int Lower, int Upper)
    {
        GeneratingDone.set(false);
        Thread thread = new Thread()
        {
            public void run()
            {
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
                    else if(i == 15)
                    {
                        if(generateRight)
                            rightY = y;
                        else
                            leftY = y;
                    }

                    int rng = new Random().nextInt(0, 100);

                    if(rng <= 33)
                    {
                        y--;
                    }
                    else if(rng >= 77)
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

                    int realX = (generateRight ? i : 15 - i);

                    blockList.add(new Block("blocks/grass", new Vector2(offset + realX * 32, y * 32)));

                    for(int j = y - 1; j > 0; j--)
                    {
                        if(j >= y - 3)
                        {
                            blockList.add(new Block(("blocks/dirt"), new Vector2(offset + realX * 32, j * 32)));
                        }else{
                            blockList.add(new Block(("blocks/stone"), new Vector2(offset + realX * 32, j * 32)));
                        }
                    }
                }


                GeneratingDone.set(true);
            }
        };

        thread.start();
    }

    AtomicBoolean GeneratingDone = new AtomicBoolean(false);

    public void Draw(Batch batch)
    {
        if(!GeneratingDone.get()) return;

        if(isPlayerInChunk())
        {
            GameRoom.getInstance().Generate(offset);
            batch.setColor(0, 1, 0, 1);
        }

        for (Block block : blockList)
        {
            if(block.cube.shouldInitLater)
            {
                block.cube.createBody(RoomManager.world);
            }
            block.draw(batch, 1.0f);
        }

        batch.setColor(1, 1, 1, 1);


    }
}