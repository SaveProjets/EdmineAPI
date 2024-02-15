package fr.edminecoreteam.api.edorm.tables;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.api.edorm.MySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class createTables
{
    private final EdmineAPI edmineAPI = EdmineAPI.getInstance();
    private final MySQL mySQL = edmineAPI.getMySQL();
    private final String table;

    public createTables(String table)
    {
        this.table = table;
    }

    public void creatingTable(String firstContent, int firstContentType, List<String> stringEntries, List<String> integersEntries, List<String> booleanEntries)
    {
        String tableContent = "";

        /*
        1 - String
        2 - Integer
        3 - boolean
         */

        if (stringEntries == null && integersEntries == null && booleanEntries == null)
        {
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: Error to load " + table + ", null pointer exception.");
            System.out.println("DATABASE: List for errors:");
            System.out.println("DATABASE: stringEntries = null.");
            System.out.println("DATABASE: integersEntries = null.");
            System.out.println("DATABASE: booleanEntries = null.");
            System.out.println("DATABASE: Check your code, if you need more help, contact N12cf.");
            return;
        }

        if (firstContentType == 1)
        {
            tableContent = tableContent + "`" + firstContent + "` varchar(255) NOT NULL,";
        }
        else if (firstContentType == 2)
        {
            tableContent = tableContent + "`" + firstContent + "` int(11) NOT NULL,";
        }
        else if (firstContentType == 3)
        {
            tableContent = tableContent + "`" + firstContent + "` boolean NOT NULL,";
        }

        if (stringEntries != null)
        {
            for (String entries : stringEntries)
            {
                tableContent = tableContent + ", `" + entries + "` varchar(255)";
            }
        }

        if (integersEntries != null)
        {
            for (String entries : integersEntries)
            {
                tableContent = tableContent + ", `" + entries + "` int(11)";
            }
        }

        if (booleanEntries != null)
        {
            for (String entries : booleanEntries)
            {
                tableContent = tableContent + ", `" + entries + "` boolean";
            }
        }

        try
        {
            PreparedStatement stm = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + tableContent + ", PRIMARY KEY (`" + firstContent + "`), UNIQUE(`" + firstContent + "`), INDEX(`" + firstContent + "`)) CHARACTER SET utf8");
            stm.execute();
            stm.close();
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: " + table + " loaded.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
