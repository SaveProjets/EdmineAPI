package fr.edminecoreteam.api.spigot.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    public ItemBuilder createItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder createItem(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder createItem(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public ItemBuilder createItem(Material material, int amount, short meta) { return new ItemBuilder(material, amount, meta); }

}