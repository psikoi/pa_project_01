package session;

import game.models.User;
import java.util.ArrayList;

/**
 * Manages the users sessions. 
 * 
 * @author Tiago
 * @author Ruben
 */
public class SessionManager {
    private static ArrayList<UserSession> userSessions;
    
    public static ArrayList<UserSession> getUserSessions(){
        if(userSessions == null)
            userSessions = new ArrayList<>();
        
        return userSessions;
    }
    
    /**
     * Returns an Array List containing the logged in users.
     * @return logged in users.
     */
    public static ArrayList<User> getLoggedInUsers(){
        
        
        ArrayList<User> users = new ArrayList<>();
        
        if(userSessions == null)
            return users;
        
        for(UserSession userSession : userSessions){
            users.add(userSession.getUser());
        }
        
        return users;
    }
    
    /**
     * Removes a session from the collection, logging out of the account
     * @param username - Username of the account.
     */
    public static void removeSession(String username){
        for(UserSession userSession : userSessions){
            if(userSession.getUser().getUsername().equalsIgnoreCase(username)){
                userSessions.remove(userSession);
            }
        }
    }
}