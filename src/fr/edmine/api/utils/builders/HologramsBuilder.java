package fr.edmine.api.utils.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;

public class HologramsBuilder
{
	private final HashMap<String, List<ArmorStand>> armorStands = new HashMap<>();
	private final HashMap<Player, HashMap<String, List<EntityArmorStand>>> armorStandsNMS = new HashMap<>();
	private final String prefix = "EDMINE-API: ";

	/**
	 * Permet de créer un hologram avec plusieurs lignes
	 * 
	 * @param id       Identifiant pour récupérer plus tard l'hologram.
	 * @param list     Liste de string pour le texte de l'hologram.
	 * @param location Location pour positionner l'hologram.
	 */
	public void createBukkitHologram(String id, List<String> list, Location location)
	{
		Location newLocation = location;
		List<ArmorStand> armorStandList = new ArrayList<>();
		for (String element : list)
		{
			newLocation = new Location(newLocation.getWorld(), newLocation.getX(), newLocation.getY() - 0.3f, newLocation.getZ());
			ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(location.getWorld().getName()).spawnEntity(newLocation, EntityType.ARMOR_STAND);
			armorStand.setVisible(false);
			armorStand.setSmall(true);
			armorStand.setCustomName(element);
			armorStand.setCustomNameVisible(true);
			armorStand.setGravity(false);
			armorStand.setMarker(true);
			armorStandList.add(armorStand);
		}
		armorStands.put(id, armorStandList);
		System.out.println(prefix + "Load Hologram with ID: " + id + " | and loads " + list.size() + " entities.");
	}

	/**
	 * Permet de supprimé un hologram avec ses lignes
	 * 
	 * @param id Identifiant pour récupérer l'hologram.
	 */
	public void removeBukkitHolgram(String id)
	{
		for (Map.Entry<String, List<ArmorStand>> element : armorStands.entrySet())
		{
			String key = element.getKey();
			if (key.equalsIgnoreCase(id))
			{
				int entities = 0;
				for (ArmorStand armorStand : element.getValue())
				{
					armorStand.remove();
					entities++;
				}
				armorStands.remove(id);
				System.out.println(prefix + "Remove Hologram with ID: " + id + " | and removed " + entities + " entities.");
				return;
			}
		}
	}

	/**
	 * Permet de modifier une ligne sur l'hologram
	 * 
	 * @param id      Identifiant pour récupérer l'hologram a modifier.
	 * @param getLine Ligne a modifier (Min 1).
	 * @param newLine Nouveau texte.
	 */
	public void updateLineBukkitHolograms(String id, int getLine, String newLine)
	{
		int line = getLine - 1;
		for (Map.Entry<String, List<ArmorStand>> element : armorStands.entrySet())
		{
			String key = element.getKey();
			if (key.equalsIgnoreCase(id))
			{
				ArmorStand armorStand = element.getValue().get(line);
				armorStand.setCustomName(newLine);
				System.out.println(prefix + "Update Hologram Line (" + getLine + ") with ID: " + id);
				return;
			}
		}
	}

