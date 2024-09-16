package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.event.PlayerEventsBungee;
import fr.edminecoreteam.api.utils.DBUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class EdmineAPIBungee extends Plugin {
    private static EdmineAPIBungee instance;
    private DBUtils dbUtils;

    private static Configuration config;

    public static EdmineAPIBungee getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("EDMINEAPI enabling");

        instance=this;

        loadConfig();

        getLogger().info("Loading managers...");
        this.dbUtils = new DBUtils();

        createDB();
        getProxy().getPluginManager().registerListener(this, new PlayerEventsBungee());
        getLogger().info("Managers loaded successfully.");
        DatabaseManager.initAllDatabaseConnection();

    }

    @Override
    public void onDisable() {
        DatabaseManager.closeAllDatabaseConnections();
    }


    private void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try (InputStream inputStream = getResourceAsStream("config.yml")) {
                Files.copy(inputStream, configFile.toPath());
            }
            catch (IOException e) {
                getLogger().warning("[EDMINE-API] Le fichier de config n'a pas pu être chargé... : " + e.getMessage());
            }
        }

        // Charger la configuration depuis le fichier
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDB(){
        dbUtils.creatingTableAccount();
        dbUtils.creatingTableLogin();
        dbUtils.creatingTableMaintenance();
    }

    public Configuration getConfig(){
        return config;
    }

    public DBUtils getDbUtils(){return dbUtils;}
}
