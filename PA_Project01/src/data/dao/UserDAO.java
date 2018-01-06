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
    
    boolean addGamePlayed(String username);
    
    boolean addVictory(String username);

}
