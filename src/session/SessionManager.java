/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import game.models.User;
import java.util.ArrayList;

/**
 *
 * @author Tiago
 */
public class SessionManager {
    private static ArrayList<UserSession> userSessions;
    
    public static ArrayList<UserSession> getUserSessions(){
        if(userSessions == null)
            userSessions = new ArrayList<>();
        
        return userSessions;
    }
    
    public static ArrayList<User> getLoggedInUsers(){
        ArrayList<User> users = new ArrayList<>();
        
        for(UserSession userSession : userSessions){
            users.add(userSession.getUser());
        }
        
        return users;
    }
    
    public static void removeSession(String username){
        for(UserSession userSession : userSessions){
            if(userSession.getUser().getUsername().equalsIgnoreCase(username)){
                userSessions.remove(userSession);
            }
        }
    }
}