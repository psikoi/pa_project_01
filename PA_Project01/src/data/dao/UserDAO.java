/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import game.models.User;
import java.util.List;

/**
 *
 * @author Tiago
 */
public interface UserDAO {

    List<User> selectAll();

    User select(String username);

    boolean insert(User user);

    boolean remove(String username);

    boolean updatePassword(String username, String newPassword);
    
    boolean updateEmail(String username, String newEmail);
    
    boolean addEasyGamePlayed(String username);
    
    boolean addEasyVictory(String username);
    
    boolean addEasyLoss(String username);
    
    boolean addEasyTimePlayed(String username, long time);
    
    boolean addHardGamePlayed(String username);
    
    boolean addHardVictory(String username);
    
    boolean addHardLoss(String username);
    
    boolean addHardTimePlayed(String username, long time);
    
    boolean addPVPGamePlayed(String username);
    
    boolean addPVPVictory(String username);
    
    boolean addPVPLoss(String username);
    
    boolean addPVPTimePlayed(String username, long time);

}