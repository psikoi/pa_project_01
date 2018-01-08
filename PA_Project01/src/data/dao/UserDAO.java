package data.dao;

import game.models.User;
import java.util.List;

/**
 * Interface containing the methods that the DAO variations will have to implement.
 * 
 * @author Ruben
 * @author Tiago
 */
public interface UserDAO {

    /**
     * Selects a list containing all users in file.
     * @return List containing all users in file.
     */
    List<User> selectAll();

    /**
     * Selects a specific user.
     * @param username - Searching criteria. Will be used to find a specific user.
     * @return user with the corresponding username.
     */
    User select(String username);

    /**
     * Inserts a new user in file.
     * @param user - user being inserted.
     * @return sucess of the operation.
     */
    boolean insert(User user);

    /**
     * Removes a specific user from the file.
     * @param username - Searching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean remove(String username);

    /**
     * Updates the password of a specific user.
     * @param username - Searching criteria. Will be used to find a specific user.
     * @param newPassword - new value of the users password.
     * @return sucess of the operation.
     */
    boolean updatePassword(String username, String newPassword);
    
    /**
     * Updates the email of a specific user.
     * @param username - Searching criteria. Will be used to find a specific user.
     * @param newEmail - new value of the users email.
     * @return sucess of the operation.
     */
    boolean updateEmail(String username, String newEmail);
    
    /**
     * Increments by 1 the number of games played in the easy difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addEasyGamePlayed(String username);
    
    /**
     * Increments by 1 the number of victories in the easy difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addEasyVictory(String username);
    
    /**
     * Increments by 1 the number of losses in the easy difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addEasyLoss(String username);
    
    /**
     * Increments the value of time played in the easy difficulty by the
     * user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @param time - value that will be added to the current value.
     * @return sucess of the operation.
     */
    boolean addEasyTimePlayed(String username, long time);
    
    /**
     * Increments by 1 the number of games played in the hard difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addHardGamePlayed(String username);
    
    /**
     * Increments by 1 the number of victories in the hard difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addHardVictory(String username);
    
    /**
     * Increments by 1 the number of losses in the easy difficulty by
     * a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addHardLoss(String username);
    
    /**
     * Increments the value of time played in the hard difficulty by the
     * user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @param time - value that will be added to the current value.
     * @return sucess of the operation.
     */
    boolean addHardTimePlayed(String username, long time);
    
    /**
     * Increments by 1 the number of games played in the player vs player
     * game mode by a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addPVPGamePlayed(String username);
    
    /**
     * Increments by 1 the number of victories in the player vs player
     * game mode by a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addPVPVictory(String username);
    
    /**
     * Increments by 1 the number of losses in the player vs player
     * game mode by a specific user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @return sucess of the operation.
     */
    boolean addPVPLoss(String username);
    
    /**
     * Increments the value of time played in the player vs player game mode
     * by the user.
     * @param username - Seaching criteria. Will be used to find a specific user.
     * @param time - value that will be added to the current value.
     * @return sucess of the operation.
     */
    boolean addPVPTimePlayed(String username, long time);

}