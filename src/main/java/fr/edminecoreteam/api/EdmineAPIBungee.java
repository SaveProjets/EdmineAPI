package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.event.PlayerEventsBungee;
import fr.edminecoreteam.api.utils.DBUtils;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.Bukkit;

public class EdmineAPIBungee extends Plugin {
    private static EdmineAPIBungee instance;
    private DBUtils dbUtils;

    public static EdmineAPIBungee getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("EDMINEAPI enabling");

        instance=this;

        getLogger().info("Loading managers...");
        this.dbUtils = new DBUtils();
        getProxy().getPluginManager().registerListener(this, new PlayerEventsBungee());

        getLogger().info("Managers loaded successfully.");
        DatabaseManager.initAllDatabaseConnection();

    }

    @Override
    public void onDisable() {
        DatabaseManager.closeAllDatabaseConnections();
    }


    public DBUtils getDbUtils(){return dbUtils;}
}
