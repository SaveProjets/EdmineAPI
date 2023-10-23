package fr.edminecoreteam.api.spigot.item;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item extends ItemStack {

    protected Item(ItemStack itemStack) {
        super(itemStack);
    }

    protected Item(Material material) {
        super(material);
    }

    protected Item(Material material, int amount) {
        super(material, amount);
    }

    public Item setName(String name) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public String getName() {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        if (meta.hasDisplayName()) {
            return meta.getDisplayName();
        }
        return this.getType().name();
    }

    public Item setLore(List<String> lore) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.setLore(lore);
        this.setItemMeta(meta);
        return this;
    }

    public List<String> getLore() {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        return meta.getLore();
    }

    public Item setQuantity(int amount) {
        this.setAmount(amount);
        return this;
    }

    public Item setUnbreakable(boolean unbreakable) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.spigot().setUnbreakable(unbreakable);
        this.setItemMeta(meta);
        return this;
    }

    public boolean isUnbreakable() {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        return meta.spigot().isUnbreakable();
    }

    public Item addEnchant(Enchantment enchant, int level) {
        this.addEnchantment(enchant, level);
        return this;
    }

    public Item addEnchants(Map<Enchantment, Integer> enchants) {
        this.addEnchantments(enchants);
        return this;
    }

    public Item addFlags(ItemFlag... flags) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.addItemFlags(flags);
        this.setItemMeta(meta);
        return this;
    }

    public Item removeFlags(ItemFlag... flags) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.removeItemFlags(flags);
        this.setItemMeta(meta);
        return this;
    }

    public Button asButton() {
        return new Button(this);
    }
}
