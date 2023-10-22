package xyz.someboringnerd.superwispy.gui.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Util.RenderUtil;
import xyz.someboringnerd.superwispy.content.items.debug.Air;
import xyz.someboringnerd.superwispy.gui.screens.Inventory;
import xyz.someboringnerd.superwispy.managers.InventoryManager;
import xyz.someboringnerd.superwispy.rooms.GameRoom;
import xyz.someboringnerd.superwispy.util.ItemStack;

public class Slot
{
    private final Texture slot = FileManager.getTexture("gui/slot");

    private Rectangle getBoundingBox(Vector2 coordinates)
    {
        return new Rectangle(coordinates.x + (21 * .75f), coordinates.y + (22 * .75f), 21 * 2f, 22 * 2f);
    }

    public void Draw(Batch batch, Vector2 coordinates, int id)
    {
        if(getBoundingBox(coordinates).overlaps(GameRoom.getMousePosition()))
            batch.setColor(0, 1, 1, 1);

        batch.draw(slot, coordinates.x, coordinates.y, 21 * 2f, 22 * 2f);

        // dessine l'itemstack dans le slot
        ItemStack stack = InventoryManager.getInstance().getItemInSlot(id);
        if(!(stack.getItem() instanceof Air ))
        {
            if((Inventory.getInstance().selected == null || Inventory.getInstance().id != id))
            {
                batch.draw(stack.getItem().getItemTexture(), coordinates.x + 5, coordinates.y + 5, 21 * 1.65f, 22 * 1.65f);
                RenderUtil.DrawText(batch, stack.getQuantity() + "", new Vector2(coordinates.x + 10, coordinates.y + 20), RenderUtil.DebugFont);
            }
        }

        if(getBoundingBox(coordinates).overlaps(GameRoom.getMousePosition()))
        {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            {
                if (Inventory.getInstance().selected == null && !(stack.getItem() instanceof Air))
                {
                    Inventory.getInstance().selected = new ItemStack(stack.getItem());
                    Inventory.getInstance().selected.setQuantity(stack.getQuantity());
                    Inventory.getInstance().id = id;
                    InventoryManager.getInstance().getPlayerInventory().set(id, new ItemStack(new Air()));
                    InventoryManager.getInstance().getPlayerInventory().get(id).setQuantity(0);
                }
                else
                {
                    if(Inventory.getInstance().selected == stack && Inventory.getInstance().id == id)
                    {
                        Inventory.getInstance().selected = null;
                        Inventory.getInstance().id = -1;
                    }
                    else
                    {
                        if(Inventory.getInstance().selected != null)
                        {
                            if (Inventory.getInstance().selected.isEqual(InventoryManager.getInstance().getPlayerInventory().get(id)))
                            {
                                InventoryManager.getInstance().getPlayerInventory().get(id).setQuantity(InventoryManager.getInstance().getPlayerInventory().get(id).getQuantity() + Inventory.getInstance().selected.getQuantity());
                                Inventory.getInstance().selected = null;
                                Inventory.getInstance().id = -1;
                            }
                            else if (InventoryManager.getInstance().getPlayerInventory().get(id).getItem() instanceof Air)
                            {
                                InventoryManager.getInstance().getPlayerInventory().set(id, Inventory.getInstance().selected);
                                Inventory.getInstance().selected = null;
                                Inventory.getInstance().id = -1;
                            }
                        }
                    }
                }
            }

            else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
            {
                // place la moitié du stack dans le selected
                if (Inventory.getInstance().selected == null && !(stack.getItem() instanceof Air))
                {
                    if (stack.getQuantity() > 1)
                    {
                        Inventory.getInstance().selected = new ItemStack(stack.getItem());
                        Inventory.getInstance().id = -1;

                        if (stack.getQuantity() % 2 == 0)
                        {
                            int quantity = InventoryManager.getInstance().getPlayerInventory().get(id).getQuantity();
                            Inventory.getInstance().selected.setQuantity(quantity / 2);
                            InventoryManager.getInstance().getPlayerInventory().get(id).setQuantity(quantity / 2);
                        }else
                        {
                            int quantity = InventoryManager.getInstance().getPlayerInventory().get(id).getQuantity() - 1;
                            Inventory.getInstance().selected.setQuantity((quantity / 2));
                            InventoryManager.getInstance().getPlayerInventory().get(id).setQuantity(1 + (quantity / 2));
                        }
                    }
                }
                else
                {
                    if (Inventory.getInstance().selected != null)
                    {
                        if(Inventory.getInstance().selected.isEqual(stack))
                        {
                            InventoryManager.getInstance().getPlayerInventory().get(id).setQuantity(stack.getQuantity() + 1);
                            Inventory.getInstance().selected.setQuantity(Inventory.getInstance().selected.getQuantity() - 1);
                        }
                        else if(InventoryManager.getInstance().getPlayerInventory().get(id).getItem() instanceof Air)
                        {
                            if(Inventory.getInstance().id == id)
                            {
                                Inventory.getInstance().id = -1;
                            }
                            InventoryManager.getInstance().getPlayerInventory().set(id, new ItemStack(Inventory.getInstance().selected.getItem()));
                            Inventory.getInstance().selected.setQuantity(Inventory.getInstance().selected.getQuantity() - 1);
                        }
                        else if(InventoryManager.getInstance().getPlayerInventory().get(id) == null || InventoryManager.getInstance().getPlayerInventory().get(id).getItem() == null)
                        {
                            InventoryManager.getInstance().getPlayerInventory().set(id, new ItemStack(Inventory.getInstance().selected.getItem()));
                            Inventory.getInstance().selected.setQuantity(Inventory.getInstance().selected.getQuantity() - 1);
                        }
                    }
                }
            }
        }

        batch.setColor(1, 1, 1, 1);
    }
}
