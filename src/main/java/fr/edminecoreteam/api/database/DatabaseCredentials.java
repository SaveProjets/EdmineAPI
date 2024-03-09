package fr.edminecoreteam.api.database;

import lombok.Getter;

public class DatabaseCredentials {

    private final String host;
    @Getter
    private final String user;
    @Getter
    private final String pass;
    private final String dbName;
    private final int port;

    public DatabaseCredentials(String host, String user, String pass, String dbName, int port) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.port = port;
    }

    public String toURL(){

        return "jdbc:mysql://" +
                host +
                ":" +
                port +
                "/" +
                dbName;
    }

}
