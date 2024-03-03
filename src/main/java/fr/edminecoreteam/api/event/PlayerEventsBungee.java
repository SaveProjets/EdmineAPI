package fr.edminecoreteam.api.event;

import fr.edminecoreteam.api.EdmineAPIBungee;
import fr.edminecoreteam.api.management.PlayerManager;
import fr.edminecoreteam.api.management.list.RankList;
import fr.edminecoreteam.api.management.list.StaffRankList;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerEventsBungee implements Listener {

    @EventHandler
    public void onJoin(ServerConnectEvent e){
        EdmineAPIBungee.getInstance().getDbUtils().haveAccount(e.getPlayer()).whenComplete((haveaccount, throwable) -> {
            if(haveaccount){
                int soul_fragment = EdmineAPIBungee.getInstance().getDbUtils().getInt("player_fragments_d_ames", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int divine_radiance = EdmineAPIBungee.getInstance().getDbUtils().getInt("player_eclats_divins", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int money = EdmineAPIBungee.getInstance().getDbUtils().getInt("player_argent", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                int level = EdmineAPIBungee.getInstance().getDbUtils().getInt("player_level", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                String guild = EdmineAPIBungee.getInstance().getDbUtils().getString("player_guild_name", "ed_accounts", "player_uuid", e.getPlayer().getUniqueId().toString());
                new PlayerManager(e.getPlayer().getUniqueId(), RankList.JOUEUR, StaffRankList.NONE, soul_fragment, divine_radiance, money, level, guild);
            }else{
                EdmineAPIBungee.getInstance().getDbUtils().createAccount(e.getPlayer());
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerDisconnectEvent e){
        if(PlayerManager.exist(e.getPlayer().getUniqueId())){
            // Sauvegarde du joueur - TO DO
            PlayerManager.removePlayer(e.getPlayer().getUniqueId());
        }
    }
}
