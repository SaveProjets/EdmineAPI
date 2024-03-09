package fr.edminecoreteam.api.utils.builder;

import fr.edminecoreteam.api.EdmineAPISpigot;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossBarBuilder extends BukkitRunnable
{
    private String title;
    private double health;

    private final HashMap<Player, Wither> withers = new HashMap<>();
    private final List<Player> init = new ArrayList<>();

    public BossBarBuilder(String title, double health)
    {
        this.title = title;
        this.health = health;
        runTaskTimer(EdmineAPISpigot.getInstance(), 0, 10);
    }
    
    /**
     * Permet de put un joueur dans une liste qui gère tout les joueurs et leur bossbar.
     * @param p Joueur
     */
    public void putPlayer(Player p)
    {
        Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        Location toSpawn = location.add(location.getDirection().multiply(5));
        Wither wither = (Wither) EdmineAPISpigot.getInstance().getServer().getWorld(p.getWorld().getName()).spawnEntity(toSpawn, EntityType.WITHER);

        wither.setCustomName(title);
        wither.setHealth(wither.getMaxHealth());
        wither.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        wither.setCanPickupItems(false);
        noAI(wither);
        withers.put(p, wither);
        init.add(p);
        new BukkitRunnable()
        {
            int t = 0;
            int f = 0;
            public void run()
            {

                ++t;
                ++f;

                if (f == 10)
                {
                    init.remove(p);
                }

                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) EdmineAPISpigot.getInstance(), 0L, 5L);
    }
    
    /**
     * Permet de remove un joueur de la liste qui gère tout les joueurs et leur bossbar.
     * @param p Joueur
     */
    public void removePlayer(Player p)
    {
        Wither wither = withers.get(p);
        withers.remove(p);
        wither.remove();
        if (init.contains(p)) { init.remove(p); }
    }
    
    /**
     * Permet de définir un titre de bossbar pour les joueurs de la liste.
     * @param title Titre de la bossbar qui s'appliquera a tous les joueurs de la liste.
     */
    public void setTitle(String title)
    {
        this.title = title;
        for (Map.Entry<Player, Wither> en : withers.entrySet())
        {
            Wither wither = en.getValue();
            wither.setCustomName(title);
        }
    }
    
    /**
     * Permet de définir la barre de vie du bossbar, avec deux paramètres qui servent a créer un pourcentage sur 100%
     * @param health Chiffre actuel.
     * @param maxHealth Chiffre maximal.
     */
    public void setHealth(int health, int maxHealth)
    {
        if (health == 0)
        {
            this.health = health + 1;
            double newhealth = health + 1;
            for (Map.Entry<Player, Wither> en : withers.entrySet())
            {
                Wither wither = en.getValue();
                wither.setHealth(getPercentage(health, maxHealth));
            }
        }
        else
        {
            this.health = health;
            for (Map.Entry<Player, Wither> en : withers.entrySet())
            {
                Wither wither = en.getValue();
                wither.setHealth(getPercentage(health, maxHealth));
            }
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
        for (Map.Entry<Player, Wither> en : withers.entrySet())
        {
            Wither wither = en.getValue();
            wither.remove();
        }
    }

    public Location getWitherLocationInit(Location l)  {
        return l.add(l.getDirection().multiply(10));
    }

    @Override
    public void run() {
        for (Map.Entry<Player, Wither> en : withers.entrySet())
        {
            Player player = en.getKey();
            Wither wither = en.getValue();
            Location loc = getWitherLocationInit(player.getLocation());
            Location location = new Location(loc.getWorld(), loc.getX(), loc.getY() + 50, loc.getZ());

            if (init.contains(player))
            {
                Location locInit = getWitherLocationInit(player.getLocation());
                wither.teleport(locInit);
            }
            else
            {
                wither.teleport(location);
            }
            for(Player on : EdmineAPISpigot.getInstance().getServer().getOnlinePlayers())
            {
                if (!on.getName().equalsIgnoreCase(player.getName()))
                {
                    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(wither.getEntityId());
                    ((CraftPlayer) on).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }
    }

    public final HashMap<Player, Wither> getWithers()
    {
        return this.withers;
    }
}
