package fr.edminecoreteam.api;

import fr.edminecoreteam.api.edorm.MySQL;
import fr.edminecoreteam.api.edorm.SQLState;
import fr.edminecoreteam.api.spigot.item.ItemManager;
import fr.edminecoreteam.api.spigot.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class EdmineAPI extends JavaPlugin{

    private static EdmineAPI Instance;
    public MySQL database;
    private SQLState sqlState;


    public static EdmineAPI getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("EDMINEAPI enabling");
        Instance = this;

        Bukkit.getLogger().info("Loading managers...");
        itemManager = new ItemManager();
        menuManager = new MenuManager();
        Bukkit.getLogger().info("Managers loaded successfully");

        MySQLConnect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /*
     * Méthode de connexion au serveur SQL.
     *
     * "jdbc:mysql://", "45.140.165.235", "22728-database", "22728-database", "S5bV5su4p9"
     */
    public void MySQLConnect()
    {
        Instance = this;

        (this.database = new MySQL(Instance, "jdbc:mysql://", "45.140.165.235", "22728-database", "22728-database", "S5bV5su4p9")).connexion();


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

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static MenuManager getMenuManager() {
        return menuManager;
    }
}