	/**
	 * Permet de supprimer une ligne sur l'hologram
	 * 
	 * @param id      Identifiant pour récupérer l'hologram a modifier.
	 * @param getLine Ligne a supprimer (Min 1).
	 */
	public void removeLineBukkitHolograms(String id, int getLine)
	{
		for (Map.Entry<String, List<ArmorStand>> element : armorStands.entrySet())
		{
			String key = element.getKey();
			if (key.equalsIgnoreCase(id))
			{
				for (int line = 0; line < element.getValue().size(); line++)
				{
					if (line > getLine)
					{
						ArmorStand armorStand = element.getValue().get(line);
						Location location = new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY() + 0.3f, armorStand.getLocation().getZ());
						armorStand.teleport(location);
					}
				}
				element.getValue().remove(getLine);
				System.out.println(prefix + "Remove Hologram Line (" + getLine + ") with ID: " + id);
			}
		}
	}

	/**
	 * Permet de téléporter un hologram et toutes ses lignes a une location
	 * 
	 * @param id       Identifiant pour récupérer l'hologram a modifier.
	 * @param location Nouvelle location.
	 */
	public void teleportBukkitHolograms(String id, Location location)
	{
		Location newLocation = location;
		for (Map.Entry<String, List<ArmorStand>> element : armorStands.entrySet())
		{
			String key = element.getKey();
			if (key.equalsIgnoreCase(id))
			{
				for (ArmorStand armorStand : element.getValue())
				{
					newLocation = new Location(newLocation.getWorld(), newLocation.getX(), newLocation.getY() - 0.3f, newLocation.getZ());
					armorStand.teleport(newLocation);
				}
			}
		}
	}

	/**
	 * Permet de créer un hologram NMS avec plusieurs lignes
	 * 
	 * @param player   Le joueur qui va recevoir les packets.
	 * @param id       Identifiant pour récupérer plus tard l'hologram.
	 * @param list     Liste de string pour le texte de l'hologram.
	 * @param location Location pour positionner l'hologram.
	 */
	public void createPacketHologram(Player player, String id, List<String> list, Location location)
	{
		Location newLocation = location;
		List<EntityArmorStand> armorStandList = new ArrayList<>();
		for (String element : list)
		{
			WorldServer ws = ((CraftWorld) newLocation.getWorld()).getHandle();
			EntityArmorStand nmsArmorStand = new EntityArmorStand(ws);
			newLocation = new Location(newLocation.getWorld(), newLocation.getX(), newLocation.getY() - 0.3f, newLocation.getZ());
			nmsArmorStand.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0.0f, 0.0f);
			nmsArmorStand.setInvisible(true);
			nmsArmorStand.setSmall(true);
			nmsArmorStand.setCustomName(element);
			nmsArmorStand.setCustomNameVisible(true);
			nmsArmorStand.setGravity(false);
			armorStandList.add(nmsArmorStand);
			PacketPlayOutSpawnEntityLiving sendPacket = new PacketPlayOutSpawnEntityLiving(nmsArmorStand);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(sendPacket);
		}
		HashMap<String, List<EntityArmorStand>> armorStandMap = new HashMap<>();
		armorStandMap.put(id, armorStandList);
		armorStandsNMS.put(player, armorStandMap);
		System.out.println(prefix + "Load Packet Hologram for player (" + player.getName() + ") with ID: " + id + " | and send " + list.size() + " packets.");
	}

	/**
	 * Permet de supprimer un hologram NMS
	 * 
	 * @param player Le joueur qui va recevoir les packets.
	 * @param id     Identifiant pour récupérer plus tard l'hologram.
	 */
	public void removePacketHologram(Player player, String id)
	{
		for (Map.Entry<Player, HashMap<String, List<EntityArmorStand>>> element : armorStandsNMS.entrySet())
		{
			Player key = element.getKey();
			if (key == player)
			{
				HashMap<String, List<EntityArmorStand>> armorStandMap = element.getValue();
				for (Map.Entry<String, List<EntityArmorStand>> entry : armorStandMap.entrySet())
				{
					if (entry.getKey().equalsIgnoreCase(id))
					{
						int packets = 0;
						for (EntityArmorStand entityArmorStands : entry.getValue())
						{
							PacketPlayOutEntityDestroy sendPacket = new PacketPlayOutEntityDestroy(entityArmorStands.getId());
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(sendPacket);
							packets++;
						}
						armorStandsNMS.remove(player, armorStandMap);
						System.out.println(prefix + "Remove Packet Hologram for player (" + player.getName() + ") with ID: " + id + " | and removed " + packets + " packets.");
						return;
					}
				}
			}
		}
	}

	public HashMap<Player, HashMap<String, List<EntityArmorStand>>> getArmorStandsNMS()
	{
		return this.armorStandsNMS;
	}

	public HashMap<String, List<ArmorStand>> getArmorStands()
	{
		return this.armorStands;
	}

	public String getPrefix()
	{
		return this.prefix;
	}
}
