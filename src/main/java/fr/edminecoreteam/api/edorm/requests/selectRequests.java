package fr.edminecoreteam.api.edorm.requests;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.api.edorm.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class selectRequests
{
    private final EdmineAPI edmineAPI = EdmineAPI.getInstance();
    private final MySQL mySQL = edmineAPI.getMySQL();
    private final String table;

    public selectRequests(String table)
    {
        this.table = table;
    }

    public String selectString(String value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT " + value + " FROM " + table + " WHERE " + object + " = ?");
            preparedStatement.setString(1, object);
            String response = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                response = rs.getString(value);
            }
            preparedStatement.close();
            mySQL.deconnexion();
            return response;
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (String) Error to select (" + value + ") in (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
            return null;
        }
    }

    public int selectInt(String value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT " + value + " FROM " + table + " WHERE " + object + " = ?");
            preparedStatement.setString(1, object);
            int response = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                response = rs.getInt(value);
            }
            preparedStatement.close();
            mySQL.deconnexion();
            return response;
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (int) Error to select (" + value + ") in (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
            return 0;
        }
    }

    public boolean selectBoolean(String value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT " + value + " FROM " + table + " WHERE " + object + " = ?");
            preparedStatement.setString(1, object);
            boolean response = false;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                response = rs.getBoolean(value);
            }
            preparedStatement.close();
            mySQL.deconnexion();
            return response;
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (Boolean) Error to select (" + value + ") in (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
            return false;
        }
    }
}
