package fr.edminecoreteam.api.spigot.bossbar;

import fr.edminecoreteam.api.EdmineAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarEvent implements Listener
{

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e)
    {
        Player p = e.getPlayer();
        if (EdmineAPI.getBossBar().getWithers().containsKey(p))
        {
            EdmineAPI.getBossBar().removePlayer(p);
            EdmineAPI.getBossBar().putPlayer(p);
        }
    }

    @EventHandler
    public void playerLeaveBossBar(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (EdmineAPI.getBossBar().getWithers().containsKey(p))
        {
            EdmineAPI.getBossBar().removePlayer(p);
        }
    }

    @EventHandler
    public void onWitherDamage(EntityDamageEvent e)
    {
        if (e.getEntityType() == EntityType.WITHER)
        {
            e.setCancelled(true);
        }
    }
}
