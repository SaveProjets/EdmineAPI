package fr.edminecoreteam.api.edorm;

import fr.edminecoreteam.api.EdmineAPI;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {
    private MySQL instance;
    private EdmineAPI api;
    private String urlBase;
    private String host;
    private String database;
    private String userName;
    private String password;
    private static Connection connection;

    public MySQL(EdmineAPI api, String urlBase, String host, String database, String userName, String password) {
        this.api = api;
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }

    public static Connection getConnection() { return MySQL.connection; }

    public void connexion() {
        if (!this.isOnline()) {
            try {
                instance = this;

                MySQL.connection = DriverManager.getConnection(String.valueOf(this.urlBase) + this.host + "/" + this.database, this.userName, this.password);

                SQLTasks start = new SQLTasks(api, instance);
             //   start.runTaskTimer((Plugin)this.api, 0L, 20L);

                message("connectMSG");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        if (this.isOnline()) {
            try {
                MySQL.connection.close();

                message("disconnectMSG");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isOnline() {
        try
        {
            if (MySQL.connection == null || MySQL.connection.isClosed())
            {
                return false;
            }
            else if (MySQL.connection != null || !MySQL.connection.isClosed())
            {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void creatingTableSetting() {
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ed_settings (`player_name` varchar(255) NOT NULL, `player_uuid` varchar(255), `player_lang` int(11), `player_friend_sort` int(11), `player_friend_request` varchar(255), `player_group_request` varchar(255), `player_guild_request` varchar(255), `player_private_message` varchar(255), `player_profil_view` varchar(255), `player_players_display` varchar(255), `player_account_state` varchar(255), `player_chat_mention` varchar(255), `player_group_follow` varchar(255), `player_live_notification` varchar(255), `player_message_connection` varchar(255), `player_night_or_day` varchar(255), `player_auto_tip` varchar(255), `player_lobby_protection` varchar(255), `player_guild_chat` varchar(255), `player_guild_notification` varchar(255), PRIMARY KEY (`player_name`), UNIQUE(`player_name`), INDEX(`player_name`)) CHARACTER SET utf8");
            stm.execute();
            stm.close();
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: ed_ranks loaded.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void creatingTableLink() {
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ed_accounts_link (`player_uuid` varchar(255) NOT NULL, `youtube` varchar(255), `twitch` varchar(255), `kick` varchar(255), `instagram` varchar(255), `twitter` varchar(255), `snapchat` varchar(255), `reddit` varchar(255), `discord` varchar(255), `tiktok` varchar(255), `spotify` varchar(255), PRIMARY KEY (`player_uuid`), UNIQUE(`player_uuid`), INDEX(`player_uuid`)) CHARACTER SET utf8");
            stm.execute();
            stm.close();
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: ed_ranks loaded.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void message(String condition) {
        if (condition == "connectMSG")
        {
            System.out.println("+--------------------+");
            System.out.println("ED-NETWORK API");
            System.out.println("ORM: Enable");
            System.out.println("ORM-DATABASE: Connected");
            System.out.println("+--------------------+");
        }
        if (condition == "disconnectMSG")
        {
            System.out.println("+--------------------+");
            System.out.println("ED-NETWORK API");
            System.out.println("ORM: Disable");
            System.out.println("ORM-DATABASE: Disconnected");
            System.out.println("+--------------------+");
        }
    }

}
