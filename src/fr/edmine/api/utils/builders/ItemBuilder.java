package fr.edmine.api.utils.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

public class ItemBuilder
{
	private ItemStack itemStack;
	private ItemMeta itemMeta;

	public ItemBuilder(Material material)
	{
		this(material, 1);
	}

	public ItemBuilder(ItemStack itemStack)
	{
		this.itemStack = itemStack;
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, int amount)
	{
		this.itemStack = new ItemStack(material, amount);
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, int amount, byte durability)
	{
		this.itemStack = new ItemStack(material, amount, durability);
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder clone()
	{
		return new ItemBuilder(this.itemStack);
	}

	public ItemBuilder setDurability(short durability)
	{
		this.itemStack.setDurability(durability);
		return this;
	}

	public ItemBuilder setName(String name)
	{
		this.itemMeta.setDisplayName(name);
		this.itemStack.setItemMeta(this.itemMeta);
		return this;
	}

	public ItemBuilder addUnsafeEnchantment(Enchantment enchant, int level)
	{
		this.itemStack.addUnsafeEnchantment(enchant, level);
		return this;
	}

	public ItemBuilder removeEnchantment(Enchantment enchant)
	{
		this.itemStack.removeEnchantment(enchant);
		return this;
	}

	public ItemBuilder setSkullOwner(String owner)
	{
		try
		{
			SkullMeta skullMeta = (SkullMeta) this.itemMeta;
			skullMeta.setOwner(owner);
			this.itemStack.setItemMeta(skullMeta);
		}
		catch (ClassCastException ignored)
		{
		}
		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchant, int level)
	{
		this.itemMeta.addEnchant(enchant, level, true);
		this.itemStack.setItemMeta(this.itemMeta);
		return this;
	}

	public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments)
	{
		this.itemStack.addEnchantments(enchantments);
		return this;
	}

	public ItemBuilder setInfinityDurability()
	{
		this.itemStack.setDurability(Short.MAX_VALUE);
		return this;
	}

	public ItemBuilder setLore(String... lore)
	{
		this.itemMeta.setLore(Arrays.asList(lore));
		this.itemStack.setItemMeta(this.itemMeta);
		return this;
	}

	public ItemBuilder setLore(List<String> lore)
	{
		this.itemMeta.setLore(lore);
		this.itemStack.setItemMeta(this.itemMeta);
		return this;
	}

	public ItemBuilder removeLoreLine(String line)
	{
		List<String> lore = new ArrayList<>(this.itemMeta.getLore());
		if (!lore.contains(line)) return this;
		lore.remove(line);
		this.itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder removeLoreLine(int index)
	{
		List<String> lore = new ArrayList<>(this.itemMeta.getLore());
		if (index < 0 || index > lore.size()) return this;
		lore.remove(index);
		this.itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder addLoreLine(String line)
	{
		List<String> lore = new ArrayList<>();
		if (this.itemMeta.hasLore()) lore = new ArrayList<>(this.itemMeta.getLore());
		lore.add(line);
		this.itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder addLoreLine(String line, int position)
	{
		List<String> lore = new ArrayList<>(this.itemMeta.getLore());
		lore.set(position, line);
		this.itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder setDyeColor(DyeColor color)
	{
		this.itemStack = new Dye(color).toItemStack();
		return this;
	}
	
	public ItemBuilder setLeatherArmorColor(Color color)
	{
		try
		{
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemMeta;
			leatherArmorMeta.setColor(color);
			this.itemStack.setItemMeta(leatherArmorMeta);
		}
		catch (ClassCastException ignored)
		{
		}
		return this;
	}

	public void buildItem()
	{
		this.itemStack.setItemMeta(this.itemMeta);
	}
	
	public ItemStack toItemStack()
	{
		return itemStack;
	}
}
