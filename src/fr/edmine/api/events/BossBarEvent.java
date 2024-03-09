package fr.edmine.api.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.edmine.api.EdmineAPISpigot;
import fr.edmine.api.utils.builders.BossBarBuilder;

public class BossBarEvent implements Listener
{

	private BossBarBuilder bossBar = EdmineAPISpigot.getInstance().getBossBarBuilder();
	
	@EventHandler
	public void onPlayerWorldChange(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		if (bossBar.getWithers().containsKey(player))
		{
			bossBar.removePlayer(player);
			bossBar.putPlayer(player);
		}
	}

	@EventHandler
	public void playerLeaveBossBar(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if (bossBar.getWithers().containsKey(player)) bossBar.removePlayer(player);
	}

	@EventHandler
	public void onWitherDamage(EntityDamageEvent event)
	{
		if (event.getEntityType() == EntityType.WITHER) event.setCancelled(true);
	}
}
