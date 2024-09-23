package fr.edminecoreteam.api.utils.PluginMessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.edminecoreteam.api.management.PlayerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class SendPluginMessage{
    public static void sendPlayerAccount(PlayerManager pm, String serverName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("transfertAccount");
        out.writeUTF(pm.getUUID().toString());
        out.writeUTF(pm.getRank().toString());
        out.writeUTF(pm.getStaffRank().toString());

        out.writeInt(pm.getSoulFragment());
        out.writeInt(pm.getDivineRadiance());
        out.writeInt(pm.getMoney());
        out.writeInt(pm.getLevel());
        out.writeUTF(pm.getGuild());


        ProxyServer.getInstance().getServerInfo(serverName).sendData("transfertAccount", out.toByteArray());
    }

    public static void sendPlayerModActivation(ProxiedPlayer p, ArrayList<UUID> staffList, Boolean modActivation){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ModActivate");
        out.writeUTF(p.getName());


        out.writeInt(staffList.size());

        for(UUID staffP : staffList){
            out.writeUTF(staffP.toString());
        }

        out.writeBoolean(modActivation);

        for(String serverName : ProxyServer.getInstance().getServers().keySet()){
            if(!ProxyServer.getInstance().getServerInfo(serverName).getPlayers().isEmpty()){
                ProxyServer.getInstance().getServerInfo(serverName).sendData("ModActivate", out.toByteArray());
            }
        }

    }
}
