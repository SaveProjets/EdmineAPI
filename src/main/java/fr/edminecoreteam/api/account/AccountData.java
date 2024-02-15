package fr.edminecoreteam.api.account;

import fr.edminecoreteam.api.edorm.MySQL;
import fr.edminecoreteam.api.utils.CheckServerType;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountData
{
    private String p;

    protected AccountData(String p)
    {
        this.p = p;
    }

    protected boolean hasRank() {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, p);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            e.toString();
            return false;
        }
    }

    protected int getRankID()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int power = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                power = rs.getInt("player_rank_id");
            }
            preparedStatement.close();
            return power;
        }
        catch (SQLException e)
        {
            e.toString();
            return 0;
        }
    }

    protected String getRankType()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_type FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, p);
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_type");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    protected String getPurchaseDate()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_purchase_date FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, p);
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_purchase_date");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    protected String getDeadLineDate()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_deadline_date FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, p);
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_deadline_date");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    protected int getRankModule()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_modulable_rank FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, p);
                int response = 0;
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getInt("player_modulable_rank");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return 0;
    }

    protected String getRankName(){
        try{
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT player_rank_name FROM ed_ranks WHERE player_name = ?");
            ps.setString(1, p);
            String response = "§7Le §astaff";
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                response = rs.getString("player_rank_name");
            }
            if(response.equalsIgnoreCase("MOD")){
                response = "§7Le §9modérateur";
            }else if(response.equalsIgnoreCase("DEV")){
                response = "§7Le §5développeur";
            }else if (response.equalsIgnoreCase("RESP")){
                response = "§7Le §4responsable";
            }else if(response.equalsIgnoreCase("ADMIN")){
                response = "§7L'§cadministrateur";
            }else {
                response = "§7Le §astaff";
            }
            ps.close();
            return response;
        }
        catch (SQLException e){
            e.toString();
        }
        return "§7Le §astaff";
    }

    protected String getUUID(){
        if(CheckServerType.isSpigotServer()){
            if(Bukkit.getPlayer(p) != null) {
                return Bukkit.getPlayer(p).getUniqueId().toString();
            }
        }else if (CheckServerType.isBungeeCordServer()) {
            if (ProxyServer.getInstance().getPlayer(p) != null) {
                return ProxyServer.getInstance().getPlayer(p).getUniqueId().toString();
            }
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT player_uuid FROM ed_accounts WHERE player_name = ?");
            ps.setString(1, p);
            String response = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                response = rs.getString("player_uuid");
            }
            ps.close();
            return response;

        }
        catch (SQLException e){
            e.toString();
        }


        return null;
    }

}
