package xyz.someboringnerd.superwispy.managers;

import xyz.someboringnerd.superwispy.util.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager
{
    private List<ItemStack> playerInventory = new ArrayList<>();

    /**
     * informations importantes : <br>
     * entre 0 et 9 : hotbar <br>
     * 10 a 18 : première rangée en partant d'en bas<br>
     * 19 a 27 : etc...<br>
     * 37 : bottes<br>
     * 38 : leggings<br>
     * 39 : plastron<br>
     * 40 : casque<br>
     *<br>
     * notes <br>
     *      * les slots de la table de craft sont gérés ailleurs<br>
     *<br>
     *      * pas besoin de failsafe, le jeu plantera si on essaye d'acceder un stack hors de la liste :)
     * @param index : slot du stack a obtenir
     * @return le stack si il existe, null sinon.
     */
    public ItemStack getItemInSlot(int index)
    {
        return playerInventory.get(index);
    }
}
