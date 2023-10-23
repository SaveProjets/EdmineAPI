package fr.edminecoreteam.api.spigot.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    public Item createItem(ItemStack itemStack) {
        return new Item(itemStack);
    }

    public Item createItem(Material material) {
        return new Item(material);
    }

    public Item createItem(Material material, int amount) {
        return new Item(material, amount);
    }

}