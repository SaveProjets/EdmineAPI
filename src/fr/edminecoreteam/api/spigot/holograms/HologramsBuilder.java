package fr.edminecoreteam.api.spigot.holograms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramsBuilder
{
    private final HashMap<String, List<ArmorStand>> armorStands = new HashMap<>();
    private final HashMap<Player, HashMap<String, List<EntityArmorStand>>> armorStandsNMS = new HashMap<>();
    private final String prefix = "EDMINE-API: ";

    //To Main Class
    public HologramsBuilder() { }
    public final HashMap<String, List<ArmorStand>> getArmorStands() { return this.armorStands; }
    public final HashMap<Player, HashMap<String, List<EntityArmorStand>>> getArmorStandsNMS() { return this.armorStandsNMS; }
    
    /**
     * Permet de créer un hologram avec plusieurs lignes
     * @param id Identifiant pour récupérer plus tard l'hologram.
     * @param entry Liste de string pour le texte de l'hologram.
     * @param location Location pour positionner l'hologram.
     */
    public void createBukkitHologram(String id, List<String> entry, Location location)
    {
        Location newLoc = location;
        List<ArmorStand> aList = new ArrayList<>();
        for (String en : entry)
        {
            newLoc = new Location(newLoc.getWorld(), newLoc.getX(), newLoc.getY() - 0.3f, newLoc.getZ());
            ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(location.getWorld().getName()).spawnEntity(newLoc, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            armorStand.setCustomName(en);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            aList.add(armorStand);
        }
        armorStands.put(id, aList);
        System.out.println(prefix + "Load Hologram with ID: " + id + " | and loads " + entry.size() + " entities.");
    }
    
    /**
     * Permet de supprimé un hologram avec ses lignes
     * @param id Identifiant pour récupérer l'hologram.
     */
    public void removeBukkitHolgram(String id)
    {
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                int entities = 0;
                for (ArmorStand stand : en.getValue())
                {
                    stand.remove();
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
     * @param id Identifiant pour récupérer l'hologram a modifier.
     * @param getLine Ligne a modifier (Min 1).
     * @param newLine Nouveau texte.
     */
    public void updateLineBukkitHolograms(String id, int getLine, String newLine)
    {
        int line = getLine - 1;
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                ArmorStand armorStand = en.getValue().get(line);
                armorStand.setCustomName(newLine);
                System.out.println(prefix + "Update Hologram Line (" + getLine + ") with ID: " + id);
                return;
            }
        }
    }
    
    /**
     * Permet de supprimer une ligne sur l'hologram
     * @param id Identifiant pour récupérer l'hologram a modifier.
     * @param getLine Ligne a supprimer (Min 1).
     */
    public void removeLineBukkitHolograms(String id, int getLine)
    {
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                for(int i = 0; i < en.getValue().size(); i++)
                {
                    if (i > getLine)
                    {
                        ArmorStand armorStand = en.getValue().get(i);
                        Location loc = new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY() + 0.3f, armorStand.getLocation().getZ());
                        armorStand.teleport(loc);
                    }
                }
                en.getValue().remove(getLine);
                System.out.println(prefix + "Remove Hologram Line (" + getLine + ") with ID: " + id);
            }
        }
    }
    
    /**
     * Permet de téléporter un hologram et toutes ses lignes a une location
     * @param id Identifiant pour récupérer l'hologram a modifier.
     * @param location Nouvelle location.
     */
    public void teleportBukkitHolograms(String id, Location location)
    {
        Location newLoc = location;
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                for (ArmorStand stand : en.getValue())
                {
                    newLoc = new Location(newLoc.getWorld(), newLoc.getX(), newLoc.getY() - 0.3f, newLoc.getZ());
                    stand.teleport(newLoc);
                }
            }
        }
    }
    
    /**
     * Permet de créer un hologram NMS avec plusieurs lignes
     * @param p Le joueur qui va recevoir les packets.
     * @param id Identifiant pour récupérer plus tard l'hologram.
     * @param entry Liste de string pour le texte de l'hologram.
     * @param location Location pour positionner l'hologram.
     */
    public void createPacketHologram(Player p, String id, List<String> entry, Location location)
    {
        Location newLoc = location;
        List<EntityArmorStand> aList = new ArrayList<>();
        for (String en : entry)
        {
            WorldServer ws = ((CraftWorld)newLoc.getWorld()).getHandle();
            EntityArmorStand nmsStand = new EntityArmorStand(ws);
            newLoc = new Location(newLoc.getWorld(), newLoc.getX(), newLoc.getY() - 0.3f, newLoc.getZ());
            nmsStand.setLocation(newLoc.getX(), newLoc.getY(), newLoc.getZ(), 0.0f, 0.0f);
            nmsStand.setInvisible(true);
            nmsStand.setSmall(true);
            nmsStand.setCustomName(en);
            nmsStand.setCustomNameVisible(true);
            nmsStand.setGravity(false);
            aList.add(nmsStand);
            PacketPlayOutSpawnEntityLiving sendPacket = new PacketPlayOutSpawnEntityLiving((EntityLiving) nmsStand);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(sendPacket);
        }
        HashMap<String, List<EntityArmorStand>> innerMap = new HashMap<>();
        innerMap.put(id, aList);
        armorStandsNMS.put(p, innerMap);
        System.out.println(prefix + "Load Packet Hologram for player (" + p.getName() + ") with ID: " + id + " | and send " + entry.size() + " packets.");
    }
    
    /**
     * Permet de supprimer un hologram NMS
     * @param p Le joueur qui va recevoir les packets.
     * @param id Identifiant pour récupérer plus tard l'hologram.
     */
    public void removePacketHologram(Player p, String id)
    {
        for (Map.Entry<Player, HashMap<String, List<EntityArmorStand>>> en : armorStandsNMS.entrySet())
        {
            Player key = en.getKey();
            if (key == p)
            {
                HashMap<String, List<EntityArmorStand>> innerMap = en.getValue();
                for (Map.Entry<String, List<EntityArmorStand>> entry : innerMap.entrySet())
                {
                    if (entry.getKey().equalsIgnoreCase(id))
                    {
                        int packets = 0;
                        for (EntityArmorStand entityArmorStands : entry.getValue())
                        {
                            PacketPlayOutEntityDestroy sendPacket = new PacketPlayOutEntityDestroy(entityArmorStands.getId());
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sendPacket);
                            packets++;
                        }
                        armorStandsNMS.remove(p, innerMap);
                        System.out.println(prefix + "Remove Packet Hologram for player (" + p.getName() + ") with ID: " + id + " | and removed " + packets + " packets.");
                        return;
                    }
                }
            }
        }
    }
}
