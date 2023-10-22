package xyz.someboringnerd.superwispy.gui.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.entities.DroppedItemEntity;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;
import xyz.someboringnerd.superwispy.gui.GUI;
import xyz.someboringnerd.superwispy.gui.content.Slot;
import xyz.someboringnerd.superwispy.managers.ItemManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;
import xyz.someboringnerd.superwispy.util.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends GUI
{

    List<Slot> slotList = new ArrayList<>();

    @Getter(AccessLevel.PUBLIC)
    private static Inventory instance;

    public ItemStack selected;
    public int id;

    public Inventory()
    {
        super(FileManager.getTexture("gui/inventory"), new Vector2(191 * 2.5f, 190 * 2.5f), false);

        instance = this;

        LogManager.print("Inventory was opened");

        for(int i = 0; i <= 40; i++)
        {
            slotList.add(new Slot());
        }
    }

    @Override
    public void UpdateGUI(Batch batch)
    {
        int i = 0;
        int j = 0;
        int yOffset = -100;
        int yOffsetStep = 50;

        for (Slot slot : slotList)
        {
            if(i < 36) {
                int xOffset = (int) (RenderUtil.getXRelativeZero() + getScale().x + (21 * 2f * j) - 25);
                slot.Draw(batch, new Vector2(xOffset, RenderUtil.getYRelativeZero() - getScale().y + yOffset), i);

                if (j >= 8)
                {
                    j = 0;
                    yOffset += yOffsetStep;
                }
                else
                {
                    j++;
                }

                i++;
            }
        }

        if(selected != null)
        {
            if(selected.getQuantity() <= 0)
            {
                selected = null;
                Inventory.getInstance().id = -1;
            }
            else
            {
                batch.draw(selected.getItem().getItemTexture(), GameRoom.getMousePosition().x - 24, GameRoom.getMousePosition().y - 24, 21 * 1.65f, 22 * 1.65f);
                RenderUtil.DrawText(batch, selected.getQuantity() + "", new Vector2(GameRoom.getMousePosition().x - 24, GameRoom.getMousePosition().y - 12), RenderUtil.DebugFont);
            }
        }
    }

    @Override
    public void OnClose()
    {
        if(selected != null)
        {
            ItemManager.dropSomething(new DroppedItemEntity(PlayerEntity.getInstance().getPosition(), selected));
        }
    }
}
