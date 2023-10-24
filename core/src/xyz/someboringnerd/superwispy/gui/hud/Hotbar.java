package xyz.someboringnerd.superwispy.gui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.content.items.debug.Air;
import xyz.someboringnerd.superwispy.managers.InventoryManager;

public class Hotbar
{

    Texture image = FileManager.getTexture("gui/hotbar");
    Texture selected_slot = FileManager.getTexture("gui/selected_slot");

    @Getter(AccessLevel.PUBLIC)
    int selected = 1;

    Vector2 position = new Vector2(RenderUtil.getXRelativeZero() + 640 - getScale().x / 2, RenderUtil.getYRelativeZero() - 700);

    public Hotbar()
    {

    }

    public Vector2 getScale()
    {
        return new Vector2((float) 1086 / 2f, (float) 131 / 2f);
    }



    public void Draw(Batch batch)
    {
        position = new Vector2(RenderUtil.getXRelativeZero() + 640 - getScale().x / 2, RenderUtil.getYRelativeZero() - 700);

        batch.draw(image, position.x, position.y, getScale().x, getScale().y);

        batch.draw(selected_slot, position.x + ((getScale().x / 9f) * selected), position.y, getScale().x / 9f, getScale().y);

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            selected = 0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            selected = 1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            selected = 2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            selected = 3;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
            selected = 4;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
            selected = 5;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)){
            selected = 6;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)){
            selected = 7;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
            selected = 8;
        }

        for(int i = 0; i < 9; i++)
        {
            if(InventoryManager.getInstance().getPlayerInventory().get(i) != null)
            {

                if(InventoryManager.getInstance().getPlayerInventory().get(i).getItem() instanceof Air)
                {
                    InventoryManager.getInstance().getPlayerInventory().get(i).setQuantity(0);
                }
                batch.draw(InventoryManager.getInstance().getPlayerInventory().get(i).getItem().getItemTexture(), position.x + ((getScale().x / 9f) * i) + 7.5f, position.y + 7.5f, getScale().x / 11f, getScale().x / 11f);
                int size = InventoryManager.getInstance().getPlayerInventory().get(i).getQuantity();

                if (size != 0 || !(InventoryManager.getInstance().getPlayerInventory().get(i).getItem() instanceof Air))
                    RenderUtil.DrawText(batch, size + "", new Vector2(position.x + ((getScale().x / 9f) * i) + (12), position.y + 25), RenderUtil.DebugFont);
            }
        }
    }
}
