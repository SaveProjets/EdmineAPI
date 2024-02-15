package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.utils.builder.BossBarBuilder;
import fr.edminecoreteam.api.event.BossBarEvent;
import fr.edminecoreteam.api.utils.builder.HologramsBuilder;
import fr.edminecoreteam.api.spigot.item.ItemManager;
import fr.edminecoreteam.api.spigot.menu.MenuManager;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class EdmineAPI extends JavaPlugin {

    private static EdmineAPI instance;
    private HologramsBuilder hologramsBuilder;
    private BossBarBuilder bossBar;
    private final InventoryManager invManager = new InventoryManager(this);

    public static EdmineAPI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getLogger().info("EDMINEAPI enabling");
        this.invManager.init();
        instance = this;

        Bukkit.getLogger().info("Loading managers...");
        this.hologramsBuilder = new HologramsBuilder();
        this.bossBar = new BossBarBuilder("§r", 300);
        Bukkit.getPluginManager().registerEvents(new BossBarEvent(), this);
        Bukkit.getLogger().info("Managers loaded successfully.");
        DatabaseManager.initAllDatabaseConnection();
    }

    @Override
    public void onDisable() {
        DatabaseManager.closeAllDatabaseConnections();
    }
    public HologramsBuilder getHologramBuilder() { return hologramsBuilder; }
    public BossBarBuilder getBossBar() { return bossBar; }
}
