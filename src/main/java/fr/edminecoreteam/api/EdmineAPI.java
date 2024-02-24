package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.event.PlayerEvents;
import fr.edminecoreteam.api.event.GuiManager;
import fr.edminecoreteam.api.management.WorldManager;
import fr.edminecoreteam.api.utils.DBUtils;
import fr.edminecoreteam.api.utils.builder.BossBarBuilder;
import fr.edminecoreteam.api.event.BossBarEvent;
import fr.edminecoreteam.api.utils.builder.HologramsBuilder;
import fr.edminecoreteam.api.utils.builder.gui.Gui;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class EdmineAPI extends JavaPlugin {

    @Getter
    private static EdmineAPI instance;
    private HologramsBuilder hologramsBuilder;
    @Getter
    private WorldManager worldManager;
    private BossBarBuilder bossBar;
    @Getter
    private DBUtils dbUtils;
    private final InventoryManager smartInvManager = new InventoryManager(this);
    @Getter
    private GuiManager guiManager;

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
        Bukkit.getLogger().info("Loading listenes...");
        this.registerListeners();

        Bukkit.getLogger().info("Managers loaded successfully.");
        DatabaseManager.initAllDatabaseConnection();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BossBarEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
        this.guiManager = new GuiManager(this);
    }

    @Override
    public void onDisable() {
        DatabaseManager.closeAllDatabaseConnections();
    }
    public HologramsBuilder getHologramBuilder() { return hologramsBuilder; }
    public BossBarBuilder getBossBarBuilder() { return bossBar; }

}
