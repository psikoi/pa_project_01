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
    private static final String STATEMENT_SELECT_ALL_USER_INFORMATION = "SELECT username, password, email, gamesPlayed, totalVictories, bestScore FROM Users WHERE username = ?";
    private static final String STATEMENT_SELECT_ACCOUNT_INFORMATION = "SELECT username, password, email FROM Users WHERE usename = ?";
    private static final String STATEMENT_SELECT_ACCOUNT_STATISTICS = "SELECT gamesPlayed, totalVictories, bestScore FROM Users WHERE username = ?";
    private static final String STATEMENT_INSERT = "INSERT INTO Users(username, password, email, gamesPlayed, totalVictories, bestScore) VALUES(?,?,?,0,0,0)";
    private static final String STATEMENT_DELETE = "DELETE FROM Users WHERE username = ?";
    private static final String STATEMENT_UPDATE_PASSWORD = "UPDATE Users SET password = ? WHERE username = ?";
    private static final String STATEMENT_UPDATE_EMAIL = "UPDATE Users SET email = ? WHERE username = ?";
    private static final String STATEMENT_UPDATE_GAMES_PLAYED_INCREMENT_1 = "UPDATE Users SET gamesPlayed = gamesPlayed + 1 WHERE username = ?";
    private static final String STATEMENT_UPDATE_TOTAL_VICTORIES_INCREMENT_1 = "UPDATE Users SET totalVictories = totalVictories + 1 WHERE username = ?";

    public UserDAOSQLite() {
        strutureCreate();
    }

    @Override
    public List<User> selectAll() {
        List<User> list = new LinkedList<>();

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL)){ 
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                User user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                
                user.setGamesPlayed(rs.getInt("gamesPlayed"));
                user.setTotalVictories(rs.getInt("totalVictories"));
                user.setBestScore(rs.getInt("bestScore"));

                list.add(user);
            }


        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    @Override
    public User select(String username) {

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL_USER_INFORMATION)){

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            User user = new User(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
            );
            
            user.setGamesPlayed(rs.getInt("gamesPlayed"));
            user.setTotalVictories(rs.getInt("totalVictories"));
            user.setBestScore(rs.getInt("bestScore"));

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

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_INSERT)){

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

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_DELETE)){

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

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_PASSWORD)){
            
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

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EMAIL)){
            
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
    public boolean addGamePlayed(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_GAMES_PLAYED_INCREMENT_1)){
            
            pstmt.setString(1, username);

            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addVictory(String username) {
        if (select(username) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_TOTAL_VICTORIES_INCREMENT_1)){
            
            pstmt.setString(1, username);

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
                + "     gamesPlayed int NOT NULL,\n"
                + "     totalVictories int NOT NULL,\n"
                + "     bestScore int NOT NULL\n"
                + ");";

        try (Connection sqlConnection = connect(); Statement stmt = sqlConnection.createStatement()){

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
