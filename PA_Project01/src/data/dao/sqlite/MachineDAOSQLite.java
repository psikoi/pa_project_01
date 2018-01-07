/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao.sqlite;

import data.dao.MachineDAO;
import game.models.Machine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class MachineDAOSQLite implements MachineDAO{
    
    private static final String DB_FILE = "machine.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    private static final String STATEMENT_SELECT_ALL = "SELECT * FROM Machine";
    private static final String STATEMENT_INSERT = "INSERT INTO Machine(name, gamesPlayed, totalVictories, totalLosses, bestScore, timePlayed) VALUES(\"Machine\",0,0,0,0,0)";
    private static final String STATEMENT_UPDATE_GAMES_PLAYED_INCREMENT_1 = "UPDATE Machine SET gamesPlayed = gamesPlayed + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_TOTAL_VICTORIES_INCREMENT_1 = "UPDATE Machine SET totalVictories = totalVictories + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_TOTAL_LOSSES_INCREMENT_1 = "UPDATE Machine SET totalLosses = totalLosses + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_TIME_PLAYED_INCREMENT_X = "UPDATE Machine SET timePlayed = timePlayed + ? WHERE name = \"Machine\"";

    public MachineDAOSQLite() {
        strutureCreate();
    }

    @Override
    public Machine select() {

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL)){

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Machine machine = new Machine();
            
            machine.setGamesPlayed(rs.getInt("gamesPlayed"));
            machine.setTotalVictories(rs.getInt("totalVictories"));
            machine.setBestScore(rs.getInt("bestScore"));
            machine.setLosses(rs.getInt("totalLosses"));
            machine.setTimePlayed(rs.getLong("timePlayed"));

            return machine;

        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    @Override
    public boolean insert(Machine machine) {

        if (select() != null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_INSERT)){

            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;

    }

    @Override
    public boolean addGamePlayed() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_GAMES_PLAYED_INCREMENT_1)){
            
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addVictory() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_TOTAL_VICTORIES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addLoss(){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_TOTAL_LOSSES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addTimePlayed(long time){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_TIME_PLAYED_INCREMENT_X)){
           
            pstmt.setLong(1, time);
            
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void strutureCreate() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Machine (\n"
                + "	name varchar(50) PRIMARY KEY,\n"
                + "     gamesPlayed int NOT NULL,\n"
                + "     totalVictories int NOT NULL,"
                + "     totalLosses int NOT NULL,\n"
                + "     bestScore int NOT NULL,\n"
                + "     timePlayed int NOT NULL\n"
                + ");";

        try (Connection sqlConnection = connect(); Statement stmt = sqlConnection.createStatement()){

            // create a new table
            stmt.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
             Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}