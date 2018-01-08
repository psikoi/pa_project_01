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
    private static final String STATEMENT_INSERT = "INSERT INTO Machine(name, easyGamesPlayed, easyVictories, easyLosses, easyTimePlayed, "
                                                                     + "hardGamesPlayed, hardVictories, hardLosses, hardTimePlayed) "
                                                                     + "VALUES(\"Machine\",0,0,0,0,0,0,0,0)";
    private static final String STATEMENT_UPDATE_EASY_GAMES_PLAYED_INCREMENT_1 = "UPDATE Machine SET easyGamesPlayed = easyGamesPlayed + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_EASY_VICTORIES_INCREMENT_1 = "UPDATE Machine SET easyVictories = easyVictories + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_EASY_LOSSES_INCREMENT_1 = "UPDATE Machine SET easyLosses = easyLosses + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_EASY_TIME_PLAYED_INCREMENT_X = "UPDATE Machine SET easyTimePlayed = easyTimePlayed + ? WHERE name = \"Machine\"";

    private static final String STATEMENT_UPDATE_HARD_GAMES_PLAYED_INCREMENT_1 = "UPDATE Machine SET hardGamesPlayed = hardGamesPlayed + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_HARD_VICTORIES_INCREMENT_1 = "UPDATE Machine SET hardVictories = hardVictories + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_HARD_LOSSES_INCREMENT_1 = "UPDATE Machine SET hardLosses = hardLosses + 1 WHERE name = \"Machine\"";
    private static final String STATEMENT_UPDATE_HARD_TIME_PLAYED_INCREMENT_X = "UPDATE Machine SET hardTimePlayed = hardTimePlayed + ? WHERE name = \"Machine\"";
    
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
            
            machine.setEasyGamesPlayed(rs.getInt("easyGamesPlayed"));
            machine.setEasyVictories(rs.getInt("easyVictories"));
            machine.setEasyLosses(rs.getInt("easyLosses"));
            machine.setEasyTimePlayed(rs.getLong("easyTimePlayed"));
            
            machine.setHardGamesPlayed(rs.getInt("hardGamesPlayed"));
            machine.setHardVictories(rs.getInt("hardVictories"));
            machine.setHardLosses(rs.getInt("hardLosses"));
            machine.setHardTimePlayed(rs.getLong("hardTimePlayed"));

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
    public boolean addEasyGamePlayed() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_GAMES_PLAYED_INCREMENT_1)){
            
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addEasyVictory() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_VICTORIES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addEasyLoss(){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_LOSSES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addEasyTimePlayed(long time){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_EASY_TIME_PLAYED_INCREMENT_X)){
           
            pstmt.setLong(1, time);
            
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addHardGamePlayed() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_GAMES_PLAYED_INCREMENT_1)){
            
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addHardVictory() {
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_VICTORIES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addHardLoss(){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_LOSSES_INCREMENT_1)){
           
            pstmt.execute();

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MachineDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean addHardTimePlayed(long time){
        if (select() == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_HARD_TIME_PLAYED_INCREMENT_X)){
           
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
                + "     easyGamesPlayed int NOT NULL,\n"
                + "     easyVictories int NOT NULL,"
                + "     easyLosses int NOT NULL,\n"
                + "     easyTimePlayed int NOT NULL,\n"
                + "     hardGamesPlayed int NOT NULL,\n"
                + "     hardVictories int NOT NULL,\n"
                + "     hardLosses int NOT NULL,\n"
                + "     hardTimePlayed int NOT NULL"
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