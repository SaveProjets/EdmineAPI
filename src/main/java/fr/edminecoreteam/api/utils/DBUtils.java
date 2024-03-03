package fr.edminecoreteam.api.utils;

import fr.edminecoreteam.api.database.DatabaseManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

public class DBUtils {

    //===================================
    // Base De Données - Sauvegarde
    //===================================

    /**public void saveAll(){
        Bukkit.broadcastMessage("§cEnregistrement des données dans la base de donnée ! §4Un coup de lag peut se faire ressentir !");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            savePlayer(PlayerManager.getPlayer(onlinePlayer));
        }
    }

    public void savePlayer(PlayerManager playerManager){
        DBSetInfo("money", String.valueOf(playerManager.getMoney()), "users_informations", "uuid", playerManager.getUser().getUniqueId().toString());
        DBSetInfo("iris", String.valueOf(playerManager.getIris()), "users_informations", "uuid", playerManager.getUser().getUniqueId().toString());
    }**/

    //===================================
    // Base De Données - Create
    //===================================

    // Exemple SIMPLE de code possible sql à éxécuter en asynchrone
    public void createAccount(Player player) {
        CompletableFuture.runAsync(() -> {
            // Création du compte
            try (Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ed_accounts (player_id, player_name, player_uuid, player_first_connection) VALUES (?,?,?,?)");

                preparedStatement.setInt(1, getMaxIntOfColumn("ed_accounts", "player_id"));
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.setString(3, player.getName());
                preparedStatement.setString(4, new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()));
                preparedStatement.executeUpdate();

            } catch (SQLException event) {
                event.printStackTrace();
            }
        }, MultiThread.getThreadPool());
        // MultiThread uniquement si c'est vrmt des grosses requetes SQL, inutile si elles sont petites
    }

    // Exemple SIMPLE de code possible sql à éxécuter en asynchrone
    public void createAccount(ProxiedPlayer player) {
        CompletableFuture.runAsync(() -> {
            // Création du compte
            try (Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ed_accounts (player_id, player_name, player_uuid, player_first_connection) VALUES (?,?,?,?)");

                preparedStatement.setInt(1, getMaxIntOfColumn("ed_accounts", "player_id")+1);
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.setString(3, player.getName());
                preparedStatement.setString(4, new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()));
                preparedStatement.executeUpdate();

            } catch (SQLException event) {
                event.printStackTrace();
            }
        }, MultiThread.getThreadPool());
        // MultiThread uniquement si c'est vrmt des grosses requetes SQL, inutile si elles sont petites
    }

    //===================================
    // Base De Données - Vérification
    //===================================

    /**
     * Permet de verifier si un joueur à un compte dans la base de donnée
     * @param player Joueur
     * @return boolean
     */
    public CompletableFuture<Boolean> haveAccount(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ed_accounts WHERE player_uuid = ? LIMIT 1");

                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.executeQuery();

                final ResultSet resultSet = preparedStatement.getResultSet();
                boolean result = resultSet.next();
                connection.close();
                return result;
            } catch (SQLException event) {
                event.printStackTrace();
                return false;
            }
        });
    }

    /**
     * Permet de verifier si un joueur à un compte dans la base de donnée
     * @param player Joueur
     * @return boolean
     */
    public CompletableFuture<Boolean> haveAccount(ProxiedPlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ed_accounts WHERE player_uuid = ? LIMIT 1");

                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.executeQuery();

                final ResultSet resultSet = preparedStatement.getResultSet();
                boolean result = resultSet.next();
                connection.close();
                return result;
            } catch (SQLException event) {
                event.printStackTrace();
                return false;
            }
        });
    }

    //===================================
    // Base De Données - Set
    //===================================

    /**
     * * Met à jour la valeur d'une colonne spécifique pour une entrée dans la base de données.
     * Cette méthode construit et exécute une requête SQL de mise à jour pour modifier la valeur
     * d'une colonne donnée où une condition spécifique est remplie.
     * @param setting Le nom de la colonne à mettre à jour.
     * @param settingsvalue La nouvelle valeur à assigner à la colonne spécifiée.
     * @param table_name Le nom de la table dans la base de données où la mise à jour doit être appliquée.
     * @param where Le nom de la colonne utilisée pour identifier les enregistrements à mettre à jour.
     * @param whereFinding La valeur de la colonne utilisée pour identifier les enregistrements à mettre à jour.
     * Exemple : set("money", "100", "users_informations", "uuid", player.getUniqueId().toString());
     */
    public void set(String setting, String settingsvalue, String table_name, String where, String whereFinding) {
        try {
            final Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + table_name + " SET " + setting + "='" + settingsvalue + "' WHERE " + where + " = ?");

            preparedStatement.setString(1, whereFinding);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException event) {
            event.printStackTrace();
        }
    }

    //===================================
    // Base De Données - Get
    //===================================

    /**
     * Permet de get la plus grande valeur d'une colonne
     * @param table_name Nom de la table
     * @param column Nom de la colonne
     * @return int
     */

    public int getMaxIntOfColumn(String table_name, String column){
        try {
            Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(" + column +") FROM " + table_name);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()){
                int result = rs.getInt(1);
                connection.close();
                return result;
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param geting Le nom de la colonne dont la valeur est à récupérer.
     * @param table_name Le nom de la table dans la base de données à interroger.
     * @param where Le nom de la colonne utilisée pour identifier les enregistrements à récupérer.
     * @param whereFinding La valeur spécifique de la colonne 'where' utilisée pour filtrer les enregistrements.
     * @return String La valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     *                Retourne null si aucun enregistrement correspondant n'est trouvé ou en cas d'erreur SQL.
     *                Retourne la valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     */
    public String getString(String geting, String table_name, String where, String whereFinding) {
        try {
            final Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table_name + " WHERE " + where + " = ?");

            preparedStatement.setString(1, whereFinding);
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                String getted = resultSet.getString(geting);
                connection.close();
                return getted;
            }

            connection.close();
        } catch (SQLException event) {
            event.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * @param geting Le nom de la colonne dont la valeur est à récupérer.
     * @param table_name Le nom de la table dans la base de données à interroger.
     * @param where Le nom de la colonne utilisée pour identifier les enregistrements à récupérer.
     * @param whereFinding La valeur spécifique de la colonne 'where' utilisée pour filtrer les enregistrements.
     * @return boolean La valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     *                Retourne false si aucun enregistrement correspondant n'est trouvé ou en cas d'erreur SQL.
     *                Retourne la valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     */
    public boolean getBoolean(String geting, String table_name, String where, String whereFinding) {
        try {
            final Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table_name + " WHERE " + where + " = ?");

            preparedStatement.setString(1, whereFinding);
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                boolean getted = resultSet.getBoolean(geting);
                connection.close();
                return getted;
            }

            connection.close();
        } catch (SQLException event) {
            event.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param geting Le nom de la colonne dont la valeur est à récupérer.
     * @param table_name Le nom de la table dans la base de données à interroger.
     * @param where Le nom de la colonne utilisée pour identifier les enregistrements à récupérer.
     * @param whereFinding La valeur spécifique de la colonne 'where' utilisée pour filtrer les enregistrements.
     * @return int La valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     *                Retourne 0 si aucun enregistrement correspondant n'est trouvé ou en cas d'erreur SQL.
     *                Retourne la valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     */
    public int getInt(String geting, String table_name, String where, String whereFinding) {
        try {
            final Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table_name + " WHERE " + where + " = ?");

            preparedStatement.setString(1, whereFinding);
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                int getted = resultSet.getInt(geting);
                connection.close();
                return getted;
            }

            connection.close();
        } catch (SQLException event) {
            event.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * @param geting Le nom de la colonne dont la valeur est à récupérer.
     * @param table_name Le nom de la table dans la base de données à interroger.
     * @param where Le nom de la colonne utilisée pour identifier les enregistrements à récupérer.
     * @param whereFinding La valeur spécifique de la colonne 'where' utilisée pour filtrer les enregistrements.
     * @return float La valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     *                Retourne 0.0F si aucun enregistrement correspondant n'est trouvé ou en cas d'erreur SQL.
     *                Retourne la valeur de la colonne demandée pour le premier enregistrement correspondant aux critères de recherche.
     */
    public float getFloat(String geting, String table_name, String where, String whereFinding) {
        try {
            final Connection connection = DatabaseManager.EDMINE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table_name + " WHERE " + where + " = ?");

            preparedStatement.setString(1, whereFinding);
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                float getted = resultSet.getFloat(geting);
                connection.close();
                return getted;
            }

            connection.close();
        } catch (SQLException event) {
            event.printStackTrace();
            return 0.0F;
        }
        return 0.0F;
    }
}
