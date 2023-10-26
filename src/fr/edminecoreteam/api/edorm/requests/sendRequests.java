package fr.edminecoreteam.api.edorm.requests;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.api.edorm.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sendRequests
{
    private final EdmineAPI edmineAPI = EdmineAPI.getInstance();
    private final MySQL mySQL = edmineAPI.getMySQL();
    private final String table;

    public sendRequests(String table)
    {
        this.table = table;
    }

    public void updateString(String value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET " + value + " = ? WHERE " + object + " = ?");
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, object);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            mySQL.deconnexion();
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (String) Error to update (" + value + ") where object is (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
        }
    }

    public void updateInt(int value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET " + value + " = ? WHERE " + object + " = ?");
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, object);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            mySQL.deconnexion();
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (int) Error to update (" + value + ") where object is (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
        }
    }

    public void updateBoolean(boolean value, String object)
    {

        if (!mySQL.isOnline())
        {
            edmineAPI.MySQLConnect();
        }
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET " + value + " = ? WHERE " + object + " = ?");
            preparedStatement.setBoolean(1, value);
            preparedStatement.setString(2, object);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            mySQL.deconnexion();
        }
        catch (SQLException e)
        {
            System.out.println("EDMINEAPI | ORM-DATABASE: (Boolean) Error to update (" + value + ") where object is (" + object + ") from table (" + table + ") ...");
            mySQL.deconnexion();
        }
    }
}
