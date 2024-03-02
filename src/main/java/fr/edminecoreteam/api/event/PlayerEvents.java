package fr.edminecoreteam.api.event;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.api.management.PlayerManager;
import fr.edminecoreteam.api.management.list.RankList;
import fr.edminecoreteam.api.management.list.StaffRankList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        EdmineAPISpigot.getInstance().getDbUtils().haveAccount(e.getPlayer()).whenComplete((haveaccount, throwable) -> {
            if (haveaccount) {
                int soul_fragment = EdmineAPISpigot.getInstance().getDbUtils().getInt("player_fragments_d_ames", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int divine_radiance = EdmineAPISpigot.getInstance().getDbUtils().getInt("player_eclats_divins", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int money = EdmineAPISpigot.getInstance().getDbUtils().getInt("player_argent", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int level = EdmineAPISpigot.getInstance().getDbUtils().getInt("player_level", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                String guild = EdmineAPISpigot.getInstance().getDbUtils().getString("player_guild_name", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                new PlayerManager(e.getPlayer(), RankList.JOUEUR, StaffRankList.NONE, soul_fragment, divine_radiance, money, level, guild);
            }
            else {
                EdmineAPISpigot.getInstance().getDbUtils().createAccount(e.getPlayer());
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (PlayerManager.exist(e.getPlayer())){
            // Sauvegarde du joueur - TO DO
            PlayerManager.removePlayer(e.getPlayer());
        }
    }

}
