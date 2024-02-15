package fr.edminecoreteam.api;

import fr.edminecoreteam.api.database.DatabaseManager;
import fr.edminecoreteam.api.spigot.bossbar.BossBarBuilder;
import fr.edminecoreteam.api.spigot.bossbar.BossBarEvent;
import fr.edminecoreteam.api.spigot.holograms.HologramsBuilder;
import fr.edminecoreteam.api.spigot.item.ItemManager;
import fr.edminecoreteam.api.spigot.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class EdmineAPI extends JavaPlugin {

    private static EdmineAPI instance;
    private ItemManager itemManager;
    private MenuManager menuManager;
    private HologramsBuilder hologramsBuilder;
    private BossBarBuilder bossBar;


    public static EdmineAPI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getLogger().info("EDMINEAPI enabling");
        instance = this;

        Bukkit.getLogger().info("Loading managers...");
        this.itemManager = new ItemManager();
        this.menuManager = new MenuManager();
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
    public ItemManager getItemManager() {
        return itemManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
    public HologramsBuilder getHologramsBuilder() { return hologramsBuilder; }
    public BossBarBuilder getBossBar() { return bossBar; }
}
