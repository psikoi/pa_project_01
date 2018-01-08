/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao.sqlite;

import data.dao.UserDAO;
import game.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class UserDAOSQLite implements UserDAO {

    private static final String DB_FILE = "user.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    private static final String STATEMENT_SELECT_ALL = "SELECT * FROM Users";
    private static final String STATEMENT_SELECT_ALL_USER_INFORMATION = "SELECT username, password, email, "
            + "easyGamesPlayed, easyVictories, easyLosses, easyTimePlayed, "
            + "hardGamesPlayed, hardVictories, hardLosses, hardTimePlayed, "
            + "pvpGamesPlayed, pvpVictories, pvpLosses, pvpTimePlayed "
            + "FROM Users WHERE username = ?";
    private static final String STATEMENT_INSERT = "INSERT INTO Users(username, password, email, "
            + "easyGamesPlayed, easyVictories, easyLosses, easyTimePlayed, "
            + "hardGamesPlayed, hardVictories, hardLosses, hardTimePlayed, "
            + "pvpGamesPlayed, pvpVictories, pvpLosses, pvpTimePlayed) "
            + "VALUES(?,?,?,0,0,0,0,0,0,0,0,0,0,0,0)";
    private static final String STATEMENT_DELETE = "DELETE FROM Users WHERE username = ?";
    private static final String STATEMENT_UPDATE_PASSWORD = "UPDATE Users SET password = ? WHERE username = ?";
    private static final String STATEMENT_UPDATE_EMAIL = "UPDATE Users SET email = ? WHERE username = ?";

    private static final String STATEMENT_UPDATE_EASY_GAMES_PLAYED_INCREMENT_1 = "UPDATE Users SET easyGamesPlayed = easyGamesPlayed + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_EASY_VICTORIES_INCREMENT_1 = "UPDATE Users SET easyVictories = easyVictories + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_EASY_LOSSES_INCREMENT_1 = "UPDATE Users SET easyLosses = easyLosses + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_EASY_TIME_PLAYED_INCREMENT_X = "UPDATE Users SET easyTimePlayed = easyTimePlayed + ? WHERE username = ?";

    private static final String STATEMENT_UPDATE_HARD_GAMES_PLAYED_INCREMENT_1 = "UPDATE Users SET hardGamesPlayed = hardGamesPlayed + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_HARD_VICTORIES_INCREMENT_1 = "UPDATE Users SET hardVictories = hardVictories + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_HARD_LOSSES_INCREMENT_1 = "UPDATE Users SET hardLosses = hardLosses + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_HARD_TIME_PLAYED_INCREMENT_X = "UPDATE Users SET hardTimePlayed = hardTimePlayed + ? WHERE username = ?";
    
    private static final String STATEMENT_UPDATE_PVP_GAMES_PLAYED_INCREMENT_1 = "UPDATE Users SET pvpGamesPlayed = pvpGamesPlayed + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_PVP_VICTORIES_INCREMENT_1 = "UPDATE Users SET pvpVictories = pvpVictories + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_PVP_LOSSES_INCREMENT_1 = "UPDATE Users SET pvpLosses = pvpLosses + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_PVP_TIME_PLAYED_INCREMENT_X = "UPDATE Users SET pvpTimePlayed = pvpTimePlayed + ? WHERE username = ?";

    public UserDAOSQLite() {
        strutureCreate();
    }

    @Override
    public List<User> selectAll() {
        List<User> list = new LinkedList<>();

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                User user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );

                user.setEasyGamesPlayed(rs.getInt("easyGamesPlayed"));
                user.setEasyVictories(rs.getInt("easyVictories"));
                user.setEasyLosses(rs.getInt("easyLosses"));
                user.setEasyTimePlayed(rs.getLong("easyTimePlayed"));

                user.setHardGamesPlayed(rs.getInt("hardGamesPlayed"));
                user.setHardVictories(rs.getInt("hardVictories"));
                user.setHardLosses(rs.getInt("hardLosses"));
                user.setHardTimePlayed(rs.getLong("hardTimePlayed"));

                user.setPVPGamesPlayed(rs.getInt("pvpGamesPlayed"));
                user.setPVPVictories(rs.getInt("pvpVictories"));
                user.setPVPLosses(rs.getInt("pvpLosses"));
                user.setPVPTimePlayed(rs.getLong("pvpTimePlayed"));

                list.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    @Override
    public User select(String username) {

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL_USER_INFORMATION)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            User user = new User(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
            );

            user.setEasyGamesPlayed(rs.getInt("easyGamesPlayed"));
            user.setEasyVictories(rs.getInt("easyVictories"));
            user.setEasyLosses(rs.getInt("easyLosses"));
            user.setEasyTimePlayed(rs.getLong("easyTimePlayed"));

            user.setHardGamesPlayed(rs.getInt("hardGamesPlayed"));
            user.setHardVictories(rs.getInt("hardVictories"));
            user.setHardLosses(rs.getInt("hardLosses"));
            user.setHardTimePlayed(rs.getLong("hardTimePlayed"));

            user.setPVPGamesPlayed(rs.getInt("pvpGamesPlayed"));
            user.setPVPVictories(rs.getInt("pvpVictories"));
            user.setPVPLosses(rs.getInt("pvpLosses"));
            user.setPVPTimePlayed(rs.getLong("pvpTimePlayed"));

            return user;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    @Override
    public boolean insert(User user) {

        if (select(user.getUsername()) != null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_INSERT)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    @Override
    public boolean remove(String username) {

        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_DELETE)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PASSWORD)) {

            pstmt.setString(1, username);
            pstmt.setString(2, newPassword);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    @Override
    public boolean updateEmail(String username, String newEmail) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EMAIL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, newEmail);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addEasyGamePlayed(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_GAMES_PLAYED_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addEasyVictory(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_VICTORIES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addEasyLoss(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_LOSSES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addEasyTimePlayed(String username, long time) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_TIME_PLAYED_INCREMENT_X)) {

            pstmt.setLong(1, time);
            pstmt.setString(2, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addHardGamePlayed(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_GAMES_PLAYED_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addHardVictory(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_VICTORIES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addHardLoss(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_LOSSES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addHardTimePlayed(String username, long time) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_TIME_PLAYED_INCREMENT_X)) {

            pstmt.setLong(1, time);
            pstmt.setString(2, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addPVPGamePlayed(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PVP_GAMES_PLAYED_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addPVPVictory(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PVP_VICTORIES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addPVPLoss(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PVP_LOSSES_INCREMENT_1)) {

            pstmt.setString(1, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addPVPTimePlayed(String username, long time) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PVP_TIME_PLAYED_INCREMENT_X)) {

            pstmt.setLong(1, time);
            pstmt.setString(2, username);

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void strutureCreate() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "	username varchar(50) PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	email text NOT NULL,\n"
                + "     easyGamesPlayed int NOT NULL,\n"
                + "     easyVictories int NOT NULL,\n"
                + "     easyLosses int NOT NULL,\n"
                + "     easyTimePlayed int NOT NULL,\n"
                + "     hardGamesPlayed int NOT NULL,\n"
                + "     hardVictories int NOT NULL,\n"
                + "     hardLosses int NOT NULL,\n"
                + "     hardTimePlayed int NOT NULL,\n"
                + "     pvpGamesPlayed int NOT NULL,\n"
                + "     pvpVictories int NOT NULL,\n"
                + "     pvpLosses int NOT NULL,\n"
                + "     pvpTimePlayed int NOT NULL\n"
                + ");";

        try (Connection sqlConnection = connect(); Statement stmt = sqlConnection.createStatement()) {

            // create a new table
            stmt.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
