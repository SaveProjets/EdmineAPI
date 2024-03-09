package fr.edmine.api.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.edmine.api.EdmineAPISpigot;
import fr.edmine.api.managers.PlayerManager;
import fr.edmine.api.managers.lists.RankList;
import fr.edmine.api.managers.lists.StaffRankList;
import fr.edmine.api.utils.DatabaseUtils;

public class PlayerEvents implements Listener
{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		DatabaseUtils databaseUtils = EdmineAPISpigot.getInstance().getDatabaseUtils();
		String uuid = event.getPlayer().getUniqueId().toString();
		
		databaseUtils.haveAccount(event.getPlayer()).whenComplete((haveaccount, throwable) ->
		{
			if (haveaccount)
			{
				int soul_fragment = databaseUtils.getInt("player_fragments_d_ames", "ed_accounts", "player_uuid", uuid);
				int divine_radiance = databaseUtils.getInt("player_eclats_divins", "ed_accounts", "player_uuid", uuid);
				int money = databaseUtils.getInt("player_argent", "ed_accounts", "player_uuid", uuid);
				int level = databaseUtils.getInt("player_level", "ed_accounts", "player_uuid", uuid);
				String guild = databaseUtils.getString("player_guild_name", "ed_accounts", "player_uuid", uuid);
				new PlayerManager(event.getPlayer(), RankList.JOUEUR, StaffRankList.NONE, soul_fragment, divine_radiance, money, level, guild);
				return;
			}
			databaseUtils.createAccount(event.getPlayer());
		});
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (PlayerManager.exist(event.getPlayer()))
		{
			// Sauvegarde du joueur - TO DO
			PlayerManager.removePlayer(event.getPlayer());
		}
	}
}
