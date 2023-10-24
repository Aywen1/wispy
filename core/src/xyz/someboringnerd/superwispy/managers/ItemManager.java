package xyz.someboringnerd.superwispy.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import net.npcinteractive.TranscendanceEngine.Managers.LogManager;
import org.reflections.Reflections;
import xyz.someboringnerd.superwispy.content.items.Item;
import xyz.someboringnerd.superwispy.content.items.debug.Air;
import xyz.someboringnerd.superwispy.entities.DroppedItemEntity;
import xyz.someboringnerd.superwispy.entities.PlayerEntity;
import xyz.someboringnerd.superwispy.util.ItemStack;
import xyz.someboringnerd.superwispy.util.MathUtil;

import java.lang.reflect.Constructor;
import java.util.*;

public class ItemManager
{
    private static final Map<Integer, Class<? extends Item>> blockMap = new HashMap<>();

    public static final List<DroppedItemEntity> dropList = new ArrayList<>();
    public static List<DroppedItemEntity> removeList = new ArrayList<>();

    public ItemManager()
    {
        /*
            Le code suivant permet de référencer automatiquement les items du jeu de manière simple et efficace.

            Paniquez pas, c'est juste l'orienté object et non un problème fondamental de design du langage /s
        */

        Reflections reflections = new Reflections("xyz.someboringnerd.superwispy.content.items");
        Set<Class<? extends Item>> itemClasses = reflections.getSubTypesOf(Item.class);

        List<Class<? extends Item>> sortedItemClasses = new ArrayList<>(itemClasses);
        sortedItemClasses.sort(Comparator.comparing(Class::getSimpleName));

        int i = 0;
        for (Class<? extends Item> itemClass : sortedItemClasses)
        {
            try
            {
                Constructor<? extends Item> constructor = itemClass.getConstructor();
                blockMap.put(i, itemClass);
                i++;
            }
            catch (NoSuchMethodException e)
            {
                //throw new RuntimeException("Block class " + itemClass.getSimpleName() + " is missing the required constructor.");
            }
        }
    }

    public static void Update(Batch batch)
    {
        for(DroppedItemEntity entity : dropList)
        {
            entity.draw(batch, 1.0f);
        }

        for(DroppedItemEntity entity : removeList)
        {
            dropList.remove(entity);
        }
        boolean found = false;
        for(DroppedItemEntity entity : dropList)
        {
            if(MathUtil.getDistance(entity.getPosition(), PlayerEntity.getInstance().getPosition()) / 32 <= 1.5f && entity.frameAlive >= 45)
            {
                int i = 0;
                for(ItemStack stack : InventoryManager.getInstance().getPlayerInventory())
                {
                    if(!found)
                    {
                        if (stack.isEqual(entity.getStack()))
                        {
                            stack.setQuantity(stack.getQuantity() + entity.getStack().getQuantity());
                            entity.DestroySelf();
                            removeList.add(entity);
                            LogManager.print("Added " + entity.getStack().getQuantity() + " " + entity.getStack().getName() + " to slot " + i);
                            found = true;
                        }
                        else if (stack.getItem() instanceof Air)
                        {
                            PlayerEntity.getInstance().getInventory().getPlayerInventory().set(i, entity.getStack());
                            entity.DestroySelf();
                            removeList.add(entity);
                            LogManager.print("Put " + entity.getStack().getQuantity() + " " + entity.getStack().getName() + " in slot " + i);
                            found = true;
                        }
                    }
                    i++;
                }

                found = false;
            }
        }

        InventoryManager.getInstance().Update();
    }

    public static void dropSomething(DroppedItemEntity entity)
    {
        dropList.add(entity);
    }
}
