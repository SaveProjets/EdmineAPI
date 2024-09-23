package fr.edminecoreteam.api.utils.PluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.edminecoreteam.api.management.PlayerManager;
import fr.edminecoreteam.api.management.list.RankList;
import fr.edminecoreteam.api.management.list.StaffRankList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class ReceivedPluginMessage implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if(!channel.equals("transfertAccount")){
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if(subChannel.equals("transfertAccount")){
            String playerUUID = in.readUTF();
            String playerRank = in.readUTF();
            String playerStaffRank = in.readUTF();
            int soulFragments = in.readInt();
            int divineRadiance = in.readInt();
            int money = in.readInt();
            int level = in.readInt();
            String guild = in.readUTF();

            PlayerManager pm = new PlayerManager(UUID.fromString(playerUUID), RankList.valueOf(playerRank), StaffRankList.valueOf(playerStaffRank), soulFragments, divineRadiance, money, level, guild);

        }
    }
}
