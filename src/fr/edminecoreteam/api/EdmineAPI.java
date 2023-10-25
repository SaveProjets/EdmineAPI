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
    private String SQLAdress;
    private String SQLDatabase;
    private String SQLUser;
    private String SQLPassword;


    public static EdmineAPI getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("EDMINEAPI enabling");
        Instance = this;

        Bukkit.getLogger().info("Loading SQL informations...");
        SQLAdress = "45.140.165.235";
        SQLDatabase = "22728-database";
        SQLUser = "22728-database";
        SQLPassword = "S5bV5su4p9";
        Bukkit.getLogger().info("SQL ready for connect.");
        Bukkit.getLogger().info("SQL informations loaded successfully.");

        Bukkit.getLogger().info("Loading managers...");
        itemManager = new ItemManager();
        menuManager = new MenuManager();
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
        (this.database = new MySQL(Instance, "jdbc:mysql://", this.SQLAdress, this.SQLDatabase, this.SQLUser, this.SQLPassword)).connexion();
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

    public MySQL getMySQL() { return database; }

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static MenuManager getMenuManager() {
        return menuManager;
    }
}
