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
    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param mat The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     *
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }
    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param mat   The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material mat, int amount) {
        is = new ItemStack(mat, amount);
    }
    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param mat          The material of the item.
     * @param amount     The amount of the item.

     */
    public ItemBuilder(Material mat, int amount, short meta) {
        is = new ItemStack(mat, amount, meta);
    }
    /**
     * Converts the JsonItemBuilder back to a ItemBuilder
     * @param json Which JsonItemBuilder should be converted
     */

    public static ItemStack fromJson(String json) {
        return new Gson().fromJson(json, ItemStack.class);
    }

    /**
     * Clone the ItemBuilder into a new one.
     *
     * @return The cloned instance.
     */

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }
    /**
     * Change the durability of the item.
     *
     * @param durability The durability to set it to.
     */

    public ItemBuilder setDurability(short durability) {
        is.setDurability(durability);
        return this;
    }

    /**
     * Change the durability of the item.
     *
     * @param durability The durability to set it to.
     */
    public ItemBuilder setDurability(int durability) {
        setDurability((short) durability);
        return this;
    }
    /**
     * Change the texture of an item
     *
     * @param hash the head hash
     */

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
    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
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
    /**
     * Add an unsafe enchantment.
     *
     * @param e  The enchantment to add.
     * @param level The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantement(Enchantment e, int level) {
        is.addUnsafeEnchantment(e, level);
        return this;
    }
    /**
     * Remove a certain enchant from the item.
     *
     * @param e The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment e) {
        is.removeEnchantment(e);
        return this;
    }
    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param owner The name of the skull's owner.
     */
    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setOwner(owner);
            is.setItemMeta(meta);
        } catch (ClassCastException expected) {

        }
        return this;
    }
    /**
     * Add an enchant to the item.
     *
     * @param e  The enchant to add
     * @param level The level
     */
    public ItemBuilder addEnchant(Enchantment e,int level){
        ItemMeta meta = is.getItemMeta();
        meta.addEnchant(e,level,true);
        is.setItemMeta(meta);
        return this;
    }
    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments(Map<Enchantment,Integer> enchantments){
        is.addEnchantments(enchantments);
        return this;
    }
    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemBuilder setInfinityDurability(){
        ItemMeta meta = is.getItemMeta();
        meta.spigot().setUnbreakable(true);
        is.setItemMeta(meta);
        return this;
    }
    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        List<String> list = new ArrayList<>();
        for (String s : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        im.setLore(list);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
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
    /**
     * Remove a lore line.
     *
     * @param line The lore to remove.
     */
    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line)) return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
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
    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @param pos  The index of where to put it.
     */
    public ItemBuilder addLoreLine(String line, int pos) {
        line = ChatColor.translateAlternateColorCodes('&', line);
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Sets the dye color on an item.
     * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
     *
     * @param color The color to put.
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setDyeColor(DyeColor color) {
        this.is.setDurability(color.getData());
        return this;
    }
    /**
     * Sets the dye color of a wool item. Works only on wool.
     *
     * @param color The DyeColor to set the wool item to.
     * @see ItemBuilder#setDyeColor(DyeColor)
     * @deprecated As of version 1.2 changed to setDyeColor.
     */
    @Deprecated
    public ItemBuilder setWoolColor(DyeColor color) {
        if (!is.getType().equals(Material.WOOL)) return this;
        this.is.setDurability(color.getData());
        return this;
    }
    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     *
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }
    /**
     * Retrieves the itemstack from the ItemBuilder.
     *
     * @return The itemstack created/modified by the ItemBuilder instance.
     */
    public ItemStack toItemStack() {
        return is;
    }
    /**
     * Converts the ItemBuilder to a JsonItemBuilder
     * @return The ItemBuilder as JSON String
     */
    public String toJson() {
        return new Gson().toJson(is);
    }

    public Button toButton() {
        return new Button(this.toItemStack());
    }


}
