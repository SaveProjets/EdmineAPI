package fr.edminecoreteam.api.uuid;

import fr.edminecoreteam.api.edorm.MySQL;
import fr.edminecoreteam.api.utils.CheckServerType;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ProxyServer;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UUIDData {
    private static String p;
    protected UUIDData(String p){ this.p = p;}

    protected String getUUID(){
        if(CheckServerType.isSpigotServer()){
            if(Bukkit.getPlayer(p) != null) {
                return Bukkit.getPlayer(p).getUniqueId().toString();
            }
        }else if (CheckServerType.isBungeeCordServer()) {
            if (ProxyServer.getInstance().getPlayer(p) != null) {
                return ProxyServer.getInstance().getPlayer(p).getUniqueId().toString();
            }
        }
            try {
                if(CheckServerType.isSpigotServer()){
                    Bukkit.getLogger().info("spigot test console");
                }else if (CheckServerType.isBungeeCordServer()){
                    ProxyServer.getInstance().getConsole().sendMessage("Ceci est un message dans la console BungeeCord.");
                }

                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT player_uuid FROM ed_accounts WHERE player_name = ?");
                ps.setString(1, p);
                String response = null;
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    response = rs.getString("player_uuid");
                }
                ps.close();
                return response;

            }
            catch (SQLException e){
                e.toString();
            }


        return null;
    }
}
