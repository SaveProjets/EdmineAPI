package fr.edminecoreteam.api.spigot.item;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemNotUse extends ItemStack {

    protected ItemNotUse(ItemStack itemStack) {
        super(itemStack);
    }

    protected ItemNotUse(Material material) {
        super(material);
    }

    protected ItemNotUse(Material material, int amount) {
        super(material, amount);
    }

    public ItemNotUse setName(String name) {
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

    public ItemNotUse setLore(List<String> lore) {
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

    public ItemNotUse setQuantity(int amount) {
        this.setAmount(amount);
        return this;
    }

    public ItemNotUse setUnbreakable(boolean unbreakable) {
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

    public ItemNotUse addEnchant(Enchantment enchant, int level) {
        this.addEnchantment(enchant, level);
        return this;
    }

    public ItemNotUse addEnchants(Map<Enchantment, Integer> enchants) {
        this.addEnchantments(enchants);
        return this;
    }

    public ItemNotUse addFlags(ItemFlag... flags) {
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.addItemFlags(flags);
        this.setItemMeta(meta);
        return this;
    }

    public ItemNotUse removeFlags(ItemFlag... flags) {
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
