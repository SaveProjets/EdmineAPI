package fr.edmine.api.utils.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edmine.api.EdmineAPISpigot;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

@SuppressWarnings("unused")
public class BossBarBuilder extends BukkitRunnable
{
	private String title;
	private double health;

	private final HashMap<Player, Wither> withers = new HashMap<>();
	private final List<Player> init = new ArrayList<>();

	public BossBarBuilder(String title,double health)
	{
		this.title = title;
		this.health = health;
		runTaskTimer(EdmineAPISpigot.getInstance(), 0, 10);
	}

	/**
	 * Permet de put un joueur dans une liste qui gère tout les joueurs et leur
	 * bossbar.
	 * 
	 * @param player Joueur
	 */
	public void putPlayer(Player player)
	{
		Location location = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		Location toSpawn = location.add(location.getDirection().multiply(5));
		Wither wither = (Wither) EdmineAPISpigot.getInstance().getServer().getWorld(player.getWorld().getName()).spawnEntity(toSpawn, EntityType.WITHER);

		wither.setCustomName(title);
		wither.setHealth(wither.getMaxHealth());
		wither.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		wither.setCanPickupItems(false);
		noAI(wither);
		withers.put(player, wither);
		init.add(player);
		new BukkitRunnable()
		{
			int t = 0;
			int f = 0;

			@Override
			public void run()
			{
				t++;
				f++;

				if (f == 10) init.remove(player);
				if (t == 1) run();
			}
		}.runTaskTimer((Plugin) EdmineAPISpigot.getInstance(), 0L, 5L);
	}

	/**
	 * Permet de remove un joueur de la liste qui gère tout les joueurs et leur
	 * bossbar.
	 * 
	 * @param player Joueur
	 */
	public void removePlayer(Player player)
	{
		Wither wither = withers.get(player);
		withers.remove(player);
		wither.remove();
		if (init.contains(player)) init.remove(player);
	}

	/**
	 * Permet de définir un titre de bossbar pour les joueurs de la liste.
	 * 
	 * @param title Titre de la bossbar qui s'appliquera a tous les joueurs de la
	 *              liste.
	 */
	public void setTitle(String title)
	{
		this.title = title;
		for (Map.Entry<Player, Wither> element : withers.entrySet())
		{
			Wither wither = element.getValue();
			wither.setCustomName(title);
		}
	}

	/**
	 * Permet de définir la barre de vie du bossbar, avec deux paramètres qui
	 * servent a créer un pourcentage sur 100%
	 * 
	 * @param health    Chiffre actuel.
	 * @param maxHealth Chiffre maximal.
	 */
	public void setHealth(int health, int maxHealth)
	{
		this.health = health + 1;
		double newhealth = health + 1;
		for (Map.Entry<Player, Wither> element : withers.entrySet())
		{
			Wither wither = element.getValue();
			if (health == 0)
			{
				wither.setHealth(getPercentage(health, maxHealth));
				return;
			}
			this.health = health;
			wither.setHealth(getPercentage(health, maxHealth));
		}
	}

	private double getPercentage(int currentNumber, int hundredPercentNumber)
	{
		if (hundredPercentNumber == 0)
		{
			throw new IllegalArgumentException("Le nombre représentant 100% ne peut pas être zéro.");
		}

		double percentage = ((double) currentNumber / (double) hundredPercentNumber) * 300;
		return percentage;
	}

	private void noAI(Entity bukkitEntity)
	{
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if (tag == null)
		{
			tag = new NBTTagCompound();
		}
		nmsEntity.c(tag);
		tag.setInt("NoAI", 1);
		tag.setBoolean("Silent", true);
		tag.setBoolean("Visible", false);
		nmsEntity.f(tag);
	}

	/**
	 * Permet de clear la liste et de remove tout les bossbar
	 */
	public void removeAllFromMap()
	{
		for (Map.Entry<Player, Wither> element : withers.entrySet())
		{
			Wither wither = element.getValue();
			wither.remove();
		}
	}

	public Location getWitherLocationInit(Location location)
	{
		return location.add(location.getDirection().multiply(10));
	}

	@Override
	public void run()
	{
		for (Map.Entry<Player, Wither> element : withers.entrySet())
		{
			Player player = element.getKey();
			Wither wither = element.getValue();
			Location witherLocation = getWitherLocationInit(player.getLocation());
			Location location = new Location(witherLocation.getWorld(), witherLocation.getX(), witherLocation.getY() + 50, witherLocation.getZ());

			if (init.contains(player))
			{
				Location locationInit = getWitherLocationInit(player.getLocation());
				wither.teleport(locationInit);
			}
			else wither.teleport(location);

			for (Player playersOnline : EdmineAPISpigot.getInstance().getServer().getOnlinePlayers())
			{
				if (!playersOnline.getName().equalsIgnoreCase(player.getName()))
				{
					PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(wither.getEntityId());
					((CraftPlayer) playersOnline).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}
	}

	public final HashMap<Player, Wither> getWithers()
	{
		return this.withers;
	}
}
