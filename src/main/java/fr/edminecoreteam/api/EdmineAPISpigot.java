package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.event.PlayerEventsSpigot;
import fr.edminecoreteam.api.management.WorldManager;
import fr.edminecoreteam.api.utils.DBUtils;
import fr.edminecoreteam.api.utils.PluginMessage.ReceivedPluginMessage;
import fr.edminecoreteam.api.utils.builder.BossBarBuilder;
import fr.edminecoreteam.api.event.BossBarEvent;
import fr.edminecoreteam.api.utils.builder.HologramsBuilder;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;


public final class EdmineAPISpigot extends JavaPlugin {

    private static EdmineAPISpigot instance;
    private HologramsBuilder hologramsBuilder;
    private WorldManager worldManager;
    private BossBarBuilder bossBar;
    private DBUtils dbUtils;
    private final InventoryManager smartInvManager = new InventoryManager(this);

    public static EdmineAPISpigot getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getLogger().info("EDMINEAPI enabling");
        this.smartInvManager.init();
        instance = this;

        Bukkit.getLogger().info("Loading managers...");
        this.hologramsBuilder = new HologramsBuilder();
        this.bossBar = new BossBarBuilder("§r", 300);
        this.dbUtils = new DBUtils();
        this.worldManager = new WorldManager();
        Bukkit.getPluginManager().registerEvents(new BossBarEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventsSpigot(), this);
        Bukkit.getLogger().info("Managers loaded successfully.");
        DatabaseManager.initAllDatabaseConnection();
        
        loadPluginMessage();
    }

    @Override
    public void onDisable() {
        DatabaseManager.closeAllDatabaseConnections();
    }
    public HologramsBuilder getHologramBuilder() { return hologramsBuilder; }
    public BossBarBuilder getBossBarBuilder() { return bossBar; }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    private void loadPluginMessage() {
        ReceivedPluginMessage pluginCListener = new ReceivedPluginMessage();
        getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, "transfertAccount", (PluginMessageListener) pluginCListener);
    }

    public DBUtils getDbUtils() {
        return dbUtils;
    }
}
