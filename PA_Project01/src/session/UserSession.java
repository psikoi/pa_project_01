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
}