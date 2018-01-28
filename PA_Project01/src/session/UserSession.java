package session;

import game.models.User;

/**
 * Represents a session.
 * 
 * @author Ruben
 * @author Tiago
 */
public class UserSession {
    
    private User user;
    
    public UserSession(User user){
        this.user = user;
    }
    
    public User getUser(){
        return user;
    }
    
    public boolean usernameMatches(String username){
        return username != null && this.user != null && username.equalsIgnoreCase(user.getPassword());
    }
}