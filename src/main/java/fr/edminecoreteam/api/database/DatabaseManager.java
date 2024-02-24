package fr.edminecoreteam.api.database;

import fr.edminecoreteam.api.EdmineAPI;
import lombok.Getter;

@Getter
public enum DatabaseManager {

    EDMINE(new DatabaseCredentials(EdmineAPI.getInstance().getConfig().getString("mysql.host"),
            EdmineAPI.getInstance().getConfig().getString("mysql.user"),
            EdmineAPI.getInstance().getConfig().getString("mysql.password"),
            EdmineAPI.getInstance().getConfig().getString("mysql.database"),
            3306));

    private final DatabaseAccess databaseAccess;

    DatabaseManager(DatabaseCredentials credentials){
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public static void initAllDatabaseConnection(){
        for(DatabaseManager databaseManager : values()){
            databaseManager.databaseAccess.initPool();
        }
    }
    public static void closeAllDatabaseConnections(){
        for (DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }

}
