package fr.edminecoreteam.api;

import fr.edminecoreteam.api.edorm.MySQL;
import fr.edminecoreteam.api.edorm.SQLState;
import fr.edminecoreteam.api.spigot.holograms.HologramsBuilder;
import fr.edminecoreteam.api.spigot.item.ItemManager;
import fr.edminecoreteam.api.spigot.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class EdmineAPI extends JavaPlugin{

    private static EdmineAPI Instance;
    private MySQL database;
    private SQLState sqlState;


    public static EdmineAPI getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getLogger().info("EDMINEAPI enabling");
        Instance = this;

        Bukkit.getLogger().info("Loading managers...");
        itemManager = new ItemManager();
        menuManager = new MenuManager();
        this.hologramsBuilder = new HologramsBuilder();
        Bukkit.getLogger().info("Managers loaded successfully.");

        MySQLConnect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /*
     * Méthode de connexion au serveur SQL.
     *
     * "jdbc:mysql://", "address (ip)", "database", "user", "password"
     */
    public void MySQLConnect()
    {
        Instance = this;
        (this.database = new MySQL(Instance, "jdbc:mysql://", this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.user"), this.getConfig().getString("mysql.password"))).connexion();
    }

    /*
     * Méthode de déconnexion au serveur SQL.
     */
    private void MySQLDisconnect()
    {
        database.deconnexion();
    }

    public void setSQLState(SQLState sqlState)
    {
        this.sqlState = sqlState;
    }


    private static ItemManager itemManager;
    private static MenuManager menuManager;
    private HologramsBuilder hologramsBuilder;

    public MySQL getMySQL() { return this.database; }

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static MenuManager getMenuManager() {
        return menuManager;
    }
    public HologramsBuilder getHologramsBuilder() { return this.hologramsBuilder; }
}
