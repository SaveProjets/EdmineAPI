package fr.edminecoreteam.api.spigot.item;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder {
    private final ItemStack is;

    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material mat, int amount) {
        is = new ItemStack(mat, amount);
    }

    public ItemBuilder(Material mat, int amount, short meta) {
        is = new ItemStack(mat, amount, meta);
    }

    public static ItemStack fromJson(String json) {
        return new Gson().fromJson(json, ItemStack.class);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setDurability(short durability) {
        is.setDurability(durability);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        setDurability((short) durability);
        return this;
    }

    public ItemBuilder setTexture(String hash) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        propertyMap.put("textures", new Property("textures", hash));
        SkullMeta skullMeta = (SkullMeta) this.is.getItemMeta();
        Class<?> c_skullMeta = skullMeta.getClass();
        try {
            Field f_profile = c_skullMeta.getDeclaredField("profile");
            f_profile.setAccessible(true);
            f_profile.set(skullMeta, profile);
            f_profile.setAccessible(false);
            this.is.setItemMeta(skullMeta);
            return this;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideItemFlags() {
        ItemMeta meta = is.getItemMeta();
        for (ItemFlag value : ItemFlag.values()) {
            meta.addItemFlags(value);
        }
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder addUnsafeEnchantement(Enchantment e, int level) {
        is.addUnsafeEnchantment(e, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment e) {
        is.removeEnchantment(e);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(owner);
            is.setItemMeta(meta);
        } catch (ClassCastException expected) {

        }
        return this;
    }
    public ItemBuilder addEnchant(Enchantment e,int level){
        ItemMeta meta = is.getItemMeta();
        meta.addEnchant(e,level,true);
        is.setItemMeta(meta);
        return this;
    }
    public ItemBuilder addEnchantments(Map<Enchantment,Integer> enchantments){
        is.addEnchantments(enchantments);
        return this;
    }
    public ItemBuilder setInfinityDurability(){
        ItemMeta meta = is.getItemMeta();
        meta.spigot().setUnbreakable(true);
        is.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        List<String> list = new ArrayList<>();
        for (String s : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        im.setLore(list);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line)) return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder addLoreLine(String line) {
        line = ChatColor.translateAlternateColorCodes('&', line);
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder addLoreLine(String line, int pos) {
        line = ChatColor.translateAlternateColorCodes('&', line);
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    @SuppressWarnings("deprecation")
    public ItemBuilder setDyeColor(DyeColor color) {
        this.is.setDurability(color.getData());
        return this;
    }
    @Deprecated
    public ItemBuilder setWoolColor(DyeColor color) {
        if (!is.getType().equals(Material.WOOL)) return this;
        this.is.setDurability(color.getData());
        return this;
    }
    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }
    public ItemStack toItemStack() {
        return is;
    }
    public String toJson() {
        return new Gson().toJson(is);
    }

    public Button toButton() {
        return new Button(this.toItemStack());
    }


}
