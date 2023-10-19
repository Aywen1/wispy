package xyz.someboringnerd.superwispy.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import xyz.someboringnerd.superwispy.content.items.Item;

public class ItemStack
{
    /**
     * Modifier selon préférence, quantité d'un même objet dans un seul stack
     *
     * /!\ DIFFÉRENT DE maxQuantity/!\
     */
    public static final int MAX_ITEM_QUANTITY = 999;



    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int quantity, maxQuantity;

    @Getter(AccessLevel.PUBLIC)
    private String name;

    @Getter(AccessLevel.PUBLIC)
    private Item item;

    public ItemStack(Item item, String displayName)
    {
        this.item = item;
        this.name = displayName;
    }

    public ItemStack(Item item)
    {
        this.item = item;
        this.name = item.getDefaultName();
    }

    /**
     * Permet de savoir si deux stacks sont similaires
     * @param stack : instance du stack a comparer
     * @return : vrai si ils peuvent être merge en un stack
     */
    public boolean isEqual(ItemStack stack)
    {
        return this.getItem() == stack.getItem() && stack.getName().equals(getName());
    }

    /**
     * Permet de sauvegarder l'object dans l'inventaire du joueur
     * @return : l'object au format json
     */
    public JSONObject toObject()
    {
        JSONObject obj = new JSONObject();

        obj.put("quantity", quantity);
        obj.put("displayName", name);
        obj.put("item_id", item.getID());

        return obj;
    }
}
