package fr.edminecoreteam.api.event;

import fr.edminecoreteam.api.EdmineAPISpigot;
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
        if (EdmineAPISpigot.getInstance().getBossBarBuilder().getWithers().containsKey(p))
        {
            EdmineAPISpigot.getInstance().getBossBarBuilder().removePlayer(p);
            EdmineAPISpigot.getInstance().getBossBarBuilder().putPlayer(p);
        }
    }

    @EventHandler
    public void playerLeaveBossBar(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (EdmineAPISpigot.getInstance().getBossBarBuilder().getWithers().containsKey(p))
        {
            EdmineAPISpigot.getInstance().getBossBarBuilder().removePlayer(p);
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
